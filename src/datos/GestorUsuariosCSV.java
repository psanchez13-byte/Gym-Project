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

                    // Si la línea tiene los 8 datos completos, parseamos los opcionales
                    if (datos.length >= 8) {
                        u.setEdad(Integer.parseInt(datos[3]));
                        u.setAltura(Double.parseDouble(datos[4]));
                        u.setPeso(Double.parseDouble(datos[5]));
                        u.setGenero(datos[6]);
                        u.setDiasActivoFis(Integer.parseInt(datos[7]));
                    }

                    usuarios.add(u);
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
                    .append(u.getAltura()).append(DELIMITADOR)
                    .append(u.getPeso()).append(DELIMITADOR)
                    .append(u.getGenero()).append(DELIMITADOR)
                    .append(u.getDiasActivoFis());

            // Escribimos la línea y damos un salto de línea para el próximo usuario
            escritor.write(lineaCsv.toString());
            escritor.newLine();

        } catch (IOException e) {
            System.out.println("Error al intentar guardar el usuario: " + e.getMessage());
        }
    }
}