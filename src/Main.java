import enums.Rol;
import org.json.JSONObject;
import proyecto.Proyecto;
import proyecto.Tarea;
import usuario.Administrador;
import usuario.Lider;
import usuario.MiembroEquipo;

public class Main  {
    public static void main(String[] args) {
        MiembroEquipo Enzo = new MiembroEquipo("Enzo","Fernandez","elmaleducado21@gmail.com","Futbolista", Rol.DEVOPS);
        MiembroEquipo Lionel = new MiembroEquipo("Lionel", "Messi", "lapulga10@gmail.com", "Futbolista", Rol.DEVOPS);
        MiembroEquipo Sergio = new MiembroEquipo("Sergio", "Aguero", "kunaguero@gmail.com", "Streamer", Rol.DEVOPS);
        MiembroEquipo Angel = new MiembroEquipo("Angel", "DiMaria", "fideo11@gmail.com", "Futbolista", Rol.DEVOPS);
        MiembroEquipo Emiliano = new MiembroEquipo("Emiliano", "Martinez", "dibu1@gmail.com", "Arquero", Rol.DEVOPS);

        Administrador Monica = new Administrador("Monica", "Richiardi", "moni@hotmail.com","Computer Science");

        Lider Mike = new Lider("Mike","Wazowszki","Mikewazawski@gmail.com","Asustador");
        Lider Sullivan = new Lider("Sullivan","Mikelson","sullivan@gmail.com","Asustador");

        Tarea tarea1 = new Tarea("Sprint 1","Creamos e instanciamos las clases. Primer commit", Enzo);
        Tarea tarea2 = new Tarea("Sprint 2","Instanciar clases. Segundo commit", Lionel);
        Tarea tarea3 = new Tarea("Sprint 3","Primer prueba", Angel);

        Proyecto nuevoProyecto = new Proyecto(Monica, Mike,"TPFINAL");

        nuevoProyecto.agregarMiembro(Enzo);
        nuevoProyecto.agregarMiembro(Lionel);
        nuevoProyecto.agregarMiembro(Sergio);
        nuevoProyecto.agregarMiembro(Angel);
        nuevoProyecto.agregarMiembro(Emiliano);

        nuevoProyecto.agregarTarea(tarea1);
        nuevoProyecto.agregarTarea(tarea2);
        nuevoProyecto.agregarTarea(tarea3);

        nuevoProyecto.eliminarMiembro(Lionel); // Funciona
        nuevoProyecto.eliminarTareaPorId(tarea2.getId()); //Funciona
        // System.out.println(nuevoProyecto.existeTarea(tarea2)); //Funciona
        // System.out.println(nuevoProyecto.getEquipo()); // Hacer metodo en clase gestora que busque por ID los usuarios

        System.out.println(nuevoProyecto.toString());

        /* Prueba de serializacion de clases referidas al usuario */
        JSONObject enzoJSON = Enzo.serializar();
        JSONObject sullivanJSON = Sullivan.serializar();
        JSONObject monicaJSON = Monica.serializar();

        /* Prueba de deserializacion de clases referidas al usuario */
        MiembroEquipo Enzo_2 = new MiembroEquipo(enzoJSON);
        Lider sullivan_2 = new Lider(sullivanJSON);
        Administrador monica_2 = new Administrador(monicaJSON);

        // MiembroEquipo
        /*
        System.out.println(enzoJSON);
        System.out.println(Enzo_2);
        System.out.println();

        // Lider
        System.out.println(sullivanJSON);
        System.out.println(sullivan_2);
        System.out.println();

        // Administrador
        System.out.println(monicaJSON);
        System.out.println(monica_2);

         */

        /* Prueba de serializacion de Proyecto y Tarea */
        JSONObject p1JSON = nuevoProyecto.serializar();

        System.out.println();
        System.out.println(p1JSON);
        System.out.println();

        Proyecto nuevoProyecto_2 = new Proyecto(p1JSON);

        System.out.println(nuevoProyecto_2);
    }
}
