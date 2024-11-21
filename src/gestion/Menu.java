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
import java.util.*;

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
            if (usuario instanceof Administrador) {
                System.out.println("\t***** MENU ADMINISTRADOR *****");
                System.out.println("1. CREAR USUARIO");
                System.out.println("2. ELIMINAR USUARIO");
                System.out.println("3. CREAR NUEVO PROYECTO");
                System.out.println("4. ELIMINAR PROYECTO");
                System.out.println("5. PROYECTOS INACTIVOS");
                System.out.println("6. PROYECTOS ACTIVOS");
                System.out.println("8. AGREGAR MIEMBRO A UN PROYECTO");
                System.out.println("9. ELIMINAR MIEMBRO DEL PROYECTO");
                System.out.println("10. CREAR TAREA EN PROYECTO");
                System.out.println("11. LISTAR USUARIOS");
                System.out.println("12. FINALIZAR PROYECTO");
                System.out.println("13. FINALIZAR TAREA");
            } else if (usuario instanceof Lider) {
                System.out.println("\t***** MENU LIDER *****");
                System.out.println("5. PROYECTOS INACTIVOS");
                System.out.println("6. PROYECTOS ACTIVOS");
                System.out.println("8. AGREGAR MIEMBRO A UN PROYECTO");
                System.out.println("9. ELIMINAR MIEMBRO DEL PROYECTO");
                System.out.println("10. CREAR TAREA EN PROYECTO");
                System.out.println("13. FINALIZAR TAREA");
            } else if (usuario instanceof MiembroEquipo) {
                System.out.println("\t***** MENU MIEMBRO DE EQUIPO *****");
                System.out.println("7. VER TAREAS");
            } else {
                System.out.println("No tienes permisos para acceder a este menú.");
                return; // Sale del método si no hay ningún rol válido
            }

            System.out.println();
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
                        try {
                            int caso = -1;

                            System.out.println("* Ingrese el tipo de Usuario a crear: \n[0] Miembro Equipo \n[1] Lider \n[2] Administrador");
                            caso = scanner.nextInt();
                            scanner.nextLine();

                            System.out.print("Ingrese el nombre del Usuario a crear: ");
                            String nombre = scanner.nextLine();

                            System.out.print("Ingrese el apellido del Usuario a crear: ");
                            String apellido = scanner.nextLine();

                            System.out.print("Ingrese el correo del Usuario a crear: ");
                            String correo = scanner.nextLine();

                            System.out.print("Ingrese el titulo del Usuario a crear: ");
                            String titulo = scanner.nextLine();

                            System.out.print("Ingrese el Password del Usuario a crear: ");
                            int password = scanner.nextInt();
                            scanner.nextLine();

                            if (caso == 0) {
                                try {
                                    MiembroEquipo m = GestionUsuarios.crearMiembroEquipo(nombre, apellido, correo, titulo, password, Rol.DEVOPS);
                                    GestionUsuarios.agregarUsuario(m);
                                } catch (UsuarioExisteException e) {
                                    System.err.println(e.getMessage());
                                    continue;
                                }
                            } else if (caso == 1) {
                                try {
                                    Lider l = GestionUsuarios.crearLider(nombre, apellido, correo, titulo, password);
                                    GestionUsuarios.agregarUsuario(l);
                                } catch (UsuarioExisteException e) {
                                    System.err.println(e.getMessage());
                                    continue;
                                }
                            } else if (caso == 2) {
                                try {
                                    Administrador a = GestionUsuarios.crearAdministrador(nombre, apellido, correo, titulo, password);
                                    GestionUsuarios.agregarUsuario(a);
                                } catch (UsuarioExisteException e) {
                                    System.err.println(e.getMessage());
                                    continue;
                                }
                            } else {
                                System.out.println();
                                System.out.print("Por favor, ingrese un numero entre 0 y 2");
                            }
                        } catch (Exception e) {
                                System.err.println(e.getMessage());
                                continue;
                        }
                    } else {
                        System.out.println("No tienes permisos para crear un usuario.");
                    }
                    break;
                case 2:
                    if (usuario instanceof Administrador) {
                        try {
                            // Para esta opcion, se deberia imprimir una tabla con los IDs de todos los miembros y lideres

                            System.out.println("* A continuacion, ingrese el ID del Usuario que quiere eliminar");

                            int ID = scanner.nextInt();
                            scanner.nextLine();

                            Usuario usuarioAeliminar = GestionUsuarios.buscarUsuario(ID);

                            if (usuarioAeliminar instanceof Administrador) {
                                GestionUsuarios.eliminarUsuario((Administrador) usuarioAeliminar);
                            } else if (usuarioAeliminar instanceof Lider) {
                                GestionUsuarios.eliminarUsuario((Lider) usuarioAeliminar);
                            } else if (usuarioAeliminar instanceof MiembroEquipo) {
                                GestionUsuarios.eliminarUsuario((MiembroEquipo) usuarioAeliminar);
                            }

                            System.out.println("* El Usuario con nombre " + usuarioAeliminar.getNombre() + " se pudo eliminar con exito!");
                        } catch (UsuarioNoEncontradoException | InputMismatchException e) {
                            System.err.println(e.getMessage());
                            continue;
                        }
                    } else {
                        System.out.println("No tienes permisos para eliminar un usuario.");
                    }
                    break;
                case 3:
                    if (usuario instanceof Administrador) {
                        try {
                            // Para esta opcion, se deberian imprimir una tabla con los IDs de los lideres

                            System.out.println("* A continuacion, ingrese el ID del Lider del Proyecto");
                            int ID = scanner.nextInt();
                            scanner.nextLine();

                            System.out.println("* A conitnuacion, ingrese el Nombre del Proyecto");
                            String nombreProyecto = scanner.nextLine();

                            Proyecto p = new Proyecto((Administrador) usuario, (Lider) GestionUsuarios.buscarUsuario(ID), nombreProyecto);

                            GestionProyecto.addProyecto(p);
                            System.out.println("* El proyecto con nombre '" + p.getNombre() + "' fue creado con exito!");
                        } catch (UsuarioNoEncontradoException | InputMismatchException e) {
                            System.err.println(e.getMessage());
                            continue;
                        }
                    } else {
                        System.out.println("No tienes permisos para crear un nuevo proyecto.");
                    }
                    break;
                case 4:
                    if (usuario instanceof Administrador) {
                        try {
                            // Para esta opcion, se deberian imprimir una tabla con los IDs de los proyectos, junto con su nombre

                            System.out.println("* A continuacion, ingrese el ID del Proyecto a eliminar");
                            int id = scanner.nextInt();
                            scanner.nextLine();

                            Proyecto aEliminar = GestionProyecto.buscarProyectoPorID(id);
                            GestionProyecto.removeProyecto(aEliminar.getId());

                            System.out.println("* El Proyecto con ID '" + aEliminar.getNombre() + "' se elimino con exito!");
                        } catch (ProyectoNoEncontradoException | InputMismatchException e) {
                            System.err.println(e.getMessage());
                            continue;
                        }
                    } else {
                        System.out.println("No tienes permisos para eliminar un proyecto.");
                    }
                    break;
                case 5:
                    if (usuario instanceof Administrador || usuario instanceof Lider) {
                        // Para esta opcion, se deberian imprimir una tabla con los proyectos y la informacion mas relevante

                        ArrayList<Proyecto> proyectosInactivos = GestionProyecto.verProyectosinactivos();

                        System.out.println();
                        if (proyectosInactivos.isEmpty())
                            System.out.println("* No hay proyectos inactivos");
                        else
                            for (Proyecto o : proyectosInactivos)
                                System.out.println(o);
                        System.out.println();
                    } else {
                        System.out.println("No tienes permisos para ver proyectos inactivos.");
                    }
                    break;
                case 6:
                    if (usuario instanceof Administrador || usuario instanceof Lider ) {
                        // Para esta opcion, se deberian imprimir una tabla con los proyectos y la informacion mas relevante

                        ArrayList<Proyecto> proyectosActivos = GestionProyecto.verProyectosActivos();

                        System.out.println();
                        if (proyectosActivos.isEmpty())
                            System.out.println("No hay proyectos activos");
                        else
                            for (Proyecto o : proyectosActivos)
                                System.out.println(o);
                        System.out.println();
                    } else {
                        System.out.println("No tienes permisos para ver proyectos activos.");
                    }
                    break;
                case 7:
                    if (usuario instanceof MiembroEquipo) {
                        try {
                            // Para esta opcion, se deberian imprimir una tabla con los proyectos en curso y las tareas pendientes

                            MiembroEquipo m = (MiembroEquipo) usuario;

                            System.out.println();
                            System.out.println("Proyectos en Curso:");
                            for (int idProyecto : m.getProyectosEnCurso())
                                System.out.println('\t' + GestionProyecto.buscarProyectoPorID(idProyecto).getNombre());

                            System.out.println("Tareas Pendientes:");
                            for (int idProyecto : m.getProyectosEnCurso()) {
                                Proyecto p = GestionProyecto.buscarProyectoPorID(idProyecto);

                                for (Tarea t : p.getTareas())
                                    if (t.getResponsable().equals(m))
                                        System.out.println(t);
                            }
                        } catch (ProyectoNoEncontradoException | InputMismatchException | ClassCastException e) {
                            System.err.println(e.getMessage());
                            continue;
                        }
                    } else {
                        System.out.println("No tienes permisos para ver tareas.");
                    }
                    break;
                case 8:
                    if (usuario instanceof Administrador || usuario instanceof Lider) {
                        MiembroEquipo m = null;
                        int idProyecto;

                        try {
                            // Para esta opcion, se deberian imprimir una tabla con los IDs de los proyectos y los IDs de los miembros de cada equipo

                            System.out.println("* A continuacion, ingrese el ID del Proyecto");
                            idProyecto = scanner.nextInt();
                            scanner.nextLine();

                            System.out.println("* A continuacion, ingrese el ID del Miembro que integrara el equipo del Proyecto");
                            int idMiembro = scanner.nextInt();
                            scanner.nextLine();

                            // Se busca al usuario por ID y se lo agrega al equipo
                            m = (MiembroEquipo) GestionUsuarios.buscarUsuario(idMiembro);
                            GestionProyecto.agregarMiembroAlEquipo(idProyecto, m);

                            // Se agrega a la coleccion 'proyectosEnCurso' del miembro y se modifica en el archivo usuarios.json
                            MiembroEquipo m_modificado = m;
                            Proyecto p = GestionProyecto.buscarProyectoPorID(idProyecto);

                            m_modificado.agregarProyecto(p.getId());
                            GestionUsuarios.modificarUsuario(m, m_modificado);

                            System.out.println("* El usuario con nombre '" + m.getNombre() + "' fue agregado al equipo con exito!");
                        } catch (UsuarioNoEncontradoException | UsuarioExisteException | InputMismatchException | ProyectoNoEncontradoException e) {
                            System.err.println(e.getMessage());
                            continue;
                        }
                    } else {
                        System.out.println("No tienes permisos para agregar miembros a un proyecto.");
                    }
                    break;
                case 9:
                    if (usuario instanceof Administrador || usuario instanceof Lider) {
                        try {
                            // Para esta opcion, se deberian imprimir una tabla con los IDs de los proyectos y los IDs de los miembros de cada equipo

                            System.out.println("* A continuacion, ingrese el ID del Proyecto");
                            int idProyecto = scanner.nextInt();
                            scanner.nextLine();

                            System.out.println("* A continuacion, ingrese el ID del Miembro que ya no integrara el equipo del Proyecto");
                            int idMiembro = scanner.nextInt();
                            scanner.nextLine();

                            // Se busca al usuario por ID y se lo elimina del equipo
                            MiembroEquipo m = (MiembroEquipo) GestionUsuarios.buscarUsuario(idMiembro);
                            GestionProyecto.eliminarMiembroDelEquipo(idProyecto, m.getId());

                            // Se elimina del la coleccion 'proyectosEnCurso' del miembro y se modifica en el archivo usuarios.json
                            MiembroEquipo m_modificado = m;
                            Proyecto p = GestionProyecto.buscarProyectoPorID(idProyecto);

                            m_modificado.eliminarProyecto(p.getId());
                            GestionUsuarios.modificarUsuario(m, m_modificado);

                            System.out.println("* El usuario con nombre '" + m.getNombre() + "' fue eliminado del equipo con exito!");
                        } catch (UsuarioNoEncontradoException | InputMismatchException | ProyectoNoEncontradoException | ClassCastException e) {
                            System.err.println(e.getMessage());
                            continue;
                        }
                    } else {
                        System.out.println("No tienes permisos para eliminar miembros de un proyecto.");
                    }
                    break;
                case 10:
                    if (usuario instanceof Administrador || usuario instanceof Lider) {
                        MiembroEquipo responsable = null;
                        int idResponsable;

                        try {
                            // Para esta opcion, se deberian imprimir una tabla con los IDs de los proyectos y los IDs de los miembros de cada equipo

                            System.out.println("* A continuacion, ingrese el ID del responsable de la tarea");
                            idResponsable = scanner.nextInt();
                            scanner.nextLine();

                            responsable = (MiembroEquipo) GestionUsuarios.buscarUsuario(idResponsable);

                            System.out.println("* A continuacion, ingrese el ID del Proyecto");
                            int idProyecto = scanner.nextInt();
                            scanner.nextLine();

                            System.out.println("* A continuacion, ingrese el titulo de la Tarea");
                            String titulo = scanner.nextLine();

                            System.out.println("* Por ultimo, ingrese la descripcion de la Tarea");
                            String descripcion = scanner.nextLine();

                            Tarea t = new Tarea(titulo, descripcion, responsable);
                            GestionProyecto.agregarTareaAlProyecto(idProyecto, t);

                            System.out.println("La tarea con titulo '" + t.getTitulo() + "' fue agregada con exito al proyecto");
                        } catch (UsuarioNoEncontradoException | InputMismatchException | ClassCastException | ProyectoNoEncontradoException e) {
                            System.err.println(e.getMessage());
                            continue;
                        }
                    } else {
                        System.out.println("No tienes permisos para crear tareas en un proyecto.");
                    }
                    break;
                case 11:
                    // Para esta opcion, se deberia mostrar una tabla con todos los usuarios
                    if (usuario instanceof Administrador) {
                        HashMap<Integer, Usuario> usuarios = GestionUsuarios.obtenerUsuariosActivos();

                        for (Map.Entry<Integer, Usuario> entry : usuarios.entrySet())
                            System.out.println(entry.getKey() + " : " + entry.getValue().getNombre());
                    } else {
                        System.out.println("No tenes permiso para ver los usuarios");
                    }
                    break;
                case 12:
                    // Para esta opcion, se deberia mostrar una tabla con los IDs de los proyectos

                    if (usuario instanceof Administrador) {
                        System.out.println("* A continuacion, ingrese el ID del Proyecto");
                        int idProyecto = scanner.nextInt();
                        scanner.nextLine();

                        try {
                            Proyecto p = GestionProyecto.buscarProyectoPorID(idProyecto);
                            GestionProyecto.finalizarProyecto(p);
                        } catch (ProyectoNoEncontradoException e) {
                            System.err.println(e.getMessage());
                            continue;
                        }
                    } else {
                        System.out.println("No tienes permiso para finalizar proyectos");
                    }
                    break;
                case 13:
                    // Para esta opcion, se deberia mostrar una tabla con los IDs de los proyectos y las tareas que tiene

                    if (usuario instanceof Administrador || usuario instanceof Lider) {
                        System.out.println("* A continuacion, ingrese el ID del Proyecto al que pertenece la Tarea");
                        int idProyecto = scanner.nextInt();
                        scanner.nextLine();

                        System.out.println("* A continuacion, ingrese el ID de la Tarea");
                        int idTarea = scanner.nextInt();
                        scanner.nextLine();

                        try {
                            Tarea t = GestionProyecto.buscarTareaPorID(idProyecto, idTarea);
                            GestionProyecto.finalizarTarea(idProyecto, t);
                        } catch (TareaNoEncontradaException | ProyectoNoEncontradoException e) {
                            System.err.println(e.getMessage());
                            continue;
                        }
                    } else {
                        System.out.println("No tenes permiso para finalizar tareas");
                    }
                    break;
                default:
                    System.out.println("Opción no válida, por favor inténtalo de nuevo.");
            }

            System.out.println("Presione ENTER para continuar");
        }
    }

    // Método para limpiar la consola en Windows
    private static void clearConsole() {
        System.out.println();
        /*
        for (int i = 0; i < 100; i++) {
            System.out.println();
        }

         */
    }
}
