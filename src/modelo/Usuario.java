package modelo;

public class Usuario {
    // Datos obligatorios de registro
    private String nombre;
    private String matricula;
    private String contraseña;

    // Datos del test
    private int edad;
    private double altura; // En metros o centímetros
    private double peso;   // En kilogramos
    private String genero; // 'F' o 'M'
    private int diasactivofis; // 0 a 7 días

    // Constructor base para registro
    public Usuario(String nombre, String matricula, String contraseña) {
        setNombre(nombre);
        setMatricula(matricula);
        setContraseña(contraseña);
    }

    // Constructor completo
    public Usuario(String nombre, String matricula, String contraseña, int edad, double altura, double peso, String genero, int diasactivofis) {
        this(nombre, matricula, contraseña);
        setEdad(edad);
        setAltura(altura);
        setPeso(peso);
        setGenero(genero);
        setDiasactivofis(diasactivofis);
    }

    // --- GETTERS Y SETTERS CON VALIDACIONES ---

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        this.nombre = nombre;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        if (matricula == null || matricula.trim().isEmpty()) {
            throw new IllegalArgumentException("La matrícula no puede estar vacía.");
        }
        // Ejemplo: Valida que contenga solo números y tenga entre 10 y 12 dígitos (RUT + Año)
        if (!matricula.matches("^[0-9]{10,12}$")) {
            throw new IllegalArgumentException("La matrícula debe contener solo números (RUT + año de ingreso).");
        }
        this.matricula = matricula;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        if (contraseña == null || contraseña.length() < 6) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres.");
        }
        this.contraseña = contraseña;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        if (edad < 0 || edad > 100) {
            throw new IllegalArgumentException("La edad debe estar entre 0 y 100 años.");
        }
        this.edad = edad;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        if (altura <= 0) {
            throw new IllegalArgumentException("La altura debe ser mayor a 0.");
        }
        this.altura = altura;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        if (peso <= 0) {
            throw new IllegalArgumentException("El peso debe ser mayor a 0.");
        }
        this.peso = peso;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        if (genero == null || (!genero.equalsIgnoreCase("F") && !genero.equalsIgnoreCase("M"))) {
            throw new IllegalArgumentException("El género debe ser 'F' o 'M'.");
        }
        this.genero = genero.toUpperCase();
    }

    public int getDiasactivofis() {
        return diasactivofis;
    }

    public void setDiasactivofis(int diasactivofis) {
        if (diasactivofis < 0 || diasactivofis > 7) {
            throw new IllegalArgumentException("Los días de actividad física deben estar entre 0 y 7.");
        }
        this.diasactivofis = diasactivofis;
    }
}