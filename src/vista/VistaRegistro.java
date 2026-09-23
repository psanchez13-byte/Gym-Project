package vista;

import controlador.RegistroControlador;
import datos.GestorAmins;

import java.util.Scanner;

import modelo.PlanSemanal;

public class VistaRegistro {

    public void iniciarregistro (){
        RegistroControlador gestor = new RegistroControlador();
        GestorAmins admin = new GestorAmins();
        VistaAdminConsola perfilAdmin = new VistaAdminConsola();
        VistaLogin sesion = new VistaLogin();
        Scanner es = new Scanner(System.in);
        boolean esValido = false;

        do {
            sesion.inicio();

            System.out.println("\n--- MENÚ REGISTRO DEL GIMNASIO ---");
            System.out.print("\nIngrese su Nombre:");
            String nombre = es.nextLine();
            System.out.print("Ingrese su matrícula:");
            String mat = es.nextLine();
            System.out.print("Ingrese su contraseña:");
            String contra = es.nextLine();
            if (nombre.isEmpty() || mat.isEmpty() || contra.isEmpty()) {
                System.out.println("Error: Ningún campo puede estar vacío.");
                continue; // Repite el bucle sin cambiar esValido
            }
//            System.out.print("Acceder como admin? (s/n):");
//            boolean op = es.nextLine().trim().equalsIgnoreCase("s");
//            if (op) {
//                boolean esValidoAdmin = admin.validarCredenciales(mat, contra);
//                if (esValidoAdmin){
//                    System.out.println("¡Bienvenido Administrador " + nombre + "!");
//                    perfilAdmin.panelDeAdministracion();
//                }else{
//                    System.out.println("Error: Administrador no dectectado.");
//                }
//            } else {
                gestor.procesarRegistro(nombre, mat, contra);
                System.out.println("Bienvenido usuario: " +nombre);
                System.out.println("\n--- CREACIÓN DE PLAN DE ENTRENAMIENTO ---");
                PlanSemanal planUsuario = new PlanSemanal();
                planUsuario.generaPlan();

//            }
        }while (!esValido);
    }
}

