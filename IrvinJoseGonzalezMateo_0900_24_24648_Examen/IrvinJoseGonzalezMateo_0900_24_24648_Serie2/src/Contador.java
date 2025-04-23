import java.io.*;
import java.util.InputMismatchException; 
import java.util.Scanner;

public class Contador {

    public static void main(String[] args) {
        Contador contador = new Contador();
        contador.iniciarOperacion();
    }

    public void iniciarOperacion() {
        operacion();
    }    

    private void operacion() {
        Scanner scanner = new Scanner(System.in);
        int opcion = -1;

        do {
            System.out.println("\n=== Ingrese el numero positivo ===");

            try {
                opcion = scanner.nextInt();
                if (opcion < 0) {
                    System.out.println("El numero ingresado no es positivo");
                } else {
                    System.out.println("El numero ingresado es positivo");

                    // Mostrar los numeros pares de 0 al numero ingresado
                    System.out.print("Los numeros pares de 0 al " + opcion + " son: ");
                    for (int par = 0; par <= opcion; par++) {
                        if (par % 2 == 0) {
                            System.out.print(par + " ");
                        }
                    }
                    System.out.println();

                    // Mostrar los numeros impares de 0 al numero ingresado
                    System.out.print("Los numeros impares de 0 al " + opcion + " son: ");
                    for (int impar = 0; impar <= opcion; impar++) {
                        if (impar % 2 != 0) {
                            System.out.print(impar + " ");
                        }
                    }
                    System.out.println();
                }
            } catch (InputMismatchException e) {
                System.out.println("El valor ingresado no es un numero");
                scanner.next(); // Limpiar el scanner
            }
        } while (opcion != 0);

        scanner.close();
    }
}