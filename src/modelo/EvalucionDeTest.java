package modelo;

public class EvalucionDeTest {

    // nivel por edad, sexo y repeticiones - lagartijas
    public String nivelLagartijas(int edad, boolean esHombre, int repeticiones) {

        if (esHombre) {
            if (edad >= 17 && edad <= 19) {
                if (repeticiones <= 12) {
                    return "Nivel Principiante";
                } else if (repeticiones < 48) {
                    return "Nivel Intermedio";
                } else {
                    return "Nivel Avanzado";
                }
            } else if (edad >= 20 && edad <= 24) {
                if (repeticiones <= 18) {
                    return "Nivel Principiante";
                } else if (repeticiones < 59) {
                    return "Nivel Intermedio";
                } else {
                    return "Nivel Avanzado";
                }
            } else { // 25 a 40
                if (repeticiones <= 19) {
                    return "Nivel Principiante";
                } else if (repeticiones < 62) {
                    return "Nivel Intermedio";
                } else {
                    return "Nivel Avanzado";
                }
            }
        } else {// Nivel Mujeres
            if (edad >= 17 && edad <= 19) {
                if (repeticiones <= 2) {
                    return "Nivel Principiante";
                } else if (repeticiones < 22) {
                    return "Nivel Intermedio";
                } else {
                    return "Nivel Avanzado";
                }
            } else if (edad >= 20 && edad <= 24) {
                if (repeticiones <= 6) {
                    return "Nivel Principiante";
                } else if (repeticiones < 30) {
                    return "Nivel Intermedio";
                } else {
                    return "Nivel Avanzado";
                }
            } else { // 25 a 40
                if (repeticiones <= 7) {
                    return "Nivel Principiante";
                } else if (repeticiones < 31) {
                    return "Nivel Intermedio";
                } else {
                    return "Nivel Avanzado";
                }
            }
        }
    }
    public String nivelSentadilla(int edad, boolean esHombre, int repeticiones) {

        if (esHombre) {
            if (edad >= 17 && edad <= 19) {
                if (repeticiones <= 16) {
                    return "Nivel Principiante";
                } else if (repeticiones < 73) {
                    return "Nivel Intermedio";
                } else {
                    return "Nivel Avanzado";
                }
            } else if (edad >= 20 && edad <= 24) {
                if (repeticiones <= 22) {
                    return "Nivel Principiante";
                } else if (repeticiones < 88) {
                    return "Nivel Intermedio";
                } else {
                    return "Nivel Avanzado";
                }
            } else { // 25 a 40 de edad
                if (repeticiones <= 23) {
                    return "Nivel Principiante";
                } else if (repeticiones < 91) {
                    return "Nivel Intermedio";
                } else {
                    return "Nivel Avanzado";
                }
            }
        } else {// Nivel Mujeres
            if (edad >= 17 && edad <= 19) {
                if (repeticiones <= 7) {
                    return "Nivel Principiante";
                } else if (repeticiones < 57) {
                    return "Nivel Intermedio";
                } else {
                    return "Nivel Avanzado";
                }
            } else if (edad >= 20 && edad <= 24) {
                if (repeticiones <= 10) {
                    return "Nivel Principiante";
                } else if (repeticiones < 70) {
                    return "Nivel Intermedio";
                } else {
                    return "Nivel Avanzado";
                }
            } else { // 25 a 40
                if (repeticiones <= 12) {
                    return "Nivel Principiante";
                } else if (repeticiones < 72) {
                    return "Nivel Intermedio";
                } else {
                    return "Nivel Avanzado";
                }
            }
        }
    }
}