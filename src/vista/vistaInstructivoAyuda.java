package vista;

import javax.swing.*;
import java.awt.*;

public class vistaInstructivoAyuda extends JFrame{
    public void mostrarVentana() {
        setTitle("Instructivo de Ayuda ");
        setSize(400, 450); // Ancho de 400px y alto de 450px
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  ///DISPOSE cierra solo esta ventana, no todo el programa.
        setLayout(null); // pagamos el organizador para usar coordenadas
        setLocationRelativeTo(null);
        //JtextArea ideal para mostrar arto teto en lieas
        JTextArea textoAyuda = new JTextArea();
        textoAyuda.setText("¡Bienvenido a Campus-Fit!\n\n" +
                "1. Si eres nuevo, ve a Registro.\n" +
                "2. Inicia sesión con tu matrícula.\n" +
                "3. Realiza tu test físico para comenzar.");
        textoAyuda.setBounds(50, 50, 300, 200);
        textoAyuda.setFont(new Font("Arial", Font.PLAIN, 14));
        textoAyuda.setEditable(false); //  solo pueda leer, no borrar el texto
        textoAyuda.setBackground(null); // Quita el fondo blanco para que se mezcle con la ventana

        add(textoAyuda);
        setVisible(true);


    }
}
