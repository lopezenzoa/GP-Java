package usuario;

import java.util.HashSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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

    public void agregarProyecto(Proyecto proyecto){
        proyectosEnCurso.add(proyecto);
    }
    public void eliminarProyecto(Proyecto proyecto){
        proyectosEnCurso.remove(proyecto);
    }

   /* public JSONObject serializarLider(Lider lider){
        JSONObject liderJson= null;
        try{
            liderJson= new JSONObject();
            liderJson= super.serializar(lider);

            JSONArray proyectosJson= new JSONArray();
            for(String nombreProyecto: this.proyectosEnCurso){
                proyectosJson.put(nombreProyecto);
            }
            liderJson.put("proyectos", proyectosJson);

            JSONArray miembroEquipoJson= new JSONArray();
            //// ESTO HAY QUE EDITARLO XQ TIENEN QUE SER STRINGS
            for(MiembroEquipo miembro: this.miembrosACargo){
                miembroEquipoJson.put(miembro);
            }
            liderJson.put("miembros_equipo", miembroEquipoJson);


        }catch (JSONException e){
            e.printStackTrace();
        }
        return liderJson;
    }

   /* public Lider deserializarLider(JSONObject liderJson){
        Lider lider= new Lider();

        try{
            lider.setId(liderJson.getInt("id"));
            lider.setNombre(liderJson.getString("nombre"));
            lider.setApellido(liderJson.getString("apellido"));
            lider.setEmail(liderJson.getString("mail"));
            lider.setTitulo(liderJson.getString("titulo"));

            JSONArray proyectoJson= liderJson.getJSONArray("proyectos");
            for(int i=0; proyectoJson.length() > i; i++) {
                //POR LO MISMO DE ARRIBA( TIENEN QUE SER STRING )
                lider.agregarProyecto(proyectoJson.getJSONObject(i));



            }
            }

        }
        return lider; // FALTA TERMINAR
    }*/


}
