package usuario;

import java.util.HashSet;
import proyecto.Proyecto;

public class Lider extends Usuario {
    private HashSet<Proyecto> proyectosEnCurso;
    private HashSet<MiembroEquipo> miembrosACargo;

    public Lider(String nombre,
                 String apellido,
                 String email,
                 String titulo) {
        super(nombre, apellido, email, titulo);
        this.proyectosEnCurso = new HashSet<>();
        this.miembrosACargo = new HashSet<>();
    }

    public HashSet<Proyecto> getProyectosEnCurso() {
        return proyectosEnCurso;
    }

    public void setProyectosEnCurso(HashSet<Proyecto> proyectosEnCurso) {
        this.proyectosEnCurso = proyectosEnCurso;
    }

    public HashSet<MiembroEquipo> getMiembrosACargo() {
        return miembrosACargo;
    }

    public void setMiembrosACargo(HashSet<MiembroEquipo> miembrosACargo) {
        this.miembrosACargo = miembrosACargo;
    }
}
