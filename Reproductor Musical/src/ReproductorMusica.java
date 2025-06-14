import java.util.Scanner;

public class ReproductorMusica {
    private static Scanner scanner = new Scanner(System.in);
    private static GestorPlaylists gestorPlaylists = new GestorPlaylists();
    
    public static void main(String[] args) {
        // Configurar UTF-8 de forma más simple
        System.setProperty("file.encoding", "UTF-8");
        
        mostrarMenuPrincipal();
    }
    
    public static void limpiarPantalla() {
        // Método más compatible - imprimir líneas vacías
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
        
        // Alternativamente, usar códigos ANSI si están disponibles
        try {
            System.out.print("\033[2J\033[H");
            System.out.flush();
        } catch (Exception e) {
            // Ignorar si no funciona
        }
    }
    
    public static void mostrarMenuPrincipal() {
        while (true) {
            limpiarPantalla();
            System.out.println("═══════════════════════════════════════");
            System.out.println("    REPRODUCTOR DE MÚSICA - MENÚ PRINCIPAL");
            System.out.println("═══════════════════════════════════════");
            System.out.println("1. CREAR PLAYLIST");
            System.out.println("2. AGREGAR CANCIÓN A PLAYLIST");
            System.out.println("3. MOSTRAR PLAYLIST");
            System.out.println("4. BUSCAR CANCIÓN POR TÍTULO");
            System.out.println("5. ELIMINAR CANCIÓN");
            System.out.println("6. MOSTRAR TODAS LAS PLAYLISTS");
            System.out.println("0. SALIR");
            System.out.println("═══════════════════════════════════════");
            System.out.print("SELECCIONE UNA OPCIÓN: ");
            
            try {
                int opcion = Integer.parseInt(scanner.nextLine().trim());
                
                switch (opcion) {
                    case 1:
                        crearPlaylist();
                        break;
                    case 2:
                        agregarCancionAPlaylist();
                        break;
                    case 3:
                        mostrarPlaylist();
                        break;
                    case 4:
                        buscarCancionPorTitulo();
                        break;
                    case 5:
                        eliminarCancion();
                        break;
                    case 6:
                        mostrarTodasLasPlaylists();
                        break;
                    case 0:
                        System.out.println("¡GRACIAS POR USAR EL REPRODUCTOR DE MÚSICA!");
                        System.exit(0);
                        break;
                    default:
                        mostrarError("OPCIÓN NO VÁLIDA. INTENTE NUEVAMENTE.");
                }
            } catch (NumberFormatException e) {
                mostrarError("DEBE INGRESAR UN NÚMERO VÁLIDO.");
            }
        }
    }
    
    public static void crearPlaylist() {
        while (true) {
            limpiarPantalla();
            System.out.println("═══════════════════════════════════════");
            System.out.println("         CREAR NUEVA PLAYLIST");
            System.out.println("═══════════════════════════════════════");
            System.out.print("INGRESE EL NOMBRE DE LA PLAYLIST (M para menú): ");
            
            String nombre = scanner.nextLine().trim().toUpperCase();
            
            if (nombre.equals("M")) {
                return;
            }
            
            if (nombre.isEmpty()) {
                mostrarError("EL NOMBRE NO PUEDE ESTAR VACÍO.");
                continue;
            }
            
            if (gestorPlaylists.existePlaylist(nombre)) {
                mostrarError("YA EXISTE UNA PLAYLIST CON ESE NOMBRE.");
                continue;
            }
            
            gestorPlaylists.crearPlaylist(nombre);
            System.out.println("PLAYLIST '" + nombre + "' CREADA EXITOSAMENTE.");
            System.out.print("PRESIONE ENTER PARA CONTINUAR...");
            scanner.nextLine();
            return;
        }
    }
    
