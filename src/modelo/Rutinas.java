package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Rutinas {
    // lista de ejercicios por grupo muscular
    List<String> EjerciciosPecho= new ArrayList<>(List.of("Press Banca","Aperturas con polea","Press Inclinado"));

    List<String> EjerciciosEspalda= new ArrayList<>(List.of("Remo en T","Jalon al pecho","Dominadas"));
    // Piernas
    List<String> EjerciciosCuadriceps= new ArrayList<>(List.of("Sentadilla","Bulgaras","Extencion de cuadriceps"));
    List<String> EjerciciosIsquios = new ArrayList<>(List.of("Peso Muerto Rumano","Curl de femoral","Curl femoral deslizante"));
    List<String> EjerciciosGemelos= new ArrayList<>(List.of("Elevación de gemelos","Elevación de talones"));
    List<String> EjerciciosAbduptor= new ArrayList<>(List.of("Aducción en máquina","Aducción de cadera con polea"));
    List<String> EjerciciosGluteo= new ArrayList<>(List.of("Hip Thrust","Patada de Glúteo en Polea","Puente de Glúteos"));


    // Brazo
    List<String> EjerciciosTriceps= new ArrayList<>(List.of("Rompe craneos","Fondos","Lagartijas en diamante "));
    List<String> EjerciciosHombro= new ArrayList<>(List.of("Press de hombro","flexion pino","Vuelos laterales"));
    List<String> EjerciciosBiceps= new ArrayList<>(List.of("Curl de biceps","Curl martillo"));
    List<String> EjerciciosAntebrazo= new ArrayList<>(List.of("Girar una pesa rusa"));


    List<String> EjerciciosAbdomen= new ArrayList<>(List.of("plancha","crush"));
}
