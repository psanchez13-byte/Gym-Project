package vista;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Herramientas {


    public static JLabel crearLabels(int letra, String texto, int x, int y, int largo, int alto){
        JLabel label = new JLabel(texto);
        label.setBounds(x, y, largo, alto);
        label.setFont(new Font("Arial", Font.BOLD, letra));
        return label;
    }
//
//    public static JTextArea crearCuadrosDeTexto(int x, int y, int largo , int alto, String texto){
//        JTextArea textA = new JTextArea();
//        textA.setBounds(x, y, largo, alto);
//        textA.setText(texto);
//        textA.setFont(new Font("Arial", Font.PLAIN, alto));
//        textA.setEditable(false); //  solo pueda leer, no borrar el texto
//        textA.setBackground(null); // Quita el fondo blanco para que se mezcle con la ventana
//        return textA;
//    }

    public static JTextField crearTexto(int x,int y, int largo, int alto){
        JTextField txt = new JTextField();
        txt.setBounds(x, y, largo, alto);
        txt.setHorizontalAlignment(JTextField.CENTER);
        return txt;
    }

    public static JPasswordField crearTextoContraseña(int x, int y,int largo, int alto){
        JPasswordField clave = new JPasswordField();
        clave.setBounds(x, y, largo, alto);
        clave.setHorizontalAlignment(JPasswordField.CENTER);
        return clave;
    }
    public static JButton crearBoton(String Texto, int x, int y, int largo, int alto, ActionListener accion) {
        JButton boton = new JButton(Texto);
        boton.setBounds(x, y, largo, alto);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setFocusPainted(false);
        boton.addActionListener(accion);
        return boton;
    }

    public static JPanel crearPanel(Color color, int x, int y, int ancho, int alto) {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(color);
        panel.setBounds(x, y, ancho, alto);
        return panel;
    }


}
