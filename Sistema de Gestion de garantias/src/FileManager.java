import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class FileManager {
    private static final String DATA_FILE = "warranty_system.dat";
    private static final String HISTORY_FILE = "warranty_history.txt";
    
    public static void saveData(Map<ComputerStatus, ComputerQueue> queues) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(queues);
            System.out.println("DATOS GUARDADOS CORRECTAMENTE.");
        } catch (IOException e) {
            System.out.println("ERROR AL GUARDAR LOS DATOS: " + e.getMessage());
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
            System.out.println("ERROR AL CARGAR LOS DATOS: " + e.getMessage());
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
        try (PrintWriter writer = new PrintWriter(
                new OutputStreamWriter(new FileOutputStream(HISTORY_FILE, true), StandardCharsets.UTF_8))) {
            writer.println("=============================================");
            writer.println("SERVICE TAG: " + computer.getServiceTag());
            writer.println("ESTADO ACTUAL: " + computer.getCurrentStatus());
            
            writer.println("\nHISTORIAL COMPLETO:");
            writer.println("FECHA      | CONTENIDO                                    | EQUIPO");
            writer.println("-----------|----------------------------------------------|----------");
            for (HistoryRecord record : computer.getHistory()) {
                writer.println(record.getFormattedRecord());
            }
            writer.println("=============================================\n");
        } catch (IOException e) {
            System.out.println("ERROR AL ESCRIBIR EN EL HISTORIAL: " + e.getMessage());
        }
    }
    
    public static String readFullHistory() {
        StringBuilder history = new StringBuilder();
        
        if (!new File(HISTORY_FILE).exists()) {
            return "NO HAY HISTORIAL DISPONIBLE.";
        }
        
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(HISTORY_FILE), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                history.append(line).append("\n");
            }
        } catch (IOException e) {
            System.out.println("ERROR AL LEER EL HISTORIAL: " + e.getMessage());
            return "ERROR AL LEER EL HISTORIAL.";
        }
        
        return history.toString();
    }
}