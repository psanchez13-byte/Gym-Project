
package datos;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import modelo.Ejercicio;
import modelo.SerieEntrenamiento;
import modelo.SesionEntrenamiento;

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
 * Gestiona el almacenamiento de las sesiones de entrenamiento
 * de Campus-Fit utilizando un archivo JSON.
 *
 * Permite guardar sesiones completas y recuperar el historial
 * de entrenamientos de un usuario mediante su matrícula.
 *
 * No genera rutinas ni analiza el rendimiento físico.
 */
public class GestorEntrenamientosJSON {

    // Archivo donde se almacenarán los entrenamientos.
    private static final Path RUTA_ARCHIVO =
            Path.of("entrenamientos.json");

    // Biblioteca encargada de convertir objetos a formato JSON.
    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    // Tipo utilizado para recuperar la lista desde Gson.
    private final Type tipoLista =
            new TypeToken<List<SesionGuardada>>() {}.getType();

    /**
     * Guarda una nueva sesión de entrenamiento.
     *
     * Cada sesión se agrega al historial, sin eliminar
     * las sesiones anteriores del mismo usuario.
     *
     * @param sesion Sesión que deseamos almacenar.
     */
    public void guardarSesion(SesionEntrenamiento sesion) {

        if (sesion == null) {
            throw new IllegalArgumentException(
                    "La sesión no puede ser nula."
            );
        }

        // Recuperamos las sesiones guardadas anteriormente.
        List<SesionGuardada> sesiones = leerArchivo();

        // Convertimos la sesión a una estructura compatible con JSON.
        List<EjercicioGuardado> ejercicios = new ArrayList<>();

        for (Ejercicio ejercicio : sesion.getEjercicios()) {

            List<SerieGuardada> series = new ArrayList<>();

            for (SerieEntrenamiento serie : ejercicio.getSeries()) {

                series.add(new SerieGuardada(
                        serie.getNumeroSerie(),
                        serie.getPesoKg(),
                        serie.getRepeticiones()
                ));
            }

            ejercicios.add(new EjercicioGuardado(
                    ejercicio.getNombre(),
                    series
            ));
        }

        // Agregamos la sesión con su matrícula y fecha.
        sesiones.add(new SesionGuardada(
                sesion.getMatricula(),
                sesion.getFecha().toString(),
                ejercicios
        ));

        // Guardamos todas las sesiones en el JSON.
        escribirArchivo(sesiones);
    }

    /**
     * Recupera las sesiones de un usuario.
     *
     * Busca las sesiones que coincidan con su matrícula
     * y reconstruye los objetos originales de Java.
     *
     * @param matricula Identificador del usuario.
     * @return Lista de sesiones de entrenamiento.
     */
    public List<SesionEntrenamiento> leerSesiones(String matricula) {

        if (matricula == null || matricula.isBlank()) {
            throw new IllegalArgumentException(
                    "La matrícula es obligatoria."
            );
        }

        List<SesionEntrenamiento> resultado = new ArrayList<>();

        for (SesionGuardada guardada : leerArchivo()) {

            try {
                // Solo recuperamos las sesiones del usuario solicitado.
                if (!guardada.matricula.equals(matricula.trim())) {
                    continue;
                }

                SesionEntrenamiento sesion =
                        new SesionEntrenamiento(
                                guardada.matricula,
                                LocalDate.parse(guardada.fecha)
                        );

                // Reconstruimos los ejercicios guardados.
                for (EjercicioGuardado ejercicioGuardado :
                        guardada.ejercicios) {

                    Ejercicio ejercicio =
                            new Ejercicio(ejercicioGuardado.nombre);

                    // Reconstruimos cada serie realizada.
                    for (SerieGuardada serie : ejercicioGuardado.series) {

                        ejercicio.agregarSerie(
                                new SerieEntrenamiento(
                                        serie.numeroSerie,
                                        serie.pesoKg,
                                        serie.repeticiones
                                )
                        );
                    }

                    sesion.agregarEjercicio(ejercicio);
                }

                resultado.add(sesion);

            } catch (RuntimeException e) {
                throw new IllegalStateException(
                        "El archivo contiene datos de entrenamiento inválidos.",
                        e
                );
            }
        }

        return resultado;
    }

