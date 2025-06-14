// ListaLibros.java
public class ListaLibros {
    protected Nodo inicio, fin; // Punteros para saber dónde está el inicio y el final
    private int tamano; // Para llevar un conteo de los libros

    public ListaLibros() {
        inicio = null;
        fin = null;
        tamano = 0;
    }

    public boolean estaVacia() {
        return inicio == null;
    }

    public int getTamano() {
        return tamano;
    }

    // Método para agregar un libro al final de la lista (más intuitivo para una colección)
    public void agregarAlFinal(Libro libro) {
        Nodo nuevoNodo = new Nodo(libro);
        if (estaVacia()) {
            inicio = nuevoNodo;
            fin = nuevoNodo;
        } else {
            fin.siguiente = nuevoNodo;
            fin = nuevoNodo;
        }
        tamano++;
    }

    // Método para mostrar todos los libros
    public void mostrarLista() {
        if (estaVacia()) {
            System.out.println("LA COLECCIÓN DE LIBROS ESTÁ VACÍA.");
            return;
        }
        Nodo recorrer = inicio;
        System.out.println("--- COLECCIÓN DE LIBROS ---");
        while (recorrer != null) {
            System.out.println(recorrer.dato.toString());
            recorrer = recorrer.siguiente;
        }
        System.out.println("---------------------------");
    }

    // Método para buscar un libro por su título
    public Libro buscarLibro(String tituloBuscar) {
        if (estaVacia()) {
            return null;
        }
        Nodo actual = inicio;
        String tituloMayusculas = tituloBuscar.toUpperCase(); // Para búsqueda sin distinción de mayúsculas

        while (actual != null) {
            if (actual.dato.getTitulo().equals(tituloMayusculas)) {
                return actual.dato; // Se encontró el libro
            }
            actual = actual.siguiente;
        }
        return null; // El libro no fue encontrado
    }

    // Método para eliminar un libro por su título
    public boolean eliminarLibro(String tituloEliminar) {
        if (estaVacia()) {
            return false; // La lista está vacía, no hay nada que eliminar
        }

        String tituloMayusculas = tituloEliminar.toUpperCase();

        if (inicio.dato.getTitulo().equals(tituloMayusculas)) {
            // El libro a eliminar es el primero
            inicio = inicio.siguiente;
            if (inicio == null) { // Si la lista queda vacía
                fin = null;
            }
            tamano--;
            return true;
        }

        Nodo actual = inicio;
        Nodo anterior = null;

        while (actual != null && !actual.dato.getTitulo().equals(tituloMayusculas)) {
            anterior = actual;
            actual = actual.siguiente;
        }

        if (actual == null) {
            return false; // El libro no fue encontrado
        }

        // Se encontró el libro, ahora se elimina
        anterior.siguiente = actual.siguiente;
        if (actual == fin) { // Si el libro eliminado era el último
            fin = anterior;
        }
        tamano--;
        return true;
    }
}