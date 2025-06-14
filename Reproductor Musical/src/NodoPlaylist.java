public class NodoPlaylist {
    public Playlist playlist;
    public NodoPlaylist siguiente;
    
    // Constructor para insertar al final
    public NodoPlaylist(Playlist playlist) {
        this.playlist = playlist;
        this.siguiente = null;
    }
    
    // Constructor para insertar al inicio
    public NodoPlaylist(Playlist playlist, NodoPlaylist siguiente) {
        this.playlist = playlist;
        this.siguiente = siguiente;
    }
} 