    /**
     * Lee las sesiones almacenadas en entrenamientos.json.
     *
     * Si el archivo todavía no existe, devuelve una lista vacía.
     * Los errores de lectura se informan para evitar pérdida de datos.
     */
    private List<SesionGuardada> leerArchivo() {

        if (Files.notExists(RUTA_ARCHIVO)) {
            return new ArrayList<>();
        }

        try (Reader lector = Files.newBufferedReader(
                RUTA_ARCHIVO, StandardCharsets.UTF_8)) {

            List<SesionGuardada> sesiones =
                    gson.fromJson(lector, tipoLista);

            // Un archivo existente debe contener un arreglo JSON.
            if (sesiones == null) {
                throw new IllegalStateException(
                        "El archivo JSON está vacío. Debe contener []."
                );
            }

            return sesiones;

        } catch (IOException | JsonParseException e) {
            throw new IllegalStateException(
                    "Error al leer los entrenamientos.", e
            );
        }
    }

    /**
     * Guarda la lista completa de sesiones.
     *
     * Primero escribe en un archivo temporal y luego
     * reemplaza el original para reducir el riesgo
     * de pérdida de información durante la escritura.
     */
    private void escribirArchivo(List<SesionGuardada> sesiones) {

        Path temporal = null;

        try {
            Path destino = RUTA_ARCHIVO.toAbsolutePath();

            // Creamos un archivo temporal en el mismo directorio.
            temporal = Files.createTempFile(
                    destino.getParent(),
                    "entrenamientos-",
                    ".tmp"
            );

            try (Writer escritor = Files.newBufferedWriter(
                    temporal, StandardCharsets.UTF_8)) {

                gson.toJson(sesiones, tipoLista, escritor);
            }

            // Intentamos reemplazar el archivo de forma atómica.
            try {
                Files.move(
                        temporal,
                        destino,
                        StandardCopyOption.REPLACE_EXISTING,
                        StandardCopyOption.ATOMIC_MOVE
                );

            } catch (AtomicMoveNotSupportedException e) {

                Files.move(
                        temporal,
                        destino,
                        StandardCopyOption.REPLACE_EXISTING
                );
            }

        } catch (IOException e) {
            throw new IllegalStateException(
                    "Error al guardar los entrenamientos.", e
            );

        } finally {

            // Limpiamos el archivo temporal si quedó pendiente.
            if (temporal != null) {
                try {
                    Files.deleteIfExists(temporal);
                } catch (IOException ignored) {
                    // La limpieza no altera los datos originales.
                }
            }
        }
    }

    /**
     * Estructura utilizada para representar una sesión en JSON.
     */
    private static class SesionGuardada {

        String matricula;
        String fecha;
        List<EjercicioGuardado> ejercicios;

        SesionGuardada(String matricula, String fecha,
                       List<EjercicioGuardado> ejercicios) {

            this.matricula = matricula;
            this.fecha = fecha;
            this.ejercicios = ejercicios;
        }
    }

    /**
     * Estructura utilizada para representar un ejercicio en JSON.
     */
    private static class EjercicioGuardado {

        String nombre;
        List<SerieGuardada> series;

        EjercicioGuardado(String nombre, List<SerieGuardada> series) {
            this.nombre = nombre;
            this.series = series;
        }
    }

    /**
     * Estructura utilizada para representar una serie en JSON.
     */
    private static class SerieGuardada {

        int numeroSerie;
        double pesoKg;
        int repeticiones;

        SerieGuardada(int numeroSerie, double pesoKg, int repeticiones) {
            this.numeroSerie = numeroSerie;
            this.pesoKg = pesoKg;
            this.repeticiones = repeticiones;
        }
    }
}
