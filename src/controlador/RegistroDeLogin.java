package controlador;

import java.util.HashMap;
import java.util.Scanner;

public class RegistroDeLogin {

   // Simulamos una base de datos en memoria usando un HashMap (Clave: Usuario, Valor: Contraseña)
   private static HashMap<String, String> usuariosDB = new HashMap<>();
   private static Scanner scanner = new Scanner(System.in);

   public static void main(String[] args) {
      boolean ejecutando = true;

      System.out.println("== BIENVENIDO AL SISTEMA ==");

      while (ejecutando) {
         System.out.println("\nSeleccione una opción:");
         System.out.println("1. Registrar nuevo usuario");
         System.out.println("2. Iniciar sesión");
         System.out.println("3. Salir");
         System.out.print("Opción: ");

         String opcion = scanner.nextLine();

         switch (opcion) {
            case "1":
               registrarUsuario();
               break;
            case "2":
               iniciarSesion();
               break;
            case "3":
               System.out.println("Saliendo del sistema...");
               ejecutando = false;
               break;
            default:
               System.out.println("Opción no válida. Intente de nuevo.");
         }
      }
   }


   private static void registrarUsuario() {
      System.out.print("\nIngrese un nombre de usuario: ");
      String usuario = scanner.nextLine();

      // Verificamos si el usuario ya existe
      if (usuariosDB.containsKey(usuario)) {
         System.out.println("Error: El nombre de usuario ya está en uso.");
         return;
      }

      System.out.print("Ingrese una contraseña: ");
      String contrasena = scanner.nextLine();

      // Guardamos el usuario y la contraseña en el HashMap
      usuariosDB.put(usuario, contrasena);
      System.out.println("¡Registro exitoso! Ya puede iniciar sesión.");
   }

   // Método para iniciar sesión
   private static void iniciarSesion() {
      System.out.print("\nUsuario: ");
      String usuario = scanner.nextLine();

      System.out.print("Contraseña: ");
      String contrasena = scanner.nextLine();

      // Verificamos las credenciales
      if (usuariosDB.containsKey(usuario)) {
         // Obtenemos la contraseña guardada para ese usuario
         String contrasenaGuardada = usuariosDB.get(usuario);

         if (contrasenaGuardada.equals(contrasena)) {
            System.out.println("¡Login exitoso! Bienvenido, " + usuario + ".");
         } else {
            System.out.println("Error: Contraseña incorrecta.");
         }
      } else {
         System.out.println("Error: El usuario no existe.");
      }
   }
}