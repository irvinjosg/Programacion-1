public class NodoCancion {
    public Cancion cancion;
    public NodoCancion siguiente;
    
    // Constructor para insertar al final
    public NodoCancion(Cancion cancion) {
        this.cancion = cancion;
        this.siguiente = null;
    }
    
    // Constructor para insertar al inicio
    public NodoCancion(Cancion cancion, NodoCancion siguiente) {
        this.cancion = cancion;
        this.siguiente = siguiente;
    }
} 