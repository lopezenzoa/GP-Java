package gestion;

import proyecto.Proyecto;
import proyecto.Tarea;
import usuario.Lider;
import usuario.MiembroEquipo;
import usuario.Usuario;

import java.io.IOException;
import java.util.Scanner;

public class Menu {
    /**
     * @author Emilia
     */
    public static void menu(Usuario usuario) {
        Scanner scanner = new Scanner(System.in);
        int option;
        while (true) {
            // Mostrar el menú
            System.out.println("Por favor, elija una opción:");
            System.out.println("1. Opción 1");
            System.out.println("2. Opción 2");
            System.out.println("3. Opción 3");
            System.out.println("4. Opción 4");
            System.out.println("5. Opción 5");
            System.out.println("6. Opción 6");
            System.out.println("7. Opción 7");
            System.out.println("8. Opción 8");
            System.out.println("S. Salir");

            // Leer la opción del usuario
            String input = scanner.nextLine();

            // Verificar si el usuario quiere salir
            if (input.equalsIgnoreCase("S")) {
                System.out.println("Saliendo del programa.");
                scanner.close();
                return; // Salir del método main
            }

            // Convertir el input a número
            try {
                option = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Opción no válida, por favor elija un número del 1 al 8 o S para salir.");
                clearConsole();
                continue; // Volver al inicio del bucle
            }

            // Manejo de las opciones
            switch (option) {
                case 1:
                    // Se tiene que pedir los datos para crear el usuario con un Scanner
                    GestionUsuarios.agregarUsuario(GestionUsuarios.crearUsuario());//Solo administrador
                    break;
                case 2:
                    // Se tiene que pedir el ID del usuario que se quiere eliminar con Scanner
                    int ID;
                    GestionUsuarios.eliminarUsuario(GestionUsuarios.buscarUsuario(ID)); //Solo administrador
                    break;
                case 3:
                    // Pedir los datos del proyecto para contruirlo con Scanner
                    Proyecto p = new Proyecto();
                    GestionProyecto.addProyecto(p); // solo administrador y lider
                    break;
                case 4:
                    // Se pide el ID del proyecto que se quiere eliminar y lo busca dentro del archivo proyectos.json
                    // Se tiene que crear el metodo buscarProyectoPorID() en la clase GestionProyecto
                    int ID;
                    GestionProyecto.removeProyecto(GestionProyecto.buscarProyectoPorID(ID)); // solo administrador y lider
                    break;
                case 5:
                    System.out.println(GestionProyecto.verProyectosinactivos().toString()); // solo administrador y lider
                    break;
                case 6:
                    System.out.println(GestionProyecto.verProyectosActivos().toString()); // solo administrador y lider
                    break;
                case 7:
                    MiembroEquipo m = (MiembroEquipo) usuario;
                    System.out.println(m.getProyectosEnCurso());

                    // Pedir el ID del proyecto que se quiere consultar
                    int idProyecto;
                    System.out.println(GestionProyecto.tareasDeMiembro(idProyecto, m));// solo miembros
                    break;
                case 8:
                    // Pedir el ID del proyecto y del miembro que se quiere agregar
                    int idProyecto;
                    int idMiembro;

                    // Pedir los datos para crear un MiembroEquipo
                    MiembroEquipo m = (MiembroEquipo) GestionUsuarios.buscarUsuario(idMiembro);
                    GestionProyecto.agregarMiembroAlEquipo(idProyecto, m); // solo administrador y lider

                    // Se agrega el proyecto a la lista de proyectos en curso del miembro
                    MiembroEquipo m_modificado = m;

                    Proyecto p = GestionProyecto.buscarProyectoPorID(idProyecto);
                    m_modificado.agregarProyecto(p.getNombre());

                    GestionUsuarios.modificarUsuario(m, m_modificado);
                case 9:
                    // Pedir el ID del miembro y del proyecto que se quiere eliminar
                    int idProyecto;
                    int idMiembro;
                    MiembroEquipo m = (MiembroEquipo) GestionUsuarios.buscarUsuario(idMiembro);
                    GestionProyecto.agregarMiembroAlEquipo(idProyecto, m); // solo administrador y lider

                    // Se elimina el proyecto de la lista de proyectos en curso del miembro
                    MiembroEquipo m_modificado = m;

                    Proyecto p = GestionProyecto.buscarProyectoPorID(idProyecto);
                    m_modificado.eliminarProyecto(p.getNombre());

                    GestionUsuarios.modificarUsuario(m, m_modificado); // solo administrador y lider
                case 10:
                    // Pedir al usuario el ID del responsable de la tarea
                    int idResponsable;
                    // Pedir al usuario el ID del proyecto al que se quiere agregar la tarea
                    // Pedir al usuario los atributos de las tareas
                    MiembroEquipo responable = GestionUsuarios.buscarUsuario(idResponsable);
                    Tarea t = new Tarea();

                    GestionProyecto.agregarTareaAlProyecto(idProyecto, t); // solo administrador y lider
                    break;
                default:
                    System.out.println("Opción no válida, por favor inténtalo de nuevo.");
            }

            // Limpiar la consola después de cada opción
            clearConsole();
        }
    }

    // Método para limpiar la consola en Windows
    private static void clearConsole() {
        try {
            // Ejecuta el comando cls en Windows
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            System.out.println("No se pudo limpiar la consola.");
        }
    }

}
