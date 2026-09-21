package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PlanSemanal {

    public List<Integer> elegirDias() {
        List<Integer> diasElegidos = new ArrayList<>();
        //crea una caja vacía, Su objetivo es meter exactamente 4 números ahí dentro.
        Scanner scanner = new Scanner(System.in);

        System.out.println("Elige 4 días para entrenar (escribe el número, uno por uno):");
        System.out.println("1. Lunes");
        System.out.println("2. Martes");
        System.out.println("3. Miércoles");
        System.out.println("4. Jueves");
        System.out.println("5. Viernes");
        System.out.println("6. Sábado");
        System.out.println("7. Domingo");

        for (int i = 0; i < 4; i++) {
            int dia;
            do {
                System.out.println("Elige el día " + (i + 1) + ":");
                dia = scanner.nextInt();

                if (diasElegidos.contains(dia)) {
             //El método .contains() hace toda esa validación en una sola línea,
              // evitando que un cliente elija "Lunes" cuatro veces seguidas.
                    System.out.println("Ese día ya lo elegiste, elige uno distinto.");
                }
            } while (diasElegidos.contains(dia));

            diasElegidos.add(dia);
        }
        return diasElegidos;
    }
    public String nombreDia(int dia) {
        if (dia == 1) {
            return "Lunes";
        } else if (dia == 2) {
            return "Martes";
        } else if (dia == 3) {
            return "Miércoles";
        } else if (dia == 4) {
            return "Jueves";
        } else if (dia == 5) {
            return "Viernes";
        } else if (dia == 6) {
            return "Sábado";
        } else {
            return "Domingo";
        }
    }

   public void  generaPlan(){
        Rutinas rutinas= new Rutinas();
       List<Integer> diasElegidos = elegirDias();

       System.out.println();
       System.out.println(nombreDia(diasElegidos.get(0)) + ":");
       rutinas.generarDiaPecho();

       System.out.println();
       System.out.println(nombreDia(diasElegidos.get(1)) + ":");
       rutinas.generarDiaEspalda();

       System.out.println();
       System.out.println(nombreDia(diasElegidos.get(2)) + ":");
       rutinas.generarDiaBrazo();

       System.out.println();
       System.out.println(nombreDia(diasElegidos.get(3)) + ":");
       rutinas.generarDiaPierna();

   }
}
