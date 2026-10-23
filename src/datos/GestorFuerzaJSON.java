package datos;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import modelo.RegistroFuerza;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Guarda y recupera los entrenamientos de fuerza en un archivo JSON.
 * Conserva los registros de todos los usuarios y permite consultar
 * el historial de un ejercicio específico.
 */
public class GestorFuerzaJSON {

    private static final Path RUTA_ARCHIVO = Path.of("fuerza.json");

    private static final Type TIPO_REGISTROS =
            new TypeToken<List<RegistroFuerza>>() {}.getType();

    private static final Type TIPO_SERIES =
            new TypeToken<List<RegistroFuerza.Serie>>() {}.getType();

    private final Gson gson;

    /**
     * Configura Gson para manejar fechas y recuperar series validadas.
     */
    public GestorFuerzaJSON() {
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new AdaptadorFecha())
                .registerTypeAdapter(TIPO_SERIES, new AdaptadorSeries())
                .create();
    }

    /**
     * Añade un entrenamiento sin borrar los registros anteriores.
     * Rechaza un identificador que ya esté guardado.
     */
    public synchronized void guardarRegistro(RegistroFuerza registro) {
        validarRegistro(registro);

        List<RegistroFuerza> registros = leerArchivo();

        for (RegistroFuerza existente : registros) {
            if (existente.getId().equals(registro.getId())) {
                throw new IllegalArgumentException(
                        "Este registro ya está guardado."
                );
            }
        }

        registros.add(registro);
        escribirArchivo(registros);
    }

    /**
     * Recupera solamente los entrenamientos del usuario y ejercicio indicados.
     * Ordena los resultados desde la fecha más reciente.
     */
    public List<RegistroFuerza> leerHistorial(
            String matricula,
            String ejercicioId) {

        if (matricula == null || matricula.isBlank()
                || ejercicioId == null || ejercicioId.isBlank()) {
            throw new IllegalArgumentException(
                    "La matrícula y el identificador del ejercicio son obligatorios."
            );
        }

        List<RegistroFuerza> historial = new ArrayList<>();

        for (RegistroFuerza registro : leerArchivo()) {
            if (registro.getMatricula().equals(matricula)
                    && registro.getEjercicioId().equals(ejercicioId)) {
                historial.add(registro);
            }
        }

        historial.sort(
                Comparator.comparing(RegistroFuerza::getFecha).reversed()
        );

        return List.copyOf(historial);
    }

    /**
     * Lee y valida todos los registros del archivo.
     * Si todavía no existe, devuelve una lista vacía.
     * Un archivo inválido produce un error para evitar sobrescribirlo.
     */
    private List<RegistroFuerza> leerArchivo() {
        try (Reader lector = Files.newBufferedReader(
                RUTA_ARCHIVO, StandardCharsets.UTF_8)) {

            List<RegistroFuerza> registros =
                    gson.fromJson(lector, TIPO_REGISTROS);

            if (registros == null) {
                throw new IllegalArgumentException(
                        "El archivo debe contener una lista de registros."
                );
            }

            Set<String> identificadores = new HashSet<>();

            for (RegistroFuerza registro : registros) {
                validarRegistro(registro);

                if (!identificadores.add(registro.getId())) {
                    throw new IllegalArgumentException(
                            "El archivo contiene identificadores duplicados."
                    );
                }
            }

            return new ArrayList<>(registros);

        } catch (NoSuchFileException ex) {
            return new ArrayList<>();

        } catch (IOException | RuntimeException ex) {
            throw new IllegalStateException(
                    "No se pudo leer el historial de fuerza. "
                            + "Revisa fuerza.json.",
                    ex
            );
        }
    }

    /**
     * Comprueba que un registro tenga los datos requeridos por el modelo.
     * Esta validación también se aplica a los objetos recuperados por Gson.
     */
    private void validarRegistro(RegistroFuerza registro) {
        if (registro == null
                || registro.getId() == null
                || registro.getId().isBlank()) {
            throw new IllegalArgumentException(
                    "El registro debe tener un identificador válido."
            );
        }

        // Reutiliza las validaciones del modelo sin reemplazar el registro
        // ni cambiar su identificador original.
        new RegistroFuerza(
                registro.getMatricula(),
                registro.getEjercicioId(),
                registro.getNombreEjercicio(),
                registro.getFecha(),
                registro.getUnidad(),
                registro.getSeries()
        );

        for (RegistroFuerza.Serie serie : registro.getSeries()) {
            new RegistroFuerza.Serie(
                    serie.getPeso(),
                    serie.getCantidad()
            );
        }
    }

    /**
     * Escribe primero en un archivo temporal y luego reemplaza el original.
     * Informa los fallos de escritura para que la vista no anuncie un éxito falso.
     */
    private void escribirArchivo(List<RegistroFuerza> registros) {
        Path temporal = null;

        try {
            Path destino = RUTA_ARCHIVO.toAbsolutePath();

            temporal = Files.createTempFile(
                    destino.getParent(),
                    "fuerza-",
                    ".tmp"
            );

            try (Writer escritor = Files.newBufferedWriter(
                    temporal, StandardCharsets.UTF_8)) {
                gson.toJson(registros, TIPO_REGISTROS, escritor);
            }

            try {
                Files.move(
                        temporal,
                        destino,
                        StandardCopyOption.REPLACE_EXISTING,
                        StandardCopyOption.ATOMIC_MOVE
                );
            } catch (AtomicMoveNotSupportedException ex) {
                Files.move(
                        temporal,
                        destino,
                        StandardCopyOption.REPLACE_EXISTING
                );
            }

        } catch (IOException | RuntimeException ex) {
            throw new IllegalStateException(
                    "No se pudo guardar el entrenamiento de fuerza.",
                    ex
            );

        } finally {
            // Elimina el temporal si quedó pendiente después de un error.
            if (temporal != null) {
                try {
                    Files.deleteIfExists(temporal);
                } catch (IOException ex) {
                    // La limpieza no debe ocultar el error original.
                }
            }
        }
    }

    /**
     * Convierte LocalDate a texto y viceversa para evitar
     * que Gson intente acceder a los campos internos de Java.
     */
    private static class AdaptadorFecha
            implements JsonSerializer<LocalDate>,
            JsonDeserializer<LocalDate> {

        /**
         * Guarda la fecha en formato AAAA-MM-DD.
         */
        @Override
        public JsonElement serialize(
                LocalDate fecha,
                Type tipo,
                JsonSerializationContext contexto) {

            return new JsonPrimitive(fecha.toString());
        }

        /**
         * Recupera una fecha desde su representación en texto.
         */
        @Override
        public LocalDate deserialize(
                JsonElement elemento,
                Type tipo,
                JsonDeserializationContext contexto) {

            return LocalDate.parse(elemento.getAsString());
        }
    }

    /**
     * Reconstruye las series utilizando su constructor para validar
     * los valores y mantener la lista protegida contra modificaciones.
     */
    private static class AdaptadorSeries
            implements JsonDeserializer<List<RegistroFuerza.Serie>> {

        /**
         * Convierte cada elemento JSON en una serie validada
         * y devuelve una lista que no permite modificaciones.
         */
        @Override
        public List<RegistroFuerza.Serie> deserialize(
                JsonElement elemento,
                Type tipo,
                JsonDeserializationContext contexto) {

            List<RegistroFuerza.Serie> series = new ArrayList<>();

            for (JsonElement item : elemento.getAsJsonArray()) {
                JsonObject datos = item.getAsJsonObject();

                double peso = datos.get("peso")
                        .getAsBigDecimal().doubleValue();

                int cantidad = datos.get("cantidad")
                        .getAsBigDecimal().intValueExact();

                series.add(new RegistroFuerza.Serie(peso, cantidad));
            }

            return List.copyOf(series);
        }
    }
}