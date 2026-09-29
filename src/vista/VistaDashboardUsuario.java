package vista;

import modelo.PlanSemanal;
import modelo.Usuario;
import java.util.Scanner;
import datos.GestorRutinasCSV;

public class VistaDashboardUsuario {

    public void mostrarDashboard(Usuario usuarioLogueado, Scanner sc) {
        int opcion = 0;
        boolean cerrarSesion = false;

        do {
            System.out.println("\n==================================");
            System.out.println("   DASHBOARD - " + usuarioLogueado.getNombre().toUpperCase());
            System.out.println("   Nivel Físico: " + usuarioLogueado.getNivel());
            System.out.println("==================================");
            System.out.println("1. Ver mi perfil físico");
            System.out.println("2. Armar / Ver mi Plan Semanal");
            System.out.println("3. Cerrar sesión");
            System.out.print("Elige una opción: ");

            try {
                opcion = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                opcion = 0; //un error controlado si escriben letras
            }

            switch (opcion) {
                case 1:
                    System.out.println("\n--- MI PERFIL ---");
                    System.out.println("Matrícula: " + usuarioLogueado.getMatricula());
                    System.out.println("Edad: " + usuarioLogueado.getEdad() + " años");
                    System.out.println("Peso: " + usuarioLogueado.getPeso() + " kg");
                    System.out.println("Género: " + usuarioLogueado.getGenero());
                    break;
                case 2:
                    datos.GestorRutinasCSV gestorRutinas = new datos.GestorRutinasCSV();
                    String rutinaGuardada = gestorRutinas.leerRutina(usuarioLogueado.getMatricula());

                    System.out.println("\nNOTA: en cada ejercicio, elige un peso con el que las últimas repeticiones del rango indicado te cuesten realizar, pero sin perder la técnica. Si te resultan muy fáciles, sube el peso la próxima sesión; si no logras completar el rango, bájalo.");


                    if (rutinaGuardada != null) {
                        System.out.println("\n--- MI RUTINA ACTUAL ---");
                        System.out.println(rutinaGuardada);
                    } else {
                        System.out.println("\n[Aviso] Aún no has configurado tus días ni tu rutina.");

                        PlanSemanal plan = new PlanSemanal();
                        // 1. Pedimos los días
                        java.util.List<Integer> dias = plan.elegirDias();

                        // 2. Generamos el texto completo de los ejercicios
                        String rutinaGenerada = plan.generaPlan(dias);

                        // 3. Imprimimos en pantalla para que el usuario la vea
                        System.out.println(rutinaGenerada);

                        // 4. Guardamos en el nuevo CSV de rutinas
                        gestorRutinas.guardarRutina(usuarioLogueado.getMatricula(), rutinaGenerada);

                        //  Actualizar los días en el GestorUsuariosCSV
                        String diasTexto = plan.nombreDia(dias.get(0)) + "-" + plan.nombreDia(dias.get(1)) + "-" +
                                plan.nombreDia(dias.get(2)) + "-" + plan.nombreDia(dias.get(3));
                        usuarioLogueado.setDiasDisponibles(diasTexto);
                        new datos.GestorUsuariosCSV().actualizarUsuario(usuarioLogueado);

                        System.out.println("\n[Éxito] Tu rutina detallada ha sido guardada permanentemente.");
                    }
                    break;
                case 3:
                    System.out.println("\nCerrando sesión. ¡Sigue entrenando duro, " + usuarioLogueado.getNombre() + "!");
                    cerrarSesion = true;
                    break;
                default:
                    System.out.println("Error: Opción no válida. Intenta nuevamente.");
            }

        } while (!cerrarSesion);
    }
}