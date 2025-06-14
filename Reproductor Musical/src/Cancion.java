public class Cancion {
    private String titulo;
    private String artista;
    private int duracion; // en segundos
    
    public Cancion(String titulo, String artista, int duracion) {
        this.titulo = titulo.toUpperCase();
        this.artista = artista.toUpperCase();
        this.duracion = duracion;
    }
    
    // Getters
    public String getTitulo() {
        return titulo;
    }
    
    public String getArtista() {
        return artista;
    }
    
    public int getDuracion() {
        return duracion;
    }
    
    // Setters
    public void setTitulo(String titulo) {
        this.titulo = titulo.toUpperCase();
    }
    
    public void setArtista(String artista) {
        this.artista = artista.toUpperCase();
    }
    
    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }
    
    // Método para mostrar la información de la canción
    public void mostrarInformacion() {
        System.out.println("┌─────────────────────────────────────┐");
        System.out.printf("│ TÍTULO: %-27s │%n", titulo);
        System.out.printf("│ ARTISTA: %-26s │%n", artista);
        System.out.printf("│ DURACIÓN: %-25s │%n", duracion + " SEGUNDOS");
        System.out.println("└─────────────────────────────────────┘");
    }
    
    // Método para convertir duración a formato mm:ss
    public String getDuracionFormateada() {
        int minutos = duracion / 60;
        int segundos = duracion % 60;
        return String.format("%02d:%02d", minutos, segundos);
    }
    
    // Método toString para debug
    @Override
    public String toString() {
        return "CANCIÓN{" +
                "TÍTULO='" + titulo + '\'' +
                ", ARTISTA='" + artista + '\'' +
                ", DURACIÓN=" + duracion + " SEGUNDOS" +
                '}';
    }
    
    // Método equals para comparar canciones por título
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Cancion cancion = (Cancion) obj;
        return titulo.equals(cancion.titulo);
    }
} 