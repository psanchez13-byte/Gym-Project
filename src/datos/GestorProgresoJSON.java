
package datos;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import modelo.HistorialProgreso;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase encargada de guardar y recuperar los historiales
 * de progreso de los usuarios mediante un archivo JSON.
 *
 * Su única responsabilidad es gestionar la persistencia.
 * No calcula diferencias de peso ni verifica plazos mensuales.
 */
public class GestorProgresoJSON {

    // Archivo donde se almacenarán los historiales de usuarios.
    private static final Path RUTA_ARCHIVO = Path.of("progreso.json");

    // Gson convierte los objetos Java a JSON y viceversa.
    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    // Indica a Gson el tipo de lista que debe recuperar.
    private final Type tipoLista = new TypeToken<List<HistorialGuardado>>() {}
            .getType();

    /**
     * Guarda el historial completo de un usuario.
     *
     * Si la matrícula ya existe, actualiza sus registros.
     * Si no existe, incorpora un nuevo historial.
     * Los historiales de otros usuarios se conservan.
     *
     * @param historial Historial que deseamos guardar.
     */
    public void guardarProgreso(HistorialProgreso historial) {

        if (historial == null) {
            throw new IllegalArgumentException(
                    "El historial no puede ser nulo."
            );
        }

        // Recuperamos los historiales almacenados.
        List<HistorialGuardado> historiales = leerArchivo();

        // Convertimos las mediciones a un formato apto para JSON.
        List<MedicionGuardada> mediciones = new ArrayList<>();

        for (HistorialProgreso.RegistroPeso registro : historial.getRegistros()) {
            mediciones.add(new MedicionGuardada(
                    registro.getFecha().toString(),
                    registro.getPeso()
            ));
        }

        HistorialGuardado nuevo = new HistorialGuardado(
                historial.getMatricula(), mediciones
        );

        boolean encontrado = false;

        // Buscamos si el usuario ya tiene un historial.
        for (int i = 0; i < historiales.size(); i++) {

            if (historiales.get(i).matricula.equals(historial.getMatricula())) {

                // Reemplazamos solamente el historial de ese usuario.
                historiales.set(i, nuevo);
                encontrado = true;
                break;
            }
        }

        // Si la matrícula no existe, añadimos el historial.
        if (!encontrado) {
            historiales.add(nuevo);
        }

        // Guardamos la lista actualizada en el archivo JSON.
        escribirArchivo(historiales);
    }

    /**
     * Recupera el historial de un usuario por su matrícula.
     *
     * Si no existen registros, devuelve un historial vacío.
     *
     * @param matricula Identificador del usuario.
     * @return HistorialProgreso con las mediciones recuperadas.
     */
    public HistorialProgreso leerProgreso(String matricula) {

        // Creamos el historial que devolveremos.
        HistorialProgreso historial = new HistorialProgreso(matricula);

        List<HistorialGuardado> historiales = leerArchivo();

        // Buscamos los registros correspondientes a la matrícula.
        for (HistorialGuardado guardado : historiales) {

            if (guardado.matricula.equals(matricula)) {

                // Restauramos las mediciones respetando sus fechas.
                for (MedicionGuardada medicion : guardado.registros) {

                    LocalDate fecha = LocalDate.parse(medicion.fecha);

                    historial.registrarPeso(
                            medicion.peso,
                            fecha
                    );
                }

                break;
            }
        }

        return historial;
    }

    /**
     * Comprueba si el usuario posee mediciones guardadas.
     *
     * @param matricula Identificador del usuario.
     * @return true si tiene registros, false si no tiene.
     */
    public boolean existeHistorial(String matricula) {

        HistorialProgreso historial = leerProgreso(matricula);

        return !historial.getRegistros().isEmpty();
    }

    /**
     * Lee todos los historiales desde el archivo JSON.
     *
     * Si el archivo todavía no existe, retorna una lista vacía.
     * Si ocurre un error de lectura, informa el problema
     * en lugar de sobrescribir accidentalmente los datos.
     */
    private List<HistorialGuardado> leerArchivo() {

        if (Files.notExists(RUTA_ARCHIVO)) {
            return new ArrayList<>();
        }

        try (Reader lector = Files.newBufferedReader(
                RUTA_ARCHIVO, StandardCharsets.UTF_8)) {

            List<HistorialGuardado> historiales =
                    gson.fromJson(lector, tipoLista);

            return historiales != null
                    ? historiales
                    : new ArrayList<>();

        } catch (IOException | JsonParseException e) {
            throw new IllegalStateException(
                    "Error al leer el historial JSON.", e
            );
        }
    }

    /**
     * Escribe todos los historiales en formato JSON.
     *
     * Utiliza un archivo temporal para reducir el riesgo
     * de perder información si falla la escritura.
     */
    private void escribirArchivo(List<HistorialGuardado> historiales) {

        Path temporal = Path.of("progreso.json.tmp");

        try {

            // Primero escribimos los datos en un archivo temporal.
            try (Writer escritor = Files.newBufferedWriter(
                    temporal, StandardCharsets.UTF_8)) {

                gson.toJson(historiales, escritor);
            }

            // Reemplazamos el archivo original al terminar.
            try {
                Files.move(
                        temporal,
                        RUTA_ARCHIVO,
                        StandardCopyOption.REPLACE_EXISTING,
                        StandardCopyOption.ATOMIC_MOVE
                );

            } catch (AtomicMoveNotSupportedException e) {

                // Alternativa para sistemas sin movimiento atómico.
                Files.move(
                        temporal,
                        RUTA_ARCHIVO,
                        StandardCopyOption.REPLACE_EXISTING
                );
            }

        } catch (IOException e) {
            throw new IllegalStateException(
                    "Error al guardar el historial JSON.", e
            );
        }
    }

    /**
     * Estructura auxiliar para representar un historial en JSON.
     *
     * Agrupa la matrícula con todas las mediciones del usuario.
     */
    private static class HistorialGuardado {

        String matricula;
        List<MedicionGuardada> registros;

        HistorialGuardado(String matricula, List<MedicionGuardada> registros) {
            this.matricula = matricula;
            this.registros = registros;
        }
    }

    /**
     * Estructura auxiliar para guardar una medición en JSON.
     *
     * La fecha se almacena como texto ISO (AAAA-MM-DD).
     * Así evitamos problemas al serializar LocalDate con Gson.
     */
    private static class MedicionGuardada {

        String fecha;
        double peso;

        MedicionGuardada(String fecha, double peso) {
            this.fecha = fecha;
            this.peso = peso;
        }
    }
}
