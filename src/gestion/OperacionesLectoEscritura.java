package gestion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class OperacionesLectoEscritura {

    /// es una clase con metodos ESTATICOS para leer y escribir en un archivo JSON

    public OperacionesLectoEscritura() {
    }

    /**
     * Graba un JSONObject en un archivo.
     * @param nombreArchivo es el nombre del archivo en el que se quiere grabar.
     * @author Emlia.
     * */
    public static void grabar(String nombreArchivo, JSONObject jsonObject) {
        try {
            FileWriter fileWriter = new FileWriter(nombreArchivo);
            fileWriter.write(jsonObject.toString());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Lee un archivo.
     * @return hago que retorne un tokener para que sea util para todos los casos.
     * @param nombreArchivo es el nombre del archivo que se quiere leer.
     * @author Emlia.
     * */
    public static JSONTokener leer(String nombreArchivo) {
        JSONTokener jsonTokener = null;

        try {
            jsonTokener = new JSONTokener(new FileReader(nombreArchivo));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return jsonTokener;
    }

    /**
     * Limpia los archivos. (Solo para debugging)
     * @author Enzo.
     * */
    public static void limpiarArchivos() {
        JSONObject proyectosJSON = null;
        JSONObject usuariosJSON = null;

        try {
            proyectosJSON = new JSONObject();
            usuariosJSON = new JSONObject();

            JSONArray proyectos = new JSONArray();
            JSONArray miembros = new JSONArray();

            proyectosJSON.put("proyectos", proyectos);

            usuariosJSON.put("miembrosEquipo", miembros);
            usuariosJSON.put("administradores", miembros);
            usuariosJSON.put("lideres", miembros);

            FileWriter fileWriter = new FileWriter("usuarios.json");
            fileWriter.write(usuariosJSON.toString());
            fileWriter.close();

            fileWriter = new FileWriter("proyectos.json");
            fileWriter.write(proyectosJSON.toString());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
