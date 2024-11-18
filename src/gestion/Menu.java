package gestion;

import enums.Rol;
import exception.ProyectoNoEncontradoException;
import exception.UsuarioExisteException;
import exception.UsuarioNoEncontradoException;
import proyecto.Proyecto;
import proyecto.Tarea;
import usuario.Administrador;
import usuario.Lider;
import usuario.MiembroEquipo;
import usuario.Usuario;

import java.io.IOException;
import java.sql.SQLOutput;
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
                    int caso = -1;
                    System.out.print("Ingrese tipo de Usuario a crear: [0] Miembro equipo, [1] Lider, [2] Administrador ");
                    caso = scanner.nextInt();
                    System.out.println("Ingrese Nombre del Usuario a crear: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Ingrese Apellido del Usuario a crear: ");
                    String apellido = scanner.nextLine();
                    System.out.print("Ingrese Correo del Usuario a crear: ");
                    String correo = scanner.nextLine();
                    System.out.println("Ingrese el titulo del Usuario a crear: ");
                    String titulo = scanner.nextLine();

                    if(caso == 0 ){
                        try {
                            System.out.println("Ingrese ROL");
                            GestionUsuarios.agregarUsuario((MiembroEquipo)GestionUsuarios.crearUsuario(nombre,apellido,correo,titulo, Rol.DEVOPS));
                        } catch (UsuarioExisteException e) {
                            throw new RuntimeException(e);
                        }
                    }if(caso == 1){
                        try {
                            GestionUsuarios.agregarUsuario((Lider) GestionUsuarios.crearUsuario(nombre,apellido,correo,titulo));
                        } catch (UsuarioExisteException e) {
                            throw new RuntimeException(e);
                        }
                    }if(caso == 2){
                        try {
                            GestionUsuarios.agregarUsuario((Administrador) GestionUsuarios.crearUsuario(nombre,apellido,correo,titulo));
                        } catch (UsuarioExisteException e) {
                            throw new RuntimeException(e);
                        }
                    }else{
                    System.out.print("Opcion incorrecta ");
                }
                    break;
                case 2:

                    int ID;
                    System.out.print("Ingrese ID del usuario a eliminar ");
                    ID = scanner.nextInt();
                    try {
                        Usuario usuarioAeliminar = GestionUsuarios.buscarUsuario(ID);
                        if(usuarioAeliminar instanceof Administrador){
                            GestionUsuarios.eliminarUsuario((Administrador) usuarioAeliminar);
                        }
                        if(usuarioAeliminar instanceof Lider){
                            GestionUsuarios.eliminarUsuario((Lider) usuarioAeliminar);
                        }
                        if(usuarioAeliminar instanceof MiembroEquipo){
                            GestionUsuarios.eliminarUsuario((MiembroEquipo) usuarioAeliminar);
                        }
                    } catch (UsuarioNoEncontradoException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 3:
                    // Pedir los datos del proyecto para contruirlo con Scanner
                    System.out.println("Ingrese ID del Lider del Proyecto");
                    ID = scanner.nextInt();
                    System.out.println("Ingrese Nombre del Proyecto");
                    String nombreProyecto = scanner.nextLine();
                    Proyecto p = null;
                    try {
                        p = new Proyecto((Administrador)usuario, (Lider)GestionUsuarios.buscarUsuario(ID),nombreProyecto);
                    } catch (UsuarioNoEncontradoException e) {
                        throw new RuntimeException(e);
                    }
                    GestionProyecto.addProyecto(p); // solo administrador y lider
                    break;
                case 4:
                    System.out.println("Ingrese ID del proyecto a eliminar");
                    int id = scanner.nextInt();
                    try {
                        GestionProyecto.removeProyecto(GestionProyecto.buscarProyectoPorID(id)); // solo administrador y lider
                    } catch (ProyectoNoEncontradoException e) {
                        throw new RuntimeException(e);
                    }
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
                    System.out.println("Ingrese id del proyecto a consultar");
                    int idProyecto = scanner.nextInt();
                    try {
                        System.out.println(GestionProyecto.tareasDeMiembro(idProyecto, m));// solo miembros
                    } catch (ProyectoNoEncontradoException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 8:
                    // Pedir el ID del proyecto y del miembro que se quiere agregar
                    //int idProyecto;
                    System.out.println("Ingrese id del miembro a agergar al proyecto");
                    int idMiembro = scanner.nextInt();
                    System.out.println("Ingrese id del proyecto");
                    idProyecto = scanner.nextInt();

                    // Pedir los datos para crear un MiembroEquipo
                    try {
                        m = (MiembroEquipo) GestionUsuarios.buscarUsuario(idMiembro);
                    } catch (UsuarioNoEncontradoException e) {
                        throw new RuntimeException(e);
                    }

                    try {
                        GestionProyecto.agregarMiembroAlEquipo(idProyecto, m); // solo administrador y lider
                    } catch (UsuarioExisteException e) {
                        throw new RuntimeException(e);
                    }

                    // Se agrega el proyecto a la lista de proyectos en curso del miembro
                    MiembroEquipo m_modificado = m;

                    p = null;
                    try {
                        p = GestionProyecto.buscarProyectoPorID(idProyecto);
                    } catch (ProyectoNoEncontradoException e) {
                        throw new RuntimeException(e);
                    }

                    m_modificado.agregarProyecto(p.getNombre());
                    GestionUsuarios.modificarUsuario(m, m_modificado);

                    break;
                case 9:
                    // Pedir el ID del miembro y del proyecto que se quiere eliminar
                    System.out.println("Ingrese id del miembro a eliminar del proyecto");
                    idMiembro = scanner.nextInt();
                    System.out.println("Ingrese id del proyecto");
                    idProyecto = scanner.nextInt();

                    // Pedir los datos para crear un MiembroEquipo
                    try {
                        m = (MiembroEquipo) GestionUsuarios.buscarUsuario(idMiembro);
                    } catch (UsuarioNoEncontradoException e) {
                        throw new RuntimeException(e);
                    }

                    try {
                        GestionProyecto.eliminarMiembroDelEquipo(idProyecto, m.getId()); // solo administrador y lider
                    } catch (UsuarioNoEncontradoException e) {
                        throw new RuntimeException(e);
                    }

                    // Se agrega el proyecto a la lista de proyectos en curso del miembro
                    m_modificado = m;

                    p = null;
                    try {
                        p = GestionProyecto.buscarProyectoPorID(idProyecto);
                    } catch (ProyectoNoEncontradoException e) {
                        throw new RuntimeException(e);
                    }

                    m_modificado.eliminarProyecto(p.getNombre());
                    GestionUsuarios.modificarUsuario(m, m_modificado);

                    break;
                case 10:
                    // Pedir al usuario el ID del responsable de la tarea
                    int idResponsable;
                    System.out.println("Ingrese ID del responsable de la tarea");
                    idResponsable = scanner.nextInt();
                    // Pedir al usuario el ID del proyecto al que se quiere agregar la tarea
                    System.out.println("Ingrese ID del proyecto");
                    idProyecto = scanner.nextInt();
                    // Pedir al usuario los atributos de las tareas
                    MiembroEquipo responsable = null;
                    try {
                        responsable = (MiembroEquipo)GestionUsuarios.buscarUsuario(idResponsable);
                    } catch (UsuarioNoEncontradoException e) {
                        throw new RuntimeException(e);
                    }

                    System.out.println("Ingrese titulo de la tarea:");
                    titulo = scanner.nextLine();
                    System.out.println("Ingrese descripcion de la tarea:`");
                    String descripcion = scanner.nextLine();
                    Tarea t = new Tarea(titulo,descripcion,responsable);

                    try {
                        GestionProyecto.agregarTareaAlProyecto(idProyecto, t); // solo administrador y lider
                    } catch (ProyectoNoEncontradoException e) {
                        throw new RuntimeException(e);
                    }
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
