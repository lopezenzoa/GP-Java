package usuario;

import java.util.HashSet;
import proyecto.Proyecto;

import enums.Rol;

public class MiembroEquipo extends Usuario {
    private HashSet<String> proyectosEnCurso;
    private Rol rol;

    public MiembroEquipo(String nombre,
                         String apellido,
                         String email,
                         String titulo,
                         Rol rol) {
        super(nombre, apellido, email, titulo);
        this.proyectosEnCurso = new HashSet<>();
        this.rol = rol;
    }



    public HashSet<String> getProyectosEnCurso() {
        return proyectosEnCurso;
    }

    public void setProyectosEnCurso(HashSet<String> proyectosEnCurso) {
        this.proyectosEnCurso = proyectosEnCurso;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
