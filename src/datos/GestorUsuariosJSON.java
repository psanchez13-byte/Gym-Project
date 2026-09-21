package datos;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import modelo.Usuario;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GestorUsuariosJSON {
    private final String RUTA_ARCHIVO = "usuarios.json";
    private Gson gson;

   //CREAMOS EL objeto JSON
    public GestorUsuariosJSON(){
        this.gson = new Gson();
    }
    //PARA LEER:
    public List<Usuario> leerUsuarios(){
        try(Reader reader = new FileReader(RUTA_ARCHIVO)){
            // Le decimos a Gson exactamente qué tipo de lista estamos esperando
            Type tipoLista = new TypeToken<ArrayList<Usuario>>(){}.getType();
            List<Usuario> usuarios = gson.fromJson(reader, tipoLista);

         // Si el archivo está vacío, devolvemos una lista nueva en lugar de "null"
            return usuarios !=null ? usuarios : new ArrayList<>();
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        }catch (IOException e){
            e.printStackTrace();
            return new ArrayList<>();
        }

    }
  //metodo necesario para agregar usuarios al archivo
    public void guardarUsuarios(Usuario nuevoUsuario){
        // Primero descargamos la lista actual para no borrar a los antiguos
        List<Usuario> usuariosActuales = leerUsuarios();

        //agregamos +
        usuariosActuales.add(nuevoUsuario);

        try (Writer escribir = new FileWriter(RUTA_ARCHIVO)){
            gson.toJson(usuariosActuales, escribir);

        }catch (IOException e){
            System.out.println("Error al intentar guardar el usuario: " + e.getMessage());
        }

    }

}
