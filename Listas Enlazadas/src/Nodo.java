// Nodo.java
public class Nodo {
    public Libro dato;
    public Nodo siguiente; // Puntero al siguiente nodo

    // Constructor
    public Nodo(Libro libro) {
        this.dato = libro;
        this.siguiente = null; // Inicialmente no apunta a nada
    }

    // Constructor para insertar al inicio (como en tu ejemplo)
    public Nodo(Libro libro, Nodo siguiente) {
        this.dato = libro;
        this.siguiente = siguiente;
    }
}