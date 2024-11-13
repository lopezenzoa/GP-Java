package usuario;

import java.util.HashSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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

            for (Object proyectoJSON : jsonObject.getJSONArray("proyectosEnCurso"))
                proyectosEnCurso.add(proyectoJSON.toString());

            String rolJSON = jsonObject.getString("rol");
            this.rol = Rol.valueOf(rolJSON);
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    /**
     * Agrega un proyecto a la coleccion proyectosEnCurso.
     * @param proyecto es el nombre del proyecto que se quiere agregar.
     * @author Enzo.
     * */
    public void agregarProyecto(String proyecto) {
        proyectosEnCurso.add(proyecto);
    }

    /**
     * Elimina un proyecto de la coleccion proyectosEnCurso.
     * @param proyecto es el nombre del proyecto que se quiere eliminar.
     * @author Enzo.
     * */
    public void eliminarProyecto(String proyecto) {
        proyectosEnCurso.remove(proyecto);
    }

    /**
     * Serializa la calse MiembroEquipo.
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

            for (String nombreProyecto : proyectosEnCurso)
                proyectosEnCursoJSON.put(nombreProyecto);

            miembroJSON.put("proyectosEnCurso", proyectosEnCursoJSON);
            miembroJSON.put("rol", rol.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return miembroJSON;
    }

}
