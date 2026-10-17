package vista;
import javax.swing.*;
import java.awt.Color;

public class VistaLogin extends JFrame {

    public VistaLogin(){
        setTitle("Campus-Fit ");
        setSize(500, 500);// Ancho de 400px y alto de 450px
        setResizable(false); // Impide que la ventana se pueda maximizar o achicar
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);//desactivar organizador automatico
        setLocationRelativeTo(null);//aparecer en el centro de la pantalla
        getContentPane().setBackground(Color.WHITE);

        Color azulCampus = new Color(20, 70, 160);
        Color blancoTexto = new Color(255, 255, 255);
        JPanel barraSuperior = Herramientas.crearPanel(azulCampus, 0, 0, 500, 60);

        JLabel lblTitulo = Herramientas.crearLabels(18, "Bienvenido a campus fit", 135, 20, 300, 40);
        lblTitulo.setForeground(blancoTexto);
        barraSuperior.add(lblTitulo);
        JLabel logo = Herramientas.crearImagen("/Recursos/logo-gym1.png", 110, 59, 286, 286);

        JButton btnInicio = Herramientas.crearBoton("Inicio de sesión", 150, 280, 200, 40, e-> btnInicio());
        JButton btnRegistro = Herramientas.crearBoton("Registro", 150, 340, 200, 40,e-> btnRegistro() );
        JButton btnAyuda = Herramientas.crearBoton("Instructivo de ayuda", 150, 400, 200, 40, e -> btnInstructivo());

        add(barraSuperior);
        add(btnInicio);
        add(btnRegistro);
        add(btnAyuda);
        add(logo);

        setVisible(true);

    }
    public void btnInstructivo(){
       vistaInstructivoAyuda btnAyuda = new vistaInstructivoAyuda();
       btnAyuda.mostrarVentana();
    }

    public void btnInicio(){
        VistaInicioDeSesion btnSesion = new VistaInicioDeSesion();
        btnSesion.mostrarventaSesion();
    }

    public void btnRegistro(){
       VistaRegistro btnRegistro = new VistaRegistro();
       btnRegistro.mostrarVentanaRegistro();


    }

}
