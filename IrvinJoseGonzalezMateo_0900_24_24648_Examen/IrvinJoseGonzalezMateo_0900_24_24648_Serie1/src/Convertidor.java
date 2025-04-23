import java.io.*;
import java.util.InputMismatchException; 
import java.util.Scanner;

public class Convertidor {

    public static void main(String[] args) {
        Convertidor convertidor = new Convertidor();
        convertidor.iniciarMenu();
    }
    public void iniciarMenu() {
        mostrarMenu();
    }    
    // muestra el menú principal y maneja las opciones
private void mostrarMenu() {
    Scanner scanner = new Scanner(System.in);
    int opcion;

    do {
        // interfaz de menú
        System.out.println("\n=== CONVERSION DE MONEDAS ===");
        System.out.println("1. DOLAR A EURO");
        System.out.println("2. EURO A DOLAR");
        System.out.println("3. DOLAR A QUETZAL");
        System.out.println("4. QUETZAL A DOLAR");
        System.out.println("5. SALIR");
        System.out.print("Seleccione una opción: ");

        try {
            opcion = scanner.nextInt();
            
            // manejo de selección de opciones
            switch (opcion) {
                case 1:
                    dollarEuro(scanner);
                    break;
                case 2:
                    euroDollar(scanner);
                    break;
                case 3:
                    dollarQuetzal(scanner);
                    break;
                case 4:
                    quetzalDolar(scanner);
                    break;
                case 5:
                    System.out.println("¡Gracias por usar nuestro servicio!");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        } catch (InputMismatchException e) {
            // maneja entrada no numérica
            System.out.println("Error: Debe ingresar un número válido");
            scanner.nextLine();
            opcion = 0;
        } catch (IOException e) {
            // maneja errores de archivo
            System.out.println("Error en operación: " + e.getMessage());
            opcion = 5;
        }
    } while (opcion != 5);
    
    scanner.close();
}

// Conversion de dolar a euro
private void dollarEuro(Scanner scanner) throws IOException {
    System.out.print("Ingrese monto a convertir: ");
    
    try {
        double monto = scanner.nextDouble();
        // valida monto positivo
        if (monto <= 0) {
            System.out.println("El monto debe ser positivo");
            return;
        }
        double resultado = monto * 0.92;
        System.out.printf("El monto en euros es: $%.2f%n", resultado);

    } catch (InputMismatchException e) {
        // maneja entrada no numérica en retiro
        System.out.println("Error: Ingrese un valor numérico válido");
        scanner.nextLine();
    }
}
// conversion de euro a dolar
private void euroDollar(Scanner scanner) throws IOException {
    System.out.print("Ingrese monto a convertir: ");
    
    try {
        double monto = scanner.nextDouble();
        // valida monto positivo
        if (monto <= 0) {
            System.out.println("El monto debe ser positivo");
            return;
        }
        double resultado = monto / 0.92;
        System.out.printf("El monto en dólares es: $%.2f%n", resultado);

    } catch (InputMismatchException e) {
        // maneja entrada no numérica en retiro
        System.out.println("Error: Ingrese un valor numérico válido");
        scanner.nextLine();
    }
}

// conversion de dolar a quetzal
private void dollarQuetzal(Scanner scanner) throws IOException {
    System.out.print("Ingrese monto a convertir: ");
    
    try {
        double monto = scanner.nextDouble();
        // valida monto positivo
        if (monto <= 0) {
            System.out.println("El monto debe ser positivo");
            return;
        }
        double resultado = monto * 7.70;
        System.out.printf("El monto en quetzales es: Q%.2f%n", resultado);

    } catch (InputMismatchException e) {
        // maneja entrada no numérica en retiro
        System.out.println("Error: Ingrese un valor numérico válido");
        scanner.nextLine();
    }
}

// conversion de quetzal a dolar
private void quetzalDolar(Scanner scanner) throws IOException {
    System.out.print("Ingrese monto a convertir: ");
    
    try {
        double monto = scanner.nextDouble();
        // valida monto positivo
        if (monto <= 0) {
            System.out.println("El monto debe ser positivo");
            return;
        }
        double resultado = monto / 7.70;
        System.out.printf("El monto en dólares es: $%.2f%n", resultado);

    } catch (InputMismatchException e) {
        // maneja entrada no numérica en retiro
        System.out.println("Error: Ingrese un valor numérico válido");
        scanner.nextLine();
    }
}

    
}
