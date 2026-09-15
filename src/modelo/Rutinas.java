package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Rutinas {
    // lista de ejercicios por grupo muscular
    List<String> ejerciciosPecho = new ArrayList<>(List.of("Press Banca", "Aperturas con polea", "Fondos"));
    List<String> ejerciciosEspalda = new ArrayList<>(List.of("Remo en T", "Jalon al pecho", "Dominadas"));
    // Piernas
    List<String> ejerciciosCuadriceps = new ArrayList<>(List.of("Sentadilla", "Bulgaras", "Extencion de cuadriceps"));
    List<String> ejerciciosIsquios = new ArrayList<>(List.of("Peso Muerto Rumano", "Curl de femoral", "Curl femoral deslizante"));
    List<String> ejerciciosGemelos = new ArrayList<>(List.of("Elevación de gemelos", "Elevación de talones"));
    List<String> ejerciciosAbductor = new ArrayList<>(List.of("Aducción en máquina", "Aducción de cadera con polea"));
    List<String> ejerciciosGluteo = new ArrayList<>(List.of("Hip Thrust", "Patada de Glúteo en Polea", "Puente de Glúteos"));

    // Brazo
    List<String> ejerciciosTriceps = new ArrayList<>(List.of("Rompe craneos", "Fondos", "Lagartijas en diamante"));
    List<String> ejerciciosHombro = new ArrayList<>(List.of("Press de hombro", "flexion pino", "Vuelos laterales"));
    List<String> ejerciciosBiceps = new ArrayList<>(List.of("Curl de biceps", "Curl martillo"));
    List<String> ejerciciosAntebrazo = new ArrayList<>(List.of("Girar una pesa rusa"));

    List<String> ejerciciosAbdomen = new ArrayList<>(List.of("plancha", "crush"));

    // ================== SELECCIÓN RANDOM ==================
    public List<String> elegirEjerciciosRandom(List<String> listaEjercicios, int cantidad) {
        List<String> copia = new ArrayList<>(listaEjercicios);
        List<String> elegidos = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < cantidad; i++) {
            int posicion = random.nextInt(copia.size());
            elegidos.add(copia.get(posicion));
            copia.remove(posicion);
        }
        return elegidos;
    }

    // ================== FORMATEO ==================
    // Sin cálculo de peso: series fijas + rango 8-10 repeticiones (RPE / autorregulación).
    public String formatearEjercicio(String nombreEjercicio, int series) {
        return nombreEjercicio + " - " + series + " series x 8-10 repeticiones "
                + "(elige un peso donde las últimas repeticiones te cuesten)";
    }

    // Imprime el bloque de abdomen (por tiempo, no por peso).
    private void imprimirAbdomen() {
        System.out.println("-- Abdomen --");
        for (String ejercicio : elegirEjerciciosRandom(ejerciciosAbdomen, 2)) {
            System.out.println(ejercicio + " - 3 series x 30 segundos");
        }
    }

    // ================== GENERAR DÍAS (4 días, enfoque principiante) ==================
    // Abdomen se incluye en el día de pecho y en el día de brazo.
    public void generarDiaPecho() {
        System.out.println("DIA DE PECHO");
        for (String ejercicio : elegirEjerciciosRandom(ejerciciosPecho, 3)) {
            System.out.println(formatearEjercicio(ejercicio, 3));
        }
        imprimirAbdomen();
    }

    public void generarDiaEspalda() {
        System.out.println("DIA DE ESPALDA");
        for (String ejercicio : elegirEjerciciosRandom(ejerciciosEspalda, 3)) {
            System.out.println(formatearEjercicio(ejercicio, 3));
        }
    }

    public void generarDiaBrazo() {
        System.out.println("DIA DE BRAZO Y ABDOMEN");
        List<String> ejerciciosDelDia = new ArrayList<>();
        ejerciciosDelDia.addAll(elegirEjerciciosRandom(ejerciciosBiceps, 2));
        ejerciciosDelDia.addAll(elegirEjerciciosRandom(ejerciciosTriceps, 2));
        ejerciciosDelDia.addAll(elegirEjerciciosRandom(ejerciciosHombro, 2));
        ejerciciosDelDia.addAll(elegirEjerciciosRandom(ejerciciosAntebrazo, 1));

        for (String ejercicio : ejerciciosDelDia) {
            System.out.println(formatearEjercicio(ejercicio, 3));
        }
        imprimirAbdomen();
    }

    public void generarDiaPierna() {
        System.out.println("DIA DE PIERNA Y ABDOMEN");
        List<String> ejerciciosDelDia = new ArrayList<>();
        ejerciciosDelDia.addAll(elegirEjerciciosRandom(ejerciciosCuadriceps, 2));
        ejerciciosDelDia.addAll(elegirEjerciciosRandom(ejerciciosIsquios, 2));
        ejerciciosDelDia.addAll(elegirEjerciciosRandom(ejerciciosAbductor, 1));
        ejerciciosDelDia.addAll(elegirEjerciciosRandom(ejerciciosGemelos, 1));
        ejerciciosDelDia.addAll(elegirEjerciciosRandom(ejerciciosGluteo, 1));

        for (String ejercicio : ejerciciosDelDia) {
            System.out.println(formatearEjercicio(ejercicio, 3));
        }
    }
}