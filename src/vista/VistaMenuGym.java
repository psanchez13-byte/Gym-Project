package vista;

import javax.swing.*;
import java.awt.*;

public class VistaMenuGym extends JFrame {
    private JButton btnMiprogreso;
    private JButton btnPlanSemanal;
    private JButton btnCerrarSesion;

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

        JLabel logo = Herramientas.crearImagen("/Recursos/logo-gym2.png", 150, 80, 200, 150);
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
        VistaProgresoGym progreso = new VistaProgresoGym();
        progreso.mostrarventa(); // Asegúrate de que el método se llame así en tu otra clase
//
    }

    private void btnPlanSemanalAccion() {
        VistaGestorRutina gestor = new VistaGestorRutina();
        gestor.mostrarventa();
    }

    private void btnCerrarSesionAccion() {
        this.dispose(); // Destruye el menú actual (cierra tu sesión)
        new VistaLogin().setVisible(true); // Abre un login limpio para el siguiente usuario
    }



}
