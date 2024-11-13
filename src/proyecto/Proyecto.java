package proyecto;

import java.util.HashSet;
import java.util.LinkedList;

import usuario.Administrador;
import usuario.Lider;
import usuario.MiembroEquipo;

import enums.Estado;
import usuario.Usuario;

import java.util.Objects;

public class Proyecto<E extends Usuario> {
    private int id;
    private Administrador administrador;
    private Lider lider;
    private HashSet<E> equipo;
    private LinkedList<Tarea> tareas;
    private String nombre;
    private Estado estado;

    // Constructor principal
    public Proyecto(Administrador administrador, Lider lider, String nombre) {
        this.id = (int) (Math.random() * 100000 + 1);
        this.administrador = administrador;
        this.lider = lider;
        this.equipo = new HashSet<>();
        this.tareas = new LinkedList<>();
        this.nombre = nombre;
        this.estado = Estado.PENDIENTE;
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

    public HashSet<E> getEquipo() {
        return equipo;
    }

    public void setEquipo(HashSet<E> equipo) {
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
        return "Proyecto {" + "\n" +
                "    ID            : " + id + "\n" +
                "    Administrador : " + administrador + "\n" +
                "    Líder         : " + lider + "\n" +
                "    Miembros      : " + obtenerIDsDelEquipo() + "\n" +
                "    Tareas        : " + obtenerIDsDeTareas() + "\n" +
                "    Nombre        : '" + nombre + '\'' + "\n" +
                "    Estado        : " + estado + "\n" +
                '}';
    }

    /**
     * Retorna una coleccion con los IDs de las tareas del proyecto.
     * @return un Set con los IDs de las tareas.
     * @author Enzo.
     * */
    public HashSet<Integer> obtenerIDsDeTareas() {
        HashSet<Integer> IDs = new HashSet<>();

        for (Tarea tarea : tareas)
            IDs.add(tarea.getId());

        return IDs;
    }

    /**
     * Retorna una coleccion con los IDs de los miembros del equipo involucrados en el proyecto.
     * @return un Set con los IDs de los miembros.
     * @author Enzo.
     * */
    public HashSet<Integer> obtenerIDsDelEquipo() {
        HashSet<Integer> IDs = new HashSet<>();

        for (E miembro : equipo)
            IDs.add(miembro.getId());

        return IDs;
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
    public boolean agregarMiembro(E miembro) {
        return equipo.add(miembro); // Agrega al equipo y devuelve true si se añadió, false si ya existía
    }

    // Método para verificar si un miembro está en el equipo
    public boolean existeMiembro(E miembro) {
        return equipo.contains(miembro); // Devuelve true si el miembro ya está en el equipo
    }

    // Método para eliminar un miembro del equipo
    public boolean eliminarMiembro(E miembro) {
        return equipo.remove(miembro); // Devuelve true si el miembro fue eliminado, false si no se encontró
    }
}





