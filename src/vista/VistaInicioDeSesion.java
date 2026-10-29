package vista;

import controlador.loginControlador;
import modelo.Usuario;

import javax.swing.*;
import java.awt.*;

public class VistaInicioDeSesion extends JFrame {

    private JTextField cajamatricula;
    private JPasswordField cajacontraseña;
    private JButton btnconfirmar;
    private final loginControlador controlador = new loginControlador();

    public void mostrarVentaSesion() {
        setTitle("Iniciando sesión ¡Hola de nuevo!");
        setSize(500, 500);
        setResizable(false);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);

        JLabel logo = Herramientas.crearImagen("/Recursos/logo-gym2.png", 160, 50, 170, 175);
        add(logo);

        JLabel titulo = new JLabel("Iniciar sesión", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setBounds(153, 22, 190, 30);
        add(titulo);



        JLabel lblMatricula = new JLabel("Matrícula:");
        lblMatricula.setBounds(150, 200, 200, 20);
        add(lblMatricula);

        cajamatricula = new JTextField();
        cajamatricula.setBounds(150, 220, 200, 30);
        add(cajamatricula);

        JLabel lblContra = new JLabel("Contraseña:");
        lblContra.setBounds(150, 270, 200, 20);
        add(lblContra);

        cajacontraseña = new JPasswordField();
        cajacontraseña.setBounds(150, 290, 200, 30);
        add(cajacontraseña);

        btnconfirmar = Herramientas.crearBoton("Confirmar", 150, 360, 200, 40, e -> iniciarSesion());

        // Permite iniciar sesión al presionar Enter.
        getRootPane().setDefaultButton(btnconfirmar);
        setVisible(true);
    }

    // Recoge las credenciales y solicita su validación al controlador.
    private void iniciarSesion() {
        String matricula = cajamatricula.getText().trim();
        String contraseña = new String(cajacontraseña.getPassword());

        boolean adminVerificacion = controlador.iniciarAdmin(matricula,contraseña);
        if (adminVerificacion){
            LoginAdminVentana ventanaAdmin = new LoginAdminVentana();
            ventanaAdmin.menuAdmin();
            return;
        }
            if (matricula.isEmpty() || contraseña.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Por favor, complete todos los campos.",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {
            Usuario usuario = controlador.iniciarSesion(matricula, contraseña);

            if (usuario == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "Matrícula o contraseña incorrectas.",
                        "Error de inicio de sesión",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            // Entrega al menú el usuario autenticado.
            VistaMenuGym menuGym = new VistaMenuGym();
            menuGym.setUsuarioActual(usuario);
            menuGym.mostrarVentanaGym();

            // Cierra la ventana de inicio de sesión.
            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Hay datos numéricos inválidos en usuarios.csv.",
                    "Error al cargar usuarios",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}