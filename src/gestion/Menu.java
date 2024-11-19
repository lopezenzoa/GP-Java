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
            if (usuario instanceof Administrador) {
                System.out.println("1. CREAR USUARIO");
                System.out.println("2. ELIMINAR USUARIO");
                System.out.println("3. CREAR NUEVO PROYECTO");
                System.out.println("4. ELIMINAR PROYECTO");
                System.out.println("5. PROYECTOS INACTIVOS");
                System.out.println("6. PROYECTOS ACTIVOS");
                System.out.println("8. AGREGAR MIEMBRO A UN PROYECTO");
                System.out.println("9. ELIMINAR MIEMBRO DEL PROYECTO");
                System.out.println("10. CREAR TAREA EN PROYECTO");
            } else if (usuario instanceof Lider) {
                System.out.println("1. CREAR USUARIO");
                System.out.println("3. CREAR NUEVO PROYECTO");
                System.out.println("4. ELIMINAR PROYECTO");
                System.out.println("5. PROYECTOS INACTIVOS");
                System.out.println("6. PROYECTOS ACTIVOS");
                System.out.println("7. VER TAREAS");
                System.out.println("8. AGREGAR MIEMBRO A UN PROYECTO");
                System.out.println("9. ELIMINAR MIEMBRO DEL PROYECTO");
                System.out.println("10. CREAR TAREA EN PROYECTO");
            } else if (usuario instanceof MiembroEquipo) {
                System.out.println("7. VER TAREAS");
            } else {
                System.out.println("No tienes permisos para acceder a este menú.");
                return; // Sale del método si no hay ningún rol válido
            }

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
                    if (usuario instanceof Administrador) {

                        int caso = -1;
                        System.out.println("* Ingrese tipo de Usuario a crear: \n [0] Miembro equipo, \n [1] Lider, \n [2] Administrador");
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

                        if (caso == 0) {
                            try {
                                GestionUsuarios.agregarUsuario((MiembroEquipo) GestionUsuarios.crearUsuario(nombre, apellido, correo, titulo, Rol.DEVOPS));
                            } catch (UsuarioExisteException e) {
                                throw new RuntimeException(e);
                            }
                        } else if (caso == 1) {
                            try {
                                GestionUsuarios.agregarUsuario((Lider) GestionUsuarios.crearUsuario(nombre, apellido, correo, titulo));
                            } catch (UsuarioExisteException e) {
                                throw new RuntimeException(e);
                            }
                        } else if (caso == 2) {
                            try {
                                GestionUsuarios.agregarUsuario((Administrador) GestionUsuarios.crearUsuario(nombre, apellido, correo, titulo));
                            } catch (UsuarioExisteException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            System.out.println();
                            System.out.print("Opcion incorrecta");
                        }
                    }else {
                        System.out.println("No tienes permisos para crear un usuario.");
                    }
                    break;
                case 2:
                    if (usuario instanceof Administrador) {
                        int ID;
                        System.out.println("* A continuacion, ingrese el ID del Usuario a eliminar");
                        ID = scanner.nextInt();
                        scanner.nextLine();

                        try {
                            Usuario usuarioAeliminar = GestionUsuarios.buscarUsuario(ID);

                            if (usuarioAeliminar instanceof Administrador) {
                                GestionUsuarios.eliminarUsuario((Administrador) usuarioAeliminar);
                            } else if (usuarioAeliminar instanceof Lider) {
                                GestionUsuarios.eliminarUsuario((Lider) usuarioAeliminar);
                            } else if (usuarioAeliminar instanceof MiembroEquipo) {
                                GestionUsuarios.eliminarUsuario((MiembroEquipo) usuarioAeliminar);
                            }

                            System.out.println();
                            System.out.println("El usuario se pudo modificar con exito!");
                            System.out.println();
                        } catch (UsuarioNoEncontradoException e) {
                            throw new RuntimeException(e);
                        }
                    }else {
                        System.out.println("No tienes permisos para eliminar un usuario.");
                    }
                    break;
                case 3:
                    if (usuario instanceof Administrador || usuario instanceof Lider) {
                        System.out.println("* A continuacion, ingrese el ID del Lider del Proyecto");
                        int ID = scanner.nextInt();
                        scanner.nextLine();

                        System.out.println("* A conitnuacion, ingrese el Nombre del Proyecto");
                        String nombreProyecto = scanner.nextLine();

                        Proyecto p = null;
                        try {
                            p = new Proyecto((Administrador) usuario, (Lider) GestionUsuarios.buscarUsuario(ID), nombreProyecto);
                        } catch (UsuarioNoEncontradoException e) {
                            throw new RuntimeException(e);
                        }

                        GestionProyecto.addProyecto(p); // solo administrador
                        System.out.println();
                        System.out.println("El proyecto fue creado con exito!");
                        System.out.println();
                    }else {
                        System.out.println("No tienes permisos para crear un nuevo proyecto.");
                    }
                    break;
                case 4:
                    if (usuario instanceof Administrador || usuario instanceof Lider) {
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
                    } else {
                        System.out.println("No tienes permisos para eliminar un proyecto.");
                    }
                    break;
                case 5:
                    if (usuario instanceof Administrador || usuario instanceof Lider) {
                    ArrayList<Proyecto> proyectosInactivos = GestionProyecto.verProyectosinactivos();

                    System.out.println();
                    for (Proyecto o : proyectosInactivos)
                        System.out.println(o); // solo administrador y lider
                    System.out.println();
                    }else {
                        System.out.println("No tienes permisos para ver proyectos inactivos.");
                    }
                    break;
                case 6:
                    if (usuario instanceof Administrador || usuario instanceof Lider ) {
                        ArrayList<Proyecto> proyectosActivos = GestionProyecto.verProyectosActivos();

                        System.out.println();
                        for (Proyecto o : proyectosActivos)
                            System.out.println(o); // solo administrador y lider
                        System.out.println(); // solo administrador y lider
                    }else {
                            System.out.println("No tienes permisos para ver proyectos activos.");
                        }
                        break;
                case 7:
                    if (usuario instanceof MiembroEquipo) {

                        MiembroEquipo m = (MiembroEquipo) usuario;

                        System.out.println();
                        System.out.println(m.getProyectosEnCurso());

                        // Pedir el ID del proyecto que se quiere consultar
                        System.out.println("* A continuacion, ingrese el ID del Proyecto a consultar");
                        int idProyecto = scanner.nextInt();
                        scanner.nextLine();

                        try {
                            System.out.println(GestionProyecto.tareasDeMiembro(idProyecto, m));// solo miembros
                        } catch (ProyectoNoEncontradoException e) {
                            throw new RuntimeException(e);
                        }

                        System.out.println();
                    }else {
                        System.out.println("No tienes permisos para ver tareas.");
                    }
                    break;
                case 8:
                    if (usuario instanceof Administrador || usuario instanceof Lider) {
                        System.out.println("* A continuacion, ingrese el ID del Proyecto");
                        int idProyecto = scanner.nextInt();
                        scanner.nextLine();
                        MiembroEquipo m = null;
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

                        Proyecto p = null;

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
                    }else {
                        System.out.println("No tienes permisos para agregar miembros a un proyecto.");
                    }
                    break;
                case 9:
                    if (usuario instanceof Administrador || usuario instanceof Lider) {
                        System.out.println("* A continuacion, ingrese el ID del Proyecto");
                        int idProyecto = scanner.nextInt();
                        scanner.nextLine();

                        System.out.println("* A continuacion, ingrese el ID del Miembro que ya no integrara el equipo del Proyecto");
                        int idMiembro = scanner.nextInt();
                        scanner.nextLine();

                        MiembroEquipo m = null;
                        try {
                            m = (MiembroEquipo) GestionUsuarios.buscarUsuario(idMiembro);
                            GestionProyecto.eliminarMiembroDelEquipo(idProyecto, m.getId()); // solo administrador y lider
                        } catch (UsuarioNoEncontradoException e) {
                            throw new RuntimeException(e);
                        }

                        // Se agrega el proyecto a la lista de proyectos en curso del miembro
                        MiembroEquipo m_modificado = m;

                        Proyecto p = null;

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
                    } else {
                        System.out.println("No tienes permisos para eliminar miembros de un proyecto.");
                    }
                    break;
                case 10:
                    if (usuario instanceof Administrador || usuario instanceof Lider) {
                        MiembroEquipo responsable = null;
                        int idResponsable;
                        System.out.println("* A continuacion, ingrese el ID del responsable de la tarea");
                        idResponsable = scanner.nextInt();
                        scanner.nextLine();

                        try {
                            responsable = (MiembroEquipo) GestionUsuarios.buscarUsuario(idResponsable);
                        } catch (UsuarioNoEncontradoException e) {
                            throw new RuntimeException(e);
                        }

                        // Pedir al usuario el ID del proyecto al que se quiere agregar la tarea
                        System.out.println("* A continuacion, ingrese el ID del Proyecto");
                        int idProyecto = scanner.nextInt();
                        scanner.nextLine();

                        System.out.println("* A continuacion, ingrese el titulo de la Tarea");
                        String titulo = scanner.nextLine();

                        System.out.println("* Por ultimo, ingrese la descripcion de la Tarea");
                        String descripcion = scanner.nextLine();

                        Tarea t = new Tarea(titulo, descripcion, responsable);

                        try {
                            GestionProyecto.agregarTareaAlProyecto(idProyecto, t); // solo administrador y lider
                        } catch (ProyectoNoEncontradoException e) {
                            throw new RuntimeException(e);
                        }

                        System.out.println();
                        System.out.println("La tarea fue agregada con exito al proyecto");
                        System.out.println();
                    } else {
                        System.out.println("No tienes permisos para crear tareas en un proyecto.");
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
