package modelo;

import java.util.Locale;

public class Usuario {
    private String nombre;
    private String matricula;
    private String contraseña;

    // DATOS OBLIGATORIOS PA CREAR UN REGISTRO

    private int edad;
    private double altura;
    private double peso;
    private String genero; //F O M
    private int diasactivofis;
    // DATOS OBLIGATORISO DEL TEST

    public Usuario(String nombre, String matricula, String contraseña) {
        setNombre(nombre);
        setMatricula(matricula);
        setContraseña(contraseña);
    }
    public Usuario(String nombre, String matricula, String contraseña, int edad, double altura, double peso, String genero, int diasactivofis) {
        this(nombre, matricula, contraseña);
        setEdad(edad);
        setAltura(altura);
        setPeso(peso);
        setGenero(genero);
        setDiasactivofis(diasactivofis);
    }

    //Validaciones
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre){
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio");
        }
        this.nombre = nombre;

    }
    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        if (matricula == null || matricula.trim().isEmpty()) {
            throw new IllegalArgumentException("La matricula no puede estar vacia.");
        }
        if (!matricula.matches("^[0-9]{10,12}$")) {
            throw new IllegalArgumentException("La matricula debe contener solo numeros (Rut + año de ingreso).");
        }
        this.matricula = matricula;

    }
    public String getContraseña(){
        return  contraseña;
    }

    public void setContraseña(String contraseña) {
        if (contraseña == null || contraseña.length() < 6) {
            throw new IllegalArgumentException(" la contraseña debe tener al menos 6 caracteres.");
        }
        this.contraseña = contraseña;
    }

    public int getEdad(){
        return edad;
    }

    public void setEdad(int edad) {
        if (edad < 10 || edad > 90) {
            throw new IllegalArgumentException("la edad estar entre 15 y 100 años.");
        }
        this.edad = edad;

    }

    public double getAltura(){
        return altura;
    }

    public void setAltura(double altura){
        if (altura <= 0) {
            throw new IllegalArgumentException("La altura debe ser mayor a 0");

        }
        this.altura = altura;

    }

    public double getPeso() {
        return peso;

    }

    public void setPeso(double peso){
        if (peso <=0) {
            throw new IllegalArgumentException("El peso tiene que ser mayor a 0.");
        }
        this.peso = peso;
    }

    public void setGenero(String genero) {
        if (genero == null || (!genero.equalsIgnoreCase("F") && !genero.equalsIgnoreCase("M"))){
            throw new IllegalArgumentException("El genero debe ser M o F.");
        }
        this.genero = genero.toUpperCase();
    }

    public void setDiasactivofis(int diasactivofis){
        if (diasactivofis < 0 || diasactivofis > 7) {
            throw new IllegalArgumentException("Los dias de actividad fisica deben estar entre 0 y 7.");
        }
        this.diasactivofis =  diasactivofis;
    }
}
