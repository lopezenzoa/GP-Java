import enums.Rol;
import exception.ProyectoNoEncontradoException;
import exception.UsuarioExisteException;
import exception.UsuarioNoEncontradoException;
import gestion.*;
import org.json.JSONArray;
import org.json.JSONObject;
import proyecto.Proyecto;
import proyecto.Tarea;
import usuario.Administrador;
import usuario.Lider;
import usuario.MiembroEquipo;
import usuario.Usuario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Main  {
    public static void main(String[] args) {
        OperacionesLectoEscritura.limpiarArchivos();

        // Se intancian 15 usuarios (5 para cada rango de privilegios)
        MiembroEquipo Enzo = new MiembroEquipo("Enzo", "Fernandez", "elmaleducado21@gmail.com", "Futbolista", 1234, Rol.DEVOPS);
        MiembroEquipo Lionel = new MiembroEquipo("Lionel", "Messi", "lapulga10@gmail.com", "Futbolista", 1234, Rol.DEVOPS);
        MiembroEquipo Sergio = new MiembroEquipo("Sergio", "Aguero", "kunaguero@gmail.com", "Streamer", 1234, Rol.DEVOPS);
        MiembroEquipo Angel = new MiembroEquipo("Angel", "DiMaria", "fideo11@gmail.com", "Futbolista", 1234, Rol.DEVOPS);
        MiembroEquipo Emiliano = new MiembroEquipo("Emiliano", "Martinez", "dibu1@gmail.com", "Arquero", 1234, Rol.DEVOPS);

        Administrador Monica = new Administrador("Monica", "Richiardi", "moni@hotmail.com", "Computer Science", 1234);
        Administrador Cesar = new Administrador("Cesar", "II", "ceseremperador@hotmail.com", "Emperador", 1234);
        Administrador Lucas = new Administrador("Lucas", "González", "lucas.gonzalez@example.com", "Information Systems", 1234);
        Administrador Carla = new Administrador("Carla", "Ramírez", "carla.ramirez@example.com", "Software Engineering", 1234);
        Administrador Diego = new Administrador("Diego", "Fernández", "diego.fernandez@example.com", "Data Science", 1234);

        Lider Mike = new Lider("Mike", "Wazowszki", "Mikewazawski@gmail.com", "Asustador", 1234);
        Lider Sullivan = new Lider("Sullivan", "Mikelson", "sullivan@gmail.com", "Asustador", 1234);
        Lider Randall = new Lider("Randall", "Boggs", "randall.boggs@gmail.com", "Asustador Experto", 1234);
        Lider Roz = new Lider("Roz", "Doe", "roz.doe@gmail.com", "Supervisora", 1234);
        Lider Celia = new Lider("Celia", "Mae", "celia.mae@gmail.com", "Recepcionista", 1234);

        Tarea tarea1 = new Tarea("Sprint 1", "Creamos e instanciamos las clases. Primer commit", Enzo);
        Tarea tarea2 = new Tarea("Sprint 2", "Instanciar clases. Segundo commit", Lionel);
        Tarea tarea3 = new Tarea("Sprint 3", "Primer prueba", Angel);
        Tarea tarea4 = new Tarea("Sprint 2", "Implementamos los métodos principales y realizamos pruebas iniciales", Sergio);
        Tarea tarea5 = new Tarea("Sprint 3", "Optimizamos el código y documentamos el proyecto", Emiliano);

        Proyecto p1 = new Proyecto(Monica, Mike, "TPFINAL");
        Proyecto p2 = new Proyecto(Cesar, Sullivan, "Aplicación Web");
        Proyecto p3 = new Proyecto(Lucas, Randall, "Diseño de la DB");
        Proyecto p4 = new Proyecto(Carla, Roz, "Rediseño de la pagina principal");
        Proyecto p5 = new Proyecto(Diego, Celia, "Otro Proyecto");

        // Se agregan 5 proyectos al archivo proyectos.json
        GestionProyecto.addProyecto(p1);
        GestionProyecto.addProyecto(p2);
        GestionProyecto.addProyecto(p3);
        GestionProyecto.addProyecto(p4);
        GestionProyecto.addProyecto(p5);

        // Se agregan tareas a cada uno de los proyectos
        try {
            GestionProyecto.agregarTareaAlProyecto(p1.getId(), tarea1);
            GestionProyecto.agregarTareaAlProyecto(p1.getId(), tarea2);
            GestionProyecto.agregarTareaAlProyecto(p1.getId(), tarea3);
            GestionProyecto.agregarTareaAlProyecto(p1.getId(), tarea4);
            GestionProyecto.agregarTareaAlProyecto(p1.getId(), tarea5);

            GestionProyecto.agregarTareaAlProyecto(p2.getId(), tarea1);
            GestionProyecto.agregarTareaAlProyecto(p2.getId(), tarea2);
            GestionProyecto.agregarTareaAlProyecto(p2.getId(), tarea3);
            GestionProyecto.agregarTareaAlProyecto(p2.getId(), tarea4);

            GestionProyecto.agregarTareaAlProyecto(p3.getId(), tarea1);
            GestionProyecto.agregarTareaAlProyecto(p3.getId(), tarea2);
            GestionProyecto.agregarTareaAlProyecto(p3.getId(), tarea3);

            GestionProyecto.agregarTareaAlProyecto(p4.getId(), tarea1);
            GestionProyecto.agregarTareaAlProyecto(p4.getId(), tarea2);

            GestionProyecto.agregarTareaAlProyecto(p5.getId(), tarea1);
        } catch (ProyectoNoEncontradoException e) {
            System.err.println(e.getMessage());
        }

        // Se conforman los equipos para cada uno de los proyectos
        try {
            GestionProyecto.agregarMiembroAlEquipo(p1.getId(), Enzo);
            GestionProyecto.agregarMiembroAlEquipo(p1.getId(), Lionel);
            GestionProyecto.agregarMiembroAlEquipo(p1.getId(), Sergio);
            GestionProyecto.agregarMiembroAlEquipo(p1.getId(), Angel);
            GestionProyecto.agregarMiembroAlEquipo(p1.getId(), Emiliano);

            GestionProyecto.agregarMiembroAlEquipo(p2.getId(), Enzo);
            GestionProyecto.agregarMiembroAlEquipo(p2.getId(), Lionel);
            GestionProyecto.agregarMiembroAlEquipo(p2.getId(), Sergio);
            GestionProyecto.agregarMiembroAlEquipo(p2.getId(), Angel);

            GestionProyecto.agregarMiembroAlEquipo(p3.getId(), Enzo);
            GestionProyecto.agregarMiembroAlEquipo(p3.getId(), Lionel);
            GestionProyecto.agregarMiembroAlEquipo(p3.getId(), Sergio);

            GestionProyecto.agregarMiembroAlEquipo(p4.getId(), Enzo);
            GestionProyecto.agregarMiembroAlEquipo(p4.getId(), Lionel);

            GestionProyecto.agregarMiembroAlEquipo(p5.getId(), Enzo);
        } catch (UsuarioExisteException e) {
            System.err.println(e.getMessage());
        }

        // Se cargan 15 usuarios al archivo usuarios.json
        try {
            // Administradores
            GestionUsuarios.agregarUsuario(Monica);
            GestionUsuarios.agregarUsuario(Cesar);
            GestionUsuarios.agregarUsuario(Lucas);
            GestionUsuarios.agregarUsuario(Carla);
            GestionUsuarios.agregarUsuario(Diego);

            // Lideres
            GestionUsuarios.agregarUsuario(Mike);
            GestionUsuarios.agregarUsuario(Sullivan);
            GestionUsuarios.agregarUsuario(Randall);
            GestionUsuarios.agregarUsuario(Roz);
            GestionUsuarios.agregarUsuario(Celia);

            // Miembros de Equipo
            GestionUsuarios.agregarUsuario(Enzo);
            GestionUsuarios.agregarUsuario(Lionel);
            GestionUsuarios.agregarUsuario(Sergio);
            GestionUsuarios.agregarUsuario(Angel);
            GestionUsuarios.agregarUsuario(Emiliano);
        } catch (UsuarioExisteException e) {
            System.err.println(e.getMessage());
        }

        // Debugging
        HashMap<Integer, Usuario> usuarios = GestionUsuarios.obtenerUsuariosActivos();

        /*
        for (Map.Entry<Integer, Usuario> entry : usuarios.entrySet())
            System.out.println(entry.getKey() + " (" + entry.getValue().getClass().getName() + ")");

         */

        // Login
        /*
        for (Map.Entry<Integer, Usuario> entry : usuarios.entrySet())
            if (entry.getValue() instanceof MiembroEquipo)
                System.out.println(entry.getKey());


        HashMap<String, Integer> datosDeAutenticacion;

        datosDeAutenticacion = Login.obtenerDatosDeAutenticacion();
        Usuario u = Login.autenticar(datosDeAutenticacion);

         */

        /* Descomentar la linea para ingresar como: */
        Usuario u = usuarios.get(Monica.getId()); // Administrador
        // Usuario u = usuarios.get(Mike.getId()); // Lider
        // Usuario u = usuarios.get(Enzo.getId()); // Miembro

        if (u != null)
            Menu.menu(u);

    }
}
