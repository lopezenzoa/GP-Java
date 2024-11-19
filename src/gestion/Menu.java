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
import java.util.ArrayList;
import java.util.InputMismatchException;
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
                System.out.println("5. PROYECTOS INACTIVOS");
                System.out.println("6. PROYECTOS ACTIVOS");
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
                        try {
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

                            if (caso == 0) {
                                try {
                                    GestionUsuarios.agregarUsuario(GestionUsuarios.crearMiembroEquipo(nombre, apellido, correo, titulo, Rol.DEVOPS));
                                } catch (UsuarioExisteException e) {
                                    System.err.println(e.getMessage());
                                    continue;
                                }
                            } else if (caso == 1) {
                                try {
                                    GestionUsuarios.agregarUsuario(GestionUsuarios.crearLider(nombre, apellido, correo, titulo));
                                } catch (UsuarioExisteException e) {
                                    System.err.println(e.getMessage());
                                    continue;
                                }
                            } else if (caso == 2) {
                                try {
                                    GestionUsuarios.agregarUsuario(GestionUsuarios.crearAdministrador(nombre, apellido, correo, titulo));
                                } catch (UsuarioExisteException e) {
                                    System.err.println(e.getMessage());
                                    continue;
                                }
                            } else {
                                System.out.println();
                                System.out.print("Opcion incorrecta");
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

                            System.out.println();
                            System.out.println("El usuario se pudo modificar con exito!");
                            System.out.println();
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

                            p = new Proyecto((Administrador) usuario, (Lider) GestionUsuarios.buscarUsuario(ID), nombreProyecto);

                            GestionProyecto.addProyecto(p); // solo administrador
                            System.out.println();
                            System.out.println("El proyecto fue creado con exito!");
                            System.out.println();
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
                            System.out.println("* A continuacion, ingrese el ID del Proyecto a eliminar");
                            int id = scanner.nextInt();
                            scanner.nextLine();

                            Proyecto aEliminar = GestionProyecto.buscarProyectoPorID(id);
                            GestionProyecto.removeProyecto(aEliminar.getId()); // solo administrador y lider

                            System.out.println();
                            System.out.println("El Proyecto con ID " + id + " se elimino con exito!");
                            System.out.println();
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
                        ArrayList<Proyecto> proyectosInactivos = GestionProyecto.verProyectosinactivos();

                        System.out.println();
                        if (proyectosInactivos.isEmpty())
                            System.out.println("No hay proyectos inactivos");
                        else
                            for (Proyecto o : proyectosInactivos)
                                System.out.println(o); // solo administrador y lider
                        System.out.println();
                    } else {
                        System.out.println("No tienes permisos para ver proyectos inactivos.");
                    }
                    break;
                case 6:
                    if (usuario instanceof Administrador || usuario instanceof Lider ) {
                        ArrayList<Proyecto> proyectosActivos = GestionProyecto.verProyectosActivos();

                        System.out.println();
                        if (proyectosActivos.isEmpty())
                            System.out.println("No hay proyectos activos");
                        else
                            for (Proyecto o : proyectosActivos)
                                System.out.println(o); // solo administrador y lider
                        System.out.println(); // solo administrador y lider
                    } else {
                        System.out.println("No tienes permisos para ver proyectos activos.");
                    }
                    break;
                case 7:
                    if (usuario instanceof MiembroEquipo) {
                        try {
                            MiembroEquipo m = (MiembroEquipo) usuario;

                            System.out.println();
                            System.out.println(m.getProyectosEnCurso());

                            // Pedir el ID del proyecto que se quiere consultar
                            System.out.println("* A continuacion, ingrese el ID del Proyecto a consultar");
                            int idProyecto = scanner.nextInt();
                            scanner.nextLine();

                            ArrayList<Tarea> tareas = GestionProyecto.tareasDeMiembro(idProyecto, m);
                            for (Tarea t : tareas)
                                System.out.println(t);
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
                            System.out.println("* A continuacion, ingrese el ID del Proyecto");
                            idProyecto = scanner.nextInt();
                            scanner.nextLine();

                            System.out.println("* A continuacion, ingrese el ID del Miembro que integrara el equipo del Proyecto");
                            int idMiembro = scanner.nextInt();
                            scanner.nextLine();

                            m = (MiembroEquipo) GestionUsuarios.buscarUsuario(idMiembro);
                            GestionProyecto.agregarMiembroAlEquipo(idProyecto, m); // solo administrador y lider

                            MiembroEquipo m_modificado = m;
                            Proyecto p = null;

                            p = GestionProyecto.buscarProyectoPorID(idProyecto);

                            // Se agrega al atributo 'proyectosEnCurso' del miembro
                            m_modificado.agregarProyecto(p.getNombre());
                            GestionUsuarios.modificarUsuario(m, m_modificado);

                            System.out.println();
                            System.out.println("El usuario fue agregado al equipo con exito!");
                            System.out.println();
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
                            System.out.println("* A continuacion, ingrese el ID del Proyecto");
                            int idProyecto = scanner.nextInt();
                            scanner.nextLine();

                            System.out.println("* A continuacion, ingrese el ID del Miembro que ya no integrara el equipo del Proyecto");
                            int idMiembro = scanner.nextInt();
                            scanner.nextLine();

                            MiembroEquipo m = null;

                            m = (MiembroEquipo) GestionUsuarios.buscarUsuario(idMiembro);
                            GestionProyecto.eliminarMiembroDelEquipo(idProyecto, m.getId());

                            MiembroEquipo m_modificado = m;
                            Proyecto p = GestionProyecto.buscarProyectoPorID(idProyecto);

                            // Se elimina atributo 'proyectosEnCurso' del miembro
                            m_modificado.eliminarProyecto(p.getNombre());
                            GestionUsuarios.modificarUsuario(m, m_modificado);

                            System.out.println();
                            System.out.println("El usuario fue eliminado del equipo con exito!");
                            System.out.println();
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
                            GestionProyecto.agregarTareaAlProyecto(idProyecto, t); // solo administrador y lider

                            System.out.println();
                            System.out.println("La tarea fue agregada con exito al proyecto");
                            System.out.println();
                        } catch (UsuarioNoEncontradoException | InputMismatchException | ClassCastException | ProyectoNoEncontradoException e) {
                            System.err.println(e.getMessage());
                            continue;
                        }
                    } else {
                        System.out.println("No tienes permisos para crear tareas en un proyecto.");
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
        for (int i = 0; i < 100; i++) {
            System.out.println();
        }
    }
}
