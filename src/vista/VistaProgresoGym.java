package vista;

import modelo.Usuario;

import javax.swing.*;
import java.awt.*;

/**
 * Presenta los accesos al seguimiento de peso y fuerza.
 * Comparte el usuario autenticado con las ventanas de progreso.
 */
public class VistaProgresoGym extends JFrame {

    private final Usuario usuarioActual;
       private JButton btnCerrar;
       private JButton btnFuerza;
       private JButton btnmipeso;
    /**
     * Conserva al usuario cuyos datos se consultarán.
     */
    public VistaProgresoGym(Usuario usuarioActual) {
        if (usuarioActual == null) {
            throw new IllegalArgumentException(
                    "Debe existir un usuario con sesión iniciada."
            );
        }

        this.usuarioActual = usuarioActual;
    }

    public void mostrarventa() {
        setTitle("Mi progreso - Campus-Fit");
        setSize(500, 550); // Mantenemos el estándar de "Talla M"
        setResizable(false);
        setLayout(null);

        setLocationRelativeTo(null); // Centrado en la pantalla

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);

        Color azulCampus = new Color(20, 70, 160);
        JPanel barraSuperior = Herramientas.crearPanel(azulCampus, 0, 0, 500, 60);

        JLabel lblTitulo = Herramientas.crearLabels(18, "Mi progreso ", 100, 10, 300, 40);
        lblTitulo.setForeground(Color.WHITE); // Letras blancas
        barraSuperior.add(lblTitulo);
        add(barraSuperior);
        JLabel logo = Herramientas.crearImagen("/Recursos/imagen_coach.png", 140, 59, 250, 240);
        add(logo);

        JPanel panelBotones = new JPanel(new GridLayout(2, 1, 0, 15));
        panelBotones.setOpaque(false);
        panelBotones.setPreferredSize(new Dimension(300, 110));


        btnFuerza = Herramientas.crearBoton("Mi fuerza y progreso", 150, 260, 200, 40, e -> btnevoFuerza());
        btnmipeso = Herramientas.crearBoton("Mi peso y progreso", 150, 320, 200, 40, e -> btnmipeso());
        btnCerrar = Herramientas.crearBoton("Salir", 150, 380, 200, 40, e -> dispose());
        add(btnCerrar);
        add(btnmipeso);
        add(btnFuerza);

        setVisible(true);
    }

    /**
     * Abre el registro e historial de peso del usuario.
     */
    public void btnmipeso() {
        VistaMiPeso ventanaPeso = new VistaMiPeso(usuarioActual);
        ventanaPeso.mostrarVentana();
    }

    /**
     * Abre la selección de ejercicios de la rutina del usuario.
     */
    public void btnevoFuerza() {
        VistaMiFuerza ventanaFuerza =
                new VistaMiFuerza(usuarioActual);

        ventanaFuerza.mostrarVentana();
    }
}
