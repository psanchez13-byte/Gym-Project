package modelo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Extrae los ejercicios del texto de una rutina.
 * Conserva sus series, objetivo y unidad de medida.
 */
public class LectorEjerciciosRutina {

    // Reconoce ejercicios expresados en repeticiones o segundos.
    private static final Pattern FORMATO_EJERCICIO = Pattern.compile(
            "^(.+?)(?:\\s+-\\s+|:\\s*)"
                    + "(\\d+)\\s+series\\s+x\\s+"
                    + "(\\d+(?:\\s*-\\s*\\d+)?)\\s+"
                    + "(repeticiones|segundos)\\b.*$",
            Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE
    );

    /**
     * Convierte la rutina en una lista sin ejercicios duplicados.
     * Omite encabezados de días, grupos musculares y líneas vacías.
     */
    public List<EjercicioRutina> extraerEjercicios(String rutina) {
        Map<String, EjercicioRutina> ejercicios = new LinkedHashMap<>();

        if (rutina == null || rutina.isBlank()) {
            return new ArrayList<>();
        }

        // Acepta el texto leído del CSV o el que todavía contiene @@.
        String texto = rutina.replace("@@", "\n");

        for (String linea : texto.split("\\R")) {
            String contenido = linea.trim();

            // El bloque de abdomen utiliza un guion al inicio.
            contenido = contenido.replaceFirst("^-\\s+", "");

            Matcher coincidencia = FORMATO_EJERCICIO.matcher(contenido);

            if (!coincidencia.matches()) {
                continue;
            }

            String nombre = limpiarEspacios(coincidencia.group(1));
            int series = Integer.parseInt(coincidencia.group(2));
            String objetivo = coincidencia.group(3).replaceAll("\\s+", "");
            String unidad = coincidencia.group(4).toLowerCase(Locale.ROOT);

            String identificador = normalizarNombre(nombre);

            EjercicioRutina ejercicio = new EjercicioRutina(
                    identificador, nombre, series, objetivo, unidad
            );

            // Mantiene la primera aparición de cada ejercicio.
            ejercicios.putIfAbsent(identificador, ejercicio);
        }

        return new ArrayList<>(ejercicios.values());
    }

    // Genera una clave consistente para comparar nombres.
    private String normalizarNombre(String nombre) {
        return limpiarEspacios(nombre).toLowerCase(Locale.ROOT);
    }

    // Elimina espacios iniciales, finales y repetidos.
    private String limpiarEspacios(String texto) {
        return texto.trim().replaceAll("\\s+", " ");
    }

    /**
     * Representa los datos de un ejercicio asignado.
     * No contiene los resultados registrados por el usuario.
     */
    public static class EjercicioRutina {

        private final String identificador;
        private final String nombre;
        private final int series;
        private final String objetivo;
        private final String unidad;

        public EjercicioRutina(
                String identificador,
                String nombre,
                int series,
                String objetivo,
                String unidad) {

            this.identificador = identificador;
            this.nombre = nombre;
            this.series = series;
            this.objetivo = objetivo;
            this.unidad = unidad;
        }

        public String getIdentificador() {
            return identificador;
        }

        public String getNombre() {
            return nombre;
        }

        public int getSeries() {
            return series;
        }

        public String getObjetivo() {
            return objetivo;
        }

        public String getUnidad() {
            return unidad;
        }

        public boolean esPorTiempo() {
            return "segundos".equals(unidad);
        }

        // El desplegable mostrará el nombre del ejercicio.
        @Override
        public String toString() {
            return nombre;
        }
    }
}