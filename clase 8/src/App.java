import java.util.ArrayList;

public class App {
    public static void main(String[] args) {
        // Crear un arreglo de objetos Persona
        Persona[] personas = new Persona[3];

        // Inicializar los objetos Persona en el arreglo
        personas[0] = new Persona("isaiah", 25);
        personas[1] = new Persona("María", 30);
        personas[2] = new Persona("Carlos", 20);

        // Recorrer el arreglo e imprimir los datos de cada persona
        for (Persona persona : personas) {
            System.out.println(persona);
        }
    }
}

class Persona {
    private String nombre;
    private int edad;

    public Persona(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre + ", Edad: " + edad;
    }
}

public class App {
    public static void main(String[] args) {
        // Crear una lista de objetos Persona
        ArrayList<Persona> personas = new ArrayList<>();

        // Agregar objetos Persona a la lista
        personas.add(new Persona("isaiah", 25));
        personas.add(new Persona("María", 30));
        personas.add(new Persona("Carlos", 20));

        // Recorrer la lista e imprimir los datos de cada persona
        for (Persona persona : personas) {
            System.out.println(persona);
        }
    }
}//