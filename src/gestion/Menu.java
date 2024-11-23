package gestion;

import enums.Estado;
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
                System.out.println("5. PROYECTOS PENDIENTES");
                System.out.println("6. PROYECTOS FINALIZADOS");
                System.out.println("8. AGREGAR MIEMBRO A UN PROYECTO");
                System.out.println("9. ELIMINAR MIEMBRO DEL PROYECTO");
                System.out.println("10. CREAR TAREA EN PROYECTO");
                System.out.println("11. LISTAR USUARIOS");
                System.out.println("12. FINALIZAR PROYECTO");
                System.out.println("13. FINALIZAR TAREA");
            } else if (usuario instanceof Lider) {
                System.out.println("\t***** MENU LIDER *****");
                System.out.println("5. PROYECTOS PENDIENTES");
                System.out.println("6. PROYECTOS FINALIZADOS");
                System.out.println("8. AGREGAR MIEMBRO A UN PROYECTO");
                System.out.println("9. ELIMINAR MIEMBRO DEL PROYECTO");
                System.out.println("10. CREAR TAREA EN PROYECTO");
                System.out.println("13. FINALIZAR TAREA");
            } else if (usuario instanceof MiembroEquipo) {
                System.out.println("\t***** MENU MIEMBRO DE EQUIPO *****");
                System.out.println("7. VER TAREAS PENDIENTES");
                System.out.println("14. VER TAREAS FINALIZADAS");
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

                            System.out.print("* Ingrese el nombre del Usuario a crear: ");
                            String nombre = scanner.nextLine();

                            System.out.print("* Ingrese el apellido del Usuario a crear: ");
                            String apellido = scanner.nextLine();

                            System.out.print("* Ingrese el correo del Usuario a crear: ");
                            String correo = scanner.nextLine();

                            System.out.print("* Ingrese el titulo del Usuario a crear: ");
                            String titulo = scanner.nextLine();

                            System.out.print("* Ingrese el Password del Usuario a crear: ");
                            int password = scanner.nextInt();
                            scanner.nextLine();

                            int id = 0;

                            if (caso == 0) {
                                try {
                                    MiembroEquipo m = GestionUsuarios.crearMiembroEquipo(nombre, apellido, correo, titulo, password, Rol.DEVOPS);
                                    GestionUsuarios.agregarUsuario(m);
                                    id = m.getId();
                                } catch (UsuarioExisteException e) {
                                    System.err.println(e.getMessage());
                                    continue;
                                }
                            } else if (caso == 1) {
                                try {
                                    Lider l = GestionUsuarios.crearLider(nombre, apellido, correo, titulo, password);
                                    GestionUsuarios.agregarUsuario(l);
                                    id = l.getId();
                                } catch (UsuarioExisteException e) {
                                    System.err.println(e.getMessage());
                                    continue;
                                }
                            } else if (caso == 2) {
                                try {
                                    Administrador a = GestionUsuarios.crearAdministrador(nombre, apellido, correo, titulo, password);
                                    GestionUsuarios.agregarUsuario(a);
                                    id = a.getId();
                                } catch (UsuarioExisteException e) {
                                    System.err.println(e.getMessage());
                                    continue;
                                }

                                System.out.println();
                                System.out.println("* Se creo el Usuario '" + "' con ID " + id);
                            } else {
                                System.out.println();
                                System.out.print("* Por favor, ingrese un numero entre 0 y 2");
                            }
                        } catch (Exception e) {
                                System.err.println(e.getMessage());
                                continue;
                        }
                    } else {
                        System.out.println();
                        System.out.println("* No tienes permisos para crear un usuario.");
                    }
                    break;
                case 2:
                    if (usuario instanceof Administrador) {
                        try {
                            System.out.println();
                            System.out.println("Lista de Usuarios Activos:");
                            for (Map.Entry<Integer, Usuario> entry : GestionUsuarios.obtenerUsuariosActivos().entrySet()) {
                                int ID = entry.getKey();
                                Usuario u = entry.getValue();

                                System.out.println(" " + u.getNombre() + " " + u.getApellido() + ": " + ID);
                            }

                            System.out.println();
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
                            System.out.println("* El Usuario con nombre " + usuarioAeliminar.getNombre() + " se pudo eliminar con exito!");
                        } catch (UsuarioNoEncontradoException | InputMismatchException e) {
                            System.err.println(e.getMessage());
                            continue;
                        }
                    } else {
                        System.out.println();
                        System.out.println("* No tienes permisos para eliminar un usuario.");
                    }
                    break;
                case 3:
                    if (usuario instanceof Administrador) {
                        try {
                            System.out.println();
                            System.out.println("Lista de Lideres Activos:");
                            for (Map.Entry<Integer, Usuario> entry : GestionUsuarios.obtenerUsuariosActivos().entrySet()) {
                                int ID = entry.getKey();
                                Usuario u = entry.getValue();

                                if (u instanceof Lider)
                                    System.out.println(" " + u.getNombre() + " " + u.getApellido() + ": " + ID);
                            }

                            System.out.println("* A continuacion, ingrese el ID del Lider del Proyecto");
                            int ID = scanner.nextInt();
                            scanner.nextLine();

                            System.out.println("* A conitnuacion, ingrese el Nombre del Proyecto");
                            String nombreProyecto = scanner.nextLine();

                            Proyecto p = new Proyecto((Administrador) usuario, (Lider) GestionUsuarios.buscarUsuario(ID), nombreProyecto);

                            GestionProyecto.addProyecto(p);

                            System.out.println();
                            System.out.println("* El proyecto con nombre '" + p.getNombre() + "' fue creado con exito! (ID: " + p.getId() + ")");
                        } catch (UsuarioNoEncontradoException | InputMismatchException e) {
                            System.err.println(e.getMessage());
                            continue;
                        }
                    } else {
                        System.out.println();
                        System.out.println("* No tienes permisos para crear un nuevo proyecto.");
                    }
                    break;
                case 5:
                    if (usuario instanceof Administrador || usuario instanceof Lider ) {
                        ArrayList<Proyecto> proyectosActivos = GestionProyecto.verProyectosActivos();

                        System.out.println();
                        if (proyectosActivos.isEmpty())
                            System.out.println("No hay proyectos pendientes");
                        else
                            for (Proyecto o : proyectosActivos)
                                if (o.getEstado().equals(Estado.PENDIENTE))
                                    System.out.println(o);
                        System.out.println();
                    } else {
                        System.out.println();
                        System.out.println("* No tienes permisos para ver proyectos activos.");
                    }
                    break;
                case 6:
                    if (usuario instanceof Administrador || usuario instanceof Lider ) {
                        ArrayList<Proyecto> proyectosInactivos = GestionProyecto.verProyectosinactivos();

                        System.out.println();
                        if (proyectosInactivos.isEmpty())
                            System.out.println("No hay proyectos finalizados");
                        else
                            for (Proyecto o : proyectosInactivos)
                                System.out.println(o);
                        System.out.println();
                    } else {
                        System.out.println();
                        System.out.println("* No tienes permisos para ver proyectos activos.");
                    }
                    break;
                case 7:
                    if (usuario instanceof MiembroEquipo) {
                        try {
                            MiembroEquipo m = (MiembroEquipo) usuario;

                            System.out.println();
                            System.out.println("Proyectos en Curso:");
                            for (int idProyecto : m.getProyectosEnCurso())
                                System.out.println('\t' + GestionProyecto.buscarProyectoPorID(idProyecto).getNombre());

                            System.out.println();
                            System.out.println("Tareas Pendientes:");
                            for (int idProyecto : m.getProyectosEnCurso()) {
                                Proyecto p = GestionProyecto.buscarProyectoPorID(idProyecto);

                                for (Tarea t : p.getTareas())
                                    if (t.getResponsable().equals(m) && t.getEstado().equals(Estado.PENDIENTE)) {
                                        System.out.println('\t' + "Titulo: " + t.getTitulo());
                                        System.out.println('\t' + "Descripcion: " + t.getDescripcion());
                                        System.out.println('\t' + "Proyecto: " + p.getNombre() + " (ID: " + p.getId() + ")");

                                        System.out.println();
                                    }
                            }
                        } catch (ProyectoNoEncontradoException | InputMismatchException | ClassCastException e) {
                            System.err.println(e.getMessage());
                            continue;
                        }
                    } else {
                        System.out.println();
                        System.out.println("* No tienes permisos para ver tareas pendientes.");
                    }
                    break;
                case 8:
                    if (usuario instanceof Administrador || usuario instanceof Lider) {
                        MiembroEquipo m = null;
                        int idProyecto;

                        try {
                            System.out.println();
                            System.out.println("Lista de Proyectos Activos:");
                            for (Proyecto p : GestionProyecto.verProyectosActivos())
                                System.out.println(" " + p.getNombre() + ": " + p.getId());

                            System.out.println("* A continuacion, ingrese el ID del Proyecto");
                            idProyecto = scanner.nextInt();
                            scanner.nextLine();

                            HashSet<MiembroEquipo> equipo = GestionProyecto.buscarProyectoPorID(idProyecto).getEquipo();

                            System.out.println();
                            System.out.println("Lista de Miembros Activos:");
                            for (Map.Entry<Integer, Usuario> entry : GestionUsuarios.obtenerUsuariosActivos().entrySet()) {
                                int ID = entry.getKey();
                                Usuario u = entry.getValue();

                                if (u instanceof MiembroEquipo) {
                                    if (!equipo.contains(u))
                                        System.out.println(" " + u.getNombre() + " " + u.getApellido() + ": " + ID);
                                }
                            }

                            System.out.println();
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

                            System.out.println();
                            System.out.println("* El usuario con nombre '" + m.getNombre() + "' fue agregado al equipo con exito!");
                        } catch (UsuarioNoEncontradoException | UsuarioExisteException | InputMismatchException | ProyectoNoEncontradoException e) {
                            System.err.println(e.getMessage());
                            continue;
                        }
                    } else {
                        System.out.println();
                        System.out.println("* No tienes permisos para agregar miembros a un proyecto.");
                    }
                    break;
                case 9:
                    if (usuario instanceof Administrador || usuario instanceof Lider) {
                        try {
                            System.out.println();
                            System.out.println("Lista de Proyectos Activos:");
                            for (Proyecto p : GestionProyecto.verProyectosActivos())
                                System.out.println(" " + p.getNombre() + ": " + p.getId());

                            System.out.println();
                            System.out.println("* A continuacion, ingrese el ID del Proyecto");
                            int idProyecto = scanner.nextInt();
                            scanner.nextLine();

                            HashSet<MiembroEquipo> equipo = GestionProyecto.buscarProyectoPorID(idProyecto).getEquipo();

                            System.out.println();
                            System.out.println("IDs de los miembros del equipo:");
                            for (MiembroEquipo e : equipo)
                                System.out.println(" " + e.getNombre() + " " + e.getApellido() + ": " + e.getId());

                            System.out.println();
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

                            System.out.println();
                            System.out.println("* El usuario con nombre '" + m.getNombre() + "' fue eliminado del equipo con exito!");
                        } catch (UsuarioNoEncontradoException | InputMismatchException | ProyectoNoEncontradoException | ClassCastException e) {
                            System.err.println(e.getMessage());
                            continue;
                        }
                    } else {
                        System.out.println();
                        System.out.println("* No tienes permisos para eliminar miembros de un proyecto.");
                    }
                    break;
                case 10:
                    if (usuario instanceof Administrador || usuario instanceof Lider) {
                        MiembroEquipo responsable = null;
                        int idResponsable;

                        try {
                            System.out.println();
                            System.out.println("Lista de Proyectos Activos:");
                            for (Proyecto p : GestionProyecto.verProyectosActivos())
                                System.out.println(" " + p.getNombre() + ": " + p.getId());

                            System.out.println();
                            System.out.println("* A continuacion, ingrese el ID del Proyecto");
                            int idProyecto = scanner.nextInt();
                            scanner.nextLine();

                            HashSet<MiembroEquipo> equipo = GestionProyecto.buscarProyectoPorID(idProyecto).getEquipo();

                            System.out.println();
                            System.out.println("IDs de los miembros del equipo:");
                            for (MiembroEquipo e : equipo)
                                System.out.println(" " + e.getNombre() + " " + e.getApellido() + ": " + e.getId());

                            System.out.println();
                            System.out.println("* A continuacion, ingrese el ID del responsable de la tarea");
                            idResponsable = scanner.nextInt();
                            scanner.nextLine();

                            responsable = (MiembroEquipo) GestionUsuarios.buscarUsuario(idResponsable);

                            System.out.println("* A continuacion, ingrese el titulo de la Tarea");
                            String titulo = scanner.nextLine();

                            System.out.println("* Por ultimo, ingrese la descripcion de la Tarea");
                            String descripcion = scanner.nextLine();

                            Tarea t = new Tarea(titulo, descripcion, responsable);
                            GestionProyecto.agregarTareaAlProyecto(idProyecto, t);

                            System.out.println();
                            System.out.println("* La tarea con titulo '" + t.getTitulo() + "' fue agregada con exito al proyecto");
                        } catch (UsuarioNoEncontradoException | InputMismatchException | ClassCastException | ProyectoNoEncontradoException e) {
                            System.err.println(e.getMessage());
                            continue;
                        }
                    } else {
                        System.out.println();
                        System.out.println("* No tienes permisos para crear tareas en un proyecto.");
                    }
                    break;
                case 11:
                    if (usuario instanceof Administrador) {
                        System.out.println();
                        System.out.println("Lista de Usuarios Activos:");
                        for (Map.Entry<Integer, Usuario> entry : GestionUsuarios.obtenerUsuariosActivos().entrySet()) {
                            int ID = entry.getKey();
                            Usuario u = entry.getValue();

                            System.out.println(" " + u.getNombre() + " " + u.getApellido() + ": " + ID);
                        }
                    } else {
                        System.out.println();
                        System.out.println("* No tenes permiso para ver los usuarios");
                    }
                    break;
                case 12:
                    if (usuario instanceof Administrador) {
                        System.out.println();
                        System.out.println("Lista de Proyectos Pendientes:");
                        for (Proyecto p : GestionProyecto.verProyectosActivos())
                            if (p.getEstado().equals(Estado.PENDIENTE))
                                System.out.println(" " + p.getNombre() + ": " + p.getId());

                        System.out.println();
                        System.out.println("* A continuacion, ingrese el ID del Proyecto a finalizar");
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
                        System.out.println();
                        System.out.println("* No tienes permiso para finalizar proyectos");
                    }
                    break;
                case 13:
                    if (usuario instanceof Administrador || usuario instanceof Lider) {
                        try {
                            System.out.println();
                            System.out.println("Lista de Proyectos Pendientes:");
                            for (Proyecto p : GestionProyecto.verProyectosActivos())
                                if (p.getEstado().equals(Estado.PENDIENTE))
                                    System.out.println(" " + p.getNombre() + ": " + p.getId());

                            System.out.println();
                            System.out.println("* A continuacion, ingrese el ID del Proyecto al que pertenece la Tarea");
                            int idProyecto = scanner.nextInt();
                            scanner.nextLine();

                            ArrayList<Tarea> tareas = GestionProyecto.verTareasPendientes(idProyecto);

                            System.out.println();
                            System.out.println("Tareas Pendientes del proyecto:");
                            for (Tarea t : tareas)
                                System.out.println(" " + t.getTitulo() + ": " + t.getId());

                            System.out.println();
                            System.out.println("* A continuacion, ingrese el ID de la Tarea");
                            int idTarea = scanner.nextInt();
                            scanner.nextLine();

                            Tarea t = GestionProyecto.buscarTareaPorID(idProyecto, idTarea);
                            GestionProyecto.finalizarTarea(idProyecto, t);
                        } catch (TareaNoEncontradaException | ProyectoNoEncontradoException e) {
                            System.err.println(e.getMessage());
                            continue;
                        }
                    } else {
                        System.out.println();
                        System.out.println("* No tenes permiso para finalizar tareas");
                    }
                    break;
                case 14:
                    if (usuario instanceof MiembroEquipo) {
                        try {
                            MiembroEquipo m = (MiembroEquipo) usuario;

                            System.out.println();
                            System.out.println("Proyectos en Curso:");
                            for (int idProyecto : m.getProyectosEnCurso())
                                System.out.println('\t' + GestionProyecto.buscarProyectoPorID(idProyecto).getNombre());

                            System.out.println();
                            System.out.println("Tareas Finalizadas:");
                            for (int idProyecto : m.getProyectosEnCurso()) {
                                Proyecto p = GestionProyecto.buscarProyectoPorID(idProyecto);

                                for (Tarea t : p.getTareas())
                                    if (t.getResponsable().equals(m) && t.getEstado().equals(Estado.FINALIZADO)) {
                                        System.out.println('\t' + "Titulo: " + t.getTitulo());
                                        System.out.println('\t' + "Descripcion: " + t.getDescripcion());
                                        System.out.println('\t' + "Proyecto: " + p.getNombre() + " (ID: " + p.getId() + ")");

                                        System.out.println();
                                    }
                            }
                        } catch (ProyectoNoEncontradoException | InputMismatchException | ClassCastException e) {
                            System.err.println(e.getMessage());
                            continue;
                        }
                    } else {
                        System.out.println();
                        System.out.println("* No tienes permisos para ver tareas finalizadas.");
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
        System.out.println();
        /*
        for (int i = 0; i < 100; i++) {
            System.out.println();
        }

         */
    }
}
