
package modelo;

/**
 * Representa una serie realizada durante un entrenamiento.
 *
 * Cada serie almacena su número, el peso utilizado
 * y las repeticiones completadas por el usuario.
 *
 * Esta clase solo representa los datos de una serie.
 * No administra ejercicios ni guarda archivos.
 */
public class SerieEntrenamiento {

    // Datos correspondientes a una serie realizada.
    private final int numeroSerie;
    private final double pesoKg;
    private final int repeticiones;

    /**
     * Constructor que inicializa una serie de entrenamiento.
     *
     * @param numeroSerie Número de la serie realizada.
     * @param pesoKg Peso utilizado en kilogramos.
     * @param repeticiones Cantidad de repeticiones completadas.
     */
    public SerieEntrenamiento(int numeroSerie, double pesoKg, int repeticiones) {

        // El número de serie debe comenzar desde 1.
        if (numeroSerie <= 0) {
            throw new IllegalArgumentException(
                    "El número de serie debe ser mayor que cero."
            );
        }

        // El peso debe ser válido y no puede ser negativo.
        // Se permite 0 kg para ejercicios con peso corporal.
        if (!Double.isFinite(pesoKg) || pesoKg < 0) {
            throw new IllegalArgumentException(
                    "El peso ingresado no es válido."
            );
        }

        // Evitamos registrar cantidades negativas.
        if (repeticiones < 0) {
            throw new IllegalArgumentException(
                    "Las repeticiones no pueden ser negativas."
            );
        }

        // Asignamos los valores a los atributos.
        this.numeroSerie = numeroSerie;
        this.pesoKg = pesoKg;
        this.repeticiones = repeticiones;
    }

    /**
     * Obtiene el número de la serie.
     */
    public int getNumeroSerie() {
        return numeroSerie;
    }

    /**
     * Obtiene el peso utilizado en la serie.
     */
    public double getPesoKg() {
        return pesoKg;
    }

    /**
     * Obtiene las repeticiones completadas.
     */
    public int getRepeticiones() {
        return repeticiones;
    }
}
