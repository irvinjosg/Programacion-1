// declara el paquete para organización del código
package com.cajero;

// importa clases para operaciones de entrada/salida
import java.io.*;
// importa excepción para tipos de entrada incorrectos
import java.util.InputMismatchException; 
// importa clase para leer entrada del usuario
import java.util.Scanner;

public class Cajero {
    // nombre del archivo para guardar el saldo
    private static final String ARCHIVO_SALDO = "saldo.dat";
    // saldo inicial cuando se crea el archivo por primera vez
    private static final double SALDO_INICIAL = 1000.0;

    // punto de entrada principal del programa
    public static void main(String[] args) {
        new Cajero().iniciarCajero();
    }

    // inicia el flujo principal del cajero
    public void iniciarCajero() {
        inicializarArchivoSaldo();
        mostrarMenu();
    }

    // verifica y crea el archivo de saldo si no existe
    private void inicializarArchivoSaldo() {
        File archivo = new File(ARCHIVO_SALDO);
        if (!archivo.exists()) {
            // try-with-resources para manejo automático de cierre
            try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(ARCHIVO_SALDO))) {
                dos.writeDouble(SALDO_INICIAL);
                System.out.println("Archivo de saldo creado con saldo inicial: Q" + SALDO_INICIAL);
            } catch (IOException e) {
                System.out.println("Error al crear archivo de saldo: " + e.getMessage());
            }
        }
    }

        // Método para limpiar la consola
        private void limpiarConsola() {
            try {
                // Secuencia ANSI para limpiar la consola (funciona en la mayoría de terminales compatibles)
                if (System.getProperty("os.name").contains("Windows")) {
                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                } else {
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                }
            } catch (Exception e) {
                // Si no se puede limpiar, imprime varias líneas en blanco
                for (int i = 0; i < 50; i++) {
                    System.out.println();
                }
            }
        }
    

    // muestra el menú principal y maneja las opciones
    private void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            //limpiarConsola(); // Limpia la consola antes de mostrar el menú
            // interfaz de menú
            System.out.println("\n=== CAJERO AUTOMÁTICO ===");
            System.out.println("1. Consultar saldo");
            System.out.println("2. Retirar dinero");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = scanner.nextInt();
                
                // manejo de selección de opciones
                switch (opcion) {
                    case 1:
                        limpiarConsola();
                        consultarSaldo();
                        break;
                    case 2:
                        limpiarConsola();
                        realizarRetiro(scanner);
                        break;
                    case 3:
                        limpiarConsola();
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
                System.out.println("Error en operación de archivo: " + e.getMessage());
                opcion = 3;
            }
        } while (opcion != 3);
        
        scanner.close();
    }

    // lee el saldo del archivo binario
    private double leerSaldo() throws IOException {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(ARCHIVO_SALDO))) {
            return dis.readDouble();
        }
    }

    // guarda el nuevo saldo en el archivo
    private void guardarSaldo(double nuevoSaldo) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(ARCHIVO_SALDO))) {
            dos.writeDouble(nuevoSaldo);
        }
    }

    // muestra el saldo actual formateado
    private void consultarSaldo() throws IOException {
        double saldo = leerSaldo();
        System.out.printf("Saldo actual: Q%.2f%n", saldo);
    }

    // maneja el proceso de retiro de dinero
    private void realizarRetiro(Scanner scanner) throws IOException {
        System.out.print("Ingrese monto a retirar: ");
        
        try {
            double monto = scanner.nextDouble();
            // valida monto positivo
            if (monto <= 0) {
                System.out.println("El monto debe ser positivo");
                return;
            }

            double saldoActual = leerSaldo();
            
            // verifica fondos suficientes1
            if (monto > saldoActual) {
                System.out.println("Saldo insuficiente");
            } else {
                // actualiza y guarda nuevo saldo
                double nuevoSaldo = saldoActual - monto;
                guardarSaldo(nuevoSaldo);
                System.out.printf("Retiro exitoso. Nuevo saldo: Q%.2f%n", nuevoSaldo);
            }
        } catch (InputMismatchException e) {
            // maneja entrada no numérica en retiro
            System.out.println("Error: Ingrese un valor numérico válido");
            scanner.nextLine();
        }
    }
}