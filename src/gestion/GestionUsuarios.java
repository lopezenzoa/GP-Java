package gestion;

import enums.AltaBaja;
import enums.Rol;
import exception.UsuarioExisteException;
import exception.UsuarioNoEncontradoException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import proyecto.Proyecto;
import usuario.Administrador;
import usuario.Lider;
import usuario.MiembroEquipo;
import usuario.Usuario;

public class GestionUsuarios {
    public GestionUsuarios() {}

    public static Usuario crearUsuario(String nombre, String apellido, String email, String titulo, Rol rol) {
        return new MiembroEquipo(nombre, apellido, email, titulo, rol);
    }

    public static Usuario crearUsuario(String nombre, String apellido, String email, String titulo) {
        return new Administrador(nombre, apellido, email, titulo);
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

    // Los 3 metodos que siguen puede arrojar el error si el usuario no existe dentro del sistema
    public static void eliminarUsuario(MiembroEquipo miembroEquipo) {
        JSONObject usuariosJSON = null;
        JSONArray miembrosJSON = null;

        try {
            usuariosJSON = new JSONObject(OperacionesLectoEscritura.leer("usuarios.json"));
            miembrosJSON = usuariosJSON.getJSONArray("miembrosEquipo");
            boolean miembroEncontrado = false;
            int i = 0;

            while (!miembroEncontrado && i < miembrosJSON.length()) {
                MiembroEquipo m = new MiembroEquipo(miembrosJSON.getJSONObject(i));

                if (m.equals(miembroEquipo)) {
                    m.baja();

                    // Se reemplaza el usuario en el arreglo
                    miembrosJSON.remove(i);
                    miembrosJSON.put(i, m.serializar());

                    // Se agrega el arreglo modificado al objeto
                    usuariosJSON.put("miembrosEquipo", miembrosJSON);
                    OperacionesLectoEscritura.grabar("usuarios.json", usuariosJSON);
                }

                i++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void eliminarUsuario(Lider lider) {
        JSONObject usuariosJSON = null;
        JSONArray lideresJSON = null;

        try {
            usuariosJSON = new JSONObject(OperacionesLectoEscritura.leer("usuarios.json"));
            lideresJSON = usuariosJSON.getJSONArray("lideres");
            boolean liderEncontrado = false;
            int i = 0;

            while (!liderEncontrado && i < lideresJSON.length()) {
                Lider l = new Lider(lideresJSON.getJSONObject(i));

                if (l.equals(lider)) {
                    l.baja();

                    // Se reemplaza el usuario en el arreglo
                    lideresJSON.remove(i);
                    lideresJSON.put(i, l.serializar());

                    // Se agrega el arreglo modificado al objeto
                    usuariosJSON.put("lideres", lideresJSON);
                    OperacionesLectoEscritura.grabar("usuarios.json", usuariosJSON);
                }

                i++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void eliminarUsuario(Administrador administrador) {
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
                }

                i++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Muestra por consola todos los usuarios que esten activos en el sistema.
     * @author Enzo.
     * */
    public static void mostrarUsuariosActivos() {
        JSONObject usuariosJSON = null;
        JSONArray miembrosJSON = null;
        JSONArray lideresJSON = null;
        JSONArray adminsJSON = null;

        try {
            usuariosJSON = new JSONObject(OperacionesLectoEscritura.leer("usuarios.json"));

            miembrosJSON = usuariosJSON.getJSONArray("miembrosEquipo");
            for (int i = 0; i < miembrosJSON.length(); i++) {
                MiembroEquipo m = new MiembroEquipo(miembrosJSON.getJSONObject(i));

                if (m.getAltaObaja().equals(AltaBaja.ACTIVO))
                    System.out.println(m);
            }

            lideresJSON = usuariosJSON.getJSONArray("lideres");
            for (int i = 0; i < lideresJSON.length(); i++) {
                Lider l = new Lider(lideresJSON.getJSONObject(i));

                if (l.getAltaObaja().equals(AltaBaja.ACTIVO))
                    System.out.println(l);
            }

            adminsJSON = usuariosJSON.getJSONArray("administradores");
            for (int i = 0; i < adminsJSON.length(); i++) {
                Administrador a = new Administrador(adminsJSON.getJSONObject(i));

                if (a.getAltaObaja().equals(AltaBaja.ACTIVO))
                    System.out.println(a);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    // Los 3 metodos pueden arrojar un error si el usuario existe dentro del archivo
    public static MiembroEquipo buscarMiembroEquipo(int id) {
        JSONObject usuariosJSON = null;

        try {
            usuariosJSON = new JSONObject(OperacionesLectoEscritura.leer("usuarios.json"));
            JSONArray miembrosJSON = usuariosJSON.getJSONArray("miembrosEquipo");

            for (int i = 0; i < miembrosJSON.length(); i++) {
                MiembroEquipo m = new MiembroEquipo(miembrosJSON.getJSONObject(i));

                if (m.getId() == id)
                    return m;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Administrador buscarAdministrador(int id) {
        JSONObject usuariosJSON = null;

        try {
            usuariosJSON = new JSONObject(OperacionesLectoEscritura.leer("usuarios.json"));
            JSONArray adminsJSON = usuariosJSON.getJSONArray("administradores");

            for (int i = 0; i < adminsJSON.length(); i++) {
                Administrador a = new Administrador(adminsJSON.getJSONObject(i));

                if (a.getId() == id)
                    return a;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Lider buscarLider(int id) {
        JSONObject usuariosJSON = null;

        try {
            usuariosJSON = new JSONObject(OperacionesLectoEscritura.leer("usuarios.json"));
            JSONArray lideresJSON = usuariosJSON.getJSONArray("lideres");

            for (int i = 0; i < lideresJSON.length(); i++) {
                Lider l = new Lider(lideresJSON.getJSONObject(i));

                if (l.getId() == id)
                    return l;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Los 3 metodos que siguen pueden arrojar un error si el usuario no existe dentro del archivo
    public static String modificarUsuario(Administrador viejo, Administrador nuevo) {
        Administrador encontrado = buscarAdministrador(viejo.getId());
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

    public static String modificarUsuario(Lider viejo, Lider nuevo) {
        Lider encontrado = buscarLider(viejo.getId());
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

    public static String modificarUsuario(MiembroEquipo viejo, MiembroEquipo nuevo) {
        MiembroEquipo encontrado = buscarMiembroEquipo(viejo.getId());
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
