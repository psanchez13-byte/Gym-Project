package controlador;

import datos.GestorRutinasCSV;
import modelo.LectorEjerciciosRutina;
import modelo.LectorEjerciciosRutina.EjercicioRutina;
import modelo.Usuario;

import java.util.List;

/**
 * Coordina la consulta de los ejercicios asignados al usuario.
 * Entrega a la vista los datos necesarios para registrar su fuerza.
 */
public class FuerzaControlador {

    private final Usuario usuario;
    private final GestorRutinasCSV gestorRutinas;
    private final LectorEjerciciosRutina lectorEjercicios;

    /**
     * Vincula el controlador con el usuario autenticado
     * y prepara las clases que recuperan e interpretan su rutina.
     */
    public FuerzaControlador(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException(
                    "Debe existir un usuario con sesión iniciada."
            );
        }

        if (usuario.getMatricula() == null
                || usuario.getMatricula().isBlank()) {
            throw new IllegalArgumentException(
                    "El usuario debe tener una matrícula válida."
            );
        }

        this.usuario = usuario;
        this.gestorRutinas = new GestorRutinasCSV();
        this.lectorEjercicios = new LectorEjerciciosRutina();
    }

    /**
     * Recupera la rutina del usuario y extrae sus ejercicios.
     * Informa si todavía no existe una rutina o no se puede interpretar.
     */
    public List<EjercicioRutina> obtenerEjerciciosAsignados() {
        // Consulta únicamente la rutina de la matrícula autenticada.
        String rutina = gestorRutinas.leerRutina(
                usuario.getMatricula()
        );

        if (rutina == null || rutina.isBlank()) {
            throw new IllegalStateException(
                    "Primero debes generar tu rutina en Gestor de Rutinas."
            );
        }

        // Convierte el texto guardado en una lista de ejercicios.
        List<EjercicioRutina> ejercicios =
                lectorEjercicios.extraerEjercicios(rutina);

        if (ejercicios.isEmpty()) {
            throw new IllegalStateException(
                    "No se encontraron ejercicios reconocibles "
                            + "en tu rutina guardada."
            );
        }

        return ejercicios;
    }
}
