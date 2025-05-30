import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileManager {
    private static final String DATA_FILE = "warranty_system.dat";
    private static final String HISTORY_FILE = "warranty_history.txt";
    
    public static void saveData(Map<ComputerStatus, ComputerQueue> queues) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(queues);
            System.out.println("Datos guardados correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar los datos: " + e.getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")
    public static Map<ComputerStatus, ComputerQueue> loadData() {
        if (!new File(DATA_FILE).exists()) {
            return createInitialQueues();
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            return (Map<ComputerStatus, ComputerQueue>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al cargar los datos: " + e.getMessage());
            return createInitialQueues();
        }
    }
    
    private static Map<ComputerStatus, ComputerQueue> createInitialQueues() {
        Map<ComputerStatus, ComputerQueue> queues = new HashMap<>();
        queues.put(ComputerStatus.RECEPTION, new ComputerQueue(ComputerStatus.RECEPTION));
        queues.put(ComputerStatus.INSPECTION, new ComputerQueue(ComputerStatus.INSPECTION));
        queues.put(ComputerStatus.REPAIR, new ComputerQueue(ComputerStatus.REPAIR));
        queues.put(ComputerStatus.QUALITY_CONTROL, new ComputerQueue(ComputerStatus.QUALITY_CONTROL));
        queues.put(ComputerStatus.DELIVERY, new ComputerQueue(ComputerStatus.DELIVERY));
        return queues;
    }
    
    public static void appendToHistory(Computer computer) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(HISTORY_FILE, true))) {
            writer.println("=============================================");
            writer.println("Service Tag: " + computer.getServiceTag());
            writer.println("Estado actual: " + computer.getCurrentStatus());
            
            writer.println("\nHistorial completo:");
            for (HistoryRecord record : computer.getHistory()) {
                writer.println(record);
            }
            writer.println("=============================================\n");
        } catch (IOException e) {
            System.out.println("Error al escribir en el historial: " + e.getMessage());
        }
    }
    
    public static String readFullHistory() {
        StringBuilder history = new StringBuilder();
        
        if (!new File(HISTORY_FILE).exists()) {
            return "No hay historial disponible.";
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(HISTORY_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                history.append(line).append("\n");
            }
        } catch (IOException e) {
            System.out.println("Error al leer el historial: " + e.getMessage());
            return "Error al leer el historial.";
        }
        
        return history.toString();
    }
}