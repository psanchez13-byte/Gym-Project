package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Rutinas {
    // lista de ejercicios por grupo muscular
    List<String> ejerciciosPecho = new ArrayList<>(List.of("Press Banca con Barra", "Aperturas con polea", "Press Banca con mancuernas ","Press Inclinado con mancuernas"));
    List<String> ejerciciosEspalda = new ArrayList<>(List.of("Remo en T", "Jalon al pecho", "Dominadas","Remo con mancuernas","Pull over"));
    // Piernas
    List<String> ejerciciosCuadriceps = new ArrayList<>(List.of("Sentadilla con mancuernas", "Bulgaras", "Extencion de cuadriceps","Prensa","Sentadilla hack"));
    List<String> ejerciciosIsquios = new ArrayList<>(List.of("Peso Muerto Rumano", "Curl de femoral Sentado", "Curl femoral tumbado","Curl nordico"));
    List<String> ejerciciosGemelos = new ArrayList<>(List.of("Elevaciones de talón de pie", "Elevaciones a una sola pierna","Elevaciones de talón sentado"));
    List<String> ejerciciosAbductor = new ArrayList<>(List.of("Aducción en máquina", "Aducción de cadera con polea","plancha copenhague"));
    List<String> ejerciciosGluteo = new ArrayList<>(List.of("Hip Thrust", "Patada de Glúteo en Polea", "Puente de Glúteos"));

    // Brazo
    List<String> ejerciciosTriceps = new ArrayList<>(List.of("Rompe craneos", "Extensión de tríceps ", "Lagartijas  diamante"));
    List<String> ejerciciosHombro = new ArrayList<>(List.of("Press de hombro", "flexion pino", "Vuelos laterales", "Vuelos laterales en polea"));
    List<String> ejerciciosBiceps = new ArrayList<>(List.of("Curl de biceps", "Curl martillo","Curl de biceps en polea "));
    List<String> ejerciciosAntebrazo = new ArrayList<>(List.of("Curl de muñeca con barra","Extensión de muñeca","Pronación de muñeca","Rodillo de muñeca"));

    List<String> ejerciciosAbdomen = new ArrayList<>(List.of( "Plancha", "Crunch abdominal", "Elevación de piernas", "Plancha lateral"));

    // SELECCIÓN RANDOM
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

    // Sin cálculo de peso: series fijas + rango 8-10 repeticiones
    public String formatearEjercicio(String nombreEjercicio, int series) {
        return nombreEjercicio + " - " + series + " series x 8-10 repeticiones "
                + "(elige un peso donde las últimas repeticiones te cuesten)";
    }

    // Imprime el bloque de abdomen por tiempo
    private void imprimirAbdomen() {
        System.out.println("ABDOMEN");
        for (String ejercicio : elegirEjerciciosRandom(ejerciciosAbdomen, 2)) {
            System.out.println(ejercicio + " - 3 series x 45 segundos");
        }
    }

    // GENERAR DÍAS
    // Abdomen se incluye en el día de pecho y en el día de brazo.
    public void generarDiaPecho() {
        System.out.println("PECHO");
        for (String ejercicio : elegirEjerciciosRandom(ejerciciosPecho, 3)) {
            System.out.println(formatearEjercicio(ejercicio, 3));
        }
        imprimirAbdomen();
    }

    public void generarDiaEspalda() {
        System.out.println("ESPALDA");
        for (String ejercicio : elegirEjerciciosRandom(ejerciciosEspalda, 3)) {
            System.out.println(formatearEjercicio(ejercicio, 3));
        }
    }

    public void generarDiaBrazo() {
        System.out.println("BRAZO Y ABDOMEN");
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
        System.out.println("PIERNA Y ABDOMEN");
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