    public static void agregarCancionAPlaylist() {
        if (gestorPlaylists.estaVacio()) {
            limpiarPantalla();
            System.out.println("NO HAY PLAYLISTS CREADAS.");
            System.out.print("PRESIONE ENTER PARA CONTINUAR...");
            scanner.nextLine();
            return;
        }
        
        String nombrePlaylist = seleccionarPlaylist();
        if (nombrePlaylist == null) return;
        
        String[] datosCancion = new String[3];
        String[] prompts = {
            "INGRESE EL TÍTULO DE LA CANCIÓN",
            "INGRESE EL ARTISTA",
            "INGRESE LA DURACIÓN EN SEGUNDOS"
        };
        
        int paso = 0;
        while (paso < 3) {
            limpiarPantalla();
            System.out.println("═══════════════════════════════════════");
            System.out.println("    AGREGAR CANCIÓN A: " + nombrePlaylist);
            System.out.println("═══════════════════════════════════════");
            
            if (paso > 0) {
                System.out.println("TÍTULO: " + datosCancion[0]);
            }
            if (paso > 1) {
                System.out.println("ARTISTA: " + datosCancion[1]);
            }
            
            System.out.print(prompts[paso] + " (M para menú, < para anterior): ");
            String entrada = scanner.nextLine().trim();
            
            if (entrada.equals("M")) {
                return;
            }
            
            if (entrada.equals("<") && paso > 0) {
                paso--;
                continue;
            }
            
            if (paso < 2) { // Título y artista
                if (entrada.isEmpty()) {
                    mostrarError("ESTE CAMPO NO PUEDE ESTAR VACÍO.");
                    continue;
                }
                datosCancion[paso] = entrada.toUpperCase();
                paso++;
            } else { // Duración
                try {
                    int duracion = Integer.parseInt(entrada);
                    if (duracion <= 0) {
                        mostrarError("LA DURACIÓN DEBE SER UN NÚMERO POSITIVO.");
                        continue;
                    }
                    datosCancion[paso] = String.valueOf(duracion);
                    paso++;
                } catch (NumberFormatException e) {
                    mostrarError("DEBE INGRESAR UN NÚMERO VÁLIDO PARA LA DURACIÓN.");
                }
            }
        }
        
        Cancion cancion = new Cancion(datosCancion[0], datosCancion[1], Integer.parseInt(datosCancion[2]));
        gestorPlaylists.agregarCancionAPlaylist(nombrePlaylist, cancion);
        
        limpiarPantalla();
        System.out.println("CANCIÓN AGREGADA EXITOSAMENTE A LA PLAYLIST '" + nombrePlaylist + "'");
        System.out.print("PRESIONE ENTER PARA CONTINUAR...");
        scanner.nextLine();
    }
    
    public static void mostrarPlaylist() {
        if (gestorPlaylists.estaVacio()) {
            limpiarPantalla();
            System.out.println("NO HAY PLAYLISTS CREADAS.");
            System.out.print("PRESIONE ENTER PARA CONTINUAR...");
            scanner.nextLine();
            return;
        }
        
        String nombrePlaylist = seleccionarPlaylist();
        if (nombrePlaylist == null) return;
        
        limpiarPantalla();
        System.out.println("═══════════════════════════════════════");
        System.out.println("       PLAYLIST: " + nombrePlaylist);
        System.out.println("═══════════════════════════════════════");
        
        gestorPlaylists.mostrarPlaylist(nombrePlaylist);
        
        System.out.print("PRESIONE ENTER PARA CONTINUAR...");
        scanner.nextLine();
    }
    
    public static void buscarCancionPorTitulo() {
        while (true) {
            limpiarPantalla();
            System.out.println("═══════════════════════════════════════");
            System.out.println("         BUSCAR CANCIÓN POR TÍTULO");
            System.out.println("═══════════════════════════════════════");
            System.out.print("INGRESE EL TÍTULO A BUSCAR (M para menú): ");
            
            String titulo = scanner.nextLine().trim().toUpperCase();
            
            if (titulo.equals("M")) {
                return;
            }
            
            if (titulo.isEmpty()) {
                mostrarError("EL TÍTULO NO PUEDE ESTAR VACÍO.");
                continue;
            }
            
            Cancion cancionEncontrada = gestorPlaylists.buscarCancionPorTitulo(titulo);
            
            limpiarPantalla();
            if (cancionEncontrada != null) {
                System.out.println("═══════════════════════════════════════");
                System.out.println("            CANCIÓN ENCONTRADA");
                System.out.println("═══════════════════════════════════════");
                System.out.println("TÍTULO: " + cancionEncontrada.getTitulo());
                System.out.println("ARTISTA: " + cancionEncontrada.getArtista());
                System.out.println("DURACIÓN: " + cancionEncontrada.getDuracion() + " SEGUNDOS");
            } else {
                System.out.println("NO SE ENCONTRÓ NINGUNA CANCIÓN CON EL TÍTULO: " + titulo);
            }
            
            System.out.print("PRESIONE ENTER PARA CONTINUAR...");
            scanner.nextLine();
            return;
        }
    }
    
