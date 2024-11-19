package usuario;

import java.util.HashSet;

import enums.AltaBaja;
import enums.Rol;
import interfaces.ABMLable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import proyecto.Proyecto;

public class Lider extends Usuario implements ABMLable<Lider> {
    private HashSet<String> proyectosEnCurso;
    private HashSet<MiembroEquipo> miembrosACargo;

    public Lider(String nombre,
                 String apellido,
                 String email,
                 String titulo,
                 int password) {
        super(nombre, apellido, email, titulo, password);
        this.proyectosEnCurso = new HashSet<>();
        this.miembrosACargo = new HashSet<>();
    }

    /**
     * Construye un objeto de tipo Lider a partir de un objeto de tipo JSONObject.
     * @param jsonObject es el objeto en formato JSON que representa a la clase Lider.
     * @author Enzo.
     * */
    public Lider(JSONObject jsonObject) {
        // Construye al usuario recibiendo el JSONObject
        super(jsonObject); // No se si es buena idea ponerlo fuera del bloque try-catch

        try {
            this.proyectosEnCurso = new HashSet<>();
            this.miembrosACargo = new HashSet<>();

            for (Object proyectoJSON : jsonObject.getJSONArray("proyectosEnCurso"))
                proyectosEnCurso.add(proyectoJSON.toString());

            JSONArray miembrosACargoJSON = jsonObject.getJSONArray("miembrosACargo");

            for (int i = 0; i < miembrosACargoJSON.length(); i++) {
                JSONObject miembroACargoJSON = miembrosACargoJSON.getJSONObject(i);
                miembrosACargo.add(new MiembroEquipo(miembroACargoJSON));
            }

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

    public HashSet<MiembroEquipo> getMiembrosACargo() {
        return miembrosACargo;
    }

    public void setMiembrosACargo(HashSet<MiembroEquipo> miembrosACargo) {
        this.miembrosACargo = miembrosACargo;
    }

    /**
     * Agrega un proyecto a la coleccion proyectosEnCurso.
     * @param proyecto es el nombre del proyecto que se quiere agregar.
     * @author Ailen.
     * */
    public void agregarProyecto(String proyecto) {
        proyectosEnCurso.add(proyecto);
    }

    /**
     * Elimina un proyecto de la coleccion proyectosEnCurso.
     * @param proyecto es el nombre del proyecto que se quiere eliminar.
     * @author Ailen.
     * */
    public void eliminarProyecto(String proyecto) {
        proyectosEnCurso.remove(proyecto);
    }

    /**
     * Agrega un miembro a la coleccion miembrosACargo.
     * @param miembro es el nombre del miembro que se quiere agregar.
     * @author Enzo.
     * */
    public void agregarMiembroACargo(MiembroEquipo miembro) {
        miembrosACargo.add(miembro);
    }

    /**
     * Elimina un miembro a la coleccion miembrosACargo.
     * @param miembro es el nombre del proyecto que se quiere eliminar.
     * @author Enzo.
     * */
    public void eliminarMiembroACargo(MiembroEquipo miembro) {
        miembrosACargo.remove(miembro);
    }

    /**
     * Serializa la clase lider.
     * @return un objeto de tipo JSONObject con los atributos de la clase.
     * @author Ailen.
     * */
    @Override
    public JSONObject serializar(){
        JSONObject liderJson = null;

        try {
            liderJson = super.serializar();
            JSONArray proyectosEnCursoJSON = new JSONArray();
            JSONArray miembrosACargoJSON = new JSONArray();

            for (String nombreProyecto : proyectosEnCurso){
                proyectosEnCursoJSON.put(nombreProyecto);
            }

            liderJson.put("proyectosEnCurso", proyectosEnCursoJSON);

            for(MiembroEquipo miembro: miembrosACargo){
                miembrosACargoJSON.put(miembro.serializar());
            }

            liderJson.put("miembrosACargo", miembrosACargoJSON);
        } catch (JSONException e){
            e.printStackTrace();
        }
        return liderJson;
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
    public void modificar(Lider nuevoDato) {
        this.proyectosEnCurso = nuevoDato.proyectosEnCurso;
        this.miembrosACargo = nuevoDato.miembrosACargo;
        this.setId(nuevoDato.getId());
        this.setNombre(nuevoDato.getNombre());
        this.setApellido(nuevoDato.getApellido());
        this.setEmail(nuevoDato.getEmail());
        this.setTitulo(nuevoDato.getTitulo());
        this.setAltaObaja(nuevoDato.getAltaObaja());
    }

    @Override
    public String toString() {
        return "Lider{" +
                super.toString() +
                ", proyectosEnCurso=" + proyectosEnCurso +
                ", miembrosACargo=" + obtenerIDsDeMiembros() +
                '}';
    }

    /**
     * Retorna una coleccion con los IDs de miembros a cargo del lider.
     * @return un Set con los IDs de los miembros.
     * @author Enzo.
     * */
    public HashSet<Integer> obtenerIDsDeMiembros() {
        HashSet<Integer> IDs = new HashSet<>();

        for (MiembroEquipo miembroEquipo : miembrosACargo)
            IDs.add(miembroEquipo.getId());

        return IDs;
    }
}
