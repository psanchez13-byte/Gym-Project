
package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Clase encargada de administrar el historial de peso de un usuario.
 *
 * Permite almacenar mediciones, consultar registros anteriores
 * y calcular la diferencia entre las últimas dos mediciones.
 *
 * La frecuencia de los registros será controlada por
 * la clase SeguimientoMensual.
 */
public class HistorialProgreso {

    // Matrícula que identifica al propietario del historial.
    private final String matricula;

    // Lista que almacena los registros de peso del usuario.
    private final List<RegistroPeso> registros = new ArrayList<>();

    /**
     * Constructor del historial de progreso.
     *
     * @param matricula Identificador del usuario.
     */
    public HistorialProgreso(String matricula) {
        if (matricula == null || matricula.isBlank()) {
            throw new IllegalArgumentException(
                    "La matrícula no puede estar vacía."
            );
        }

        this.matricula = matricula;
    }

    /**
     * Guarda una nueva medición de peso.
     *
     * Verifica que el peso sea válido y que las fechas
     * mantengan un orden cronológico.
     *
     * @param peso Peso registrado en kilogramos.
     * @param fecha Fecha de la medición.
     */
    public void registrarPeso(double peso, LocalDate fecha) {

        // Validamos que el peso sea positivo y finito.
        if (!Double.isFinite(peso) || peso <= 0) {
            throw new IllegalArgumentException(
                    "El peso debe ser válido y positivo."
            );
        }

        // Evitamos fechas nulas o futuras.
        if (fecha == null || fecha.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "La fecha ingresada no es válida."
            );
        }

        // Evitamos registros duplicados o fuera de orden.
        LocalDate ultimaFecha = getUltimaFecha();

        if (ultimaFecha != null && !fecha.isAfter(ultimaFecha)) {
            throw new IllegalArgumentException(
                    "La fecha debe ser posterior al último registro."
            );
        }

        // Incorporamos la medición al historial.
        registros.add(new RegistroPeso(fecha, peso));
    }

    /**
     * Calcula la diferencia entre las dos últimas mediciones.
     *
     * @return Diferencia de peso o null si no hay
     * suficientes registros para comparar.
     */
    public Double calcularDiferencia() {

        // Necesitamos al menos dos registros para comparar.
        if (registros.size() < 2) {
            return null;
        }

        int ultimo = registros.size() - 1;

        double pesoActual = registros.get(ultimo).getPeso();
        double pesoAnterior = registros.get(ultimo - 1).getPeso();

        return pesoActual - pesoAnterior;
    }

    /**
     * Obtiene la fecha de la última medición registrada.
     *
     * @return Última fecha o null si no existen registros.
     */
    public LocalDate getUltimaFecha() {

        if (registros.isEmpty()) {
            return null;
        }

        return registros.get(registros.size() - 1).getFecha();
    }

    // Devuelve la matrícula del usuario.
    public String getMatricula() {
        return matricula;
    }

    /**
     * Permite consultar las mediciones sin modificar
     * directamente la lista interna.
     */
    public List<RegistroPeso> getRegistros() {
        return Collections.unmodifiableList(registros);
    }

    /**
     * Representa una medición individual de peso.
     *
     * Cada registro almacena la fecha y el peso.
     */
    public static class RegistroPeso {

        private final LocalDate fecha;
        private final double peso;

        public RegistroPeso(LocalDate fecha, double peso) {
            this.fecha = fecha;
            this.peso = peso;
        }

        public LocalDate getFecha() {
            return fecha;
        }

        public double getPeso() {
            return peso;
        }
    }
}
