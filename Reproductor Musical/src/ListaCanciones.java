public class ListaCanciones {
    protected NodoCancion inicio, fin;
    private int tamaño;
    
    public ListaCanciones() {
        inicio = null;
        fin = null;
        tamaño = 0;
    }
    
    // Método para agregar al final (para mantener orden de inserción)
    public void agregarCancion(Cancion cancion) {
        NodoCancion nuevoNodo = new NodoCancion(cancion);
        
        if (inicio == null) {
            inicio = nuevoNodo;
            fin = nuevoNodo;
        } else {
            fin.siguiente = nuevoNodo;
            fin = nuevoNodo;
        }
        tamaño++;
    }
    
    // Método para mostrar todas las canciones
    public void mostrarCanciones() {
        if (inicio == null) {
            System.out.println("LA PLAYLIST ESTÁ VACÍA.");
            return;
        }
        
        NodoCancion recorrer = inicio;
        int contador = 1;
        
        while (recorrer != null) {
            System.out.println("\n" + contador + ". ═══════════════════════════════════════");
            System.out.printf("   TÍTULO: %s%n", recorrer.cancion.getTitulo());
            System.out.printf("   ARTISTA: %s%n", recorrer.cancion.getArtista());
            System.out.printf("   DURACIÓN: %s (%d SEGUNDOS)%n", 
                            recorrer.cancion.getDuracionFormateada(), 
                            recorrer.cancion.getDuracion());
            
            recorrer = recorrer.siguiente;
            contador++;
        }
        System.out.println("\n═══════════════════════════════════════");
        System.out.println("TOTAL DE CANCIONES: " + tamaño);
    }
    
    // Método para buscar una canción por título
    public Cancion buscarPorTitulo(String titulo) {
        NodoCancion recorrer = inicio;
        
        while (recorrer != null) {
            if (recorrer.cancion.getTitulo().equals(titulo.toUpperCase())) {
                return recorrer.cancion;
            }
            recorrer = recorrer.siguiente;
        }
        
        return null;
    }
    
    // Método para eliminar una canción por título
    public boolean eliminarPorTitulo(String titulo) {
        if (inicio == null) {
            return false;
        }
        
        titulo = titulo.toUpperCase();
        
        // Si es el primer nodo
        if (inicio.cancion.getTitulo().equals(titulo)) {
            inicio = inicio.siguiente;
            if (inicio == null) {
                fin = null;
            }
            tamaño--;
            return true;
        }
        
        // Buscar en el resto de la lista
        NodoCancion actual = inicio;
        while (actual.siguiente != null) {
            if (actual.siguiente.cancion.getTitulo().equals(titulo)) {
                NodoCancion nodoAEliminar = actual.siguiente;
                actual.siguiente = nodoAEliminar.siguiente;
                
                // Si eliminamos el último nodo, actualizar fin
                if (nodoAEliminar == fin) {
                    fin = actual;
                }
                
                tamaño--;
                return true;
            }
            actual = actual.siguiente;
        }
        
        return false;
    }
    
    // Método para verificar si la lista está vacía
    public boolean estaVacia() {
        return inicio == null;
    }
    
    // Método para obtener el tamaño
    public int getTamaño() {
        return tamaño;
    }
    
    // Método para calcular duración total de la playlist
    public int getDuracionTotal() {
        int duracionTotal = 0;
        NodoCancion recorrer = inicio;
        
        while (recorrer != null) {
            duracionTotal += recorrer.cancion.getDuracion();
            recorrer = recorrer.siguiente;
        }
        
        return duracionTotal;
    }
    
    // Método para obtener duración formateada
    public String getDuracionTotalFormateada() {
        int duracionTotal = getDuracionTotal();
        int horas = duracionTotal / 3600;
        int minutos = (duracionTotal % 3600) / 60;
        int segundos = duracionTotal % 60;
        
        if (horas > 0) {
            return String.format("%02d:%02d:%02d", horas, minutos, segundos);
        } else {
            return String.format("%02d:%02d", minutos, segundos);
        }
    }
} 