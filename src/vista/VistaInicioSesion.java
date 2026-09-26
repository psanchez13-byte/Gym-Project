package vista;

import datos.GestorUsuariosJSON;

import java.util.Scanner;

public class VistaInicioSesion {
    GestorUsuariosJSON revisar = new GestorUsuariosJSON();

    public void inicioSesion (Scanner sc){
        System.out.print("Ingrese su matrícula: ");
        String matLogin = sc.nextLine();
        System.out.print("Ingrese su contraseña: ");
        String contraLogin = sc.nextLine();

        // 2. Traemos la lista del JSON y preparamos la bandera
        java.util.List<modelo.Usuario> listaClientes = revisar.leerUsuarios();
        boolean clienteEncontrado = false;

// 3. Recorremos la lista buscando coincidencias
        if (listaClientes != null) {
            for (modelo.Usuario cliente : listaClientes) {

                // Comparamos usando .equals() por ser cadenas de texto
                if (cliente.getMatricula().equals(matLogin) && cliente.getContraseña().equals(contraLogin)) {
                    System.out.println("¡Bienvenido de vuelta, " + cliente.getNombre() + "!");
                    clienteEncontrado = true;

                    // TODO: Aquí más adelante llamaremos a la vista del perfil del cliente
                    // Ejemplo: vistaDashboardCliente.mostrar();

                    break; // Cortamos el bucle, ya no necesitamos seguir buscando
                }
            }
        }if (!clienteEncontrado) {
            System.out.println("\nError: Matrícula o contraseña incorrecta. Intente de nuevo.");
        }
    }

}
