// Libro.java
public class Libro {
    private String titulo;
    private String autor;
    private int numeroPaginas;

    public Libro(String titulo, String autor, int numeroPaginas) {
        this.titulo = titulo.toUpperCase(); // Convertir a mayúsculas
        this.autor = autor.toUpperCase();   // Convertir a mayúsculas
        this.numeroPaginas = numeroPaginas;
    }

    // Getters
    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public int getNumeroPaginas() {
        return numeroPaginas;
    }

    // No setters para mantener inmutabilidad después de la creación

    @Override
    public String toString() {
        return "TÍTULO: " + titulo + ", AUTOR: " + autor + ", PÁGINAS: " + numeroPaginas;
    }
}