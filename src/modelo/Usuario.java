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
    private int diasactivofis;
    // DATOS OBLIGATORISO DEL TEST

    public Usuario(String nombre, String matricula, String contraseña) {
        this.nombre = nombre;
        this.matricula= matricula;
        this.contraseña = contraseña;


    }

    public String getNombre() {
        return nombre;
    }

    public String getMatricula(){
        return matricula;
    }

    public String getContraseña(){
        return contraseña;
    }







}
