package vista;

import controlador.TestFisicoControlador;
import modelo.Usuario;
import javax.swing.*;
import java.awt.*;


/**
 * Presenta las opciones principales al usuario autenticado.
 * Permite acceder al progreso, gestionar la rutina y cerrar la sesión.
 */

public class VistaMenuGym extends JFrame {
    private JButton btnMiprogreso;
    private JButton btnPlanSemanal;
    private JButton btnCerrarSesion;
    private Usuario usuarioActual;


    // Conserva al usuario que inició sesión para consultar sus datos.
    public void setUsuarioActual(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
    }

    public void mostrarVentanaGym(){

        setTitle("Menú Principal - Campus Fit");
        setSize(500, 550); // Mantenemos el estándar de "Talla M"
        setResizable(false);
        setLayout(null);

        setLocationRelativeTo(null); // Centrado en la pantalla

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);

        Color azulCampus = new Color(20, 70, 160);
        JPanel barraSuperior = Herramientas.crearPanel(azulCampus, 0, 0, 500, 60);

        JLabel lblTitulo = Herramientas.crearLabels(18, "Panel de Control", 100, 10, 300, 40);
        lblTitulo.setForeground(Color.WHITE); // Letras blancas
        barraSuperior.add(lblTitulo);
        add(barraSuperior);

        JLabel logo = Herramientas.crearImagen("/Recursos/logo-gym1.png", 115, 50, 290, 280);
        add(logo);

        btnMiprogreso = Herramientas.crearBoton("Mi Progreso", 150, 260, 200, 40, e -> btnMiprogresoAccion());
        btnPlanSemanal = Herramientas.crearBoton("Gestor de Rutinas", 150, 320, 200, 40, e -> btnPlanSemanalAccion());
        btnCerrarSesion = Herramientas.crearBoton("Cerrar Sesión", 150, 380, 200, 40, e -> btnCerrarSesionAccion());

        add(btnMiprogreso);
        add(btnPlanSemanal);
        add(btnCerrarSesion);

        setVisible(true);
    }
    private void btnMiprogresoAccion() {
        // En un futuro, el controlador inyectará los datos del usuario aquí
        VistaProgresoGym progreso = new VistaProgresoGym(usuarioActual);
        progreso.mostrarventa(); // Asegúrate de que el método se llame así en la otra clase
//
    }


    // Solicita el test si está pendiente y abre el gestor solo al completarlo.
// Si el usuario ya tiene un nivel reconocido, accede directamente al gestor.

    private void btnPlanSemanalAccion() {
        TestFisicoControlador testControlador =
                new TestFisicoControlador(usuarioActual);

        // Si falta el test, abre la ventana y espera a que se cierre.
        if (!testControlador.tieneTestRealizado()) {
            VistaTestFisico ventanaTest =
                    new VistaTestFisico(this, testControlador);

            ventanaTest.setVisible(true);
        }

        // Si cerró el test sin completarlo, permanece en el menú.
        if (!testControlador.tieneTestRealizado()) {
            return;
        }

        // Con el test completo, permite consultar o generar la rutina.
        VistaGestorRutina gestor = new VistaGestorRutina(usuarioActual);
        gestor.mostrarventa();
    }

    private void btnCerrarSesionAccion() {
        this.dispose(); // Destruye el menú actual (cierra tu sesión)
        new VistaLogin().setVisible(true); // Abre un login limpio para el siguiente usuario
    }



}
