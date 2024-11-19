package gestion;

import enums.Rol;
import exception.ProyectoNoEncontradoException;
import exception.TareaNoEncontradaException;
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
import java.util.ArrayList;
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
            clearConsole();
            System.out.println();
            System.out.println("\tBienvenido/a " + usuario.getNombre());
            System.out.println();
            System.out.println("\t***** MENU USUARIO *****");
            System.out.println("1. CREAR USUARIO");
            System.out.println("2. ELIMINAR USUARIO");
            System.out.println("3. CREAR NUEVO PROYECTO");
            System.out.println("4. ELIMINAR PROYECTO");
            System.out.println("5. PROYECTOS INACTIVOS");
            System.out.println("6. PROYECTOS ACTIVOS");
            System.out.println("7. VER TAREAS");
            System.out.println("8. AGREGAR MIEMBRO A UN PROYECTO");
            System.out.println("9. ELIMINAR MIEMBRO DEL PROYECTO");
            System.out.println("10. CREAR TAREA EN PROYECTO");
            System.out.println("11. FINALIZAR PROYECTO");
            System.out.println("12. FINALIZAR TAREA");
            System.out.println("13. VER TAREAS PENDIENTES DE UN PROYECTO");
            System.out.println("14. VER TAREAS FINALIZADAS DE UN PROYECTO");
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
                System.out.println("Opción no válida, por favor elija un número del 1 al 10 o S para salir.");
                clearConsole();
                continue; // Volver al inicio del bucle
            }

            // Manejo de las opciones
            switch (option) {
                case 1:
                    clearConsole();
                    int caso = -1;
                    System.out.println("* Ingrese tipo de Usuario a crear: \n [0] Miembro Equipo \n [1] Lider \n [2] Administrador");
                    caso = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Ingrese Nombre del Usuario a crear: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Ingrese Apellido del Usuario a crear: ");
                    String apellido = scanner.nextLine();
                    System.out.print("Ingrese Correo del Usuario a crear: ");
                    String correo = scanner.nextLine();
                    System.out.print("Ingrese el Titulo del Usuario a crear: ");
                    String titulo = scanner.nextLine();

                    if (caso == 0 ) {
                        try {
                            GestionUsuarios.agregarUsuario((MiembroEquipo)GestionUsuarios.crearUsuario(nombre,apellido,correo,titulo, Rol.DEVOPS));
                        } catch (UsuarioExisteException e) {
                            throw new RuntimeException(e);
                        }
                    } else if(caso == 1) {
                        try {
                            GestionUsuarios.agregarUsuario((Lider) GestionUsuarios.crearUsuario(nombre,apellido,correo,titulo));
                        } catch (UsuarioExisteException e) {
                            throw new RuntimeException(e);
                        }
                    } else if(caso == 2) {
                        try {
                            GestionUsuarios.agregarUsuario((Administrador) GestionUsuarios.crearUsuario(nombre,apellido,correo,titulo));
                        } catch (UsuarioExisteException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        System.out.println();
                        System.out.print("Opcion incorrecta");
                }
                    break;
                case 2:
                    int ID;
                    System.out.println("* A continuacion, ingrese el ID del Usuario a eliminar");
                    ID = scanner.nextInt();
                    scanner.nextLine();

                    try {
                        Usuario usuarioAeliminar = GestionUsuarios.buscarUsuario(ID);

                        if (usuarioAeliminar instanceof Administrador) {
                            GestionUsuarios.eliminarUsuario((Administrador) usuarioAeliminar);
                        } else if(usuarioAeliminar instanceof Lider){
                            GestionUsuarios.eliminarUsuario((Lider) usuarioAeliminar);
                        } else if(usuarioAeliminar instanceof MiembroEquipo){
                            GestionUsuarios.eliminarUsuario((MiembroEquipo) usuarioAeliminar);
                        }

                        System.out.println();
                        System.out.println("El usuario se pudo modificar con exito!");
                        System.out.println();
                    } catch (UsuarioNoEncontradoException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 3:
                    System.out.println("* A continuacion, ingrese el ID del Lider del Proyecto");
                    ID = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("* A conitnuacion, ingrese el Nombre del Proyecto");
                    String nombreProyecto = scanner.nextLine();

                    Proyecto p = null;
                    try {
                        p = new Proyecto((Administrador)usuario, (Lider)GestionUsuarios.buscarUsuario(ID),nombreProyecto);
                    } catch (UsuarioNoEncontradoException e) {
                        throw new RuntimeException(e);
                    }

                    GestionProyecto.addProyecto(p); // solo administrador
                    System.out.println();
                    System.out.println("El proyecto fue creado con exito!");
                    System.out.println();
                    break;
                case 4:
                    System.out.println("* A continuacion, ingrese el ID del Proyecto a eliminar");
                    int id = scanner.nextInt();
                    scanner.nextLine();

                    try {
                        Proyecto aEliminar = GestionProyecto.buscarProyectoPorID(id);
                        GestionProyecto.removeProyecto(aEliminar.getId()); // solo administrador y lider
                    } catch (ProyectoNoEncontradoException e) {
                        throw new RuntimeException(e);
                    }

                    System.out.println();
                    System.out.println("El Proyecto con ID " + id + " se elimino con exito!");
                    System.out.println();
                    break;
                case 5:
                    ArrayList<Proyecto> proyectosInactivos = GestionProyecto.verProyectosinactivos();

                    System.out.println();
                    for (Proyecto o : proyectosInactivos)
                        System.out.println(o); // solo administrador y lider
                    System.out.println();
                    break;
                case 6:
                    ArrayList<Proyecto> proyectosActivos = GestionProyecto.verProyectosActivos();

                    System.out.println();
                    for (Proyecto o : proyectosActivos)
                        System.out.println(o); // solo administrador y lider
                    System.out.println(); // solo administrador y lider
                    break;
                case 7:
                    MiembroEquipo m = (MiembroEquipo) usuario;

                    System.out.println();
                    System.out.println(m.getProyectosEnCurso());

                    // Pedir el ID del proyecto que se quiere consultar
                    System.out.println("* A continuacion, ingrese el ID del Proyecto a consultar");
                    int idProyecto = scanner.nextInt();
                    scanner.nextLine();

                    try {
                        ArrayList<Tarea> tareas = GestionProyecto.tareasDeMiembro(idProyecto, m);
                        for (Tarea t : tareas)
                            System.out.println(t); // solo miembros
                    } catch (ProyectoNoEncontradoException e) {
                        throw new RuntimeException(e);
                    }

                    System.out.println();
                    break;
                case 8:
                    System.out.println("* A continuacion, ingrese el ID del Proyecto");
                    idProyecto = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("* A continuacion, ingrese el ID del Miembro que integrara el equipo del Proyecto");
                    int idMiembro = scanner.nextInt();
                    scanner.nextLine();

                    try {
                        m = (MiembroEquipo) GestionUsuarios.buscarUsuario(idMiembro);
                        GestionProyecto.agregarMiembroAlEquipo(idProyecto, m); // solo administrador y lider
                    } catch (UsuarioNoEncontradoException | UsuarioExisteException e) {
                        throw new RuntimeException(e);
                    }

                    // Se agrega el proyecto a la lista de proyectos en curso del miembro
                    MiembroEquipo m_modificado = m;

                    p = null;

                    try {
                        p = GestionProyecto.buscarProyectoPorID(idProyecto);

                        // Se agrega al atributo 'proyectosEnCurso' del miembro
                        m_modificado.agregarProyecto(p.getNombre());
                        GestionUsuarios.modificarUsuario(m, m_modificado);
                    } catch (ProyectoNoEncontradoException e) {
                        throw new RuntimeException(e);
                    }

                    System.out.println();
                    System.out.println("El usuario fue agregado al equipo con exito!");
                    System.out.println();
                    break;
                case 9:
                    System.out.println("* A continuacion, ingrese el ID del Proyecto");
                    idProyecto = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("* A continuacion, ingrese el ID del Miembro que ya no integrara el equipo del Proyecto");
                    idMiembro = scanner.nextInt();
                    scanner.nextLine();

                    try {
                        m = (MiembroEquipo) GestionUsuarios.buscarUsuario(idMiembro);
                        GestionProyecto.eliminarMiembroDelEquipo(idProyecto, m.getId()); // solo administrador y lider
                    } catch (UsuarioNoEncontradoException e) {
                        throw new RuntimeException(e);
                    }

                    // Se agrega el proyecto a la lista de proyectos en curso del miembro
                    m_modificado = m;

                    p = null;

                    try {
                        p = GestionProyecto.buscarProyectoPorID(idProyecto);

                        // Se elimina atributo 'proyectosEnCurso' del miembro
                        m_modificado.eliminarProyecto(p.getNombre());
                        GestionUsuarios.modificarUsuario(m, m_modificado);
                    } catch (ProyectoNoEncontradoException e) {
                        throw new RuntimeException(e);
                    }

                    System.out.println();
                    System.out.println("El usuario fue eliminado del equipo con exito!");
                    System.out.println();
                    break;
                case 10:
                    MiembroEquipo responsable = null;
                    int idResponsable;
                    System.out.println("* A continuacion, ingrese el ID del responsable de la tarea");
                    idResponsable = scanner.nextInt();
                    scanner.nextLine();

                    try {
                        responsable = (MiembroEquipo)GestionUsuarios.buscarUsuario(idResponsable);
                    } catch (UsuarioNoEncontradoException e) {
                        throw new RuntimeException(e);
                    }

                    // Pedir al usuario el ID del proyecto al que se quiere agregar la tarea
                    System.out.println("* A continuacion, ingrese el ID del Proyecto");
                    idProyecto = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("* A continuacion, ingrese el titulo de la Tarea");
                    titulo = scanner.nextLine();

                    System.out.println("* Por ultimo, ingrese la descripcion de la Tarea");
                    String descripcion = scanner.nextLine();

                    Tarea t = new Tarea(titulo,descripcion,responsable);

                    try {
                        GestionProyecto.agregarTareaAlProyecto(idProyecto, t); // solo administrador y lider
                    } catch (ProyectoNoEncontradoException e) {
                        throw new RuntimeException(e);
                    }

                    System.out.println();
                    System.out.println("La tarea fue agregada con exito al proyecto");
                    System.out.println();
                    break;
                case 11:
                    // Solo administradores y lideres

                    // Pedir al usuario el ID del proyecto al que se quiere finalizar
                    System.out.println("* A continuacion, ingrese el ID del Proyecto");
                    idProyecto = scanner.nextInt();
                    scanner.nextLine();

                    try {
                        p = GestionProyecto.buscarProyectoPorID(idProyecto);
                        GestionProyecto.finalizarProyecto(p);
                    } catch (ProyectoNoEncontradoException e) {
                        throw new RuntimeException(e);
                    }

                    System.out.println();
                    System.out.println("El Proyecto fue finalizado con exito!");
                    System.out.println();
                    break;
                case 12:
                    // Solo para administradores y lideres
                    // Pedir al usuario el ID del proyecto al que se quiere finalizar
                    System.out.println("* A continuacion, ingrese el ID del Proyecto");
                    idProyecto = scanner.nextInt();
                    scanner.nextLine();

                    // Pedir al usuario el ID de la tarea que se quiere finalizar
                    System.out.println("* A continuacion, ingrese el ID de la Tarea que quiere finalizar");
                    int idTarea = scanner.nextInt();
                    scanner.nextLine();

                    try {
                        t = GestionProyecto.buscarTareaPorID(idProyecto, idTarea);
                        GestionProyecto.finalizarTarea(idProyecto, t);
                    } catch (ProyectoNoEncontradoException | TareaNoEncontradaException e) {
                        throw new RuntimeException(e);
                    }

                    System.out.println();
                    System.out.println("La tarea fue finalizada con exito!");
                    System.out.println();
                    break;
                case 13:
                    // Solo administradores y lideres
                    // Pedir al usuario el ID del proyecto al que se quiere finalizar
                    System.out.println("* A continuacion, ingrese el ID del Proyecto");
                    idProyecto = scanner.nextInt();
                    scanner.nextLine();

                    try {
                        ArrayList<Tarea> tareasPendientes = GestionProyecto.verTareasPendientes(idProyecto);

                        System.out.println();
                        for (Tarea tarea : tareasPendientes)
                            System.out.println(tarea); // solo administrador y lider
                        System.out.println();

                    } catch (ProyectoNoEncontradoException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 14:
                    // Solo administradores y lideres
                    // Pedir al usuario el ID del proyecto al que se quiere finalizar
                    System.out.println("* A continuacion, ingrese el ID del Proyecto");
                    idProyecto = scanner.nextInt();
                    scanner.nextLine();

                    try {
                        ArrayList<Tarea> tareasFinalizadas = GestionProyecto.verTareasFinalizadas(idProyecto);

                        System.out.println();
                        for (Tarea tarea : tareasFinalizadas)
                            System.out.println(tarea); // solo administrador y lider
                        System.out.println();
                    } catch (ProyectoNoEncontradoException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                default:
                    System.out.println("Opción no válida, por favor inténtalo de nuevo.");
            }

            System.out.println("Presione ENTER para continuar");
            scanner.nextLine();
        }
    }

    // Método para limpiar la consola en Windows
    private static void clearConsole() {
        for (int i = 0; i < 50; i++) {
            System.out.println(); // Imprimir líneas en blanco
        }
    }

}
