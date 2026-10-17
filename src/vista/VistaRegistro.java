package vista;
import controlador.RegistroControlador;

import javax.swing.*;
import java.awt.*;

public class VistaRegistro extends JFrame {

    private JTextField cajauser;
    private JTextField cajamatricula;
    private JTextField cajacontraseña;
    private JTextField cajaEdad;
    private JTextField cajaAltura;
    private JTextField cajaPeso;
    private JComboBox<String> comboSexo;

    public void mostrarVentanaRegistro(){
        setTitle("Regitro de usuarios nuevos ");
        setSize(400, 650);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        getContentPane().setBackground(Color.WHITE);

        JLabel titulo = new JLabel("Bienvenido", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setBounds(100, 20, 200, 30);
        add(titulo);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(100, 70, 200, 20);
        add(lblNombre);
        cajauser = new JTextField();
        cajauser.setBounds(100, 90, 200, 30);
        add(cajauser);

        JLabel lblEdad = new JLabel("Edad:");
        lblEdad.setBounds(100, 130, 200, 20);
        add(lblEdad);
        cajaEdad = new JTextField();
        cajaEdad.setBounds(100, 150, 200, 30);
        add(cajaEdad);

        JLabel lblAltura = new JLabel("Altura (cm):");
        lblAltura.setBounds(100, 190, 200, 20);
        add(lblAltura);
        cajaAltura = new JTextField();
        cajaAltura.setBounds(100, 210, 200, 30);
        add(cajaAltura);

        // Peso (Y: 250 y 270)
        JLabel lblPeso = new JLabel("Peso (kg):");
        lblPeso.setBounds(100, 250, 200, 20);
        add(lblPeso);
        cajaPeso = new JTextField();
        cajaPeso.setBounds(100, 270, 200, 30);
        add(cajaPeso);

        JLabel lblSexo = new JLabel("Sexo:");
        lblSexo.setBounds(100, 310, 200, 20);
        add(lblSexo);
        String[] opcionesSexo = {"Seleccione", "M", "F"};
        comboSexo = new JComboBox<>(opcionesSexo);
        comboSexo.setBounds(100, 330, 200, 30);
        comboSexo.setBackground(Color.WHITE);
        add(comboSexo);

        JLabel lblMatricula = new JLabel("Matrícula:");
        lblMatricula.setBounds(100, 370, 200, 20);
        add(lblMatricula);
        cajamatricula = new JTextField();
        cajamatricula.setBounds(100, 390, 200, 30);
        add(cajamatricula);

        JLabel lblContra = new JLabel("Contraseña:");
        lblContra.setBounds(100, 430, 200, 20);
        add(lblContra);
        // Usar JPasswordField oculta el texto con asteriscos
        cajacontraseña = new JPasswordField();
        cajacontraseña.setBounds(100, 450, 200, 30);
        add(cajacontraseña);

        // Botón Registrar (Y: 510)
        JButton btnregistro = new JButton("Registrar");
        btnregistro.setBounds(100, 510, 200, 40);
        add(btnregistro);

        btnregistro.addActionListener(e -> {
            // Extracción de datos
            String textuser = cajauser.getText().trim();
            String textpassword = cajacontraseña.getText().trim();
            String textmatricula = cajamatricula.getText().trim();
            String textEdad = cajaEdad.getText().trim();
            String textAltura = cajaAltura.getText().trim();
            String textPeso = cajaPeso.getText().trim();
            String seleccionSexo = (String) comboSexo.getSelectedItem();

            if (textuser.isEmpty() || textpassword.isEmpty() || textmatricula.isEmpty() ||
                    textEdad.isEmpty() || textAltura.isEmpty() || textPeso.isEmpty() ||
                    seleccionSexo.equals("Seleccione")) {

                JOptionPane.showMessageDialog(VistaRegistro.this, "Por favor, complete todos los campos.", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
                return;
            }
            RegistroControlador controlador = new RegistroControlador(VistaRegistro.this);

// Le enviamos todos los textos que recolectamos arriba
            controlador.procesarRegistro(textuser, textmatricula, textpassword, textEdad, textAltura, textPeso, seleccionSexo);

        });
        setVisible(true);


    }


}
