
import java.util.Scanner;
import java.util.Stack;

/*
Nombre: Irvin José González Mateo
Carnet: 0900-24-24648
Curso: Programación 1
*/

//Programa que invierte una cadena de texto utilizando una pila.
public class InversorTexto {
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        boolean continuar = true;
        
        while (continuar) {
            limpiarConsola();
            mostrarMenu();
            
            try {
                int opcion = obtenerOpcion();
                
                switch (opcion) {
                    case 1:
                        procesarTexto();
                        break;
                    case 2:
                        continuar = false;
                        break;
                    default:
                        System.out.println("\nOpción no válida. Presione Enter para continuar...");
                        scanner.nextLine();
                }
            } catch (Exception e) {
                System.out.println("\nError: " + e.getMessage());
                System.out.println("Presione Enter para continuar...");
                scanner.nextLine();
            }
        }
        
        System.out.println("\n¡Gracias por usar el Inversor de Texto!");
        scanner.close();
    }
    
    
    //Muestra el menú principal del programa.
    
    private static void mostrarMenu() {
        System.out.println("########################################");
        System.out.println("          << INVERSOR DE TEXTO >>           ");
        System.out.println("########################################");
        System.out.println(" 1. Invertir una cadena de texto      ");
        System.out.println(" 2. Salir                             ");
        System.out.print("Seleccione una opción: ");
    }
    

    private static int obtenerOpcion() throws Exception {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new Exception("Debe ingresar un número válido.");
        }
    }
    
    private static void procesarTexto() throws Exception {
        limpiarConsola();
        System.out.println("########################################");
System.out.println("#     >> INVERTIR CADENA DE TEXTO <<   #");
System.out.println("########################################");

System.out.print("\nIngrese el texto a invertir: ");
String textoOriginal = scanner.nextLine();

if (textoOriginal == null || textoOriginal.trim().isEmpty()) {
    throw new Exception("El texto no puede estar vacío.");

}

String textoInvertido = invertirCadena(textoOriginal);

System.out.println("\n########################################");
System.out.println("#           >> RESULTADO <<            #");
System.out.println("########################################");
System.out.println("# Texto original  : " + formatearTexto(textoOriginal));
System.out.println("# Texto invertido : " + formatearTexto(textoInvertido));
System.out.println("########################################");

System.out.println("\nPresione Enter para continuar...");
scanner.nextLine();

    }
    

    private static String invertirCadena(String texto) {
        // Crear una pila para almacenar los caracteres
        Stack<Character> pila = new Stack<>();
        
        // Almacenar cada carácter en la pila
        for (int i = 0; i < texto.length(); i++) {
            pila.push(texto.charAt(i));
        }
        
        // Construir la cadena invertida sacando los caracteres de la pila
        StringBuilder resultado = new StringBuilder();
        while (!pila.isEmpty()) {
            resultado.append(pila.pop());
        }
        
        return resultado.toString();
    }
    
    
    private static String formatearTexto(String texto) {
        if (texto.length() > 50) {
            return texto.substring(0, 47) + "...";
        }
        return texto;
    }
    

    //Limpia la consola para mejorar la experiencia del usuario.
    
    private static void limpiarConsola() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            // Si ocurre un error al limpiar la consola, imprimir líneas en blanco
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }
}