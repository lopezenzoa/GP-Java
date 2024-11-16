import enums.Rol;
import exception.UsuarioExisteException;
import exception.UsuarioNoEncontradoException;
import gestion.GestionProyecto;
import gestion.GestionUsuarios;
import gestion.OperacionesLectoEscritura;
import org.json.JSONArray;
import org.json.JSONObject;
import proyecto.Proyecto;
import proyecto.Tarea;
import usuario.Administrador;
import usuario.Lider;
import usuario.MiembroEquipo;

import java.util.ArrayList;

public class Main  {
    public static void main(String[] args) {
        MiembroEquipo Enzo = new MiembroEquipo("Enzo","Fernandez","elmaleducado21@gmail.com","Futbolista", Rol.DEVOPS);
        MiembroEquipo Lionel = new MiembroEquipo("Lionel", "Messi", "lapulga10@gmail.com", "Futbolista", Rol.DEVOPS);
        MiembroEquipo Sergio = new MiembroEquipo("Sergio", "Aguero", "kunaguero@gmail.com", "Streamer", Rol.DEVOPS);
        MiembroEquipo Angel = new MiembroEquipo("Angel", "DiMaria", "fideo11@gmail.com", "Futbolista", Rol.DEVOPS);
        MiembroEquipo Emiliano = new MiembroEquipo("Emiliano", "Martinez", "dibu1@gmail.com", "Arquero", Rol.DEVOPS);

        Administrador Monica = new Administrador("Monica", "Richiardi", "moni@hotmail.com","Computer Science");
        Administrador Cesar = new Administrador("Cesar", "II", "ceseremperador@hotmail.com","Emperador");
        Administrador Lucas = new Administrador("Lucas", "González", "lucas.gonzalez@example.com", "Information Systems");
        Administrador Carla = new Administrador("Carla", "Ramírez", "carla.ramirez@example.com", "Software Engineering");
        Administrador Diego = new Administrador("Diego", "Fernández", "diego.fernandez@example.com", "Data Science");

        Lider Mike = new Lider("Mike","Wazowszki","Mikewazawski@gmail.com","Asustador");
        Lider Sullivan = new Lider("Sullivan","Mikelson","sullivan@gmail.com","Asustador");
        Lider Randall = new Lider("Randall", "Boggs", "randall.boggs@gmail.com", "Asustador Experto");
        Lider Roz = new Lider("Roz", "Doe", "roz.doe@gmail.com", "Supervisora");
        Lider Celia = new Lider("Celia", "Mae", "celia.mae@gmail.com", "Recepcionista");

        Tarea tarea1 = new Tarea("Sprint 1","Creamos e instanciamos las clases. Primer commit", Enzo);
        Tarea tarea2 = new Tarea("Sprint 2","Instanciar clases. Segundo commit", Lionel);
        Tarea tarea3 = new Tarea("Sprint 3","Primer prueba", Angel);
        Tarea tarea4 = new Tarea("Sprint 2", "Implementamos los métodos principales y realizamos pruebas iniciales", Sergio);
        Tarea tarea5 = new Tarea("Sprint 3", "Optimizamos el código y documentamos el proyecto", Emiliano);

        Proyecto p1 = new Proyecto(Monica, Mike,"TPFINAL");
        Proyecto p2 = new Proyecto(Cesar, Sullivan, "Aplicación Web");
        Proyecto p3 = new Proyecto(Lucas, Randall, "Diseño de la DB");
        Proyecto p4 = new Proyecto(Carla, Roz, "Rediseño de la pagina principal");
        Proyecto p5 = new Proyecto(Diego, Celia, "Otro Proyecto");

        GestionProyecto gestionProyecto = new GestionProyecto();

        gestionProyecto.addProyecto(p1);
        gestionProyecto.addProyecto(p2);
        gestionProyecto.addProyecto(p3);
        gestionProyecto.addProyecto(p4);
        gestionProyecto.addProyecto(p5);

        /* Agregando los JSONObject para el archivo (15 usuarios) */
        /*
        try {
            GestionUsuarios.agregarUsuario(Monica);
            GestionUsuarios.agregarUsuario(Cesar);
            GestionUsuarios.agregarUsuario(Lucas);
            GestionUsuarios.agregarUsuario(Carla);
            GestionUsuarios.agregarUsuario(Diego);

            GestionUsuarios.agregarUsuario(Mike);
            GestionUsuarios.agregarUsuario(Sullivan);
            GestionUsuarios.agregarUsuario(Randall);
            GestionUsuarios.agregarUsuario(Roz);
            GestionUsuarios.agregarUsuario(Celia);

            GestionUsuarios.agregarUsuario(Enzo);
            GestionUsuarios.agregarUsuario(Lionel);
            GestionUsuarios.agregarUsuario(Sergio);
            GestionUsuarios.agregarUsuario(Angel);
            GestionUsuarios.agregarUsuario(Emiliano);
        } catch (UsuarioExisteException e) {
            System.err.println(e.getMessage());
        }

         */

        /* Prueba de serializacion de Proyecto y Tarea */
        // Generando los objetos con informacion ficticia
        /*
        JSONObject proyectosJSON = gestionProyecto.serializarListaProyectos();
        gestionProyecto.guardarProyectosEnArchivo(proyectosJSON);

        gestionProyecto.agregarTareaAlProyecto(p1.getId(), tarea1);
        gestionProyecto.agregarTareaAlProyecto(p1.getId(), tarea2);
        gestionProyecto.agregarTareaAlProyecto(p1.getId(), tarea3);
        gestionProyecto.agregarTareaAlProyecto(p1.getId(), tarea4);
        gestionProyecto.agregarTareaAlProyecto(p1.getId(), tarea5);

        gestionProyecto.agregarTareaAlProyecto(p2.getId(), tarea1);
        gestionProyecto.agregarTareaAlProyecto(p2.getId(), tarea2);
        gestionProyecto.agregarTareaAlProyecto(p2.getId(), tarea3);
        gestionProyecto.agregarTareaAlProyecto(p2.getId(), tarea4);

        gestionProyecto.agregarTareaAlProyecto(p3.getId(), tarea1);
        gestionProyecto.agregarTareaAlProyecto(p3.getId(), tarea2);
        gestionProyecto.agregarTareaAlProyecto(p3.getId(), tarea3);

        gestionProyecto.agregarTareaAlProyecto(p4.getId(), tarea1);
        gestionProyecto.agregarTareaAlProyecto(p4.getId(), tarea2);

        gestionProyecto.agregarTareaAlProyecto(p5.getId(), tarea1);

        try {
            gestionProyecto.agregarMiembroAlEquipo(p1.getId(), Enzo);
            gestionProyecto.agregarMiembroAlEquipo(p1.getId(), Lionel);
            gestionProyecto.agregarMiembroAlEquipo(p1.getId(), Sergio);
            gestionProyecto.agregarMiembroAlEquipo(p1.getId(), Angel);
            gestionProyecto.agregarMiembroAlEquipo(p1.getId(), Emiliano);

            gestionProyecto.agregarMiembroAlEquipo(p2.getId(), Enzo);
            gestionProyecto.agregarMiembroAlEquipo(p2.getId(), Lionel);
            gestionProyecto.agregarMiembroAlEquipo(p2.getId(), Sergio);
            gestionProyecto.agregarMiembroAlEquipo(p2.getId(), Angel);

            gestionProyecto.agregarMiembroAlEquipo(p3.getId(), Enzo);
            gestionProyecto.agregarMiembroAlEquipo(p3.getId(), Lionel);
            gestionProyecto.agregarMiembroAlEquipo(p3.getId(), Sergio);

            gestionProyecto.agregarMiembroAlEquipo(p4.getId(), Enzo);
            gestionProyecto.agregarMiembroAlEquipo(p4.getId(), Lionel);

            gestionProyecto.agregarMiembroAlEquipo(p5.getId(), Enzo);
        } catch (UsuarioExisteException e) {
            System.err.println(e.getMessage());
        }

        try {
            gestionProyecto.eliminarMiembroDelEquipo(p1.getId(), Enzo.getId());
        } catch (UsuarioNoEncontradoException e) {
            System.err.println(e.getMessage());
        }

         */
    }
}
