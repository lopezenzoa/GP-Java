package gestion;

import java.io.IOException;
import java.util.Scanner;

public class Menu {
    /**
     * @author Emilia
     */
    public static void menu(String[] args) {
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
                    System.out.println("Crear usuario"); //Solo administrador
                    break;
                case 2:
                    System.out.println("Dar de baja unu usuario");//Solo administrador
                    break;
                case 3:
                    System.out.println("Crear proyecto"); // solo administrador y lider
                    break;
                case 4:
                    System.out.println("Dar de baja un proyecto."); // solo administrador y lider
                    break;
                case 5:
                    System.out.println("Ver todos los proyectos inactivos"); // solo administrador y lider
                    System.out.println(GestionProyecto.verProyectosinactivos().toString());
                    break;
                case 6:
                    System.out.println("Ver todos los proyectos activos:"); // solo administrador y lider
                    System.out.println(GestionProyecto.verProyectosActivos().toString());
                    break;
                case 7:
                    System.out.println("Ver mis proyectos y mis tareas"); // solo miembros
                    break;
                case 8:
                    System.out.println("Has elegido la Opción 8.");
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
