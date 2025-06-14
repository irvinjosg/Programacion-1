import java.util.Stack;
import java.util.concurrent.TimeUnit;
import java.io.IOException;
import java.util.Scanner;

// AppGestionLibros.java
public class AppGestionLibros {

    private static ListaLibros coleccionLibros = new ListaLibros();
    private static Stack<Integer> historialOpciones = new Stack<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        int opcion;
        do {
            Utilidades.limpiarTerminal();
            mostrarMenuPrincipal();
            opcion = Utilidades.leerInt("SELECCIONE UNA OPCIÓN");

            if (opcion == -1) { // 'M' para volver al menú principal (ya estamos en él)
                Utilidades.limpiarTerminal();
                System.out.println("YA ESTÁ EN EL MENÚ PRINCIPAL.");
                pausar(1);
                continue;
            } else if (opcion == -2) { // '<' para volver atrás
                volverAtras();
                continue;
            }

            switch (opcion) {
                case 1:
                    agregarLibro();
                    historialOpciones.push(opcion);
                    break;
                case 2:
                    mostrarLibros();
                    historialOpciones.push(opcion);
                    break;
                case 3:
                    buscarLibro();
                    historialOpciones.push(opcion);
                    break;
                case 4:
                    eliminarLibro();
                    historialOpciones.push(opcion);
                    break;
                case 5:
                    Utilidades.limpiarTerminal();
                    System.out.println("GRACIAS POR USAR LA APLICACIÓN. ¡HASTA LUEGO!");
                    break;
                default:
                    Utilidades.limpiarTerminal();
                    System.out.println("OPCIÓN INVÁLIDA. POR FAVOR, INTENTE DE NUEVO.");
                    pausar(1);
                    Utilidades.limpiarTerminal(); // Limpia la terminal después de la pausa
                    continue; // Vuelve al inicio del bucle do-while
            }
            // Implementar la transición directa para opciones válidas (no salir, M o <)
            if (opcion != 5 && opcion != -1 && opcion != -2) {
                scanner.nextLine(); // Espera a que el usuario presione Enter
            }
        } while (opcion != 5);
        scanner.close();
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("******************************************");
        System.out.println("* GESTOR DE BIBLIOTECA PERSONAL          *");
        System.out.println("******************************************");
        System.out.println("1. AGREGAR NUEVO LIBRO");
        System.out.println("2. MOSTRAR TODOS LOS LIBROS");
        System.out.println("3. BUSCAR LIBRO POR TÍTULO");
        System.out.println("4. ELIMINAR LIBRO POR TÍTULO");
        System.out.println("5. SALIR");
        System.out.println("******************************************");
        System.out.println("ESCRIBA 'M' PARA VOLVER AL MENÚ EN CUALQUIER MOMENTO.");
        System.out.println("ESCRIBA '<' PARA VOLVER A LA ENTRADA ANTERIOR.");
        System.out.println("******************************************");
    }

    private static void agregarLibro() {
        Utilidades.limpiarTerminal();
        System.out.println("--- AGREGAR NUEVO LIBRO ---");
        String titulo = "";
        String autor = "";
        int numeroPaginas = 0;

        while (true) {
            titulo = Utilidades.leerString("INGRESE EL TÍTULO DEL LIBRO");
            if (titulo.equals("M")) {
                return;
            } else if (titulo.equals("<")) {
                Utilidades.limpiarTerminal();
                System.out.println("NO HAY ENTRADA ANTERIOR PARA CORREGIR AQUÍ.");
                pausar(1);
                Utilidades.limpiarTerminal();
                System.out.println("--- AGREGAR NUEVO LIBRO ---");
                continue;
            }
            if (titulo.isEmpty()) {
                Utilidades.limpiarTerminal();
                System.out.println("EL TÍTULO NO PUEDE ESTAR VACÍO. INTENTE DE NUEVO.");
                pausar(1);
                Utilidades.limpiarTerminal();
                System.out.println("--- AGREGAR NUEVO LIBRO ---");
            } else {
                break;
            }
        }

        while (true) {
            autor = Utilidades.leerString("INGRESE EL AUTOR DEL LIBRO");
            if (autor.equals("M")) {
                return;
            } else if (autor.equals("<")) {
                titulo = "";
                Utilidades.limpiarTerminal();
                System.out.println("--- AGREGAR NUEVO LIBRO ---");
                System.out.println("REINGRESANDO TÍTULO...");
                continue;
            }
            if (autor.isEmpty()) {
                Utilidades.limpiarTerminal();
                System.out.println("EL AUTOR NO PUEDE ESTAR VACÍO. INTENTE DE NUEVO.");
                pausar(1);
                Utilidades.limpiarTerminal();
                System.out.println("--- AGREGAR NUEVO LIBRO ---");
            } else {
                break;
            }
        }

        while (true) {
            numeroPaginas = Utilidades.leerInt("INGRESE EL NÚMERO DE PÁGINAS");
            if (numeroPaginas == -1) {
                return;
            } else if (numeroPaginas == -2) {
                autor = "";
                Utilidades.limpiarTerminal();
                System.out.println("--- AGREGAR NUEVO LIBRO ---");
                System.out.println("REINGRESANDO AUTOR...");
                continue;
            }
            if (numeroPaginas <= 0) {
                Utilidades.limpiarTerminal();
                System.out.println("EL NÚMERO DE PÁGINAS DEBE SER MAYOR A 0. INTENTE DE NUEVO.");
                pausar(1);
                Utilidades.limpiarTerminal();
                System.out.println("--- AGREGAR NUEVO LIBRO ---");
            } else {
                break;
            }
        }

        Libro nuevoLibro = new Libro(titulo, autor, numeroPaginas);
        coleccionLibros.agregarAlFinal(nuevoLibro);
        Utilidades.limpiarTerminal();
        System.out.println("LIBRO AGREGADO EXITOSAMENTE.");
                // ...existing code...
                        default:
                            Utilidades.limpiarTerminal();
                            System.out.println("OPCIÓN INVÁLIDA. POR FAVOR, INTENTE DE NUEVO.");
                            pausar(1);
                            Utilidades.limpiarTerminal(); // Limpia la terminal después de la pausa
                            continue; // Vuelve al inicio del bucle do-while
        // ...existing code...
    }

    private static void mostrarLibros() {
        Utilidades.limpiarTerminal();
        System.out.println("--- MOSTRAR TODOS LOS LIBROS ---");
        coleccionLibros.mostrarLista();
        pausar(2);
    }

    private static void buscarLibro() {
        Utilidades.limpiarTerminal();
        System.out.println("--- BUSCAR LIBRO POR TÍTULO ---");
        String tituloBuscar;

        while(true) {
            tituloBuscar = Utilidades.leerString("INGRESE EL TÍTULO DEL LIBRO A BUSCAR");

            if (tituloBuscar.equals("M")) {
                return;
            } else if (tituloBuscar.equals("<")) {
                Utilidades.limpiarTerminal();
                System.out.println("NO HAY ENTRADA ANTERIOR PARA CORREGIR AQUÍ.");
                pausar(1);
                Utilidades.limpiarTerminal();
                System.out.println("--- BUSCAR LIBRO POR TÍTULO ---");
                continue;
            } else if (tituloBuscar.isEmpty()) {
                Utilidades.limpiarTerminal();
                System.out.println("EL TÍTULO NO PUEDE ESTAR VACÍO. INTENTE DE NUEVO.");
                pausar(1);
                Utilidades.limpiarTerminal();
                System.out.println("--- BUSCAR LIBRO POR TÍTULO ---");
                continue;
            } else {
                break;
            }
        }

        Libro libroEncontrado = coleccionLibros.buscarLibro(tituloBuscar);
        Utilidades.limpiarTerminal();
        if (libroEncontrado != null) {
            System.out.println("LIBRO ENCONTRADO:");
            System.out.println(libroEncontrado.toString());
        } else {
            System.out.println("EL LIBRO CON TÍTULO '" + tituloBuscar + "' NO FUE ENCONTRADO.");
        }
        pausar(2);
    }

    private static void eliminarLibro() {
        Utilidades.limpiarTerminal();
        System.out.println("--- ELIMINAR LIBRO POR TÍTULO ---");
        String tituloEliminar;

        while(true) {
            tituloEliminar = Utilidades.leerString("INGRESE EL TÍTULO DEL LIBRO A ELIMINAR");

            if (tituloEliminar.equals("M")) {
                return;
            } else if (tituloEliminar.equals("<")) {
                Utilidades.limpiarTerminal();
                System.out.println("NO HAY ENTRADA ANTERIOR PARA CORREGIR AQUÍ.");
                pausar(1);
                Utilidades.limpiarTerminal();
                System.out.println("--- ELIMINAR LIBRO POR TÍTULO ---");
                continue;
            } else if (tituloEliminar.isEmpty()) {
                Utilidades.limpiarTerminal();
                System.out.println("EL TÍTULO NO PUEDE ESTAR VACÍO. INTENTE DE NUEVO.");
                pausar(1);
                Utilidades.limpiarTerminal();
                System.out.println("--- ELIMINAR LIBRO POR TÍTULO ---");
                continue;
            } else {
                break;
            }
        }

        Utilidades.limpiarTerminal();
        if (coleccionLibros.eliminarLibro(tituloEliminar)) {
            System.out.println("EL LIBRO CON TÍTULO '" + tituloEliminar + "' FUE ELIMINADO EXITOSAMENTE.");
        } else {
            System.out.println("NO SE PUDO ELIMINAR EL LIBRO. ES POSIBLE QUE NO EXISTA O LA LISTA ESTÉ VACÍA.");
        }
        pausar(2);
    }

    private static void volverAtras() {
        Utilidades.limpiarTerminal();
        if (!historialOpciones.isEmpty()) {
            historialOpciones.pop();
            System.out.println("VOLVIENDO AL MENÚ ANTERIOR...");
            pausar(1);
        } else {
            System.out.println("NO HAY ACCIONES ANTERIORES PARA VOLVER ATRÁS.");
            pausar(1);
        }
    }

    public static void pausar(int segundos) {
        try {
            TimeUnit.SECONDS.sleep(segundos);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}