
package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa una sesión de entrenamiento de Campus-Fit.
 *
 * Almacena la matrícula del usuario, la fecha de la sesión
 * y los ejercicios realizados durante el entrenamiento.
 *
 * Su responsabilidad es organizar los ejercicios de una sesión.
 * No genera rutinas ni guarda información en archivos JSON.
 */
public class SesionEntrenamiento {

    // Identificador del usuario que realizó el entrenamiento.
    private final String matricula;

    // Fecha en la que se realizó el entrenamiento.
    private final LocalDate fecha;

    // Lista de ejercicios realizados durante la sesión.
    private final List<Ejercicio> ejercicios;

    /**
     * Constructor de una sesión de entrenamiento.
     *
     * @param matricula Matrícula del usuario.
     * @param fecha Fecha del entrenamiento.
     */
    public SesionEntrenamiento(String matricula, LocalDate fecha) {

        // Validamos que exista una matrícula.
        if (matricula == null || matricula.isBlank()) {
            throw new IllegalArgumentException(
                    "La matrícula es obligatoria."
            );
        }

        // Validamos que la fecha exista y no sea futura.
        if (fecha == null || fecha.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "La fecha del entrenamiento no es válida."
            );
        }

        this.matricula = matricula.trim();
        this.fecha = fecha;

        // Inicializamos la lista de ejercicios.
        this.ejercicios = new ArrayList<>();
    }

    /**
     * Agrega un ejercicio realizado a la sesión.
     *
     * Comprueba que el ejercicio no sea nulo y que
     * no exista otro con el mismo nombre.
     *
     * @param ejercicio Ejercicio que deseamos registrar.
     */
    public void agregarEjercicio(Ejercicio ejercicio) {

        // Evitamos agregar ejercicios nulos.
        if (ejercicio == null) {
            throw new IllegalArgumentException(
                    "El ejercicio no puede ser nulo."
            );
        }

        // Evitamos registrar dos veces el mismo ejercicio.
        for (Ejercicio registrado : ejercicios) {

            if (registrado.getNombre().equalsIgnoreCase(
                    ejercicio.getNombre())) {

                throw new IllegalArgumentException(
                        "El ejercicio ya existe en esta sesión."
                );
            }
        }

        // Agregamos el ejercicio a la sesión.
        ejercicios.add(ejercicio);
    }

    /**
     * Obtiene los ejercicios realizados.
     *
     * Retorna una lista de solo lectura para impedir
     * que se agreguen o eliminen elementos directamente.
     *
     * @return Lista de ejercicios de la sesión.
     */
    public List<Ejercicio> getEjercicios() {
        return Collections.unmodifiableList(ejercicios);
    }

    /**
     * Obtiene la cantidad de ejercicios registrados.
     *
     * @return Número de ejercicios de la sesión.
     */
    public int getCantidadEjercicios() {
        return ejercicios.size();
    }

    // Obtiene la matrícula del usuario.
    public String getMatricula() {
        return matricula;
    }

    // Obtiene la fecha del entrenamiento.
    public LocalDate getFecha() {
        return fecha;
    }
}
