package modelo;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Rutinas {
    // lista de ejercicios por grupo muscular
    List<String> EjerciciosPecho= new ArrayList<>(List.of("Press Banca","Aperturas con polea","Press Inclinado","GOKU"));

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


    public void elegirEjerciciosRandom(List<String> listaEjercicios,int cantidad) {
        List<String> copia = new ArrayList<>(listaEjercicios); // copia para no dañar la original
        Random random = new Random();

        for (int i = 0; i < cantidad; i++) {
            int posicion = random.nextInt(copia.size());
            System.out.println(copia.get(posicion));
            copia.remove(posicion); // lo saco de la copia para que no vuelva a salir
        }
    }
    public void GeneraDiaPecho(){
        System.out.println("DIA DE PECHO");
        elegirEjerciciosRandom(EjerciciosPecho,3);
    }
    public void generarDiaPierna() {
        System.out.println("DIA DE PIERNA");
        elegirEjerciciosRandom(EjerciciosCuadriceps,2);
        elegirEjerciciosRandom(EjerciciosIsquios,2);
        elegirEjerciciosRandom(EjerciciosAbduptor,1);
        elegirEjerciciosRandom(EjerciciosGemelos,1);
        elegirEjerciciosRandom(EjerciciosGluteo,1);
    }
    public void  GeneraDiaEspalda(){
        System.out.println("DIA DE ESPALDA");
        elegirEjerciciosRandom(EjerciciosEspalda,3);
    }

    public void GeneraDiaBrazo(){
        System.out.println("DIA BRAZO");
        elegirEjerciciosRandom(EjerciciosBiceps,2);
        elegirEjerciciosRandom(EjerciciosTriceps,2);
        elegirEjerciciosRandom(EjerciciosHombro,2);
        elegirEjerciciosRandom(EjerciciosAntebrazo,1);
    }
    public void Generaabdomen(){
        System.out.println("Abdomen");
        elegirEjerciciosRandom(EjerciciosAbdomen,2);
    }
}

