import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
Nombre: Irvin José González Mateo
Carnet: 0900-24-24648
Curso: Programación 1
 */

public class SistemaControlEmpleados {
    
    private static final String NOMBRE_ARCHIVO = "empleados.dat";
    private static ArrayList<Empleado> listaEmpleados = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        cargarEmpleadosDesdeArchivo();
        mostrarMenu();
    }
    
    /**
     * Muestra el menú principal del sistema
     */
    private static void mostrarMenu() {
        int opcion = 0;
        
        do {
            limpiarConsola();
            System.out.println("===== SISTEMA DE CONTROL DE EMPLEADOS - SECOND BLUE S.A. =====");
            System.out.println("1. Mostrar todos los empleados");
            System.out.println("2. Agregar nuevo empleado");
            System.out.println("3. Guardar cambios");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
            
            try {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer
                
                switch (opcion) {
                    case 1:
                        mostrarEmpleados();
                        pausar();
                        break;
                    case 2:
                        agregarEmpleado();
                        break;
                    case 3:
                        guardarCambios();
                        pausar();
                        break;
                    case 4:
                        guardarCambios();
                        System.out.println("¡Gracias por usar el sistema!");
                        break;
                    default:
                        System.out.println("Opción no válida. Intente nuevamente.");
                        pausar();
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Debe ingresar un número válido.");
                scanner.nextLine(); // Limpiar buffer
                pausar();
            }
            
        } while (opcion != 4);
    }
    
    /**
     * Carga los empleados desde el archivo binario al ArrayList
     */
    private static void cargarEmpleadosDesdeArchivo() {
        File archivo = new File(NOMBRE_ARCHIVO);
        
        if (!archivo.exists()) {
            System.out.println("Archivo no encontrado. Se creará uno nuevo al guardar.");
            return;
        }
        
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(archivo))) {
            // Se intenta leer todo el ArrayList de una vez
            listaEmpleados = (ArrayList<Empleado>) entrada.readObject();
            System.out.println("Datos de empleados cargados correctamente.");
        } catch (FileNotFoundException e) {
            System.out.println("No se encontró el archivo: " + e.getMessage());
        } catch (InvalidClassException e) {
            System.out.println("Error de compatibilidad de clase: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("No se encontró la definición de la clase: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }
    
    /**
     * Muestra todos los empleados en la consola
     */
    private static void mostrarEmpleados() {
        limpiarConsola();
        System.out.println("===== LISTA DE EMPLEADOS =====");
        
        if (listaEmpleados.isEmpty()) {
            System.out.println("No hay empleados registrados.");
            return;
        }
        
        System.out.println("------------------------------------------------------------------");
        System.out.printf("%-5s | %-30s | %-5s | %-10s\n", "ID", "NOMBRE", "EDAD", "SALARIO (Q)");
        System.out.println("------------------------------------------------------------------");
        
        for (Empleado emp : listaEmpleados) {
            System.out.printf("%-5d | %-30s | %-5d | Q%-10.2f\n", 
                    emp.getId(), 
                    emp.getNombre(), 
                    emp.getEdad(), 
                    emp.getSalario());
        }
        System.out.println("------------------------------------------------------------------");
    }
    
    /**
     * Agrega un nuevo empleado al sistema
     */
    private static void agregarEmpleado() {
        limpiarConsola();
        System.out.println("===== AGREGAR NUEVO EMPLEADO =====");
        
        try {
            // Generar ID automáticamente
            int id = 1;
            if (!listaEmpleados.isEmpty()) {
                id = listaEmpleados.get(listaEmpleados.size() - 1).getId() + 1;
            }
            
            System.out.println("ID: " + id + " (generado automáticamente)");
            
System.out.print("Nombre: ");
String nombre = scanner.nextLine();
// Validar que el nombre contenga solo letras y espacios
while (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
    System.out.println("Error: El nombre solo puede contener letras y espacios. Intente nuevamente.");
    System.out.print("Nombre: ");
    nombre = scanner.nextLine();
}
            
            System.out.print("Edad: ");
            int edad = scanner.nextInt();
            
            System.out.print("Salario (Q): ");
            double salario = scanner.nextDouble();
            scanner.nextLine(); // Limpiar buffer
            
            // Crear y agregar el nuevo empleado
            Empleado nuevoEmpleado = new Empleado(id, nombre, edad, salario);
            listaEmpleados.add(nuevoEmpleado);
            
            System.out.println("\nEmpleado agregado con éxito.");
            System.out.println("¿Desea guardar los cambios ahora? (S/N): ");
            String respuesta = scanner.nextLine();
            
            if (respuesta.equalsIgnoreCase("S")) {
                guardarCambios();
            }
            
        } catch (InputMismatchException e) {
            System.out.println("Error: Dato ingresado no válido. Intente nuevamente.");
            scanner.nextLine(); // Limpiar buffer
        }
        
        pausar();
    }
    
    /**
     * Guarda los cambios en el archivo binario
     */
    private static void guardarCambios() {
        try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(NOMBRE_ARCHIVO))) {
            // Guardar todo el ArrayList de una vez
            salida.writeObject(listaEmpleados);
            System.out.println("Datos guardados correctamente en " + NOMBRE_ARCHIVO);
        } catch (IOException e) {
            System.out.println("Error al guardar los datos: " + e.getMessage());
        }
    }
    
    /**
     * Limpia la consola
     */
    private static void limpiarConsola() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            // Si falla el método de limpieza, se imprime una serie de líneas en blanco
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }
    
    /**
     * Pausa la ejecución hasta que el usuario presione Enter
     */
    private static void pausar() {
        System.out.println("\nPresione ENTER para continuar...");
        scanner.nextLine();
    }
}

/**
 * Clase Empleado que implementa Serializable para poder ser guardada en archivo binario
 */
class Empleado implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String nombre;
    private int edad;
    private double salario;
    
    public Empleado(int id, String nombre, int edad, double salario) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.salario = salario;
    }
    
    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }
    
    @Override
    public String toString() {
        return "Empleado [ID=" + id + ", nombre=" + nombre + ", edad=" + edad + ", salario=" + salario + "]";
    }
}