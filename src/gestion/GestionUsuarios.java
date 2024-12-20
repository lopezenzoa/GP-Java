package gestion;

import enums.AltaBaja;
import enums.Rol;
import exception.UsuarioExisteException;
import exception.UsuarioNoEncontradoException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import usuario.Administrador;
import usuario.Lider;
import usuario.MiembroEquipo;
import usuario.Usuario;

import java.util.HashMap;

public class GestionUsuarios {
    public GestionUsuarios() {}

    public static MiembroEquipo crearMiembroEquipo(String nombre, String apellido, String email, String titulo, int password, Rol rol) {
        return new MiembroEquipo(nombre, apellido, email, titulo, password, rol);
    }

    public static Lider crearLider(String nombre, String apellido, String email, String titulo, int password) {
        return new Lider(nombre, apellido, email, titulo, password);
    }

    public static Administrador crearAdministrador(String nombre, String apellido, String email, String titulo, int password) {
        return new Administrador(nombre, apellido, email, titulo, password);
    }

    // Se lanza la Exception personalizada si el miembro existe en el sistema
    public static void agregarUsuario(MiembroEquipo miembroEquipo) throws UsuarioExisteException {
        JSONObject usuariosJSON = null;
        JSONArray miembrosJSON = null;

        try {
            usuariosJSON = new JSONObject(OperacionesLectoEscritura.leer("usuarios.json"));
            miembrosJSON = usuariosJSON.getJSONArray("miembrosEquipo");

            for(int i=0; i < miembrosJSON.length(); i++){
                JSONObject miembroJson= miembrosJSON.getJSONObject(i);
                if(miembroJson.getInt("id") == miembroEquipo.getId()){
                    throw new UsuarioExisteException("El usuario con el ID " + miembroEquipo.getId() +" ya existe en el sistema.");
                }
            }

            miembrosJSON.put(miembroEquipo.serializar());

            OperacionesLectoEscritura.grabar("usuarios.json", usuariosJSON);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Exception personalizada si ya existe el lider que se quiere agregar
    public static void agregarUsuario(Lider lider) throws UsuarioExisteException {
        JSONObject usuariosJSON = null;
        JSONArray lideresJSON = null;

        try {
            usuariosJSON = new JSONObject(OperacionesLectoEscritura.leer("usuarios.json"));
            lideresJSON = usuariosJSON.getJSONArray("lideres");

            for(int i=0; i < lideresJSON.length(); i++){
                JSONObject liderJson= lideresJSON.getJSONObject(i);
                if(liderJson.getInt("id") == lider.getId()){
                    throw new UsuarioExisteException("El usuario con el ID " + lider.getId() +" ya existe en el sistema.");
                }
            }

            lideresJSON.put(lider.serializar());

            OperacionesLectoEscritura.grabar("usuarios.json", usuariosJSON);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    // Exception personalizada si ya existe el administrador que se quiere agregar
    public static void agregarUsuario(Administrador administrador) throws UsuarioExisteException {
        JSONObject usuariosJSON = null;
        JSONArray adminsJSON = null;

        try {
            usuariosJSON = new JSONObject(OperacionesLectoEscritura.leer("usuarios.json"));
            adminsJSON = usuariosJSON.getJSONArray("administradores");

            for(int i=0; i < adminsJSON.length(); i++){
                JSONObject admiJson= adminsJSON.getJSONObject(i);
                if(admiJson.getInt("id") == administrador.getId()){
                    throw new UsuarioExisteException("El usuario con el ID " + administrador.getId() +" ya existe en el sistema.");
                }
            }

            adminsJSON.put(administrador.serializar());

            OperacionesLectoEscritura.grabar("usuarios.json", usuariosJSON);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Exception personalizada si el usuario que se quiere eliminar no se encuentra
    public static void eliminarUsuario(MiembroEquipo miembroEquipo) throws UsuarioNoEncontradoException {
        JSONObject usuariosJSON = null;
        JSONArray miembrosJSON = null;

        try {
            usuariosJSON = new JSONObject(OperacionesLectoEscritura.leer("usuarios.json"));
            miembrosJSON = usuariosJSON.getJSONArray("miembrosEquipo");
            boolean miembroEncontrado = false;
            int i = 0;

            while (!miembroEncontrado && i < miembrosJSON.length()) {
                MiembroEquipo m = new MiembroEquipo(miembrosJSON.getJSONObject(i));

                if (m.getId() == miembroEquipo.getId()) {
                    m.baja();

                    // Se reemplaza el usuario en el arreglo
                    miembrosJSON.remove(i);
                    miembrosJSON.put(i, m.serializar());

                    // Se agrega el arreglo modificado al objeto
                    usuariosJSON.put("miembrosEquipo", miembrosJSON);
                    OperacionesLectoEscritura.grabar("usuarios.json", usuariosJSON);
                    miembroEncontrado = true;
                    break;
                }

                i++;
            }
            if(!miembroEncontrado){
                throw new UsuarioNoEncontradoException("El usuario que quieres eliminar no existe.");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Exception personalizada si el usuario que se quiere eliminar no se encuentra
    public static void eliminarUsuario(Lider lider) throws UsuarioNoEncontradoException {
        JSONObject usuariosJSON = null;
        JSONArray lideresJSON = null;

        try {
            usuariosJSON = new JSONObject(OperacionesLectoEscritura.leer("usuarios.json"));
            lideresJSON = usuariosJSON.getJSONArray("lideres");
            boolean liderEncontrado = false;
            int i = 0;

            while (!liderEncontrado && i < lideresJSON.length()) {
                Lider l = new Lider(lideresJSON.getJSONObject(i));

                if (l.getId() == lider.getId()) {
                    l.baja();

                    // Se reemplaza el usuario en el arreglo
                    lideresJSON.remove(i);
                    lideresJSON.put(i, l.serializar());

                    // Se agrega el arreglo modificado al objeto
                    usuariosJSON.put("lideres", lideresJSON);
                    OperacionesLectoEscritura.grabar("usuarios.json", usuariosJSON);
                    liderEncontrado = true;
                }

                i++;
            }
            if(!liderEncontrado){
                throw new UsuarioNoEncontradoException("El usuario que quieres eliminar no existe.");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Exception personalizada si el usuario que se quiere eliminar no se encuentra
    public static void eliminarUsuario(Administrador administrador) throws UsuarioNoEncontradoException {
        JSONObject usuariosJSON = null;
        JSONArray adminsJSON = null;

        try {
            usuariosJSON = new JSONObject(OperacionesLectoEscritura.leer("usuarios.json"));
            adminsJSON = usuariosJSON.getJSONArray("administradores");
            boolean adminEncontrado = false;
            int i = 0;

            while (!adminEncontrado && i < adminsJSON.length()) {
                Administrador a = new Administrador(adminsJSON.getJSONObject(i));

                if (a.equals(administrador)) {
                    a.baja();

                    // Se reemplaza el usuario en el arreglo
                    adminsJSON.remove(i);
                    adminsJSON.put(i, a.serializar());

                    // Se agrega el arreglo modificado al objeto
                    usuariosJSON.put("administradores", adminsJSON);
                    OperacionesLectoEscritura.grabar("usuarios.json", usuariosJSON);
                    adminEncontrado = true;
                }

                i++;
            }
            if(!adminEncontrado){
                throw new UsuarioNoEncontradoException("El usuario que quieres eliminar no existe.");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Muestra por consola todos los usuarios que esten activos en el sistema.
     * @author Enzo.
     * */
    public static HashMap<Integer, Usuario> obtenerUsuariosActivos() {
        JSONObject usuariosJSON = null;
        JSONArray miembrosJSON = null;
        JSONArray lideresJSON = null;
        JSONArray adminsJSON = null;
        HashMap<Integer, Usuario> usuarios = new HashMap<>();

        try {
            usuariosJSON = new JSONObject(OperacionesLectoEscritura.leer("usuarios.json"));

            miembrosJSON = usuariosJSON.getJSONArray("miembrosEquipo");
            for (int i = 0; i < miembrosJSON.length(); i++) {
                MiembroEquipo m = new MiembroEquipo(miembrosJSON.getJSONObject(i));

                if (m.getAltaObaja().equals(AltaBaja.ACTIVO))
                    usuarios.put(m.getId(), m);
            }

            lideresJSON = usuariosJSON.getJSONArray("lideres");
            for (int i = 0; i < lideresJSON.length(); i++) {
                Lider l = new Lider(lideresJSON.getJSONObject(i));

                if (l.getAltaObaja().equals(AltaBaja.ACTIVO))
                    usuarios.put(l.getId(), l);
            }

            adminsJSON = usuariosJSON.getJSONArray("administradores");
            for (int i = 0; i < adminsJSON.length(); i++) {
                Administrador a = new Administrador(adminsJSON.getJSONObject(i));

                if (a.getAltaObaja().equals(AltaBaja.ACTIVO))
                    usuarios.put(a.getId(), a);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return usuarios;
    }

    /**
     * Muestra por consola todos los usuarios que esten inactivos en el sistema.
     * @author Enzo.
     * */
    public static void mostrarUsuariosInactivos() {
        JSONObject usuariosJSON = null;

        try {
            usuariosJSON = new JSONObject(OperacionesLectoEscritura.leer("usuarios.json"));

            JSONArray miembrosJSON = usuariosJSON.getJSONArray("miembrosEquipo");
            JSONArray lideresJSON = usuariosJSON.getJSONArray("lideres");
            JSONArray adminsJSON = usuariosJSON.getJSONArray("administradores");

            for (int i = 0; i < miembrosJSON.length(); i++) {
                MiembroEquipo m = new MiembroEquipo(miembrosJSON.getJSONObject(i));

                if (m.getAltaObaja().equals(AltaBaja.INACTIVO))
                    System.out.println(m);
            }

            for (int i = 0; i < lideresJSON.length(); i++) {
                Lider l = new Lider(lideresJSON.getJSONObject(i));

                if (l.getAltaObaja().equals(AltaBaja.INACTIVO))
                    System.out.println(l);
            }

            for (int i = 0; i < adminsJSON.length(); i++) {
                Administrador a = new Administrador(adminsJSON.getJSONObject(i));

                if (a.getAltaObaja().equals(AltaBaja.INACTIVO))
                    System.out.println(a);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static boolean comprobarExistencia(MiembroEquipo miembroEquipo) {
        JSONObject usuariosJSON = null;

        try {
            usuariosJSON = new JSONObject(OperacionesLectoEscritura.leer("usuarios.json"));
            JSONArray miembrosJSON = usuariosJSON.getJSONArray("miembrosEquipo");

            for (int i = 0; i < miembrosJSON.length(); i++) {
                MiembroEquipo m = new MiembroEquipo(miembrosJSON.getJSONObject(i));

                if (m.equals(miembroEquipo)) {
                    return true;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean comprobarExistencia(Lider lider) {
        JSONObject usuariosJSON = null;

        try {
            usuariosJSON = new JSONObject(OperacionesLectoEscritura.leer("usuarios.json"));
            JSONArray lideresJSON = usuariosJSON.getJSONArray("lideres");

            for (int i = 0; i < lideresJSON.length(); i++) {
                Lider l = new Lider(lideresJSON.getJSONObject(i));

                if (l.equals(lider))
                    return true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean comprobarExistencia(Administrador administrador) {
        JSONObject usuariosJSON = null;

        try {
            usuariosJSON = new JSONObject(OperacionesLectoEscritura.leer("usuarios.json"));
            JSONArray adminsJSON = usuariosJSON.getJSONArray("administradores");

            for (int i = 0; i < adminsJSON.length(); i++) {
                Administrador a = new Administrador(adminsJSON.getJSONObject(i));

                if (a.equals(administrador))
                    return true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Busca al usuario dentro del archivo usuarios.json.
     * @param id es el ID del usuario que se busca.
     * @author Enzo.
     * */
    public static Usuario buscarUsuario(int id) throws UsuarioNoEncontradoException {
        JSONObject usuariosJSON = null;

        try {
            usuariosJSON = new JSONObject(OperacionesLectoEscritura.leer("usuarios.json"));

            JSONArray lideresJSON = usuariosJSON.getJSONArray("lideres");
            JSONArray adminsJSON = usuariosJSON.getJSONArray("administradores");
            JSONArray miembrosJSON = usuariosJSON.getJSONArray("miembrosEquipo");

            for (int i = 0; i < lideresJSON.length(); i++) {
                Lider l = new Lider(lideresJSON.getJSONObject(i));

                if (l.getId() == id)
                    return l;
            }

            for (int i = 0; i < adminsJSON.length(); i++) {
                Administrador a = new Administrador(adminsJSON.getJSONObject(i));

                if (a.getId() == id)
                    return a;
            }

            for (int i = 0; i < miembrosJSON.length(); i++) {
                MiembroEquipo m = new MiembroEquipo(miembrosJSON.getJSONObject(i));

                if (m.getId() == id)
                    return m;
            }

            throw new UsuarioNoEncontradoException("El usuario con el ID: " + id + " no se encuentra");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Captura de Exception personalizada
    public static String modificarUsuario(Administrador viejo, Administrador nuevo) {
        Usuario encontrado=null;
        try{
            encontrado = buscarUsuario(viejo.getId());
        }catch (UsuarioNoEncontradoException e){
            e.printStackTrace();
        }

        JSONObject usuariosJSON = null;

        if (encontrado != null) {
            try {
                usuariosJSON = new JSONObject(OperacionesLectoEscritura.leer("usuarios.json"));
                JSONArray adminsJSON = usuariosJSON.getJSONArray("administradores");

                for (int i = 0; i < adminsJSON.length(); i++) {
                    Administrador a = new Administrador(adminsJSON.getJSONObject(i));

                    if (a.getId() == viejo.getId()) {
                        adminsJSON.remove(i);
                        adminsJSON.put(i, nuevo.serializar());

                        usuariosJSON.put("administradores", adminsJSON);
                        OperacionesLectoEscritura.grabar("usuarios.json", usuariosJSON);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return "El usuario fue modificado";
        } else {
            return "El usuario no pudo ser modificado";
        }
    }

    // Captura de Exception personalizada
    public static String modificarUsuario(Lider viejo, Lider nuevo) {
        Usuario encontrado=null;
        try {
            encontrado = buscarUsuario(viejo.getId());
        }catch (UsuarioNoEncontradoException e){
            e.printStackTrace();
        }
        JSONObject usuariosJSON = null;

        if (encontrado != null) {
            try {
                usuariosJSON = new JSONObject(OperacionesLectoEscritura.leer("usuarios.json"));
                JSONArray lideresJSON = usuariosJSON.getJSONArray("lideres");

                for (int i = 0; i < lideresJSON.length(); i++) {
                    Lider l = new Lider(lideresJSON.getJSONObject(i));

                    if (l.getId() == viejo.getId()) {
                        lideresJSON.remove(i);
                        lideresJSON.put(i, nuevo.serializar());

                        usuariosJSON.put("lideres", lideresJSON);
                        OperacionesLectoEscritura.grabar("usuarios.json", usuariosJSON);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return "El usuario fue modificado";
        } else {
            return "El usuario no pudo ser modificado";
        }
    }

    // Captura de Exception personalizada
    public static String modificarUsuario(MiembroEquipo viejo, MiembroEquipo nuevo) {
        Usuario encontrado= null;
       try{
           encontrado = buscarUsuario(viejo.getId());
       }catch (UsuarioNoEncontradoException e){
           e.printStackTrace();
       }

        JSONObject usuariosJSON = null;

        if (encontrado != null) {
            try {
                usuariosJSON = new JSONObject(OperacionesLectoEscritura.leer("usuarios.json"));
                JSONArray miembrosJSON = usuariosJSON.getJSONArray("miembrosEquipo");

                for (int i = 0; i < miembrosJSON.length(); i++) {
                    MiembroEquipo m = new MiembroEquipo(miembrosJSON.getJSONObject(i));

                    if (m.getId() == viejo.getId()) {
                        miembrosJSON.remove(i);
                        miembrosJSON.put(i, nuevo.serializar());

                        usuariosJSON.put("miembrosEquipo", miembrosJSON);
                        OperacionesLectoEscritura.grabar("usuarios.json", usuariosJSON);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return "El usuario fue modificado";
        } else {
            return "El usuario no pudo ser modificado";
        }
    }
}
