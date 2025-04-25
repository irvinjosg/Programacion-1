import java.util.Scanner;
import java.util.InputMismatchException;

public class OperacionesArray {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] numeros = new int[10];
        boolean entradaValida = false;

        // Ingreso de elementos del array con validación
        System.out.println("Ingrese 10 números enteros:");
        for (int i = 0; i < numeros.length; i++) {
            while (!entradaValida) {
                try {
                    System.out.print("Elemento " + (i + 1) + ": ");
                    numeros[i] = scanner.nextInt();
                    entradaValida = true;
                } catch (InputMismatchException e) {
                    System.out.println("Entrada inválida. Por favor, ingrese un número entero.");
                    scanner.next(); // Limpiar entrada incorrecta
                }
            }
            entradaValida = false; // Reiniciar para el siguiente número
        }

        int opcion = 0;
        do {
            System.out.println("\nMENÚ DE OPCIONES");
            System.out.println("1. Mostrar el array original");
            System.out.println("2. Calcular la suma de los elementos");
            System.out.println("3. Buscar el número máximo y el número mínimo");
            System.out.println("4. Invertir el array");
            System.out.println("5. Salir");

            try {
                System.out.print("Seleccione una opción: ");
                opcion = scanner.nextInt();

                switch (opcion) {
                    case 1:
                        System.out.println("Array original:");
                        mostrarArray(numeros);
                        break;
                    case 2:
                        int suma = 0;
                        for (int num : numeros) {
                            suma += num;
                        }
                        System.out.println("Suma de los elementos: " + suma);
                        break;
                    case 3:
                        int max = numeros[0];
                        int min = numeros[0];
                        for (int num : numeros) {
                            if (num > max) max = num;
                            if (num < min) min = num;
                        }
                        System.out.println("Número máximo: " + max);
                        System.out.println("Número mínimo: " + min);
                        break;
                    case 4:
                        System.out.println("Array invertido:");
                        mostrarArrayInvertido(numeros);
                        break;
                    case 5:
                        System.out.println("Saliendo del programa...");
                        break;
                    default:
                        System.out.println("Opción no válida. Intente de nuevo.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número del menú.");
                scanner.next(); // Limpiar entrada incorrecta
            }

        } while (opcion != 5);

        scanner.close();
    }

    public static void mostrarArray(int[] array) {
        for (int num : array) {
            System.out.print(num + " ");
        }
        System.out.println();
    }

    public static void mostrarArrayInvertido(int[] array) {
        for (int i = array.length - 1; i >= 0; i--) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }
}
