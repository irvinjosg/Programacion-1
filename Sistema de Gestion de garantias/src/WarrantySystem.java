import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.Scanner;

public class WarrantySystem {
    private Map<ComputerStatus, ComputerQueue> queues;
    private Scanner scanner;
    
    public WarrantySystem() {
        this.queues = FileManager.loadData();
        this.scanner = new Scanner(System.in);
    }
    
    public void run() {
        boolean running = true;
        
        while (running) {
            clearScreen();
            displayMainMenu();
            
            try {
                int option = Integer.parseInt(scanner.nextLine().trim());
                
                switch (option) {
                    case 1:
                        registerNewComputer();
                        break;
                    case 2:
                        processInspection();
                        break;
                    case 3:
                        processRepair();
                        break;
                    case 4:
                        processQualityControl();
                        break;
                    case 5:
                        processDelivery();
                        break;
                    case 6:
                        deleteComputer();
                        break;
                    case 7:
                        showCurrentStatus();
                        break;
                    case 8:
                        showFullHistory();
                        break;
                    case 0:
                        running = false;
                        FileManager.saveData(queues);
                        System.out.println("¡Gracias por usar el Sistema de Gestión de Garantías!");
                        break;
                    default:
                        System.out.println("Opción no válida. Presione Enter para continuar...");
                        scanner.nextLine();
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido. Presione Enter para continuar...");
                scanner.nextLine();
            }
        }
    }
    
    private void displayMainMenu() {
        System.out.println("==============================================");
        System.out.println("      SISTEMA DE GESTIÓN DE GARANTÍAS        ");
        System.out.println("==============================================");
        System.out.println("1. Registrar nueva computadora");
        System.out.println("2. Procesar inspección");
        System.out.println("3. Procesar reparación");
        System.out.println("4. Procesar control de calidad");
        System.out.println("5. Procesar entrega");
        System.out.println("6. Eliminar computadora");
        System.out.println("7. Mostrar estado actual de las colas");
        System.out.println("8. Mostrar historial completo");
        System.out.println("0. Salir del sistema");
        System.out.println("==============================================");
        System.out.print("Seleccione una opción: ");
    }
    
    private void registerNewComputer() {
        clearScreen();
        System.out.println("=== REGISTRO DE NUEVA COMPUTADORA ===");
        
        try {
            System.out.print("Service Tag: ");
            String serviceTag = scanner.nextLine().trim();
            
            if (serviceTag.isEmpty()) {
                throw new IllegalArgumentException("El Service Tag no puede estar vacío.");
            }
            
            // Check if the computer already exists in any queue
            if (findComputerByServiceTag(serviceTag) != null) {
                System.out.println("Error: Ya existe una computadora con ese Service Tag.");
                waitForEnter();
                return;
            }
            
            System.out.print("Descripción del problema: ");
            String problemDescription = scanner.nextLine().trim();
            
            if (problemDescription.isEmpty()) {
                throw new IllegalArgumentException("La descripción del problema no puede estar vacía.");
            }
            
            System.out.print("Fecha de recepción (YYYY-MM-DD): ");
            String dateString = scanner.nextLine().trim();
            LocalDate receptionDate;
            
            try {
                receptionDate = LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE);
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Formato de fecha incorrecto. Use YYYY-MM-DD.");
            }
            
            System.out.print("Nombre del cliente: ");
            String clientName = scanner.nextLine().trim();
            
            if (clientName.isEmpty()) {
                throw new IllegalArgumentException("El nombre del cliente no puede estar vacío.");
            }
            
            System.out.print("Correo electrónico del cliente: ");
            String clientEmail = scanner.nextLine().trim();
            
            if (clientEmail.isEmpty() || !clientEmail.contains("@")) {
                throw new IllegalArgumentException("Correo electrónico inválido.");
            }
            
            System.out.print("Número de teléfono del cliente: ");
            String clientPhone = scanner.nextLine().trim();
            
            if (clientPhone.isEmpty()) {
                throw new IllegalArgumentException("El número de teléfono no puede estar vacío.");
            }
            
            Computer computer = new Computer(serviceTag, problemDescription, receptionDate,
                                            clientName, clientEmail, clientPhone);
            
            queues.get(ComputerStatus.RECEPTION).enqueue(computer);
            computer.addHistoryRecord("Computadora registrada en el sistema");
            
            FileManager.appendToHistory(computer);
            FileManager.saveData(queues);
            
            System.out.println("\nComputadora registrada exitosamente. Presione Enter para continuar...");
            scanner.nextLine();
            
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage() + " Presione Enter para continuar...");
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage() + " Presione Enter para continuar...");
            scanner.nextLine();
        }
    }
    
    private void processInspection() {
        clearScreen();
        System.out.println("=== PROCESO DE INSPECCIÓN ===");
        
        try {
            ComputerQueue receptionQueue = queues.get(ComputerStatus.RECEPTION);
            
            if (receptionQueue.isEmpty()) {
                System.out.println("No hay computadoras en la cola de recepción.");
                waitForEnter();
                return;
            }
            
            Computer computer = receptionQueue.dequeue();
            System.out.println("Procesando: " + computer.getServiceTag());
            System.out.println("\nInformación actual:");
            System.out.println(computer);
            
            System.out.print("\nIngrese el diagnóstico: ");
            String diagnosis = scanner.nextLine().trim();
            
            if (diagnosis.isEmpty()) {
                throw new IllegalArgumentException("El diagnóstico no puede estar vacío.");
            }
            
            computer.setDiagnosis(diagnosis);
            computer.addHistoryRecord("Inspección realizada. Diagnóstico: " + diagnosis);
            
            System.out.print("¿Se puede reparar? (S/N): ");
            String canRepair = scanner.nextLine().trim().toUpperCase();
            
            if (canRepair.equals("S")) {
                queues.get(ComputerStatus.REPAIR).enqueue(computer);
                computer.addHistoryRecord("Enviada a reparación");
                System.out.println("Computadora enviada a la cola de reparación.");
            } else {
                queues.get(ComputerStatus.DELIVERY).enqueue(computer);
                computer.addHistoryRecord("No se puede reparar. Enviada a entrega");
                System.out.println("Computadora enviada directamente a la cola de entrega.");
            }
            
            FileManager.appendToHistory(computer);
            FileManager.saveData(queues);
            
            waitForEnter();
            
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            waitForEnter();
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
            waitForEnter();
        }
    }
    
    private void processRepair() {
        clearScreen();
        System.out.println("=== PROCESO DE REPARACIÓN ===");
        
        try {
            ComputerQueue repairQueue = queues.get(ComputerStatus.REPAIR);
            
            if (repairQueue.isEmpty()) {
                System.out.println("No hay computadoras en la cola de reparación.");
                waitForEnter();
                return;
            }
            
            Computer computer = repairQueue.dequeue();
            System.out.println("Procesando: " + computer.getServiceTag());
            System.out.println("\nInformación actual:");
            System.out.println(computer);
            System.out.println("Diagnóstico: " + computer.getDiagnosis());
            
            System.out.print("\nIngrese los detalles de la reparación: ");
            String repairDetails = scanner.nextLine().trim();
            
            if (repairDetails.isEmpty()) {
                throw new IllegalArgumentException("Los detalles de la reparación no pueden estar vacíos.");
            }
            
            System.out.print("Nombre del técnico: ");
            String technicianName = scanner.nextLine().trim();
            
            if (technicianName.isEmpty()) {
                throw new IllegalArgumentException("El nombre del técnico no puede estar vacío.");
            }
            
            computer.setRepairDetails(repairDetails);
            computer.setTechnicianName(technicianName);
            computer.addHistoryRecord("Reparación realizada por " + technicianName + ": " + repairDetails);
            
            queues.get(ComputerStatus.QUALITY_CONTROL).enqueue(computer);
            
            FileManager.appendToHistory(computer);
            FileManager.saveData(queues);
            
            System.out.println("\nComputadora enviada a control de calidad.");
            waitForEnter();
            
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            waitForEnter();
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
            waitForEnter();
        }
    }
    
    private void processQualityControl() {
        clearScreen();
        System.out.println("=== PROCESO DE CONTROL DE CALIDAD ===");
        
        try {
            ComputerQueue qcQueue = queues.get(ComputerStatus.QUALITY_CONTROL);
            
            if (qcQueue.isEmpty()) {
                System.out.println("No hay computadoras en la cola de control de calidad.");
                waitForEnter();
                return;
            }
            
            Computer computer = qcQueue.dequeue();
            System.out.println("Procesando: " + computer.getServiceTag());
            System.out.println("\nInformación actual:");
            System.out.println(computer);
            System.out.println("Diagnóstico: " + computer.getDiagnosis());
            System.out.println("Reparación: " + computer.getRepairDetails());
            System.out.println("Técnico: " + computer.getTechnicianName());
            
            System.out.print("\n¿La reparación pasó el control de calidad? (S/N): ");
            String passedQC = scanner.nextLine().trim().toUpperCase();
            
            if (passedQC.equals("S")) {
                queues.get(ComputerStatus.DELIVERY).enqueue(computer);
                computer.addHistoryRecord("Pasó control de calidad. Enviada a entrega");
                System.out.println("Computadora enviada a la cola de entrega.");
            } else {
                queues.get(ComputerStatus.REPAIR).enqueue(computer);
                computer.addHistoryRecord("No pasó control de calidad. Enviada nuevamente a reparación");
                System.out.println("Computadora enviada nuevamente a la cola de reparación.");
            }
            
            FileManager.appendToHistory(computer);
            FileManager.saveData(queues);
            
            waitForEnter();
            
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
            waitForEnter();
        }
    }
    
    private void processDelivery() {
        clearScreen();
        System.out.println("=== PROCESO DE ENTREGA ===");
        
        try {
            ComputerQueue deliveryQueue = queues.get(ComputerStatus.DELIVERY);
            
            if (deliveryQueue.isEmpty()) {
                System.out.println("No hay computadoras en la cola de entrega.");
                waitForEnter();
                return;
            }
            
            Computer computer = deliveryQueue.dequeue();
            System.out.println("Procesando entrega: " + computer.getServiceTag());
            System.out.println("\nInformación completa:");
            System.out.println(computer.getFullDetails());
            
            System.out.print("\n¿Confirmar entrega al cliente? (S/N): ");
            String confirmDelivery = scanner.nextLine().trim().toUpperCase();
            
            if (confirmDelivery.equals("S")) {
                computer.addHistoryRecord("Computadora entregada al cliente");
                System.out.println("Entrega confirmada para: " + computer.getServiceTag());
                FileManager.appendToHistory(computer);
            } else {
                // Si no se confirma, devolver a la cola de entrega
                deliveryQueue.enqueue(computer);
                System.out.println("Entrega cancelada. Computadora devuelta a la cola de entrega.");
            }
            
            FileManager.saveData(queues);
            waitForEnter();
            
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
            waitForEnter();
        }
    }
    
    private void deleteComputer() {
        clearScreen();
        System.out.println("=== ELIMINAR COMPUTADORA ===");
        
        try {
            System.out.print("Ingrese el Service Tag de la computadora a eliminar: ");
            String serviceTag = scanner.nextLine().trim();
            
            if (serviceTag.isEmpty()) {
                throw new IllegalArgumentException("El Service Tag no puede estar vacío.");
            }
            
            Computer computer = findComputerByServiceTag(serviceTag);
            
            if (computer == null) {
                System.out.println("No se encontró ninguna computadora con el Service Tag: " + serviceTag);
                waitForEnter();
                return;
            }
            
            System.out.println("\nInformación de la computadora a eliminar:");
            System.out.println(computer);
            
            System.out.print("\n¿Está seguro de eliminar esta computadora? (S/N): ");
            String confirm = scanner.nextLine().trim().toUpperCase();
            
            if (confirm.equals("S")) {
                removeComputerFromQueues(computer);
                System.out.println("Computadora eliminada exitosamente.");
                FileManager.saveData(queues);
            } else {
                System.out.println("Operación cancelada.");
            }
            
            waitForEnter();
            
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            waitForEnter();
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
            waitForEnter();
        }
    }
    
    private void showCurrentStatus() {
        clearScreen();
        System.out.println("=== ESTADO ACTUAL DE LAS COLAS ===");
        
        for (ComputerStatus status : ComputerStatus.values()) {
            ComputerQueue queue = queues.get(status);
            System.out.println("\n" + status + " (" + queue.size() + " computadoras):");
            
            if (queue.isEmpty()) {
                System.out.println("  No hay computadoras en esta cola.");
            } else {
                int count = 1;
                for (Computer computer : queue.getQueue()) {
                    System.out.println("  " + count + ". " + computer.getServiceTag() + " - " + computer.getClientName());
                    count++;
                }
            }
        }
        
        waitForEnter();
    }
    
    private void showFullHistory() {
        clearScreen();
        System.out.println("=== HISTORIAL COMPLETO ===");
        
        String history = FileManager.readFullHistory();
        System.out.println(history);
        
        waitForEnter();
    }
    
    private Computer findComputerByServiceTag(String serviceTag) {
        for (ComputerQueue queue : queues.values()) {
            for (Computer computer : queue.getQueue()) {
                if (computer.getServiceTag().equalsIgnoreCase(serviceTag)) {
                    return computer;
                }
            }
        }
        return null;
    }
    
    private void removeComputerFromQueues(Computer computerToRemove) {
        for (ComputerQueue queue : queues.values()) {
            queue.getQueue().removeIf(computer -> 
                computer.getServiceTag().equalsIgnoreCase(computerToRemove.getServiceTag()));
        }
    }
    
    private void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (IOException | InterruptedException e) {
            // Fallback if clearing fails
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }
    
    private void waitForEnter() {
        System.out.println("\nPresione Enter para continuar...");
        scanner.nextLine();
    }
    
    public static void main(String[] args) {
        WarrantySystem system = new WarrantySystem();
        system.run();
    }
}