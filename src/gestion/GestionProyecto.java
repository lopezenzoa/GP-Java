package gestion;

import exception.ProyectoNoEncontradoException;
import exception.TareaNoEncontradaException;
import exception.UsuarioNoEncontradoException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import proyecto.Proyecto;
import proyecto.Tarea;
import usuario.Administrador;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class GestionProyecto {

    private String nomJSON = "proyectos.json";

    public GestionProyecto() {}


    /**
     * Este metódo serializa un arreglo de proyectos.
     * @return retorna un JSONObject que contiene un JSONArray con todos los proyectos.
     * @author Emilia
     */
    public JSONObject serializarListaProyectos(ArrayList<Proyecto> proyectos) {
        JSONObject proyectosJSON = null;
        JSONArray listaProyectos = null;
        try{
            proyectosJSON = new JSONObject();
            listaProyectos = new JSONArray();
            for (Proyecto proyecto : proyectos) {
                listaProyectos.put(proyecto.serializar());
            }

            proyectosJSON.put("proyectos", listaProyectos);

        }catch (JSONException e) {
            e.printStackTrace();
        }

        return proyectosJSON;
    }

    /**
     * Este metódo lee el contenido del archivo "proyecto.json" y lo combuerte en un JSONObject.
     * @return retorna un ArrayList de Proyectos.
     * @author
     */
    public ArrayList<Proyecto> deserializarListaProyectos(JSONObject proyectosJSON) {
        ArrayList<Proyecto> proyectos = new ArrayList<>();

        try {
            // Obtener el JSONArray que contiene los proyectos
            JSONArray listaProyectos = proyectosJSON.getJSONArray("proyectos");

            for (int i = 0; i < listaProyectos.length(); i++) {
                // Obtener cada JSONObject del JSONArray
                JSONObject proyectoJSON = listaProyectos.getJSONObject(i);

                // Usar tu método existente que deserializa un solo proyecto
                Proyecto proyecto = new Proyecto(proyectoJSON);

                // Añadir el proyecto deserializado a la lista
                proyectos.add(proyecto);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return proyectos;
    }

    /**
     * Este metódo lee el contenido del archivo "proyecto.json" y lo combierte en un JSONObject.
     * @return retorna un JSONObject que contiene un JSONArray con todos los proyectos.
     */
    public JSONObject leerArchivoProyectos() {
        JSONObject proyectos = null;
        try{
            proyectos = new JSONObject(OperacionesLectoEscritura.leer("proyecto.json"));
        }catch (JSONException e){
            e.printStackTrace();
        }
        return proyectos;
    }

    /**
     * Este metódo da de baja un proyecto y actualiza el archivo.
     * @author Emilia
     */
    public static void eliminarUsuario(Administrador administrador) throws UsuarioNoEncontradoException {
        JSONObject usuariosJSON = null;
        JSONArray adminsJSON = null;

        try {
            usuariosJSON = new JSONObject(OperacionesLectoEscritura.leer("usuarios.json"));
            adminsJSON = usuariosJSON.getJSONArray("administradores");
            boolean adminEncontrado = false;
            int i = 0;

            while (!adminEncontrado && i < adminsJSON.length()) {
                Administrador a = new Administrador(adminsJSON.getJSONObject(i));

                if (a.equals(administrador)) {
                    a.baja();

                    // Se reemplaza el usuario en el arreglo
                    adminsJSON.remove(i);
                    adminsJSON.put(i, a.serializar());

                    // Se agrega el arreglo modificado al objeto
                    usuariosJSON.put("administradores", adminsJSON);
                    OperacionesLectoEscritura.grabar("usuarios.json", usuariosJSON);
                }

                i++;
            }
            if(!adminEncontrado){
                throw new UsuarioNoEncontradoException("El usuario que quieres eliminar no existe.");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Este metódo agrega un proyecto y actualiza el archivo.
     * @author Emilia
     */


    /**
     * Este metódo agrega una tarea y actualiza el archivo.
     * Exception personalizada si no se encuentra el id del proyecto.
     * @param idProyecto es el id del proyecto en donde esta a tarea.
     * @param nuevaTarea es la tarea que se agrega.
     * @author Emilia
     */
    public void agregarTareaAlProyecto(int idProyecto, Tarea nuevaTarea) throws ProyectoNoEncontradoException {
        JSONObject proyectosJSON = null;
        JSONArray proyectosArray = null;
        try {
            proyectosJSON = leerArchivoProyectos();
            proyectosArray = proyectosJSON.getJSONArray("proyectos");
            boolean proyectoEncontrado = false;

            for (int i = 0; i < proyectosArray.length(); i++) {
                JSONObject proyectoJSON = proyectosArray.getJSONObject(i);

                // Suponiendo que cada proyecto tiene un ID
                if (proyectoJSON.getInt("id") == idProyecto) {
                    proyectoEncontrado = true;

                    // Crear el JSONObject de la nueva tarea
                    JSONObject tareaJSON = nuevaTarea.serializar();

                    // Agregar la tarea al proyecto
                    JSONArray tareasArray = proyectoJSON.getJSONArray("tareas");
                    tareasArray.put(tareaJSON);

                    break; // Salir del bucle una vez que encontramos el proyecto
                }
            }

            if (!proyectoEncontrado) {
                throw new ProyectoNoEncontradoException("El proyecto con ID: " + idProyecto + " no ha sido encontrado.");
            }
                OperacionesLectoEscritura.grabar("proyectos.json", proyectosJSON);
        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    /**
     * Este metódo da de baja una tarea y actualiza el archivo.
     * Exception personalizada si no encuentra el id del proyecto.
     * Exception personalizada si no se encuentra el id de la tarea.
     * @param idProyecto es el id del proyecto en donde esta a tarea.
     * @param idTarea es el id de la tarea que se va a der de baja.
     * @author Emilia
     */
    public void darDeBajaTarea(int idProyecto, int idTarea) throws TareaNoEncontradaException, ProyectoNoEncontradoException {
        JSONObject proyectosJSON = null;
        JSONArray proyectosArray = null;

        try {
            proyectosJSON = leerArchivoProyectos();
            proyectosArray = proyectosJSON.getJSONArray("proyectos");
            boolean proyectoEncontrado = false;

            for (int i = 0; i < proyectosArray.length(); i++) {
                JSONObject proyectoJSON = proyectosArray.getJSONObject(i);

                // Verificar si el proyecto coincide con el ID proporcionado
                if (proyectoJSON.getInt("id") == idProyecto) {
                    proyectoEncontrado = true;

                    // Obtener el arreglo de tareas del proyecto
                    JSONArray tareasArray = proyectoJSON.getJSONArray("tareas");
                    boolean tareaEncontrada = false;

                    // Buscar la tarea por su ID y eliminarla si se encuentra
                    for (int j = 0; j < tareasArray.length(); j++) {
                        JSONObject tareaJSON = tareasArray.getJSONObject(j);

                        if (tareaJSON.getInt("id") == idTarea) {
                            tareasArray.remove(j); // Eliminar la tarea
                            tareaEncontrada = true;
                            break; // Salir del bucle una vez que encontramos la tarea
                        }
                    }

                    if (!tareaEncontrada) {
                       throw new TareaNoEncontradaException("Tarea no encontrada con ID: " + idTarea);
                    }

                    break; // Salir del bucle una vez que encontramos el proyecto
                }
            }

            if (proyectoEncontrado) {
                // Serializar el objeto `proyectosJSON` nuevamente al archivo
                guardarProyectosEnArchivo(proyectosJSON);
            } else {
                throw new ProyectoNoEncontradoException("El proyecto con ID: " + idProyecto + " no se encuentra.");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * ver solo proyectos activos
     */

    /**
     * ver solo proyectos inactivos
     */


    /**
     * Modificar el estado de un proyecto
     */

    /**
     *Modificar estado de una tarea
     */

    /**
     * ver las tareas segun a quien le pertenecen
     */

    /**
     * ver solo tareas activas
     */

    /**
     * ver solo tareas inactivas
     */

    /**
     * asignarle una tarea a un miembro
     */
}
