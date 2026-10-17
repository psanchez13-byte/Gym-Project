
package modelo;

import java.time.LocalDate;

/**
 * Clase encargada de controlar el seguimiento mensual
 * de las mediciones de peso de los usuarios.
 *
 * Determina cuándo corresponde realizar una nueva
 * medición y permite registrarla si se cumple el plazo.
 *
 * No almacena directamente los datos del historial.
 */
public class SeguimientoMensual {

    /**
     * Comprueba si corresponde registrar una nueva medición.
     *
     * Si el usuario no tiene registros, permite ingresar
     * su primera medición.
     *
     * Si tiene registros, comprueba que haya transcurrido
     * un mes calendario desde el último.
     *
     * @param historial Historial del usuario.
     * @param fechaActual Fecha que se desea comprobar.
     * @return true si puede registrar, false en caso contrario.
     */
    public boolean correspondeRegistrarPeso(
            HistorialProgreso historial, LocalDate fechaActual) {

        // Verificamos que los parámetros sean válidos.
        if (historial == null || fechaActual == null) {
            throw new IllegalArgumentException(
                    "El historial y la fecha son obligatorios."
            );
        }

        // Obtenemos la última fecha almacenada.
        LocalDate ultimaFecha = historial.getUltimaFecha();

        // Si todavía no hay mediciones, permitimos la primera.
        if (ultimaFecha == null) {
            return true;
        }

        // Calculamos cuándo corresponde la siguiente medición.
        LocalDate proximaFecha = ultimaFecha.plusMonths(1);

        // Comprobamos si ya se cumplió el plazo.
        return !fechaActual.isBefore(proximaFecha);
    }

    /**
     * Registra una medición solamente cuando corresponde.
     *
     * Delega el almacenamiento a HistorialProgreso,
     * evitando duplicar la lógica de los registros.
     *
     * @param historial Historial del usuario.
     * @param peso Peso informado por el usuario.
     * @param fecha Fecha de la medición.
     * @return true si se registró, false si aún no corresponde.
     */
    public boolean registrarPesoMensual(
            HistorialProgreso historial,
            double peso,
            LocalDate fecha) {

        // Revisamos si ya transcurrió el tiempo necesario.
        if (!correspondeRegistrarPeso(historial, fecha)) {
            return false;
        }

        // El historial valida y almacena la nueva medición.
        historial.registrarPeso(peso, fecha);

        return true;
    }
}
