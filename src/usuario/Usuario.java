package usuario;

import enums.AltaBaja;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public abstract class Usuario {
    private int id;
    private String nombre;
    private String apellido;
    private String email;
    private String titulo;
    private AltaBaja altaObaja;
    private int password;

    public Usuario(String nombre, String apellido, String email, String titulo, int password) {
        this.id = (int) (Math.random() * 100000 + 1);
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.titulo = titulo;
        this.altaObaja = AltaBaja.ACTIVO;
        this.password = password;
    }

    /**
     * Construye un usuario a partir de un objeto de tipo JSONObject.
     * @param jsonObject es el objeto en formato JSON que representa al usuario.
     * @author Enzo.
     * */
    public Usuario(JSONObject jsonObject) {
        try {
            this.id = jsonObject.getInt("id");
            this.nombre = jsonObject.getString("nombre");
            this.apellido = jsonObject.getString("apellido");
            this.email = jsonObject.getString("email");
            this.titulo = jsonObject.getString("titulo");
            this.password = jsonObject.getInt("password");

            String altaOBajaJSON = jsonObject.getString("estado");
            this.altaObaja = AltaBaja.valueOf(altaOBajaJSON);
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public AltaBaja getAltaObaja() {
        return altaObaja;
    }

    public void setAltaObaja(AltaBaja altaObaja) {
        this.altaObaja = altaObaja;
    }

    public int getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return id == usuario.id && Objects.equals(nombre, usuario.nombre) && Objects.equals(apellido, usuario.apellido) && Objects.equals(email, usuario.email) && Objects.equals(titulo, usuario.titulo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, apellido, email, titulo);
    }

    @Override
    public String toString() {
        return  "    ID            : " + id + "\n" +
                "    Nombre        : " + nombre + "\n" +
                "    Apellido      : " + apellido + "\n" +
                "    Email         : " + email + "\n" +
                "    Titulo        : " + titulo + "\n" +
                "    Password      : " + password + "\n";
    }

    /***
     * Serializa la clase usuario.
     * @return un objeto de tipo JSONObject con los atributos del usuario.
     */
    public JSONObject serializar() {
        JSONObject usuarioJson = null;

        try {
            usuarioJson = new JSONObject();
            usuarioJson.put("id", id);
            usuarioJson.put("nombre", nombre);
            usuarioJson.put("apellido", apellido);
            usuarioJson.put("email", email);
            usuarioJson.put("titulo", titulo);
            usuarioJson.put("estado", altaObaja.toString());
            usuarioJson.put("password", password);

        } catch (JSONException e){
            e.printStackTrace();
        }

        return usuarioJson;
    }
}