    public static void eliminarCancion() {
        if (gestorPlaylists.estaVacio()) {
            limpiarPantalla();
            System.out.println("NO HAY PLAYLISTS CREADAS.");
            System.out.print("PRESIONE ENTER PARA CONTINUAR...");
            scanner.nextLine();
            return;
        }
        
        String nombrePlaylist = seleccionarPlaylist();
        if (nombrePlaylist == null) return;
        
        while (true) {
            limpiarPantalla();
            System.out.println("═══════════════════════════════════════");
            System.out.println("   ELIMINAR CANCIÓN DE: " + nombrePlaylist);
            System.out.println("═══════════════════════════════════════");
            System.out.print("INGRESE EL TÍTULO DE LA CANCIÓN A ELIMINAR (M para menú): ");
            
            String titulo = scanner.nextLine().trim().toUpperCase();
            
            if (titulo.equals("M")) {
                return;
            }
            
            if (titulo.isEmpty()) {
                mostrarError("EL TÍTULO NO PUEDE ESTAR VACÍO.");
                continue;
            }
            
            boolean eliminada = gestorPlaylists.eliminarCancionDePlaylist(nombrePlaylist, titulo);
            
            limpiarPantalla();
            if (eliminada) {
                System.out.println("CANCIÓN ELIMINADA EXITOSAMENTE DE LA PLAYLIST '" + nombrePlaylist + "'");
            } else {
                System.out.println("NO SE ENCONTRÓ LA CANCIÓN '" + titulo + "' EN LA PLAYLIST '" + nombrePlaylist + "'");
            }
            
            System.out.print("PRESIONE ENTER PARA CONTINUAR...");
            scanner.nextLine();
            return;
        }
    }
    
    public static void mostrarTodasLasPlaylists() {
        limpiarPantalla();
        System.out.println("═══════════════════════════════════════");
        System.out.println("         TODAS LAS PLAYLISTS");
        System.out.println("═══════════════════════════════════════");
        
        gestorPlaylists.mostrarTodasLasPlaylists();
        
        System.out.print("PRESIONE ENTER PARA CONTINUAR...");
        scanner.nextLine();
    }
    
    private static String seleccionarPlaylist() {
        while (true) {
            limpiarPantalla();
            System.out.println("═══════════════════════════════════════");
            System.out.println("         SELECCIONAR PLAYLIST");
            System.out.println("═══════════════════════════════════════");
            
            gestorPlaylists.mostrarNombresPlaylists();
            
            System.out.print("INGRESE EL NOMBRE DE LA PLAYLIST (M para menú): ");
            String nombre = scanner.nextLine().trim().toUpperCase();
            
            if (nombre.equals("M")) {
                return null;
            }
            
            if (nombre.isEmpty()) {
                mostrarError("EL NOMBRE NO PUEDE ESTAR VACÍO.");
                continue;
            }
            
            if (!gestorPlaylists.existePlaylist(nombre)) {
                mostrarError("NO EXISTE UNA PLAYLIST CON ESE NOMBRE.");
                continue;
            }
            
            return nombre;
        }
    }
    
    private static void mostrarError(String mensaje) {
        System.out.println("\n❌ ERROR: " + mensaje);
        System.out.print("PRESIONE ENTER PARA CONTINUAR...");
        scanner.nextLine();
    }
} 