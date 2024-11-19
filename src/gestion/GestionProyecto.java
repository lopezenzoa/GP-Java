package gestion;

import enums.AltaBaja;
import enums.Estado;
import exception.ProyectoNoEncontradoException;
import exception.TareaNoEncontradaException;
import exception.UsuarioExisteException;
import exception.UsuarioNoEncontradoException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import proyecto.Proyecto;
import proyecto.Tarea;
import usuario.Administrador;
import usuario.Lider;
import usuario.MiembroEquipo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class GestionProyecto {

    public GestionProyecto() {
    }

    /**
     * Este metódo da de baja un proyecto y actualiza el archivo.
     *
     * @author Emilia
     */
    public static void removeProyecto(int idProyecto) throws ProyectoNoEncontradoException {

        JSONObject proyectosJSON = null;
        JSONArray listaProyectosJSON = null;

        try {
            proyectosJSON = new JSONObject(OperacionesLectoEscritura.leer("proyectos.json"));
            listaProyectosJSON = proyectosJSON.getJSONArray("proyectos");
            boolean proyectoEncontrado = false;
            int i = 0;

            while (!proyectoEncontrado && i < listaProyectosJSON.length()) {
                Proyecto a = new Proyecto(listaProyectosJSON.getJSONObject(i));

                if (a.getId() == idProyecto) {
                    a.baja();
                    listaProyectosJSON.put(i, a.serializar());

                    // Se agrega el arreglo modificado al objeto
                    proyectosJSON.put("proyectos", listaProyectosJSON);
                    OperacionesLectoEscritura.grabar("proyectos.json", proyectosJSON);
                    proyectoEncontrado = true;
                }

                i++;
            }
            if (!proyectoEncontrado) {
                throw new ProyectoNoEncontradoException("El proyecto que quieres eliminar no existe.");
            }
        } catch (JSONException e) {
            e.printStackTrace();

        }
    }

    /**
     * Este metódo agrega un proyecto y actualiza el archivo.
     *
     * @author Emilia
     */
    public static void addProyecto(Proyecto proyecto) {
        JSONObject proyectosJSON = null;
        JSONArray listaProyectosJSON = null;
        try {
            proyectosJSON = new JSONObject(OperacionesLectoEscritura.leer("proyectos.json"));
            listaProyectosJSON = proyectosJSON.getJSONArray("proyectos");
            listaProyectosJSON.put(proyecto.serializar());
            OperacionesLectoEscritura.grabar("proyectos.json", proyectosJSON);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Este metódo serializa un arreglo de proyectos.
     *
     * @return retorna un JSONObject que contiene un JSONArray con todos los proyectos.
     * @author Emilia
     */
    public static JSONObject serializarListaProyectos(ArrayList<Proyecto> proyectos) {
        JSONObject proyectosJSON = null;
        JSONArray listaProyectos = null;
        try {
            proyectosJSON = new JSONObject();
            listaProyectos = new JSONArray();
            for (Proyecto proyecto : proyectos) {
                listaProyectos.put(proyecto.serializar());
            }

            proyectosJSON.put("proyectos", listaProyectos);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return proyectosJSON;
    }

    /**
     * Este metódo deserializa un array list de proyectos.
     *
     * @return retorna un ArrayList de Proyectos.
     * @author Emilia
     */
    public static ArrayList<Proyecto> deserializarListaProyectos(JSONObject proyectosJSON) {
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
     * Este metódo lee el contenido del archivo "proyectos.json" y lo combierte en un JSONObject.
     *
     * @return retorna un JSONObject que contiene un JSONArray con todos los proyectos.
     * @author Emilia
     */
    public static JSONObject leerArchivoProyectos() {
        JSONObject proyectos = null;
        try {
            proyectos = new JSONObject(OperacionesLectoEscritura.leer("proyectos.json"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return proyectos;
    }

    /**
     * ver solo los proyectos activos
     *
     * @return una lista con los proyectos activos
     * @author Emilia
     */
    public static ArrayList<Proyecto> verProyectosActivos() {
        ArrayList<Proyecto> listaProyectos = new ArrayList<>();
        ArrayList<Proyecto> listaProyectosActivos = new ArrayList<>();
        JSONObject proyectosJSON = null;

        try {
            proyectosJSON = leerArchivoProyectos();
            listaProyectos = deserializarListaProyectos(proyectosJSON);
            for (Object o : listaProyectos.toArray()) {
                if (((Proyecto)o).getAltaObaja() == AltaBaja.ACTIVO) {
                    listaProyectosActivos.add((Proyecto) o);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listaProyectosActivos;
    }

    /**
     * ver solo proyectos inactivos
     *
     * @return una lista con los proyectos inactivos
     * @author Emilia
     */
    public static ArrayList<Proyecto> verProyectosinactivos() {
        ArrayList<Proyecto> listaProyectos = new ArrayList<>();
        ArrayList<Proyecto> listaProyectosInactivos = new ArrayList<>();
        JSONObject proyectosJSON = null;
        try {
            proyectosJSON = leerArchivoProyectos();
            listaProyectos = deserializarListaProyectos(proyectosJSON);

            for (Object o : listaProyectos.toArray()) {
                if (((Proyecto)o).getAltaObaja() == AltaBaja.INACTIVO) {
                    listaProyectosInactivos.add((Proyecto) o);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listaProyectosInactivos;
    }

    /**
     * ver las tareas de un proyecto segun el id de la persona a la que se le asignó.
     *
     * @param idProyecto  es el id del proyecto de donde se quiere saber las tareas.
     * @param responsable es el miembro el cual se quiere saber que tareas tiene asignadas.
     */
    public static ArrayList<Tarea> tareasDeMiembro(int idProyecto, MiembroEquipo responsable) throws ProyectoNoEncontradoException {
        ArrayList<Tarea> listaTareas = new ArrayList<>();
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

                    if (!tareasArray.isEmpty()) {
                        for (int u = 0; u < tareasArray.length(); u++) {
                            Tarea tareaDeserializada = new Tarea(tareasArray.getJSONObject(u));
                            if (tareaDeserializada.getResponsable().getId() == responsable.getId() && tareaDeserializada.getAltaObaja() == AltaBaja.ACTIVO) {
                                listaTareas.add(tareaDeserializada);
                            }
                        }
                    }
                    break; // Salir del bucle una vez que encontramos el proyecto
                }
            }
            if (proyectoEncontrado) {
                // Serializar el objeto `proyectosJSON` nuevamente al archivo
                OperacionesLectoEscritura.grabar("proyectos.json", proyectosJSON);
            } else {
                throw new ProyectoNoEncontradoException("El proyecto con ID: " + idProyecto + " no se encuentra.");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return listaTareas;
    }

    /**
     * Este metodo marca como finalizado el proyecto que se envia por parametro y actualiza en archivo
     */
    public static void finalizarProyecto(Proyecto proyecto) throws ProyectoNoEncontradoException {

        JSONObject proyectosJSON = null;
        JSONArray listaProyectosJSON = null;

        try {
            proyectosJSON = new JSONObject(OperacionesLectoEscritura.leer("proyectos.json"));
            listaProyectosJSON = proyectosJSON.getJSONArray("proyectos");
            boolean proyectoEncontrado = false;
            int i = 0;

            while (!proyectoEncontrado && i < listaProyectosJSON.length()) {
                Proyecto a = new Proyecto(listaProyectosJSON.getJSONObject(i));

                if (a.equals(proyecto)) {
                    a.setEstado(Estado.FINALIZADO);
                    listaProyectosJSON.put(i, a.serializar());
                    // Se agrega el arreglo modificado al objeto
                    proyectosJSON.put("proyectos", listaProyectosJSON);
                    OperacionesLectoEscritura.grabar("proyectos.json", proyectosJSON);
                    proyectoEncontrado = true;
                }
                i++;
            }
            if (!proyectoEncontrado) {
                throw new ProyectoNoEncontradoException("El proyecto que quieres eliminar no existe.");
            }
        } catch (JSONException e) {
            e.printStackTrace();

        }
    }

    /**
     * este metodo marca como finalizada una tarea
     */
    public static void finalizarTarea(int idProyecto, Tarea tarea) throws TareaNoEncontradaException, ProyectoNoEncontradoException {
        eliminarTarea(idProyecto, tarea);
        tarea.setEstado(Estado.FINALIZADO);
        agregarTareaAlProyecto(idProyecto, tarea);

    }

    /**
     * Este metódo agrega una tarea y actualiza el archivo.
     * Exception personalizada si no se encuentra el id del proyecto.
     *
     * @param idProyecto es el id del proyecto en donde esta a tarea.
     * @param nuevaTarea es la tarea que se agrega.
     * @author Emilia
     */
    public static void agregarTareaAlProyecto(int idProyecto, Tarea nuevaTarea) throws ProyectoNoEncontradoException {
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Este metódo da de baja una tarea y actualiza el archivo.
     * Exception personalizada si no encuentra el id del proyecto.
     * Exception personalizada si no se encuentra el id de la tarea.
     *
     * @param idProyecto es el id del proyecto en donde esta a tarea.
     * @param tarea      es el id de la tarea que se va a der de baja.
     * @author Emilia
     */
    public static void darDeBajaTarea(int idProyecto, Tarea tarea) throws TareaNoEncontradaException, ProyectoNoEncontradoException {
        eliminarTarea(idProyecto, tarea);
        tarea.setAltaObaja(AltaBaja.INACTIVO);
        agregarTareaAlProyecto(idProyecto, tarea);
    }

    /**
     * Este metódo agrega un miembro al equipo.
     *
     * @param idProyecto    es el id del proyecto al que se quiere agregar el miembro.
     * @param miembroEquipo es el miembro del equipo que se quiere agregar.
     * @author Enzo.
     */
    public static void agregarMiembroAlEquipo(int idProyecto, MiembroEquipo miembroEquipo) throws UsuarioExisteException {
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
                    Proyecto proyecto = new Proyecto(proyectoJSON);
                    proyectoEncontrado = true;

                    // Crear el JSONObject del nuevo miembro
                    JSONObject miembroJSON = miembroEquipo.serializar();

                    // Agregar el miembro al proyecto
                    JSONArray miembrosJSON = proyectoJSON.getJSONArray("equipo");

                    if (proyecto.getEquipo().contains(miembroEquipo)) {
                        throw new UsuarioExisteException("El miembro que quieres agregar al proyecto ya existe.");
                    }

                    miembrosJSON.put(miembroJSON);

                    break; // Salir del bucle una vez que encontramos el proyecto
                }
            }

            if (proyectoEncontrado) {
                // Serializar el objeto `proyectosJSON` nuevamente al archivo
                OperacionesLectoEscritura.grabar("proyectos.json", proyectosJSON);
            } else {
                System.out.println("Proyecto no encontrado con ID: " + idProyecto);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Este metódo agrega un miembro al equipo.
     *
     * @param idProyecto      es el id del proyecto al que se quiere agregar el miembro.
     * @param idMiembroEquipo es el miembro del equipo que se quiere agregar.
     * @author Enzo.
     */
    public static void eliminarMiembroDelEquipo(int idProyecto, int idMiembroEquipo) throws UsuarioNoEncontradoException {
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
                    JSONArray equipoJSON = proyectoJSON.getJSONArray("equipo");
                    boolean miembroEncontrado = false;

                    // Buscar el miembro por su ID y eliminarlo si se encuentra
                    for (int j = 0; j < equipoJSON.length(); j++) {
                        JSONObject miembroJSON = equipoJSON.getJSONObject(j);

                        if (miembroJSON.getInt("id") == idMiembroEquipo) {
                            equipoJSON.remove(j); // Eliminar el usuario
                            miembroEncontrado = true;
                            break; // Salir del bucle una vez que encontramos el miembro
                        }
                    }

                    if (!miembroEncontrado) {
                        throw new UsuarioNoEncontradoException("El Miembro no pudo ser encontrado dentro del equipo");
                    }

                    break; // Salir del bucle una vez que encontramos el proyecto
                }
            }

            if (proyectoEncontrado) {
                // Serializar el objeto `proyectosJSON` nuevamente al archivo
                OperacionesLectoEscritura.grabar("proyectos.json", proyectosJSON);
            } else {
                System.out.println("Proyecto no encontrado con ID: " + idProyecto);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * elimina una tarea del proyecto
     */
    private static void eliminarTarea(int idProyecto, Tarea tarea) throws TareaNoEncontradaException, ProyectoNoEncontradoException {
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

                        if (tareaJSON.getInt("id") == tarea.getId()) {
                            tareasArray.remove(j); // Eliminar la tarea
                            tareaEncontrada = true;
                            break; // Salir del bucle una vez que encontramos la tarea
                        }
                    }

                    if (!tareaEncontrada) {
                        throw new TareaNoEncontradaException("Tarea no encontrada con ID: " + tarea.getId());
                    }

                    break; // Salir del bucle una vez que encontramos el proyecto
                }
            }

            if (proyectoEncontrado) {
                // Serializar el objeto `proyectosJSON` nuevamente al archivo
                OperacionesLectoEscritura.grabar("proyectos.json", proyectosJSON);
            } else {
                throw new ProyectoNoEncontradoException("El proyecto con ID: " + idProyecto + " no se encuentra.");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Devuelve una lista con las tareas en estado pendientes dentro del archivo proyectos.json
     * @param idProyecto es el ID del proyecto donde se consulta las tareas.
     * @return una lista de tipo parametrizado Tarea con las tareas pendientes.
     * @author Enzo.
     */
    public static ArrayList<Tarea> verTareasPendientes(int idProyecto) throws ProyectoNoEncontradoException {
        JSONObject proyectosJSON = null;
        JSONArray proyectosArray = null;
        ArrayList<Tarea> tareasPendientes = new ArrayList<>();

        try {
            proyectosJSON = new JSONObject(OperacionesLectoEscritura.leer("proyectos.json"));
            proyectosArray = proyectosJSON.getJSONArray("proyectos");

            for (int i = 0; i < proyectosArray.length(); i++) {
                JSONObject proyectoJSON = proyectosArray.getJSONObject(i);
                Proyecto p = new Proyecto(proyectoJSON);

                if (p.getId() == idProyecto) {
                    JSONArray tareasJSON = proyectoJSON.getJSONArray("tareas");

                    if (!tareasJSON.isEmpty()) {
                        for (int j = 0; j < tareasJSON.length(); j++) {
                            JSONObject tareaJSON = tareasJSON.getJSONObject(j);
                            Tarea t = new Tarea(tareaJSON);

                            if (t.getAltaObaja() == AltaBaja.ACTIVO && t.getEstado().equals(Estado.PENDIENTE)) {
                                tareasPendientes.add(t);
                            }
                        }
                    }

                    break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return tareasPendientes;
    }

    /**
     * Devuelve una lista con las tareas en estado finalizado dentro del archivo proyectos.json
     * @param idProyecto es el ID del proyecto donde se consulta las tareas.
     * @return una lista de tipo parametrizado Tarea con las tareas finalizadas.
     * @author Enzo.
     */
    public static ArrayList<Tarea> verTareasFinalizadas(int idProyecto) throws ProyectoNoEncontradoException {
        JSONObject proyectosJSON = null;
        JSONArray proyectosArray = null;
        ArrayList<Tarea> tareasFinalizadas = new ArrayList<>();

        try {
            proyectosJSON = new JSONObject(OperacionesLectoEscritura.leer("proyectos.json"));
            proyectosArray = proyectosJSON.getJSONArray("proyectos");

            for (int i = 0; i < proyectosArray.length(); i++) {
                JSONObject proyectoJSON = proyectosArray.getJSONObject(i);
                Proyecto p = new Proyecto(proyectoJSON);

                if (p.getId() == idProyecto) {
                    JSONArray tareasJSON = proyectoJSON.getJSONArray("tareas");

                    if (!tareasJSON.isEmpty()) {
                        for (int j = 0; j < tareasJSON.length(); j++) {
                            JSONObject tareaJSON = tareasJSON.getJSONObject(j);
                            Tarea t = new Tarea(tareaJSON);

                            if (t.getAltaObaja() == AltaBaja.ACTIVO && t.getEstado() == Estado.FINALIZADO) {
                                tareasFinalizadas.add(t);
                            }
                        }
                    }

                    break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return tareasFinalizadas;
    }

    /**
     * Busca un proeycto dado su ID dentro del archivo proyectos.json
     * @param idProyecto es el ID del proyecto que se busca.
     * @author Enzo.
     * */
    public static Proyecto buscarProyectoPorID(int idProyecto) throws ProyectoNoEncontradoException {
        JSONObject proyectosJSON = null;
        JSONArray proyectosArray = null;

        try {
            proyectosJSON = new JSONObject(OperacionesLectoEscritura.leer("proyectos.json"));
            proyectosArray = proyectosJSON.getJSONArray("proyectos");

            for (int i = 0; i < proyectosArray.length(); i++) {
                JSONObject proyectoJSON = proyectosArray.getJSONObject(i);
                Proyecto p = new Proyecto(proyectoJSON);

                if (p.getId() == idProyecto) {
                    return p;
                }
            }

            throw new ProyectoNoEncontradoException("El proyecto con el ID: " + idProyecto + " no se encuentra");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Busca una tarea dentro del archivo proyectos.json, dado su ID.
     * @param idProyecto es el ID del proyecto en el que se encuentra la tarea.
     * @param idTarea es el ID de la tarea que se busca.
     * @return un objeto de tipo Tarea.
     * @author Enzo.
     * */
    public static Tarea buscarTareaPorID(int idProyecto, int idTarea) throws ProyectoNoEncontradoException, TareaNoEncontradaException {
        JSONObject proyectosJSON = null;
        JSONArray proyectosArray = null;

        try {
            proyectosJSON = new JSONObject(OperacionesLectoEscritura.leer("proyectos.json"));
            proyectosArray = proyectosJSON.getJSONArray("proyectos");

            for (int i = 0; i < proyectosArray.length(); i++) {
                JSONObject proyectoJSON = proyectosArray.getJSONObject(i);
                Proyecto p = new Proyecto(proyectoJSON);

                if (p.getId() == idProyecto) {
                    JSONArray tareasJSON = proyectoJSON.getJSONArray("tareas");

                    for (int j = 0; j < tareasJSON.length(); j++) {
                        JSONObject tareaJSON = tareasJSON.getJSONObject(j);
                        Tarea t = new Tarea(tareaJSON);

                        if (t.getId() == idTarea) {
                            return t;
                        }
                    }

                    throw new TareaNoEncontradaException("La tarea con el ID " + idTarea + " no se encuentra dentro del proyecto");
                }
            }

            throw new ProyectoNoEncontradoException("El proyecto con el ID: " + idProyecto + " no se encuentra");
        } catch (JSONException | TareaNoEncontradaException e) {
            e.printStackTrace();
        }

        return null;
    }
}
