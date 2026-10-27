package vista;
import javax.swing.*;
import java.awt.*;

public class LoginAdminVentana extends JFrame {

    private JButton btnRevisarUsuario;
    private JButton btnEditarEjercicio;
    private JButton btnCerrarSesion;

    public void menuAdmin (){
        setTitle("Menú De administración - Campus Fit");
        setSize(500, 550);
        setResizable(false);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // centrado

        Color negroAdmin = new Color(30, 30, 30); // Gris muy oscuro
        Color doradoAdmin = new Color(212, 175, 55); // Tono oro elegante

        //. Fondo de la ventana oscuro
        getContentPane().setBackground(negroAdmin);

        // 3. Barra Superior Dorada
        JPanel barraSuperior = Herramientas.crearPanel(doradoAdmin, 0, 0, 500, 60);
        JLabel lblTitulo = Herramientas.crearLabels(18, "Panel de Administración", 120, 10, 300, 40);
        lblTitulo.setForeground(Color.BLACK); // Texto negro sobre barra dorada
        barraSuperior.add(lblTitulo);
        add(barraSuperior);

        //  el logo del gym aquí en Y: 80

        btnRevisarUsuario = Herramientas.crearBoton("Revicion de Usuarios ", 150, 260, 200, 40, e -> btnRevisarUsuarioAccion());
        btnEditarEjercicio = Herramientas.crearBoton("Editar Ejercicios", 150, 320, 200, 40, e -> btnEditarEjercicioAccion());
        btnCerrarSesion = Herramientas.crearBoton("Cerrar Sesión", 150, 380, 200, 40, e -> btnCerrarSesionAccion());

        add(btnRevisarUsuario);
        add(btnEditarEjercicio);
        add(btnCerrarSesion);

        setVisible(true);
    }
    public void btnRevisarUsuarioAccion (){
        VistaAdminUsuarios vistaadmin = new VistaAdminUsuarios();
        vistaadmin.mostrarVentana();
    }

    public void btnEditarEjercicioAccion(){
        VistaRevisarEjercicio vistaejercicio = new VistaRevisarEjercicio();
        vistaejercicio.mostrarVentana();
    }

    public void btnCerrarSesionAccion(){
        this.dispose(); // Destruye el menú actual
        new VistaLogin().setVisible(true); // Abre un login limpio para el siguiente usuario
    }
}
