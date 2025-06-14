public class Playlist {
    private String nombre;
    private ListaCanciones canciones;
    
    public Playlist(String nombre) {
        this.nombre = nombre.toUpperCase();
        this.canciones = new ListaCanciones();
    }
    
    // Getters
    public String getNombre() {
        return nombre;
    }
    
    public ListaCanciones getCanciones() {
        return canciones;
    }
    
    // Setter
    public void setNombre(String nombre) {
        this.nombre = nombre.toUpperCase();
    }
    
    // Método para agregar canción
    public void agregarCancion(Cancion cancion) {
        canciones.agregarCancion(cancion);
    }
    
    // Método para mostrar información completa de la playlist
    public void mostrarPlaylist() {
        System.out.println("PLAYLIST: " + nombre);
        System.out.println("CANTIDAD DE CANCIONES: " + canciones.getTamaño());
        System.out.println("DURACIÓN TOTAL: " + canciones.getDuracionTotalFormateada());
        System.out.println();
        
        canciones.mostrarCanciones();
    }
    
    // Método para buscar canción en esta playlist
    public Cancion buscarCancion(String titulo) {
        return canciones.buscarPorTitulo(titulo);
    }
    
    // Método para eliminar canción de esta playlist
    public boolean eliminarCancion(String titulo) {
        return canciones.eliminarPorTitulo(titulo);
    }
    
    // Método para verificar si la playlist está vacía
    public boolean estaVacia() {
        return canciones.estaVacia();
    }
    
    // Método para obtener resumen de la playlist
    public void mostrarResumen() {
        System.out.printf("• %s (%d CANCIONES - %s)%n", 
                         nombre, 
                         canciones.getTamaño(), 
                         canciones.getDuracionTotalFormateada());
    }
}