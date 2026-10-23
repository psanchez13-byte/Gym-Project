package controlador;

import datos.GestorFuerzaJSON;
import datos.GestorRutinasCSV;
import modelo.LectorEjerciciosRutina;
import modelo.LectorEjerciciosRutina.EjercicioRutina;
import modelo.RegistroFuerza;
import modelo.Usuario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Coordina la consulta de ejercicios y el registro de entrenamientos.
 * Vincula los resultados con el usuario autenticado y su rutina.
 */
public class FuerzaControlador {

    private final Usuario usuario;
    private final GestorRutinasCSV gestorRutinas;
    private final LectorEjerciciosRutina lectorEjercicios;
    private final GestorFuerzaJSON gestorFuerza;

    /**
     * Vincula el controlador con el usuario autenticado
     * y prepara las clases de lectura y persistencia.
     */
    public FuerzaControlador(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException(
                    "Debe existir un usuario con sesión iniciada."
            );
        }

        if (usuario.getMatricula() == null
                || usuario.getMatricula().isBlank()) {
            throw new IllegalArgumentException(
                    "El usuario debe tener una matrícula válida."
            );
        }

        this.usuario = usuario;
        this.gestorRutinas = new GestorRutinasCSV();
        this.lectorEjercicios = new LectorEjerciciosRutina();
        this.gestorFuerza = new GestorFuerzaJSON();
    }

    /**
     * Recupera la rutina del usuario y extrae sus ejercicios.
     * Informa si no existe una rutina o no contiene ejercicios reconocibles.
     */
    public List<EjercicioRutina> obtenerEjerciciosAsignados() {
        String rutina = gestorRutinas.leerRutina(
                usuario.getMatricula()
        );

        if (rutina == null || rutina.isBlank()) {
            throw new IllegalStateException(
                    "Primero debes generar tu rutina en Gestor de Rutinas."
            );
        }

        List<EjercicioRutina> ejercicios =
                lectorEjercicios.extraerEjercicios(rutina);

        if (ejercicios.isEmpty()) {
            throw new IllegalStateException(
                    "No se encontraron ejercicios reconocibles "
                            + "en tu rutina guardada."
            );
        }

        return ejercicios;
    }

    /**
     * Valida las series realizadas y guarda un entrenamiento con la fecha actual.
     * Cada posición de las listas representa una misma serie.
     * Las cantidades corresponden a repeticiones o segundos según el ejercicio.
     */
    public RegistroFuerza guardarEntrenamiento(
            String ejercicioId,
            List<String> pesosTexto,
            List<String> cantidadesTexto) {

        // Recupera los datos del ejercicio desde la rutina asignada.
        EjercicioRutina ejercicio = buscarEjercicioAsignado(ejercicioId);

        validarCantidadSeries(
                ejercicio, pesosTexto, cantidadesTexto
        );

        List<RegistroFuerza.Serie> series = new ArrayList<>();

        // Valida todas las series antes de realizar cualquier guardado.
        for (int i = 0; i < pesosTexto.size(); i++) {
            int numeroSerie = i + 1;

            double peso = convertirPeso(
                    pesosTexto.get(i), numeroSerie
            );

            int cantidad = convertirCantidad(
                    cantidadesTexto.get(i),
                    numeroSerie,
                    ejercicio.getUnidad()
            );

            series.add(new RegistroFuerza.Serie(peso, cantidad));
        }

        RegistroFuerza registro = new RegistroFuerza(
                usuario.getMatricula(),
                ejercicio.getIdentificador(),
                ejercicio.getNombre(),
                LocalDate.now(),
                ejercicio.getUnidad(),
                series
        );

        // Si falla la escritura, el gestor informa el error a la vista.
        gestorFuerza.guardarRegistro(registro);

        return registro;
    }

    /**
     * Consulta el historial del ejercicio seleccionado para el usuario actual.
     * Devuelve los entrenamientos ordenados desde la fecha más reciente.
     */
    public List<RegistroFuerza> obtenerHistorial(String ejercicioId) {
        EjercicioRutina ejercicio = buscarEjercicioAsignado(ejercicioId);

        return gestorFuerza.leerHistorial(
                usuario.getMatricula(),
                ejercicio.getIdentificador()
        );
    }

    /**
     * Busca un ejercicio por su identificador dentro de la rutina del usuario.
     * Impide registrar resultados para un ejercicio que no tenga asignado.
     */
    private EjercicioRutina buscarEjercicioAsignado(String ejercicioId) {
        if (ejercicioId == null || ejercicioId.isBlank()) {
            throw new IllegalArgumentException(
                    "Debes seleccionar un ejercicio."
            );
        }

        for (EjercicioRutina ejercicio : obtenerEjerciciosAsignados()) {
            if (ejercicio.getIdentificador().equals(ejercicioId)) {
                return ejercicio;
            }
        }

        throw new IllegalArgumentException(
                "El ejercicio seleccionado no pertenece a tu rutina actual."
        );
    }

    /**
     * Comprueba que las listas tengan una entrada por cada serie asignada.
     */
    private void validarCantidadSeries(
            EjercicioRutina ejercicio,
            List<String> pesos,
            List<String> cantidades) {

        if (pesos == null || cantidades == null) {
            throw new IllegalArgumentException(
                    "Debes ingresar los datos de las series."
            );
        }

        int seriesEsperadas = ejercicio.getSeries();

        if (seriesEsperadas <= 0) {
            throw new IllegalStateException(
                    "El ejercicio no tiene una cantidad válida de series."
            );
        }

        if (pesos.size() != seriesEsperadas
                || cantidades.size() != seriesEsperadas) {
            throw new IllegalArgumentException(
                    "Debes completar las "
                            + seriesEsperadas
                            + " series indicadas en la rutina."
            );
        }
    }

    /**
     * Convierte el peso a un número decimal y acepta coma o punto decimal.
     * Permite 0 kg para ejercicios sin carga adicional.
     */
    private double convertirPeso(String texto, int numeroSerie) {
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException(
                    "Ingresa el peso de la serie " + numeroSerie + "."
            );
        }

        double peso;

        try {
            peso = Double.parseDouble(
                    texto.trim().replace(',', '.')
            );
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException(
                    "El peso de la serie " + numeroSerie
                            + " debe ser numérico. Por ejemplo: 20,5."
            );
        }

        if (!Double.isFinite(peso) || peso < 0) {
            throw new IllegalArgumentException(
                    "El peso de la serie " + numeroSerie
                            + " debe ser válido y mayor o igual a cero."
            );
        }

        return peso;
    }

    /**
     * Convierte las repeticiones o segundos a un número entero positivo.
     * El resultado real puede ser distinto del objetivo de la rutina.
     */
    private int convertirCantidad(
            String texto,
            int numeroSerie,
            String unidad) {

        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException(
                    "Completa el campo de " + unidad
                            + " de la serie " + numeroSerie + "."
            );
        }

        int cantidad;

        try {
            cantidad = Integer.parseInt(texto.trim());
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException(
                    "El campo de " + unidad
                            + " de la serie " + numeroSerie
                            + " debe contener un número entero."
            );
        }

        if (cantidad <= 0) {
            throw new IllegalArgumentException(
                    "El campo de " + unidad
                            + " de la serie " + numeroSerie
                            + " debe ser mayor que cero."
            );
        }

        return cantidad;
    }
}