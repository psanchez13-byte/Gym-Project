package modelo;

public class Usuario {
    private String nombre;
    private String matricula;
    private String contraseña;
    // DATOS OBLIGATORIOS PA CREAR UN REGISTRO

    private int edad;
    private double altura;
    private double peso;
    private String genero; //F O M
    private int diasActivoFis;
    // DATOS OBLIGATORISO DEL TEST

    public Usuario(String nombre, String matricula, String contraseña) {
        this.nombre = nombre;
        this.matricula = matricula;
        this.contraseña = contraseña;


    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getContraseña() {
        return contraseña;
    }

    // Setters y Getters para los datos del test

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getDiasActivoFis() {
        return diasActivoFis;
    }

    public void setDiasActivoFis(int diasActivoFis) {
        this.diasActivoFis = diasActivoFis;
    }

}