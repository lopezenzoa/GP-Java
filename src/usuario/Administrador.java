package usuario;

import java.util.HashSet;

public class Administrador extends Usuario {
    private HashSet<Lider> lideresACargo;

    public Administrador(String nombre,
                         String apellido,
                         String email,
                         String titulo) {
        super(nombre, apellido, email, titulo);
        this.lideresACargo = new HashSet<>();
    }

    public HashSet<Lider> getLideresACargo() {
        return lideresACargo;
    }

    public void setLideresACargo(HashSet<Lider> lideresACargo) {
        this.lideresACargo = lideresACargo;
    }
}
