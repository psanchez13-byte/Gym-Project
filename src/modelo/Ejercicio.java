
package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa un ejercicio realizado durante una sesión
 *
 * Su responsabilidad es almacenar el nombre del ejercicio
 * y administrar las series realizadas por el usuario.
 *
 * Cada serie contiene su peso y cantidad de repeticiones,
 * utilizando la clase SerieEntrenamiento.
 */
public class Ejercicio {

    // Nombre del ejercicio realizado.
    private final String nombre;

    // Lista que almacena las series del ejercicio.
    private final List<SerieEntrenamiento> series;

    /**
     * Constructor de un ejercicio.
     *
     * Inicializa el nombre y crea una lista vacía
     * para almacenar las series del entrenamiento.
     *
     * @param nombre Nombre del ejercicio.
     */
    public Ejercicio(String nombre) {

        // Validamos que el ejercicio tenga un nombre válido.
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre del ejercicio es obligatorio."
            );
        }

        this.nombre = nombre.trim();
        this.series = new ArrayList<>();
    }

    /**
     * Agrega una nueva serie al ejercicio.
     *
     * Verifica que la serie exista y que su número
     * no esté registrado previamente.
     *
     * @param serie Serie que deseamos agregar.
     */
    public void agregarSerie(SerieEntrenamiento serie) {

        // Evitamos agregar objetos nulos.
        if (serie == null) {
            throw new IllegalArgumentException(
                    "La serie no puede ser nula."
            );
        }

        // Comprobamos que el número de serie no esté repetido.
        for (SerieEntrenamiento registrada : series) {

            if (registrada.getNumeroSerie()
                    == serie.getNumeroSerie()) {

                throw new IllegalArgumentException(
                        "El número de serie ya está registrado."
                );
            }
        }

        // Agregamos la nueva serie a la lista.
        series.add(serie);
    }

    /**
     * Obtiene el nombre del ejercicio.
     *
     * @return Nombre del ejercicio.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Devuelve las series registradas en el ejercicio.
     *
     * Se entrega una lista de solo lectura para evitar
     * modificaciones externas sobre la lista original.
     *
     * @return Lista de series registradas.
     */
    public List<SerieEntrenamiento> getSeries() {
        return Collections.unmodifiableList(series);
    }

    /**
     * Obtiene la cantidad de series registradas.
     *
     * @return Número total de series del ejercicio.
     */
    public int getCantidadSeries() {
        return series.size();
    }
}
