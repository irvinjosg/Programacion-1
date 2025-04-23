public class Basurero {
    public static void main(String[] args) {
        // Crear un objeto
        Basurero obj = new Basurero();

        // Asignar el objeto a null para que sea elegible para recolección de basura
        obj = null;

        // Solicitar la recolección de basura
        System.gc();

        System.out.println("Se solicitó la recolección de basura.");
    }

    @Override
    protected void finalize() throws Throwable {
        // Este método se llama antes de que el recolector de basura destruya el objeto
        System.out.println("El objeto está siendo recolectado por el Garbage Collector.");
    }
}