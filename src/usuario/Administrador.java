package usuario;

import enums.AltaBaja;
import enums.Rol;
import interfaces.ABMLable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashSet;

public class Administrador extends Usuario implements ABMLable<Administrador> {
    private HashSet<Lider> lideresACargo;

    public Administrador(String nombre,
                         String apellido,
                         String email,
                         String titulo) {
        super(nombre, apellido, email, titulo);
        this.lideresACargo = new HashSet<>();
    }

    /**
     * Construye un objeto de tipo Administrador a partir de un objeto de tipo JSONObject.
     * @param jsonObject es el objeto en formato JSON que representa a la clase Administrador.
     * @author Enzo.
     * */
    public Administrador(JSONObject jsonObject) {
        // Construye al usuario recibiendo el JSONObject
        super(jsonObject); // No se si es buena idea ponerlo fuera del bloque try-catch

        try {
            this.lideresACargo = new HashSet<>();

            JSONArray lideresACargoJSON = jsonObject.getJSONArray("lideresACargo");

            for (int i = 0; i < lideresACargoJSON.length(); i++) {
                JSONObject liderACargoJSON = lideresACargoJSON.getJSONObject(i);
                lideresACargo.add(new Lider(liderACargoJSON));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public HashSet<Lider> getLideresACargo() {
        return lideresACargo;
    }

    public void setLideresACargo(HashSet<Lider> lideresACargo) {this.lideresACargo = lideresACargo;}



    /**
     * Agrega un lider a la coleccion lideresACargo.
     * @param lider es el nombre del lider que se quiere agregar.
     * @author Ailen.
     * */
    public void agregarLider(Lider lider){
        lideresACargo.add(lider);
    }

    /**
     * Elimina un lider a cargo de la coleccion lideresACargo.
     * @param lider es el nombre del lider que se quiere eliminar.
     * @author Ailen.
     * */
    public void eliminarLider(Lider lider){
        lideresACargo.remove(lider);
    }

    /**
     * Serializa la clase administrador.
     * @return un objeto de tipo JSONObject con los atributos de la clase.
     * @author Ailen.
     * */
    @Override
    public JSONObject serializar(){
        JSONObject adminJSON = null;

        try {
            adminJSON = super.serializar();
            JSONArray lideresACargoJSON = new JSONArray();

            for(Lider lider: lideresACargo){
                lideresACargoJSON.put(lider);
            }

            adminJSON.put("lideresACargo", lideresACargoJSON);
        } catch (JSONException e){
            e.printStackTrace();
        }

        return adminJSON;
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
    public void modificar(Administrador nuevoDato) {
        this.lideresACargo = nuevoDato.lideresACargo;
        this.setId(nuevoDato.getId());
        this.setNombre(nuevoDato.getNombre());
        this.setApellido(nuevoDato.getApellido());
        this.setEmail(nuevoDato.getEmail());
        this.setTitulo(nuevoDato.getTitulo());
        this.setAltaObaja(nuevoDato.getAltaObaja());
    }


}
