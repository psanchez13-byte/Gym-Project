package vista;

import datos.GestorAmins;
import datos.GestorUsuariosJSON;

import java.util.Scanner;

public  class VistaLogin {

       public void inicio() {
              Scanner lec = new Scanner(System.in);
              GestorUsuariosJSON revisar = new GestorUsuariosJSON();
              VistaRegistro revisionre = new VistaRegistro();
              VistaAdminConsola perfilAdmin = new VistaAdminConsola();
              GestorAmins admin = new GestorAmins();
              int op = 0;
              boolean esValido = false;
              do {
                     System.out.println("\n1. Iniciar sesion\n" +
                             "2. Registro\n" + "\n3.Iniciar como admin");
                     System.out.print("\nElige una opción: ");
                     op = lec.nextInt();
                     lec.nextLine();

                     switch (op) {
                            case 1:
                                   System.out.println("Iniciando sesion....");
                                   java.util.List<modelo.Usuario> listaClientes = revisar.leerUsuarios();
//                                   if (listaClientes.isEmpty()){
//                                          System.out.println("Aún no hay clientes registrados en el sistema.");
//                                   } else {
//                                          for (modelo.Usuario cliente : listaClientes) {
//                                                 System.out.println("- Nombre: " + cliente.getNombre() +
//                                                         " | Matrícula: " + cliente.getMatricula() +
//                                                         " | Contraseña: " + cliente.getContraseña());
//                                          }
//                                   }
                                   break;
                            case 2:
                                   System.out.println("Registrando...");
                                   revisionre.iniciarregistro();
                                   break;
                            case 3:
                                   System.out.print("Ingrese su matrícula:");
                                   String mat = lec.nextLine();
                                   System.out.print("Ingrese su contraseña:");
                                   String contra = lec.nextLine();
                                   System.out.print("Acceder como admin? (s/n):");
                                   boolean opc = lec.nextLine().trim().equalsIgnoreCase("s");
                                   if (opc) {
                                          boolean esValidoAdmin = admin.validarCredenciales(mat, contra);
                                          if (esValidoAdmin) {
                                                 System.out.println("¡Bienvenido Administrador " + mat + "!");
                                                 perfilAdmin.panelDeAdministracion();
                                          } else {
                                                 System.out.println("Error: Administrador no dectectado.");
                                          }
                                          break;
                                   }
                     }
                     while (op != 4) ;
                     lec.close();


              }while (!esValido);
       }
}