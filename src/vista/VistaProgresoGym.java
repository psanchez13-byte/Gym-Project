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

    /**
     * Construye y muestra el menú de progreso del usuario.
     */
    public void mostrarventa() {
        setTitle("Mi progreso - Campus-Fit");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel contenido = new JPanel(new BorderLayout(15, 25));
        contenido.setBackground(Color.WHITE);
        contenido.setBorder(
                BorderFactory.createEmptyBorder(25, 35, 25, 35)
        );
        setContentPane(contenido);

        JLabel titulo = new JLabel(
                "Mi progreso", SwingConstants.CENTER
        );
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setForeground(new Color(20, 70, 160));
        contenido.add(titulo, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel(new GridLayout(2, 1, 0, 15));
        panelBotones.setOpaque(false);
        panelBotones.setPreferredSize(new Dimension(300, 110));

        JButton btnPeso = new JButton("Mi peso y progreso");
        btnPeso.addActionListener(e -> btnmipeso());
        panelBotones.add(btnPeso);

        JButton btnFuerza = new JButton("Mi fuerza y progreso");
        btnFuerza.addActionListener(e -> btnevoFuerza());
        panelBotones.add(btnFuerza);

        contenido.add(panelBotones, BorderLayout.CENTER);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        contenido.add(btnCerrar, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel logo = Herramientas.crearImagen("/Recursos/imagen_coach.png", 140, 50, 250, 260);
        add(logo);


        JButton btnmipeso = Herramientas.crearBoton("Mi peso y progreso", 150, 280, 200, 40, e-> btnmipeso());
        JButton btnevoFuerza = Herramientas.crearBoton("Registrar evolucion de mi fuerza", 150, 340, 200, 40,e-> btnevoFuerza() );

        add(btnevoFuerza);
        add(btnmipeso);



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
