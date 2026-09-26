package vista;

import controlador.RegistroControlador;
import java.util.Scanner;

public class VistaRegistro {

    public void iniciarregistro() {
        RegistroControlador gestor = new RegistroControlador();
        Scanner es = new Scanner(System.in);
        boolean esValido = false;

        do {
            System.out.println("\n--- MENÚ REGISTRO ---");
            System.out.print("Ingrese su Nombre: ");
            String nombre = es.nextLine();
            System.out.print("Ingrese su matrícula: ");
            String mat = es.nextLine();
            System.out.print("Ingrese su contraseña: ");
            String contra = es.nextLine();

            // Validación de campos vacíos
            if (nombre.isEmpty() || mat.isEmpty() || contra.isEmpty()) {
                System.out.println("Error: Ningún campo puede estar vacío.");
                continue; // Vuelve al inicio del do-while
            }

            //  Guardamos los datos a través del controlador
            gestor.procesarRegistro(nombre, mat, contra);
            System.out.println("¡Registro exitoso! Bienvenido usuario: " + nombre);

            esValido = true;

        } while (!esValido);

    }
}

