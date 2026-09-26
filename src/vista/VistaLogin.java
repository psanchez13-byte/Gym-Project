package vista;

import datos.GestorAdmins;
import datos.GestorUsuariosCSV;

import java.util.Scanner;

public  class VistaLogin {

       public void inicio(Scanner lec) {
//              Scanner lec = new Scanner(System.in);
              VistaInicioSesion inSesion = new VistaInicioSesion();
              VistaRegistro revisionre = new VistaRegistro();
              VistaAdminConsola perfilAdmin = new VistaAdminConsola();
              GestorAdmins admin = new GestorAdmins();
              int op = 0;
              boolean esValido = false;
              do {
                     System.out.println("--- MENÚ DEL GIMNASIO ---");
                      System.out.println("\n1. Iniciar sesion\n" +
                             "2. Registro\n" + "3.Iniciar como admin");

                     System.out.print("\nElige una opción: ");
                     op = lec.nextInt();
                     lec.nextLine();

                     switch (op) {
                            case 1:
                                   System.out.println("Iniciando sesion....");
                                   inSesion.inicioSesion(lec);
                                   break;
                            case 2:
                                   System.out.println("Registrando...");
                                    revisionre.iniciarregistro();
                                   break;
                            case 3:
                                   System.out.println("\nBienvenido al perfinl de administracion: ");
                                   System.out.print("\nIngrese su Nombre:");
                                   String nombre = lec.nextLine();
                                   System.out.print("Ingrese su matrícula:");
                                   String mat = lec.nextLine();
                                   System.out.print("Ingrese su contraseña:");
                                   String contra = lec.nextLine();
                                   if (nombre.isEmpty() || mat.isEmpty() || contra.isEmpty()) {
                                          System.out.println("Error: Ningún campo puede estar vacío.");
                                          continue; // Repite el bucle sin cambiar esValido
                                   }
                                   boolean ValifacionAdm = admin.validarCredenciales(mat, contra);

                                   if (ValifacionAdm){
                                          System.out.println("¡Bienvenido Administrador " + mat + "!");
                                          perfilAdmin.panelDeAdministracion();

                                   }else {
                                          System.out.println("Error: Administrador no dectectado.");
                                           inicio(lec);
                                   }
                     }
                     while (op != 4) ;
                     lec.close();

              }while (!esValido);
       }
}