package controlador;

import datos.GestorUsuariosCSV;
import modelo.Usuario;
import java.util.List;

public class AdminUsuariosControlador {
    private GestorUsuariosCSV gestorCSV;

    public AdminUsuariosControlador(){
        this.gestorCSV = new GestorUsuariosCSV();
    }

    public List<Usuario> obtenerTodosLosUsuarios() {
        return gestorCSV.leerUsuarios();
    }

    // --- ELIMINAR ---
    //(true si lo logró, false si falló)
    public boolean eliminarUsuario(String matricula) {
        List<Usuario> usuarios = gestorCSV.leerUsuarios();
        boolean encontrado = false;

        // Busca el usuario por matricula y lo removemos de la lista en memoria
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getMatricula().equals(matricula)) {
                usuarios.remove(i);
                encontrado = true;
                break;
            }
        }
        
        if (encontrado) {
            gestorCSV.guardarListaCompletaCSV(usuarios);
            return true;
        }
        return false;
    }
    // --- BUSCAR/REVISAR ---
    // Busca un usuario especifico por su matrícula para revisar sus detalles
    public Usuario buscarUsuario(String matricula) {
        List<Usuario> usuarios = gestorCSV.leerUsuarios();

        for (Usuario u : usuarios) {
            if (u.getMatricula().equals(matricula)) {
                return u;
            }
        }

        return null;
    }
}