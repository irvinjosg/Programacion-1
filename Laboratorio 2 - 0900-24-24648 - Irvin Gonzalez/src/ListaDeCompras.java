import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.InputMismatchException;

public class ListaDeCompras {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> listaCompras = new ArrayList<>();
        int opcion = 0;

        do {
            System.out.println("\nMENÚ DE LISTA DE COMPRAS");
            System.out.println("1. Agregar un producto a la lista");
            System.out.println("2. Mostrar la lista de compras");
            System.out.println("3. Eliminar un producto de la lista");
            System.out.println("4. Buscar un producto en la lista");
            System.out.println("5. Ordenar la lista alfabéticamente");
            System.out.println("6. Salir");

            try {
                System.out.print("Seleccione una opción: ");
                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar el salto de línea

                switch (opcion) {
                    case 1:
                        System.out.print("Ingrese el nombre del producto: ");
                        String productoAgregar = scanner.nextLine().trim();
                        if (!productoAgregar.isEmpty()) {
                            listaCompras.add(productoAgregar);
                            System.out.println("Producto agregado correctamente.");
                        } else {
                            System.out.println("Nombre de producto inválido.");
                        }
                        break;

                    case 2:
                        if (listaCompras.isEmpty()) {
                            System.out.println("La lista de compras está vacía.");
                        } else {
                            System.out.println("Lista de compras:");
                            for (String producto : listaCompras) {
                                System.out.println("- " + producto);
                            }
                        }
                        break;

                    case 3:
                        System.out.print("Ingrese el nombre del producto a eliminar: ");
                        String productoEliminar = scanner.nextLine().trim();
                        if (listaCompras.remove(productoEliminar)) {
                            System.out.println("Producto eliminado correctamente.");
                        } else {
                            System.out.println("El producto no se encuentra en la lista.");
                        }
                        break;

                    case 4:
                        System.out.print("Ingrese el nombre del producto a buscar: ");
                        String productoBuscar = scanner.nextLine().trim();
                        if (listaCompras.contains(productoBuscar)) {
                            System.out.println("El producto está en la lista.");
                        } else {
                            System.out.println("El producto NO está en la lista.");
                        }
                        break;

                    case 5:
                        if (listaCompras.isEmpty()) {
                            System.out.println("La lista está vacía, no hay nada que ordenar.");
                        } else {
                            Collections.sort(listaCompras, String.CASE_INSENSITIVE_ORDER);
                            System.out.println("Lista ordenada alfabéticamente:");
                            for (String producto : listaCompras) {
                                System.out.println("- " + producto);
                            }
                        }
                        break;

                    case 6:
                        System.out.println("Saliendo del programa...");
                        break;

                    default:
                        System.out.println("Opción no válida. Intente de nuevo.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número del menú.");
                scanner.next(); // Limpiar entrada incorrecta
            }

        } while (opcion != 6);

        scanner.close();
    }
}
