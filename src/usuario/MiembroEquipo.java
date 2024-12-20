package usuario;

import java.util.HashSet;

import enums.AltaBaja;
import interfaces.ABMLable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import proyecto.Proyecto;

import enums.Rol;

public class MiembroEquipo extends Usuario implements ABMLable<MiembroEquipo> {
    private HashSet<Integer> proyectosEnCurso;
    private Rol rol;

    public MiembroEquipo(String nombre,
                         String apellido,
                         String email,
                         String titulo,
                         int password,
                         Rol rol) {
        super(nombre, apellido, email, titulo, password);
        this.proyectosEnCurso = new HashSet<>();
        this.rol = rol;
    }

    /**
     * Construye un objeto de tipo MiembroEquipo del equipo a partir de un objeto de tipo JSONObject.
     * @param jsonObject es el objeto en formato JSON que representa a la clase MiembroEquipo.
     * @author Enzo.
     * */
    public MiembroEquipo(JSONObject jsonObject) {
        // Construye al usuario recibiendo el JSONObject
        super(jsonObject); // No se si es buena idea ponerlo fuera del bloque try-catch

        try {
            this.proyectosEnCurso = new HashSet<>();

            for (Object idProyectoJSON : jsonObject.getJSONArray("proyectosEnCurso"))
                proyectosEnCurso.add(Integer.parseInt(idProyectoJSON.toString()));

            String rolJSON = jsonObject.getString("rol");
            this.rol = Rol.valueOf(rolJSON);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public HashSet<Integer> getProyectosEnCurso() {
        return proyectosEnCurso;
    }

    public void setProyectosEnCurso(HashSet<Integer> proyectosEnCurso) {
        this.proyectosEnCurso = proyectosEnCurso;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    /**
     * Agrega un proyecto a la coleccion proyectosEnCurso.
     * @param idProyecto es el nombre del proyecto que se quiere agregar.
     * @author Enzo.
     * */
    public void agregarProyecto(int idProyecto) {
        proyectosEnCurso.add(idProyecto);
    }

    /**
     * Elimina un proyecto de la coleccion proyectosEnCurso.
     * @param idProyecto es el nombre del proyecto que se quiere eliminar.
     * @author Enzo.
     * */
    public void eliminarProyecto(int idProyecto) {
        proyectosEnCurso.remove(idProyecto);
    }

    /**
     * Serializa la clase MiembroEquipo.
     * @return un objeto de tipo JSONObject con los atributos de la clase.
     * @author Ailen.
     * */
    @Override
    public JSONObject serializar() {
        JSONObject miembroJSON = null;

        try {
            // Llama al metodo de la clase padre para evitar redundancia en el codigo
            miembroJSON = super.serializar();
            JSONArray proyectosEnCursoJSON = new JSONArray();

            for (int idProyecto : proyectosEnCurso)
                proyectosEnCursoJSON.put(idProyecto);

            miembroJSON.put("proyectosEnCurso", proyectosEnCursoJSON);
            miembroJSON.put("rol", rol.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return miembroJSON;
    }

    @Override
    public void alta() {
        setAltaObaja(AltaBaja.ACTIVO);
    }

    @Override
    public void baja() {
        setAltaObaja(AltaBaja.INACTIVO);
    }

    @Override
    public void modificar(MiembroEquipo nuevoDato) {
        this.proyectosEnCurso = nuevoDato.proyectosEnCurso;
        this.rol = nuevoDato.rol;
        this.setId(nuevoDato.getId());
        this.setNombre(nuevoDato.getNombre());
        this.setApellido(nuevoDato.getApellido());
        this.setEmail(nuevoDato.getEmail());
        this.setTitulo(nuevoDato.getTitulo());
        this.setAltaObaja(nuevoDato.getAltaObaja());
    }


    @Override
    public String toString() {
        return "Miembro Equipo " + "\n" +
                super.toString();
    }
}
