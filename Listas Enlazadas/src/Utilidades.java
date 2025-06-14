import java.util.Scanner;
import java.io.IOException;

// Utilidades.java
public class Utilidades {

    private static Scanner scanner = new Scanner(System.in);

    // Método para limpiar la terminal (compatible con Windows y Unix/Linux/macOS)
    public static void limpiarTerminal() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (final Exception e) {
            // Manejar la excepción si la limpieza de terminal falla
            System.out.println("NO SE PUDO LIMPIAR LA TERMINAL: " + e.getMessage());
        }
    }

    // Método para leer una cadena de texto (String) con manejo de entrada especial
    public static String leerString(String mensaje) {
        String entrada;
        while (true) {
            System.out.print(mensaje + ": ");
            try {
                entrada = scanner.nextLine().trim(); // Leer la línea completa y eliminar espacios en blanco al inicio/final
                if (entrada.isEmpty()) {
                    System.out.println("LA ENTRADA NO PUEDE ESTAR VACÍA. INTENTE DE NUEVO.");
                } else if (entrada.equalsIgnoreCase("M") || entrada.equalsIgnoreCase("<")) {
                    return entrada.toUpperCase(); // Devolver 'M' o '<' para control de flujo
                } else {
                    return entrada.toUpperCase(); // Convertir a mayúsculas
                }
            } catch (Exception e) {
                System.out.println("ERROR AL LEER LA ENTRADA. INTENTE DE NUEVO.");
            }
        }
    }

    // Método para leer un número entero (int) con manejo de excepciones y control de flujo
    public static int leerInt(String mensaje) {
        String entrada;
        int numero;
        while (true) {
            System.out.print(mensaje + ": ");
            try {
                entrada = scanner.nextLine().trim();
                if (entrada.equalsIgnoreCase("M")) {
                    return -1; // Usar -1 para indicar que se quiere volver al menú (un valor que no sea un número de páginas válido)
                } else if (entrada.equalsIgnoreCase("<")) {
                    return -2; // Usar -2 para indicar que se quiere volver atrás
                }
                numero = Integer.parseInt(entrada);
                if (numero <= 0) {
                    System.out.println("EL NÚMERO DE PÁGINAS DEBE SER MAYOR A 0. INTENTE DE NUEVO.");
                } else {
                    return numero;
                }
            } catch (NumberFormatException e) {
                System.out.println("ENTRADA INVÁLIDA. POR FAVOR, INGRESE UN NÚMERO ENTERO. INTENTE DE NUEVO.");
            } catch (Exception e) {
                System.out.println("ERROR AL LEER LA ENTRADA. INTENTE DE NUEVO.");
            }
        }
    }
}