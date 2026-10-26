package modelo;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Representa un entrenamiento de un ejercicio realizado por un usuario.
 * Conserva la fecha, la unidad de medida y los resultados de cada serie.
 */
public class RegistroFuerza {

    private final String id;
    private final String matricula;
    private final String ejercicioId;
    private final String nombreEjercicio;
    private final LocalDate fecha;
    private final String unidad;
    private final List<Serie> series;

    /**
     * Crea un registro y valida sus datos obligatorios.
     * Cada entrenamiento recibe un identificador propio.
     */
    public RegistroFuerza(
            String matricula,
            String ejercicioId,
            String nombreEjercicio,
            LocalDate fecha,
            String unidad,
            List<Serie> series) {

        validarTexto(matricula, "La matrícula");
        validarTexto(ejercicioId, "El identificador del ejercicio");
        validarTexto(nombreEjercicio, "El nombre del ejercicio");

        if (fecha == null || fecha.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "La fecha es obligatoria y no puede ser futura."
            );
        }

        if (!"repeticiones".equals(unidad)
                && !"segundos".equals(unidad)) {
            throw new IllegalArgumentException(
                    "La unidad debe ser repeticiones o segundos."
            );
        }

        if (series == null || series.isEmpty()) {
            throw new IllegalArgumentException(
                    "Debes registrar al menos una serie."
            );
        }

        for (Serie serie : series) {
            if (serie == null) {
                throw new IllegalArgumentException(
                        "Las series no pueden contener valores nulos."
                );
            }
        }

        this.id = UUID.randomUUID().toString();
        this.matricula = matricula;
        this.ejercicioId = ejercicioId;
        this.nombreEjercicio = nombreEjercicio;
        this.fecha = fecha;
        this.unidad = unidad;

        // Copia la lista para impedir modificaciones externas.
        this.series = List.copyOf(series);
    }

    /**
     * Comprueba que un dato de texto tenga contenido.
     */
    private static void validarTexto(String texto, String campo) {
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException(
                    campo + " no puede estar vacío."
            );
        }
    }

    /**
     * Devuelve el identificador único del entrenamiento.
     */
    public String getId() {
        return id;
    }

    /**
     * Devuelve la matrícula del propietario del registro.
     */
    public String getMatricula() {
        return matricula;
    }

    /**
     * Devuelve la clave del ejercicio utilizada para agrupar su historial.
     */
    public String getEjercicioId() {
        return ejercicioId;
    }

    /**
     * Devuelve el nombre que se mostrará en la ventana.
     */
    public String getNombreEjercicio() {
        return nombreEjercicio;
    }

    /**
     * Devuelve la fecha en que se realizó el entrenamiento.
     */
    public LocalDate getFecha() {
        return fecha;
    }

    /**
     * Indica si los resultados están expresados en repeticiones o segundos.
     */
    public String getUnidad() {
        return unidad;
    }

    /**
     * Devuelve las series en su orden de realización.
     * La lista no permite agregar, eliminar ni reemplazar elementos.
     */
    public List<Serie> getSeries() {
        return series;
    }

    /**
     * Obtiene la cantidad de series registradas.
     */
    public int getCantidadSeries() {
        return series.size();
    }

    /**
     * Representa el peso y el resultado de una serie.
     * Su posición en la lista determina si es la serie 1, 2, 3, etc.
     */
    public static class Serie {

        private final double peso;
        private final int cantidad;

        /**
         * Crea una serie con peso no negativo y cantidad positiva.
         * El valor 0 kg permite registrar ejercicios sin carga adicional.
         */
        public Serie(double peso, int cantidad) {
            if (!Double.isFinite(peso) || peso < 0) {
                throw new IllegalArgumentException(
                        "El peso debe ser un número válido mayor o igual a cero."
                );
            }

            if (cantidad <= 0) {
                throw new IllegalArgumentException(
                        "Las repeticiones o segundos deben ser mayores que cero."
                );
            }

            this.peso = peso;
            this.cantidad = cantidad;
        }

        /**
         * Devuelve el peso utilizado en kilogramos.
         */
        public double getPeso() {
            return peso;
        }

        /**
         * Devuelve las repeticiones o segundos realizados,
         * según la unidad definida en el entrenamiento.
         */
        public int getCantidad() {
            return cantidad;
        }
    }
}