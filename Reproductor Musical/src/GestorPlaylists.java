public class GestorPlaylists {
    private NodoPlaylist inicio, fin;
    private int totalPlaylists;
    
    public GestorPlaylists() {
        inicio = null;
        fin = null;
        totalPlaylists = 0;
    }
    
    // Método para crear una nueva playlist
    public void crearPlaylist(String nombre) {
        Playlist nuevaPlaylist = new Playlist(nombre);
        NodoPlaylist nuevoNodo = new NodoPlaylist(nuevaPlaylist);
        
        if (inicio == null) {
            inicio = nuevoNodo;
            fin = nuevoNodo;
        } else {
            fin.siguiente = nuevoNodo;
            fin = nuevoNodo;
        }
        totalPlaylists++;
    }
    
    // Método para verificar si existe una playlist
    public boolean existePlaylist(String nombre) {
        NodoPlaylist recorrer = inicio;
        nombre = nombre.toUpperCase();
        
        while (recorrer != null) {
            if (recorrer.playlist.getNombre().equals(nombre)) {
                return true;
            }
            recorrer = recorrer.siguiente;
        }
        
        return false;
    }
    
    // Método para obtener una playlist por nombre
    private Playlist obtenerPlaylist(String nombre) {
        NodoPlaylist recorrer = inicio;
        nombre = nombre.toUpperCase();
        
        while (recorrer != null) {
            if (recorrer.playlist.getNombre().equals(nombre)) {
                return recorrer.playlist;
            }
            recorrer = recorrer.siguiente;
        }
        
        return null;
    }
    
    // Método para agregar canción a una playlist específica
    public void agregarCancionAPlaylist(String nombrePlaylist, Cancion cancion) {
        Playlist playlist = obtenerPlaylist(nombrePlaylist);
        if (playlist != null) {
            playlist.agregarCancion(cancion);
        }
    }
    
    // Método para mostrar una playlist específica
    public void mostrarPlaylist(String nombrePlaylist) {
        Playlist playlist = obtenerPlaylist(nombrePlaylist);
        if (playlist != null) {
            playlist.mostrarPlaylist();
        } else {
            System.out.println("NO SE ENCONTRÓ LA PLAYLIST: " + nombrePlaylist);
        }
    }
    
    // Método para buscar una canción en todas las playlists
    public Cancion buscarCancionPorTitulo(String titulo) {
        NodoPlaylist recorrer = inicio;
        
        while (recorrer != null) {
            Cancion cancionEncontrada = recorrer.playlist.buscarCancion(titulo);
            if (cancionEncontrada != null) {
                return cancionEncontrada;
            }
            recorrer = recorrer.siguiente;
        }
        
        return null;
    }
    
    // Método para eliminar una canción de una playlist específica
    public boolean eliminarCancionDePlaylist(String nombrePlaylist, String tituloCancion) {
        Playlist playlist = obtenerPlaylist(nombrePlaylist);
        if (playlist != null) {
            return playlist.eliminarCancion(tituloCancion);
        }
        return false;
    }
    
    // Método para mostrar todas las playlists
    public void mostrarTodasLasPlaylists() {
        if (inicio == null) {
            System.out.println("NO HAY PLAYLISTS CREADAS.");
            return;
        }
        
        NodoPlaylist recorrer = inicio;
        int contador = 1;
        
        while (recorrer != null) {
            System.out.println("\n" + contador + ". ═══════════════════════════════════════");
            recorrer.playlist.mostrarPlaylist();
            System.out.println("═══════════════════════════════════════");
            
            recorrer = recorrer.siguiente;
            contador++;
        }
        
        System.out.println("\nTOTAL DE PLAYLISTS: " + totalPlaylists);
    }
    
    // Método para mostrar solo los nombres de las playlists
    public void mostrarNombresPlaylists() {
        if (inicio == null) {
            System.out.println("NO HAY PLAYLISTS DISPONIBLES.");
            return;
        }
        
        System.out.println("PLAYLISTS DISPONIBLES:");
        NodoPlaylist recorrer = inicio;
        
        while (recorrer != null) {
            recorrer.playlist.mostrarResumen();
            recorrer = recorrer.siguiente;
        }
    }
    
    // Método para verificar si no hay playlists
    public boolean estaVacio() {
        return inicio == null;
    }
    
    // Método para obtener el total de playlists
    public int getTotalPlaylists() {
        return totalPlaylists;
    }
    
    // Método para obtener estadísticas generales
    public void mostrarEstadisticas() {
        if (inicio == null) {
            System.out.println("NO HAY DATOS PARA MOSTRAR.");
            return;
        }
        
        int totalCanciones = 0;
        int duracionTotal = 0;
        NodoPlaylist recorrer = inicio;
        
        while (recorrer != null) {
            totalCanciones += recorrer.playlist.getCanciones().getTamaño();
            duracionTotal += recorrer.playlist.getCanciones().getDuracionTotal();
            recorrer = recorrer.siguiente;
        }
        
        System.out.println("═══════════════════════════════════════");
        System.out.println("           ESTADÍSTICAS GENERALES");
        System.out.println("═══════════════════════════════════════");
        System.out.println("TOTAL DE PLAYLISTS: " + totalPlaylists);
        System.out.println("TOTAL DE CANCIONES: " + totalCanciones);
        
        // Convertir duración total a formato legible
        int horas = duracionTotal / 3600;
        int minutos = (duracionTotal % 3600) / 60;
        int segundos = duracionTotal % 60;
        
        if (horas > 0) {
            System.out.printf("DURACIÓN TOTAL: %02d:%02d:%02d%n", horas, minutos, segundos);
        } else {
            System.out.printf("DURACIÓN TOTAL: %02d:%02d%n", minutos, segundos);
        }
        
        if (totalPlaylists > 0) {
            System.out.printf("PROMEDIO DE CANCIONES POR PLAYLIST: %.1f%n", 
                            (double) totalCanciones / totalPlaylists);
        }
    }