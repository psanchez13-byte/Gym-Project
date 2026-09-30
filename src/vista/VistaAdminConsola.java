package vista;
import java.util.Scanner;
import datos.GestorUsuariosCSV;
public class VistaAdminConsola {
    public void panelDeAdministracion(){
        Scanner lec = new Scanner(System.in);
        GestorUsuariosCSV revisar = new GestorUsuariosCSV();

        int op =0;

        do {
            System.out.println("\n1. Listar todos los clientes\n" +
                    "2. Ver ficha física de un cliente\n" +
                    "3. Asignar nueva rutina a cliente\n" +
                    "4. Eliminar un cliente\n" +
                    "5. Cerrar sesión");
            System.out.print("\nElige una opción: ");
            op = lec.nextInt();
            lec.nextLine();

            switch (op){
                case 1:
                    System.out.println("Listando clientes... ");
                    java.util.List<modelo.Usuario> listaClientes = revisar.leerUsuarios();
                    if (listaClientes.isEmpty()){
                        System.out.println("Aún no hay clientes registrados en el sistema.");
                    } else {
                        for (modelo.Usuario cliente : listaClientes) {
                            System.out.println("- Nombre: " + cliente.getNombre() +
                                    " | Matrícula: " + cliente.getMatricula() +
                                    " | Contraseña: " + cliente.getContraseña());
                        }
                    }
                    break;

                case 2:
                    System.out.println("En mantenimiento... ");

                    break;

                case 3:
                    System.out.println("En mantenimiento... ");
                    break;
                case 4:
                    System.out.println("En mantenimiento... ");
                    break;

                case 5:
                    VistaLogin menuNv = new VistaLogin();
                    menuNv.inicio(lec);

                    break;
            }



        }while (op!=5);
        lec.close();

    }
}
