package modelo;

public class Usuario {
    // Datos obligatorios de registro
    private String nombre;
    private String matricula;
    private String contraseña;

    // Datos del test físico
    private int edad;
    private double peso;
    private String genero; // F o M
    private String nivel; // Resultado del test físico
    private String diasDisponibles;

    // Constructor para registro básico
    public Usuario(String nombre, String matricula, String contraseña) {
        setNombre(nombre);
        setMatricula(matricula);
        setContraseña(contraseña);
    }

    // Constructor completo
    public Usuario(String nombre, String matricula, String contraseña, int edad, double peso, String genero, String nivel, String diasDisponibles) {
        this(nombre, matricula, contraseña);
        setEdad(edad);
        setPeso(peso);
        setGenero(genero);
        setNivel(nivel);
        setDiasDisponibles(diasDisponibles);
    }

    // --- GETTERS Y SETTERS CON RESTRICCIONES ---

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        this.nombre = nombre.trim();
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        if (matricula == null || matricula.trim().isEmpty()) {
            throw new IllegalArgumentException("La matrícula no puede estar vacía.");
        }
        // Valida que sean entre 10 y 12 dígitos numéricos (RUT + Año)
        if (!matricula.matches("^[0-9]{10,12}$")) {
            throw new IllegalArgumentException("La matrícula debe contener solo números (entre 10 y 12 dígitos).");
        }
        this.matricula = matricula.trim();
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        if (contraseña == null || contraseña.trim().length() < 6) {
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

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        if (peso <= 0) {
            throw new IllegalArgumentException("El peso debe ser un valor positivo mayor a 0.");
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
        this.genero = genero.toUpperCase().trim();
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        if (nivel == null || nivel.trim().isEmpty()) {
            throw new IllegalArgumentException("El nivel no puede estar vacío.");
        }
        this.nivel = nivel.trim();
    }

    public String getDiasDisponibles() {
        return diasDisponibles;
    }

    public void setDiasDisponibles(String diasDisponibles) {
        if (diasDisponibles == null || diasDisponibles.trim().isEmpty()) {
            throw new IllegalArgumentException("Los días disponibles no pueden estar vacíos.");
        }
        this.diasDisponibles = diasDisponibles.trim();
    }
}