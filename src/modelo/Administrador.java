package modelo;

public class Administrador {
    private String matricula;
    private String contraseña;

    public Administrador(String matricula, String contraseña) {
        this.matricula = matricula;
        this.contraseña = contraseña;
    }
    public boolean validarCredenciales(String u, String p) {
        return this.matricula.equals(u) && this.contraseña.equals(p);

    }

    public String getMatricula() { return matricula; }
    public String getContraseña() { return contraseña; }


}

