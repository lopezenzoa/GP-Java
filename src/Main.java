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
        MiembroEquipo Leandro = new MiembroEquipo("Leandro", "Paredes", "leito5@gmail.com", "Futbolista", Rol.DEVOPS);
        MiembroEquipo Paulo = new MiembroEquipo("Paulo", "Dybala", "ladibala@gmail.com", "Futbolista", Rol.DEVOPS);
        MiembroEquipo Lautaro = new MiembroEquipo("Lautaro", "Martinez", "eltoro22@gmail.com", "Futbolista", Rol.DEVOPS);
        MiembroEquipo Rodrigo = new MiembroEquipo("Rodrigo", "DePaul", "ladepa7@gmail.com", "Futbolista", Rol.DEVOPS);
        MiembroEquipo Nicolas = new MiembroEquipo("Nicolas", "Otamendi", "elmariscal19@gmail.com", "Defensor", Rol.DEVOPS);

        Administrador Monica = new Administrador("Monica", "Richiardi", "moni@hotmail.com","Computer Science");
        Administrador Pepe = new Administrador("Pepe", "Argento", "pepegasometro@hotmail.com","Economicas");

        Lider Mike = new Lider("Mike","Wazowszki","Mikewazawski@gmail.com","Asustador");
        Lider Sullivan = new Lider("Sullivan","Mikelson","sullivan@gmail.com","Asustador");

        Tarea tarea1 = new Tarea("Sprint 1","Creamos e instanciamos las clases. Primer commit", Enzo);
        Tarea tarea2 = new Tarea("Sprint 2","Instanciar clases. Segundo commit", Lionel);
        Tarea tarea3 = new Tarea("Sprint 3","Primer prueba",Angel);



        Proyecto nuevoProyecto = new Proyecto(Monica,Mike,"TPFINAL");

        nuevoProyecto.agregarMiembro(Enzo.getId());
        nuevoProyecto.agregarMiembro(Lionel.getId());
        nuevoProyecto.agregarMiembro(Sergio.getId());
        nuevoProyecto.agregarMiembro(Angel.getId());
        nuevoProyecto.agregarMiembro(Emiliano.getId());

        nuevoProyecto.agregarTarea(tarea1.getId());
        nuevoProyecto.agregarTarea(tarea2.getId());
        nuevoProyecto.agregarTarea(tarea3.getId());

        nuevoProyecto.eliminarMiembro(Lionel.getId()); // Funciona
        nuevoProyecto.eliminarTareaPorId(tarea2.getId()); //Funciona
        // System.out.println(nuevoProyecto.existeTarea(tarea2)); //Funciona
        // System.out.println(nuevoProyecto.getEquipo()); // Hacer metodo en clase gestora que busque por ID los usuarios

        // System.out.println(nuevoProyecto.toString());

        /* Prueba de serializacion de clases referidas al usuario */
        JSONObject enzoJSON = Enzo.serializar();
        JSONObject sullivanJSON = Sullivan.serializar();
        JSONObject monicaJSON = Monica.serializar();

        /* Prueba de deserializacion de clases referidas al usuario */
        MiembroEquipo Enzo_2 = new MiembroEquipo(enzoJSON);
        Lider sullivan_2 = new Lider(sullivanJSON);
        Administrador monica_2 = new Administrador(monicaJSON);

        // MiembroEquipo
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
    }
}
