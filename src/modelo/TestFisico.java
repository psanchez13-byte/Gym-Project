package modelo;

public class TestFisico {
    public enum Nivel {
        PRINCIPIANTE,
        INTERMEDIO,
        AVANZADO
    }

    public int puntosLagartijas(int reps, String genero) {
        if (genero.equals("M")) {
            if (reps < 15) {
                return 1;
            } else if (reps < 35) {
                return 2;
            } else {
                return 3;
            }
            //esto son los numeros de reps femininos
        } else {
            if (reps < 8) {
                return 1;
            } else if (reps < 19) {
                return 2;
            } else {
                return 3;
            }
        }
    }

    public int puntosSentadilla(int reps, String genero) {
        if (genero.equals("M")) {
            if (reps < 25) {
                return 1;
            } else if (reps < 44) {
                return 2;
            } else {
                return 3;
            }
            //esto son los numeros de reps femininos
        } else {
            if (reps < 20) {
                return 1;
            } else if (reps < 39) {
                return 2;
            } else {
                return 3;
            }
        }
    }

    public int puntosAbdominales(int reps, String genero) {
        if (genero.equals("M")) {
            if (reps < 25) {
                return 1;
            } else if (reps < 39) {
                return 2;
            } else {
                return 3;
            }
            //esto son los numeros de reps femininos
        } else {
            if (reps < 20) {
                return 1;
            } else if (reps < 34) {
                return 2;
            } else {
                return 3;
            }
        }
    }

    public Nivel calcularNivel(int lagartijas, int sentadilla, int abdominales, String genero) {
        int total = puntosAbdominales(abdominales, genero) + puntosSentadilla(sentadilla,genero)+ puntosLagartijas(lagartijas,genero);

        if (total <= 5){
            return Nivel.PRINCIPIANTE;
        }else if(total<=7){
            return Nivel.INTERMEDIO;
        }else {
            return Nivel.AVANZADO;
        }
    }
}