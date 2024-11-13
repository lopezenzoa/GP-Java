package usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

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
        this.lideresACargo = lideresACargo;}

    public void agregarLider(Lider lider){
        lideresACargo.add(lider);
    }
    public void eliminarLider(Lider lider){
        lideresACargo.remove(lider);
    }

    // SERIALIZACION
    public JSONObject serializar(Administrador admi){
        JSONObject admiJson= null;
        try{
            admiJson= new JSONObject();
            admiJson= super.serializar(admi);
            JSONArray lideresJson= new JSONArray();
            for(Lider lider: this.lideresACargo){
                lideresJson.put(lider);
            }
            admiJson.put("lideres", lideresJson);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return admiJson;
    }

    // DESERIALIZACION
    /*public Administrador deserializarAdmi(JSONObject admiJson){
        Administrador admi= new Administrador();
        try{
            admi.setId(admiJson.getInt("id"));
            admi.setNombre(admiJson.getString("nombre"));
            admi.setApellido(admiJson.getString("apellido"));
            admi.setEmail(admiJson.getString("email"));
            admi.setTitulo(admiJson.getString("titulo"));
            JSONArray lideresJson=  admiJson.getJSONArray("lideres");
            for(int i =0; lideresJson.length() > i; i++){
                JSONObject liderJson = lideresJson.getJSONObject(i);
                Lider lider= deserializarLider(liderJson);
                admi.agregarLider(lider);
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
        return admi;
    }*/






}
