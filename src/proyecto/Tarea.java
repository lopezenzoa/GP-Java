package proyecto;

import enums.Estado;
import usuario.MiembroEquipo;

import java.util.Objects;

public class Tarea {
    private int id;
    private String titulo;
    private String descripcion;
    private MiembroEquipo responsable;
    private Estado estado;

    // Constructores
    public Tarea(String titulo, String descripcion, MiembroEquipo responsable) {
        this.id = (int) (Math.random() * 100 + 1);
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.responsable = responsable;
        this.estado = Estado.PENDIENTE;
    }

    // Getters y Setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public MiembroEquipo getResponsable() {
        return responsable;
    }

    public void setResponsable(MiembroEquipo responsable) {
        this.responsable = responsable;
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
        Tarea tarea = (Tarea) o;
        return id == tarea.id && Objects.equals(titulo, tarea.titulo) && Objects.equals(descripcion, tarea.descripcion) && Objects.equals(responsable, tarea.responsable) && estado == tarea.estado;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, titulo, descripcion, responsable, estado);
    }

    @Override
    public String toString() {
        return "Tarea{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", responsable=" + responsable +
                ", estado=" + estado +
                '}';
    }
}
