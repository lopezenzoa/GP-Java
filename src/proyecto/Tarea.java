package proyecto;

import enums.AltaBaja;
import enums.Estado;
import interfaces.ABMLable;
import org.json.JSONException;
import org.json.JSONObject;
import usuario.MiembroEquipo;

import java.util.List;
import java.util.Objects;

public class Tarea implements ABMLable<Tarea> {
    private int id;
    private String titulo;
    private String descripcion;
    private MiembroEquipo responsable;
    private Estado estado;
    private AltaBaja altaObaja;

    // Constructores
    public Tarea(String titulo, String descripcion, MiembroEquipo responsable) {
        this.id = (int) (Math.random() * 100000 + 1);
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.responsable = responsable;
        this.estado = Estado.PENDIENTE;
        this.altaObaja = AltaBaja.ACTIVO;
    }

    /**
     * Construye una tarea a partir de un objeto de tipo JSONObject.
     * @param jsonObject es el objeto en formato JSON que representa a la tarea.
     * @author Enzo.
     * */
    public Tarea(JSONObject jsonObject) {
        try {
            this.id = jsonObject.getInt("id");
            this.titulo = jsonObject.getString("titulo");
            this.descripcion = jsonObject.getString("descripcion");
            this.responsable = new MiembroEquipo(jsonObject.getJSONObject("responsable"));
            this.altaObaja = AltaBaja.valueOf(jsonObject.getString("altaObaja"));
            String estadoJSON = jsonObject.getString("estado");
            this.estado = Estado.valueOf(estadoJSON);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

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
        return "Tarea {" + "\n" +
                "    ID           : " + id + "\n" +
                "    Título       : '" + titulo + '\'' + "\n" +
                "    Descripción  : '" + descripcion + '\'' + "\n" +
                "    Responsable  : " + responsable + "\n" +
                "    Estado       : " + estado + "\n" +
                '}';
    }


    /**
     * Serializa la clase Tarea.
     * @return un objeto de tipo JSONObject con los atributos de la tarea.
     * @author Enzo.
     * */
    public JSONObject serializar() {
        JSONObject tareaJSON = null;

        try {
            tareaJSON = new JSONObject();

            tareaJSON.put("id", id);
            tareaJSON.put("titulo", titulo);
            tareaJSON.put("descripcion", descripcion);
            tareaJSON.put("responsable", responsable.serializar());
            tareaJSON.put("estado", estado.toString());
            tareaJSON.put("altaObaja", altaObaja.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return tareaJSON;
    }

    @Override
    public void alta() {
        this.altaObaja = AltaBaja.ACTIVO;
    }

    @Override
    public void baja() {
        this.altaObaja = AltaBaja.INACTIVO;
    }

    @Override
    public void modificar(Tarea nuevoDato) {
        this.id = nuevoDato.id;
        this.estado = nuevoDato.estado;
        this.titulo = nuevoDato.titulo;
        this.descripcion = nuevoDato.descripcion;
        this.responsable = nuevoDato.responsable;
        this.altaObaja = nuevoDato.altaObaja;
    }


}
