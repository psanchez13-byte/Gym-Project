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
            int dia=0;
            boolean valido = false;
            while (!valido) {
                // 1) Verificar que sea un número
                if (!scanner.hasNextInt()) {
                    System.out.println("Error: solo se aceptan números.");
                    scanner.next(); // descartar el token inválido
                    continue; // Vuelve a evaluar el 'while (!valido)' desde el principio
                }
                dia = scanner.nextInt();

                // 2) Verificar que esté en rango 1-7
                if (dia < 1 || dia > 7) {
                    System.out.println("Error: el número debe estar entre 1 y 7.");
                    continue;

                }
                // 3) Verificar que no esté repetido
                if (diasElegidos.contains(dia)) {
                    System.out.println("Ese día ya lo elegiste, elige uno distinto.");
                    continue;
                }
                valido = true; // pasó todas las validaciones

                diasElegidos.add(dia);
            }
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

    public String generaPlan(List<Integer> diasElegidos){
        Rutinas rutinas = new Rutinas();
        StringBuilder planCompleto = new StringBuilder();

        planCompleto.append("\n").append(nombreDia(diasElegidos.get(0))).append(" (PECHO):\n");
        planCompleto.append(rutinas.generarDiaPecho());

        planCompleto.append("\n").append(nombreDia(diasElegidos.get(1))).append(" (ESPALDA):\n");
        planCompleto.append(rutinas.generarDiaEspalda());

        planCompleto.append("\n").append(nombreDia(diasElegidos.get(2))).append(" (BRAZO):\n");
        planCompleto.append(rutinas.generarDiaBrazo());

        planCompleto.append("\n").append(nombreDia(diasElegidos.get(3))).append(" (PIERNA):\n");
        planCompleto.append(rutinas.generarDiaPierna());

        return planCompleto.toString();

    }
}
