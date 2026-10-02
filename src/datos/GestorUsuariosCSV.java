package datos;

import modelo.Usuario;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestorUsuariosCSV {
    private final String RUTA_ARCHIVO = "usuarios.csv";
    private final String DELIMITADOR = ","; // Lo que separa nuestras columnas

    // Para leer los usuarios del archivo CSV y convertirlos en objetos Java
    public List<Usuario> leerUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();

        // Usamos try-with-resources para que el archivo se cierre automáticamente
        try (BufferedReader lector = new BufferedReader(new FileReader(RUTA_ARCHIVO))) {
            String linea;

            // Leemos línea por línea hasta que se acabe el archivo
            while ((linea = lector.readLine()) != null) {
                // Cortamos la línea por las comas para obtener un arreglo con los datos
                String[] datos = linea.split(DELIMITADOR);

                // Verificamos que al menos existan los 3 datos obligatorios
                if (datos.length >= 3) {
                    Usuario u = new Usuario(datos[0], datos[1], datos[2]);

                    // Si la línea tiene los 6 datos completos
                    if (datos.length >= 6) {
                        u.setEdad(Integer.parseInt(datos[3]));
                        u.setPeso(Double.parseDouble(datos[4]));
                        u.setGenero(datos[5]);
                    }
                    // esta es la nueva columna pepe para que la revises
                    if (datos.length >= 7 && !datos[6].equalsIgnoreCase("null")) {
                        u.setNivel(datos[6]);
                    }
                    usuarios.add(u);

                    if (datos.length >= 8) {
                        if (!datos[7].equals("null")) {
                            u.setDiasDisponibles(datos[7]);
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            // Si la primera vez que ejecutamos y el archivo no existe
            System.out.println("Archivo no encontrado, se creará uno nuevo al guardar.");
        } catch (IOException e) {
            System.out.println("Error de lectura: " + e.getMessage());
        }

        return usuarios;
    }

    // Para guardar un nuevo usuario directamente al final del archivo
    public void guardarUsuario(Usuario u) {
        // El 'true' en FileWriter indica que vamos a añadir (append) al final, sin sobrescribir
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(RUTA_ARCHIVO, true))) {

            // Construimos la línea de texto separada por comas
            StringBuilder lineaCsv = new StringBuilder();
            lineaCsv.append(u.getNombre()).append(DELIMITADOR)
                    .append(u.getMatricula()).append(DELIMITADOR)
                    .append(u.getContraseña()).append(DELIMITADOR)
                    .append(u.getEdad()).append(DELIMITADOR)
                    .append(u.getPeso()).append(DELIMITADOR)
                    .append(u.getGenero()).append(DELIMITADOR)
                    .append(u.getNivel()).append(DELIMITADOR)
                    .append(u.getDiasDisponibles());

            // Escribimos la línea y damos un salto de línea para el próximo usuario
            escritor.write(lineaCsv.toString());
            escritor.newLine();

        } catch (IOException e) {
            System.out.println("Error al intentar guardar el usuario: " + e.getMessage());
        }
    }

    // Método para actualizar un usuario existente en el archivo CSV
    public void actualizarUsuario(Usuario usuarioModificado) {
        // Leemos todos los usuarios actuales
        List<Usuario> todosLosUsuarios = leerUsuarios();
        boolean encontrado = false;

        // Buscamos y reemplazamos en la memoria RAM
        for (int i = 0; i < todosLosUsuarios.size(); i++) {
            if (todosLosUsuarios.get(i).getMatricula().equals(usuarioModificado.getMatricula())) {
                todosLosUsuarios.set(i, usuarioModificado); // Lo reemplazamos en la lista
                encontrado = true;
                break;
            }
        }

        // Si lo encontramos, sobrescribimos todo el archivo CSV
        if (encontrado) {
            try (BufferedWriter escritor = new BufferedWriter(new FileWriter(RUTA_ARCHIVO, false))) {

                for (Usuario u : todosLosUsuarios) {
                    StringBuilder lineaCsv = new StringBuilder();
                    lineaCsv.append(u.getNombre()).append(DELIMITADOR)
                            .append(u.getMatricula()).append(DELIMITADOR)
                            .append(u.getContraseña()).append(DELIMITADOR)
                            .append(u.getEdad()).append(DELIMITADOR)
                            .append(u.getPeso()).append(DELIMITADOR)
                            .append(u.getGenero()).append(DELIMITADOR)
                            .append(u.getNivel()).append(DELIMITADOR)
                            .append(u.getDiasDisponibles());


                    escritor.write(lineaCsv.toString());
                    escritor.newLine();
                }
            } catch (IOException e) {
                System.out.println("Error al intentar actualizar el CSV: " + e.getMessage());
            }
        }
    }
}