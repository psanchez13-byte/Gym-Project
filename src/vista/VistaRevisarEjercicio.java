package vista;

import javax.swing.*;
import java.awt.*;

public class VistaRevisarEjercicio extends JFrame {
    public void mostrarVentana(){
        setTitle("Menú De administración - Ejercicios Disponibles ");
        setSize(500, 550);
        setResizable(false);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        Color negroAdmin = new Color(30, 30, 30); // Gris muy oscuro
        Color doradoAdmin = new Color(212, 175, 55); // Tono oro elegante

        //. Fondo de la ventana oscuro
        getContentPane().setBackground(negroAdmin);
        setVisible(true);
        JPanel barraSuperior = Herramientas.crearPanel(doradoAdmin, 0, 0, 500, 60);
        JLabel lblTitulo = Herramientas.crearLabels(18, "Panel de Gestion de ejercicios ", 120, 10, 300, 40);
        lblTitulo.setForeground(Color.BLACK);
        barraSuperior.add(lblTitulo);
        add(barraSuperior);
    }

}
