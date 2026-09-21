package controlador;

import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.JFrame;

public class RegistroControlador {

    private static HashMap<String, String> usuariosDB = new HashMap<>();


    public void procesarRegistro(JFrame ventanaActual, String nombre, String matricula) {


        if (nombre == null || nombre.trim().isEmpty() ||
                matricula == null || matricula.trim().isEmpty()) {

            JOptionPane.showMessageDialog(ventanaActual,
                    "Error: El nombre y la matrícula no pueden estar vacíos.",
                    "Campos Incompletos",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }


        if (usuariosDB.containsKey(matricula)) {
            JOptionPane.showMessageDialog(ventanaActual,
                    "Error: La matrícula " + matricula + " ya está registrada.",
                    "Registro Fallido",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }


        usuariosDB.put(matricula, nombre);


        JOptionPane.showMessageDialog(ventanaActual,
                "¡Registro exitoso!\n\nUsuario: " + nombre + "\nMatrícula: " + matricula,
                "Registro Completado",
                JOptionPane.INFORMATION_MESSAGE);
    }
}