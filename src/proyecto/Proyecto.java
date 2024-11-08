package proyecto;

import java.util.HashSet;
import java.util.LinkedList;

import usuario.Administrador;
import usuario.Lider;
import usuario.MiembroEquipo;

import enums.Estado;

import java.util.Objects;

public class Proyecto {
    private int id;
    private Administrador administrador;
    private Lider lider;
    private HashSet<MiembroEquipo> equipo;
    private LinkedList<Tarea> tareas;
    private String nombre;
    private Estado estado;

    // Constructores
    public Proyecto(Administrador administrador, Lider lider, String nombre) {
        this.id = (int) (Math.random() * 100 + 1);
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
        return "Proyecto{" +
                "id=" + id +
                ", administrador=" + administrador +
                ", lider=" + lider +
                ", equipo=" + equipo +
                ", tareas=" + tareas +
                ", nombre='" + nombre + '\'' +
                ", estado=" + estado +
                '}';
    }
}
