package proyecto;

import java.util.HashSet;
import java.util.LinkedList;

import enums.AltaBaja;
import interfaces.ABMLable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import usuario.Administrador;
import usuario.Lider;

import enums.Estado;
import usuario.MiembroEquipo;

import java.util.List;
import java.util.Objects;

public class Proyecto implements ABMLable<Proyecto> {
    private int id;
    private Administrador administrador;
    private Lider lider;
    private HashSet<MiembroEquipo> equipo;
    private LinkedList<Tarea> tareas;
    private String nombre;
    private Estado estado;
    private AltaBaja altaObaja;

    // Constructor principal
    public Proyecto(Administrador administrador, Lider lider, String nombre) {
        this.id = (int) (Math.random() * 100000 + 1);
        this.administrador = administrador;
        this.lider = lider;
        this.equipo = new HashSet<>();
        this.tareas = new LinkedList<>();
        this.nombre = nombre;
        this.estado = Estado.PENDIENTE;
        this.altaObaja = AltaBaja.ACTIVO;
    }

    public Proyecto(JSONObject jsonObject) {
        try {
            this.id = jsonObject.getInt("id");
            this.administrador = new Administrador(jsonObject.getJSONObject("administrador"));
            this.lider = new Lider(jsonObject.getJSONObject("lider"));
            this.altaObaja = AltaBaja.valueOf(jsonObject.getString("altaObaja"));
            this.equipo = new HashSet<>();
            JSONArray equipoJSON = jsonObject.getJSONArray("equipo");

            for (int i = 0; i < equipoJSON.length(); i++) {
                JSONObject miembroJSON = equipoJSON.getJSONObject(i);
                equipo.add(new MiembroEquipo(miembroJSON));
            }

            this.tareas = new LinkedList<>();
            JSONArray tareasJSON = jsonObject.getJSONArray("tareas");

            for (int i = 0; i < tareasJSON.length(); i++) {
                JSONObject tareaJSON = tareasJSON.getJSONObject(i);
                tareas.add(new Tarea(tareaJSON));
            }

            this.nombre = jsonObject.getString("nombre");

            String estadoJSON = jsonObject.getString("estado");
            this.estado = Estado.valueOf(estadoJSON);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Administrador getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
    }

    public Lider getLider() {
        return lider;
    }

    public void setLider(Lider lider) {
        this.lider = lider;
    }

    public HashSet<MiembroEquipo> getEquipo() {
        return equipo;
    }

    public void setEquipo(HashSet<MiembroEquipo> equipo) {
        this.equipo = equipo;
    }

    public LinkedList<Tarea> getTareas() {
        return tareas;
    }

    public void setTareas(LinkedList<Tarea> tareas) {
        this.tareas = tareas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public AltaBaja getAltaObaja() {
        return altaObaja;
    }

    public void setAltaObaja(AltaBaja altaObaja) {
        this.altaObaja = altaObaja;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Proyecto proyecto = (Proyecto) o;
        return id == proyecto.id && Objects.equals(administrador, proyecto.administrador) && Objects.equals(lider, proyecto.lider) && Objects.equals(equipo, proyecto.equipo) && Objects.equals(tareas, proyecto.tareas) && Objects.equals(nombre, proyecto.nombre) && estado == proyecto.estado;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, administrador, lider, equipo, tareas, nombre, estado);
    }

    @Override
    public String toString() {
        return "Proyecto " + "\n" +
                "    ID            : " + id + "\n" +
                "    Nombre        : '" + nombre + '\'' + "\n" +
                "    Estado        : " + estado + "\n" +
                "    Administrador : " + administrador.getNombre() + " " + administrador.getApellido() + "\n" +
                "    Líder         : " + lider.getNombre() + " " + lider.getApellido() + "\n" +
                "    Miembros      : " + nombresToString(obtenerNombresDelEquipo()) + "\n" +
                "    Tareas        : " + tareasToString(obtenerTareas()) + "\n";
    }

    /**
     * Retorna una coleccion con los IDs de las tareas del proyecto.
     * @return un Set con los IDs de las tareas.
     * @author Enzo.
     * */
    public HashSet<String> obtenerTareas() {
        HashSet<String> descripcionTarea = new HashSet<>();

        for (Tarea tarea : tareas)
            descripcionTarea.add(tarea.getTitulo());

        return descripcionTarea;
    }
    public String tareasToString(HashSet<String> tareas){
        StringBuilder tareasToString = new StringBuilder();

        for (String tarea : tareas) {
            if (tareasToString.length() > 0) {
                tareasToString.append(", ");
            }
            tareasToString.append(tarea);
        }
        return tareasToString.toString();
    }

    /**
     * Retorna una coleccion con los IDs de los miembros del equipo involucrados en el proyecto.
     * @return un Set con los IDs de los miembros.
     * @author Enzo.
     * */
    public HashSet<String> obtenerNombresDelEquipo() {
        HashSet<String> nombres = new HashSet<>();

        for (MiembroEquipo miembro : equipo)
            nombres.add(miembro.getNombre());

        return nombres;
    }

    public String nombresToString(HashSet<String> nombres){
        StringBuilder nombresToString = new StringBuilder();

        for (String nombre : nombres) {
            if (nombresToString.length() > 0) {
                nombresToString.append(", ");
            }
            nombresToString.append(nombre);
        }
        return nombresToString.toString();
    }


    // Método para agregar una tarea a la lista de tareas
    public boolean agregarTarea(Tarea tarea) {
        if (!tareas.contains(tarea)) {
            tareas.add(tarea);
            return true; // Tarea agregada con éxito
        }
        return false; // La tarea ya existía en la lista
    }

    // Método para eliminar una tarea de la lista de tareas
    public boolean eliminarTarea(Tarea tarea) {
        return tareas.remove(tarea); // Devuelve true si la tarea se eliminó, false si no se encontró
    }

    // Método para eliminar una tarea por su ID
    public boolean eliminarTareaPorId(int id) {
        Tarea tareaAEliminar = buscarTareaPorId(id);
        if (tareaAEliminar != null) {
            tareas.remove(tareaAEliminar);
            return true; // Tarea eliminada con éxito
        }
        return false; // No se encontró la tarea con el ID especificado
    }

    // Método para buscar si una tarea existe en la lista
    public boolean existeTarea(Tarea tarea) {
        return tareas.contains(tarea); // Devuelve true si la tarea está en la lista, false en caso contrario
    }

    // Método para buscar si una tarea existe en la lista por ID
    public boolean existeTareaPorId(int id) {
        return buscarTareaPorId(id) != null; // Devuelve true si la tarea se encontró, false si no
    }

    // Método auxiliar para buscar una tarea por su ID
    private Tarea buscarTareaPorId(int id) {
        for (Tarea tarea : tareas) {
            if (tarea.getId() == id) {
                return tarea; // Retorna la tarea si se encontró
            }
        }
        return null; // Retorna null si no se encontró
    }

    // Método para agregar un miembro al equipo
    public boolean agregarMiembro(MiembroEquipo miembro) {
        return equipo.add(miembro); // Agrega al equipo y devuelve true si se añadió, false si ya existía
    }

    // Método para verificar si un miembro está en el equipo
    public boolean existeMiembro(MiembroEquipo miembro) {
        return equipo.contains(miembro); // Devuelve true si el miembro ya está en el equipo
    }

    // Método para eliminar un miembro del equipo
    public boolean eliminarMiembro(MiembroEquipo miembro) {
        return equipo.remove(miembro); // Devuelve true si el miembro fue eliminado, false si no se encontró
    }

    /**
     * Serializa la clase Proyecto.
     * @return un objeto de tipo JSONObject con los atributos del proyecto.
     * @author Enzo.
     * */
    public JSONObject serializar() {
        JSONObject proyectoJSON = null;

        try {
            proyectoJSON = new JSONObject();
            JSONArray equipoJSON = new JSONArray();
            JSONArray tareasJSON = new JSONArray();

            proyectoJSON.put("id", id);
            proyectoJSON.put("administrador", administrador.serializar());
            proyectoJSON.put("lider", lider.serializar());
            proyectoJSON.put("altaObaja", altaObaja.toString());
            for (MiembroEquipo miembro : equipo)
                equipoJSON.put(miembro.serializar());

            proyectoJSON.put("equipo", equipoJSON);

            for (Tarea t : tareas)
                tareasJSON.put(t.serializar());

            proyectoJSON.put("tareas", tareasJSON);
            proyectoJSON.put("nombre", nombre);
            proyectoJSON.put("estado", estado.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return proyectoJSON;
    }

    @Override
    public void alta() {
        this.altaObaja = AltaBaja.ACTIVO;
    }

    @Override
    public void baja() {
        this.altaObaja = AltaBaja.INACTIVO;
    }

    @Override
    public void modificar(Proyecto nuevoDato) {
        this.id = nuevoDato.id;
        this.estado = nuevoDato.estado;
        this.administrador = nuevoDato.administrador;
        this.altaObaja = nuevoDato.altaObaja;
        this.lider = nuevoDato.lider;
        this.equipo = nuevoDato.equipo;
        this.tareas = nuevoDato.tareas;
        this.nombre = nuevoDato.nombre;
    }

}





