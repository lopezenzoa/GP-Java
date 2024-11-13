package proyecto;

import java.util.HashSet;
import java.util.LinkedList;

import usuario.Administrador;
import usuario.Lider;
import usuario.MiembroEquipo;

import enums.Estado;
import usuario.Usuario;

import java.util.Objects;

public class Proyecto {
    private int id;
    private Administrador administrador;
    private Lider lider;
    private HashSet<Integer> equipo;
    private LinkedList<Integer> tareas;
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



    // Getters y Setters


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

    public HashSet<Integer> getEquipo() {
        return equipo;
    }

    public void setEquipo(HashSet<Integer> equipo) {
        this.equipo = equipo;
    }

    public LinkedList<Integer> getTareas() {
        return tareas;
    }

    public void setTareas(LinkedList<Integer> tareas) {
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
                "    Miembros      : " + equipo + "\n" +
                "    Tareas        : " + tareas + "\n" +
                "    Nombre        : '" + nombre + '\'' + "\n" +
                "    Estado        : " + estado + "\n" +
                '}';
    }


    // Método para agregar una tarea a la lista de tareas
    public boolean agregarTarea(Integer tarea) {
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
        Integer tareaAEliminar = buscarTareaPorId(id);
        if (tareaAEliminar != null) {
            tareas.remove(tareaAEliminar);
            return true; // Tarea eliminada con éxito
        }
        return false; // No se encontró la tarea con el ID especificado
    }

    // Método para buscar si una tarea existe en la lista
    public boolean existeTarea(Tarea tarea) {
        return tareas.contains(tarea.getId()); // Devuelve true si la tarea está en la lista, false en caso contrario
    }

    // Método para buscar si una tarea existe en la lista por ID
    public boolean existeTareaPorId(int id) {
        return buscarTareaPorId(id) != null; // Devuelve true si la tarea se encontró, false si no
    }

    // Método auxiliar para buscar una tarea por su ID
    private Integer buscarTareaPorId(int id) {
        for (Integer tarea : tareas) {
            if (tarea == id) {
                return tarea; // Retorna la tarea si se encontró
            }
        }
        return null; // Retorna null si no se encontró
    }

    // Método para agregar un miembro al equipo
    public boolean agregarMiembro(Integer miembro) {
        return equipo.add(miembro); // Agrega al equipo y devuelve true si se añadió, false si ya existía
    }

    // Método para verificar si un miembro está en el equipo
    public boolean existeMiembro(Integer miembro) {
        return equipo.contains(miembro); // Devuelve true si el miembro ya está en el equipo
    }

    // Método para eliminar un miembro del equipo
    public boolean eliminarMiembro(Integer miembro) {
        return equipo.remove(miembro); // Devuelve true si el miembro fue eliminado, false si no se encontró
    }




}





