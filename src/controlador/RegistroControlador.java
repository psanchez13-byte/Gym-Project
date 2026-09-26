package controlador;
import datos.GestorUsuariosCSV;
import modelo.Usuario;

public class RegistroControlador {

    public boolean procesarRegistro(String nombre, String matricula, String contrasena) {
        GestorUsuariosCSV gestor = new GestorUsuariosCSV();
        Usuario nuevoUsuario = new Usuario(nombre, matricula, contrasena);
        // 2. Creamos el objeto Usuario con los datos iniciales
        gestor.guardarUsuarios(nuevoUsuario);
        return true;


    }
}