package vista;

import modelo.Usuario;
import javax.swing.*;




public class VistaProgresoGym extends JFrame {

    private final Usuario usuarioActual;

    public VistaProgresoGym(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
    }

    public void mostrarventa(){
        setTitle("Bienvenido a mi Progreso ");
        setResizable(false);
        setSize(500, 500);
        setLayout(null);
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

    public void btnmipeso() {
        VistaMiPeso ventanaPeso = new VistaMiPeso(usuarioActual);
        ventanaPeso.mostrarVentana();
    }

    public void btnevoFuerza(){
        VistaMiFuerza btnFuerza =new VistaMiFuerza();
        btnFuerza.mostrarVentana();

    }
}
