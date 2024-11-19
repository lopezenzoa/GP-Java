package gestion;

import enums.AltaBaja;
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
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Esta clase gestiona el login de los usuarios
 * */
public class Login {
    /**
     * Este metodo utiliza la clase Scanner para pedir por consola los datos de autenticacion del usuario
     * @return un mapa de tipo con los datos que ingreso el usuario.
     * @author Enzo.
     * */
    public static HashMap<String, Integer> obtenerDatosDeAutenticacion() {
        HashMap<String, Integer> datos = new HashMap<>();
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println();
            System.out.println("\tGP-Java | Login");
            System.out.println("* A continuación, ingrese su ID");
            int ID = scanner.nextInt();
            scanner.nextLine(); // Consume el salto de linea

            /*
            El PIN fue pensado para que sea un numero que representa el hash del objeto de tipo usuario que se guarda en el archivo.
            El PIN solo contempla los primeros 5 numeros del hash del objeto.
             */
            System.out.println("* A continuación, ingrese su PIN");
            int PIN = scanner.nextInt();

            datos.put("ID", ID);
            datos.put("PIN", PIN);
        } catch (InputMismatchException e) {
            System.err.println(e.getMessage());
        }

        return datos;
    }

    /**
     * Este metodo comprueba que los datos que el usuario ingreso existan dentro del archivo de usuarios.
     * @param datos es el par de valores que se va a autenticar.
     * @author Enzo.
     * */
    public static Usuario autenticar(HashMap<String, Integer> datos) {
        JSONObject usuariosJSON = null;

        try {
            usuariosJSON = new JSONObject(OperacionesLectoEscritura.leer("usuarios.json"));

            /*
            Como no sabe el programa a priori que rango va a tener el usuario que se autentica (Administrador/Lider/MiembroEquipo),
            se busca en los tres arreglos que ordenan a los usuarios en el archivo usuarios.json
             */

            JSONArray miembrosJSON = usuariosJSON.getJSONArray("miembrosEquipo");
            JSONArray lideresJSON = usuariosJSON.getJSONArray("lideres");
            JSONArray adminsJSON = usuariosJSON.getJSONArray("administradores");

            for (int i = 0; i < miembrosJSON.length(); i++) {
                MiembroEquipo m = new MiembroEquipo(miembrosJSON.getJSONObject(i));

                if (m.getId() == datos.get("ID") && m.getPassword() == datos.get("PIN"))
                    return m;
            }

            for (int i = 0; i < lideresJSON.length(); i++) {
                Lider l = new Lider(lideresJSON.getJSONObject(i));

                if (l.getId() == datos.get("ID") && l.getPassword() == datos.get("PIN"))
                    return l;
            }

            for (int i = 0; i < adminsJSON.length(); i++) {
                Administrador a = new Administrador(adminsJSON.getJSONObject(i));

                if (a.getId() == datos.get("ID") && a.getPassword() == datos.get("PIN"))
                    return a;
            }

            // Si se llega hasta esta linea, significa que el usuario no esta dentro de ninguno de los arreglos.
            throw new UsuarioNoEncontradoException("El usuario no pudo ser encontrado en la base de datos");
        } catch (JSONException | UsuarioNoEncontradoException e) {
            e.printStackTrace();
        }

        return null;
    }
}
