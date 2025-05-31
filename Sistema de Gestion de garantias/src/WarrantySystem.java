import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.List;

public class WarrantySystem {
    private Map<ComputerStatus, ComputerQueue> queues;
    private Scanner scanner;
    private static final String MENU_COMMAND = "M";
    private static final String BACK_COMMAND = "<";
    
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
                String input = scanner.nextLine().trim().toUpperCase();
                
                if (input.isEmpty()) {
                    System.out.println("POR FAVOR, INGRESE UNA OPCIÓN VÁLIDA. PRESIONE ENTER PARA CONTINUAR...");
                    scanner.nextLine();
                    continue;
                }
                
                int option = Integer.parseInt(input);
                
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
                        System.out.println("¡GRACIAS POR USAR EL SISTEMA DE GESTIÓN DE GARANTÍAS!");
                        break;
                    default:
                        System.out.println("OPCIÓN NO VÁLIDA. PRESIONE ENTER PARA CONTINUAR...");
                        scanner.nextLine();
                }
            } catch (NumberFormatException e) {
                System.out.println("POR FAVOR, INGRESE UN NÚMERO VÁLIDO. PRESIONE ENTER PARA CONTINUAR...");
                scanner.nextLine();
            }
        }
    }
    
    private void displayMainMenu() {
        System.out.println("==============================================");
        System.out.println("      SISTEMA DE GESTIÓN DE GARANTÍAS        ");
        System.out.println("==============================================");
        System.out.println("1. REGISTRAR NUEVA COMPUTADORA");
        System.out.println("2. PROCESAR INSPECCIÓN");
        System.out.println("3. PROCESAR REPARACIÓN");
        System.out.println("4. PROCESAR CONTROL DE CALIDAD");
        System.out.println("5. PROCESAR ENTREGA");
        System.out.println("6. ELIMINAR COMPUTADORA");
        System.out.println("7. MOSTRAR ESTADO ACTUAL DE LAS COLAS");
        System.out.println("8. MOSTRAR HISTORIAL COMPLETO DE EQUIPOS");
        System.out.println("0. SALIR DEL SISTEMA");
        System.out.println("==============================================");
        System.out.print("SELECCIONE UNA OPCIÓN: ");
    }
    
    private void registerNewComputer() {
        clearScreen();
        System.out.println("=== REGISTRO DE NUEVA COMPUTADORA ===");
        System.out.println("ESCRIBA 'M' PARA VOLVER AL MENÚ PRINCIPAL");
        System.out.println();
        
        try {
            // Service Tag
            String serviceTag = null;
            while (serviceTag == null) {
                String inputTag = getInputWithValidation(
                    "SERVICE TAG: ",
                    input -> !input.isEmpty(),
                    "EL SERVICE TAG NO PUEDE ESTAR VACÍO.",
                    null
                );
                if (inputTag == null) return;

                if (findComputerByServiceTag(inputTag) != null) {
                    System.out.println("ERROR: YA EXISTE UNA COMPUTADORA CON ESE SERVICE TAG.");
                } else {
                    serviceTag = inputTag;
                }
            }
            
            // Descripción del problema
            String problemDescription = getInputWithValidation(
                "DESCRIPCIÓN DEL PROBLEMA: ",
                input -> !input.isEmpty(),
                "LA DESCRIPCIÓN DEL PROBLEMA NO PUEDE ESTAR VACÍA.",
                serviceTag
            );
            if (problemDescription == null) return;
            
            // Fecha de recepción
            LocalDate receptionDate = null;
            String dateInput = null;
            while (receptionDate == null) {
                dateInput = getInputWithValidation(
                    "FECHA DE RECEPCIÓN (DD/MM/YYYY): ",
                    input -> true,
                    "",
                    problemDescription
                );
                if (dateInput == null) return;
                
                try {
                    receptionDate = LocalDate.parse(dateInput, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                } catch (DateTimeParseException e) {
                    System.out.println("FORMATO DE FECHA INCORRECTO. USE DD/MM/YYYY.");
                }
            }
            
            // Nombre del cliente
            String clientName = getInputWithValidation(
                "NOMBRE DEL CLIENTE: ",
                input -> input.matches("[A-Za-zÁÉÍÓÚáéíóúÑñ ]+"),
    "EL NOMBRE DEL CLIENTE SOLO PUEDE CONTENER LETRAS Y ESPACIOS.",
                dateInput
            );
            if (clientName == null) return;
            
            // Correo electrónico
            String clientEmail = null;
            while (clientEmail == null) {
                String emailInput = getInputWithValidation(
                    "CORREO ELECTRÓNICO DEL CLIENTE: ",
                    input -> true,
                    "",
                    clientName
                );
                if (emailInput == null) return;
                
                if (isValidEmail(emailInput)) {
                    clientEmail = emailInput;
                } else {
                    System.out.println("CORREO ELECTRÓNICO INVÁLIDO. DEBE CONTENER @ Y UN DOMINIO VÁLIDO.");
                }
            }
            
            // Número de teléfono
            String clientPhone = null;
            while (clientPhone == null) {
                String phoneInput = getInputWithValidation(
                    "NÚMERO DE TELÉFONO DEL CLIENTE (8 DÍGITOS): ",
                    input -> true,
                    "",
                    clientEmail
                );
                if (phoneInput == null) return;
                
                if (isValidPhone(phoneInput)) {
                    clientPhone = phoneInput;
                } else {
                    System.out.println("NÚMERO DE TELÉFONO INVÁLIDO. DEBE CONTENER EXACTAMENTE 8 DÍGITOS.");
                }
            }
            
            Computer computer = new Computer(serviceTag, problemDescription, receptionDate,
                                            clientName, clientEmail, clientPhone);
            
            queues.get(ComputerStatus.RECEPTION).enqueue(computer);
            computer.addHistoryRecord("COMPUTADORA REGISTRADA EN EL SISTEMA");
            
            FileManager.appendToHistory(computer);
            FileManager.saveData(queues);
            
            System.out.println("\nCOMPUTADORA REGISTRADA EXITOSAMENTE.");
            waitForEnter();
            
        } catch (Exception e) {
            System.out.println("ERROR INESPERADO: " + e.getMessage());
            waitForEnter();
        }
    }
    
    private void processInspection() {
        clearScreen();
        System.out.println("=== PROCESO DE INSPECCIÓN ===");
        System.out.println("ESCRIBA 'M' PARA VOLVER AL MENÚ PRINCIPAL");
        System.out.println();
        
        try {
            ComputerQueue receptionQueue = queues.get(ComputerStatus.RECEPTION);
            
            if (receptionQueue.isEmpty()) {
                System.out.println("NO HAY COMPUTADORAS EN LA COLA DE RECEPCIÓN.");
                waitForEnter();
                return;
            }
            
            // Mostrar computadoras disponibles
            System.out.println("COMPUTADORAS DISPONIBLES PARA INSPECCIÓN:");
            receptionQueue.displayComputerList();
            System.out.println();
            
            // Opción 1: Seleccionar por número o buscar por service tag
            Computer computer = null;
            
            if (receptionQueue.size() > 1) {
                System.out.println("OPCIONES:");
                System.out.println("1. SELECCIONAR POR NÚMERO");
                System.out.println("2. BUSCAR POR SERVICE TAG");
                System.out.print("SELECCIONE UNA OPCIÓN: ");
                
                String option = scanner.nextLine().trim().toUpperCase();
                if (option.equals(MENU_COMMAND)) return;
                
                if (option.equals("1")) {
                    computer = selectComputerByNumber(receptionQueue);
                } else if (option.equals("2")) {
                    computer = selectComputerByServiceTag(receptionQueue);
                } else {
                    System.out.println("OPCIÓN INVÁLIDA.");
                    waitForEnter();
                    return;
                }
            } else {
                computer = receptionQueue.dequeue();
            }
            
            if (computer == null) return;
            
            System.out.println("PROCESANDO: " + computer.getServiceTag());
            System.out.println("\nINFORMACIÓN ACTUAL:");
            System.out.println(computer);
            
            // Diagnóstico
            String diagnosis = getInputWithValidation(
                "\nINGRESE EL DIAGNÓSTICO: ",
                input -> !input.isEmpty(),
                "EL DIAGNÓSTICO NO PUEDE ESTAR VACÍO.",
                null
            );
            if (diagnosis == null) return;
            
            computer.setDiagnosis(diagnosis);
            computer.addHistoryRecord("INSPECCIÓN REALIZADA. DIAGNÓSTICO: " + diagnosis);
            
            // Verificar si se puede reparar
            String canRepair = null;
            while (canRepair == null) {
                System.out.print("¿SE PUEDE REPARAR? (S/N): ");
                String input = scanner.nextLine().trim().toUpperCase();
                
                if (input.equals(MENU_COMMAND)) return;
                if (input.equals("S") || input.equals("N")) {
                    canRepair = input;
                } else {
                    System.out.println("RESPUESTA INVÁLIDA. INGRESE 'S' PARA SÍ O 'N' PARA NO.");
                }
            }
            
            if (canRepair.equals("S")) {
                queues.get(ComputerStatus.REPAIR).enqueue(computer);
                computer.addHistoryRecord("ENVIADA A REPARACIÓN");
                System.out.println("COMPUTADORA ENVIADA A LA COLA DE REPARACIÓN.");
            } else {
                queues.get(ComputerStatus.DELIVERY).enqueue(computer);
                computer.addHistoryRecord("NO SE PUEDE REPARAR. ENVIADA A ENTREGA");
                System.out.println("COMPUTADORA ENVIADA DIRECTAMENTE A LA COLA DE ENTREGA.");
            }
            
            FileManager.appendToHistory(computer);
            FileManager.saveData(queues);
            
            waitForEnter();
            
        } catch (Exception e) {
            System.out.println("ERROR INESPERADO: " + e.getMessage());
            waitForEnter();
        }
    }
    
    private void processRepair() {
        clearScreen();
        System.out.println("=== PROCESO DE REPARACIÓN ===");
        System.out.println("ESCRIBA 'M' PARA VOLVER AL MENÚ PRINCIPAL");
        System.out.println();
        
        try {
            ComputerQueue repairQueue = queues.get(ComputerStatus.REPAIR);
            
            if (repairQueue.isEmpty()) {
                System.out.println("NO HAY COMPUTADORAS EN LA COLA DE REPARACIÓN.");
                waitForEnter();
                return;
            }
            
            Computer computer = selectComputerFromQueue(repairQueue, "REPARACIÓN");
            if (computer == null) return;
            
            System.out.println("PROCESANDO: " + computer.getServiceTag());
            System.out.println("\nINFORMACIÓN ACTUAL:");
            System.out.println(computer);
            System.out.println("DIAGNÓSTICO: " + computer.getDiagnosis());
            
            // Detalles de reparación
            String repairDetails = getInputWithValidation(
                "\nINGRESE LOS DETALLES DE LA REPARACIÓN: ",
                input -> !input.isEmpty(),
                "LOS DETALLES DE LA REPARACIÓN NO PUEDEN ESTAR VACÍOS.",
                null
            );
            if (repairDetails == null) return;
            
            // Nombre del técnico
            String technicianName = getInputWithValidation(
                "NOMBRE DEL TÉCNICO: ",
                input -> input.matches("[A-Za-zÁÉÍÓÚáéíóúÑñ ]+"),
                "EL NOMBRE DEL TÉCNICO SOLO PUEDE CONTENER LETRAS Y ESPACIOS.",
                repairDetails
            );
            if (technicianName == null) return;
            
            computer.setRepairDetails(repairDetails);
            computer.setTechnicianName(technicianName);
            computer.addHistoryRecord("REPARACIÓN REALIZADA POR " + technicianName + ": " + repairDetails);
            
            queues.get(ComputerStatus.QUALITY_CONTROL).enqueue(computer);
            
            FileManager.appendToHistory(computer);
            FileManager.saveData(queues);
            
            System.out.println("\nCOMPUTADORA ENVIADA A CONTROL DE CALIDAD.");
            waitForEnter();
            
        } catch (Exception e) {
            System.out.println("ERROR INESPERADO: " + e.getMessage());
            waitForEnter();
        }
    }
    
    private void processQualityControl() {
        clearScreen();
        System.out.println("=== PROCESO DE CONTROL DE CALIDAD ===");
        System.out.println("ESCRIBA 'M' PARA VOLVER AL MENÚ PRINCIPAL");
        System.out.println();
        
        try {
            ComputerQueue qcQueue = queues.get(ComputerStatus.QUALITY_CONTROL);
            
            if (qcQueue.isEmpty()) {
                System.out.println("NO HAY COMPUTADORAS EN LA COLA DE CONTROL DE CALIDAD.");
                waitForEnter();
                return;
            }
            
            Computer computer = selectComputerFromQueue(qcQueue, "CONTROL DE CALIDAD");
            if (computer == null) return;
            
            System.out.println("PROCESANDO: " + computer.getServiceTag());
            System.out.println("\nINFORMACIÓN ACTUAL:");
            System.out.println(computer);
            System.out.println("DIAGNÓSTICO: " + computer.getDiagnosis());
            System.out.println("REPARACIÓN: " + computer.getRepairDetails());
            System.out.println("TÉCNICO: " + computer.getTechnicianName());
            
            String passedQC = null;
            while (passedQC == null) {
                System.out.print("\n¿LA REPARACIÓN PASÓ EL CONTROL DE CALIDAD? (S/N): ");
                String input = scanner.nextLine().trim().toUpperCase();
                
                if (input.equals(MENU_COMMAND)) return;
                if (input.equals("S") || input.equals("N")) {
                    passedQC = input;
                } else {
                    System.out.println("RESPUESTA INVÁLIDA. INGRESE 'S' PARA SÍ O 'N' PARA NO.");
                }
            }
            
            if (passedQC.equals("S")) {
                queues.get(ComputerStatus.DELIVERY).enqueue(computer);
                computer.addHistoryRecord("PASÓ CONTROL DE CALIDAD. ENVIADA A ENTREGA");
                System.out.println("COMPUTADORA ENVIADA A LA COLA DE ENTREGA.");
            } else {
                queues.get(ComputerStatus.REPAIR).enqueue(computer);
                computer.addHistoryRecord("NO PASÓ CONTROL DE CALIDAD. ENVIADA NUEVAMENTE A REPARACIÓN");
                System.out.println("COMPUTADORA ENVIADA NUEVAMENTE A LA COLA DE REPARACIÓN.");
            }
            
            FileManager.appendToHistory(computer);
            FileManager.saveData(queues);
            
            waitForEnter();
            
        } catch (Exception e) {
            System.out.println("ERROR INESPERADO: " + e.getMessage());
            waitForEnter();
        }
    }
    
    private void processDelivery() {
        clearScreen();
        System.out.println("=== PROCESO DE ENTREGA ===");
        System.out.println("ESCRIBA 'M' PARA VOLVER AL MENÚ PRINCIPAL");
        System.out.println();
        
        try {
            ComputerQueue deliveryQueue = queues.get(ComputerStatus.DELIVERY);
            
            if (deliveryQueue.isEmpty()) {
                System.out.println("NO HAY COMPUTADORAS EN LA COLA DE ENTREGA.");
                waitForEnter();
                return;
            }
            
            Computer computer = selectComputerFromQueue(deliveryQueue, "ENTREGA");
            if (computer == null) return;
            
            System.out.println("PROCESANDO ENTREGA: " + computer.getServiceTag());
            System.out.println("\nINFORMACIÓN COMPLETA:");
            System.out.println(computer.getFullDetails());
            
            String confirmDelivery = null;
            while (confirmDelivery == null) {
                System.out.print("\n¿CONFIRMAR ENTREGA AL CLIENTE? (S/N): ");
                String input = scanner.nextLine().trim().toUpperCase();
                
                if (input.equals(MENU_COMMAND)) return;
                if (input.equals("S") || input.equals("N")) {
                    confirmDelivery = input;
                } else {
                    System.out.println("RESPUESTA INVÁLIDA. INGRESE 'S' PARA SÍ O 'N' PARA NO.");
                }
            }
            
            if (confirmDelivery.equals("S")) {
                computer.addHistoryRecord("COMPUTADORA ENTREGADA AL CLIENTE");
                System.out.println("ENTREGA CONFIRMADA PARA: " + computer.getServiceTag());
                FileManager.appendToHistory(computer);
            } else {
                deliveryQueue.enqueue(computer);
                System.out.println("ENTREGA CANCELADA. COMPUTADORA DEVUELTA A LA COLA DE ENTREGA.");
            }
            
            FileManager.saveData(queues);
            waitForEnter();
            
        } catch (Exception e) {
            System.out.println("ERROR INESPERADO: " + e.getMessage());
            waitForEnter();
        }
    }
    
    private void deleteComputer() {
        clearScreen();
        System.out.println("=== ELIMINAR COMPUTADORA ===");
        System.out.println("ESCRIBA 'M' PARA VOLVER AL MENÚ PRINCIPAL");
        System.out.println();
        
        try {
            String serviceTag = getInputWithValidation(
                "INGRESE EL SERVICE TAG DE LA COMPUTADORA A ELIMINAR: ",
                input -> !input.isEmpty(),
                "EL SERVICE TAG NO PUEDE ESTAR VACÍO.",
                null
            );
            if (serviceTag == null) return;
            
            Computer computer = findComputerByServiceTag(serviceTag);
            
            if (computer == null) {
                System.out.println("NO SE ENCONTRÓ NINGUNA COMPUTADORA CON EL SERVICE TAG: " + serviceTag);
                waitForEnter();
                return;
            }
            
            System.out.println("\nINFORMACIÓN DE LA COMPUTADORA A ELIMINAR:");
            System.out.println(computer);
            
            String confirm = null;
            while (confirm == null) {
                System.out.print("\n¿ESTÁ SEGURO DE ELIMINAR ESTA COMPUTADORA? (S/N): ");
                String input = scanner.nextLine().trim().toUpperCase();
                
                if (input.equals(MENU_COMMAND)) return;
                if (input.equals("S") || input.equals("N")) {
                    confirm = input;
                } else {
                    System.out.println("RESPUESTA INVÁLIDA. INGRESE 'S' PARA SÍ O 'N' PARA NO.");
                }
            }
            
            if (confirm.equals("S")) {
                removeComputerFromQueues(computer);
                System.out.println("COMPUTADORA ELIMINADA EXITOSAMENTE.");
                FileManager.saveData(queues);
            } else {
                System.out.println("OPERACIÓN CANCELADA.");
            }
            
            waitForEnter();
            
        } catch (Exception e) {
            System.out.println("ERROR INESPERADO: " + e.getMessage());
            waitForEnter();
        }
    }
    
    private void showCurrentStatus() {
        clearScreen();
        System.out.println("=== ESTADO ACTUAL DE LAS COLAS ===");
        
        for (ComputerStatus status : ComputerStatus.values()) {
            ComputerQueue queue = queues.get(status);
            System.out.println("\n" + status + " (" + queue.size() + " COMPUTADORAS):");
            queue.displayComputerList();
        }
        
        waitForEnter();
    }
    
    private void showFullHistory() {
        clearScreen();
        System.out.println("=== HISTORIAL COMPLETO DE EQUIPOS ===");
        
        String history = FileManager.readFullHistory();
        System.out.println(history);
        
        waitForEnter();
    }
    
    // Métodos auxiliares
    private String getInputWithValidation(String prompt, java.util.function.Predicate<String> validator, 
                                         String errorMessage, String previousValue) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().toUpperCase();
            
            if (input.equals(MENU_COMMAND)) {
                return null; // Volver al menú
            }
            
            if (input.equals(BACK_COMMAND) && previousValue != null) {
                System.out.println("VOLVIENDO A LA ENTRADA ANTERIOR...");
                continue; // En una implementación más compleja, esto podría manejar el retroceso
            }
            
            if (validator.test(input)) {
                return input;
            } else if (!errorMessage.isEmpty()) {
                System.out.println(errorMessage);
            }
        }
    }
    
    private boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".") && 
               email.indexOf("@") < email.lastIndexOf(".") &&
               email.length() > 5;
    }
    
    private boolean isValidPhone(String phone) {
        return phone.matches("\\d{8}");
    }
    
    private Computer selectComputerByNumber(ComputerQueue queue) {
        while (true) {
            System.out.print("SELECCIONE EL NÚMERO DE COMPUTADORA: ");
            String input = scanner.nextLine().trim();
            
            if (input.equalsIgnoreCase(MENU_COMMAND)) return null;
            
            try {
                int number = Integer.parseInt(input);
                List<Computer> computers = queue.getComputerList();
                
                if (number >= 1 && number <= computers.size()) {
                    Computer selected = computers.get(number - 1);
                    queue.removeComputer(selected);
                    return selected;
                } else {
                    System.out.println("NÚMERO INVÁLIDO. SELECCIONE UN NÚMERO ENTRE 1 Y " + computers.size());
                }
            } catch (NumberFormatException e) {
                System.out.println("INGRESE UN NÚMERO VÁLIDO.");
            }
        }
    }
    
    private Computer selectComputerByServiceTag(ComputerQueue queue) {
        while (true) {
            System.out.print("INGRESE EL SERVICE TAG: ");
            String serviceTag = scanner.nextLine().trim();
            
            if (serviceTag.equalsIgnoreCase(MENU_COMMAND)) return null;
            
            Computer computer = queue.findByServiceTag(serviceTag);
            if (computer != null) {
                queue.removeComputer(computer);
                return computer;
            } else {
                System.out.println("NO SE ENCONTRÓ COMPUTADORA CON ESE SERVICE TAG EN ESTA COLA.");
            }
        }
    }
    
    private Computer selectComputerFromQueue(ComputerQueue queue, String processName) {
        System.out.println("COMPUTADORAS DISPONIBLES PARA " + processName + ":");
        queue.displayComputerList();
        System.out.println();
        
        if (queue.size() == 1) {
            return queue.dequeue();
        } else {
            System.out.println("OPCIONES:");
            System.out.println("1. SELECCIONAR POR NÚMERO");
            System.out.println("2. BUSCAR POR SERVICE TAG");
            System.out.print("SELECCIONE UNA OPCIÓN: ");
            
            String option = scanner.nextLine().trim().toUpperCase();
            if (option.equals(MENU_COMMAND)) return null;
            
            if (option.equals("1")) {
                return selectComputerByNumber(queue);
            } else if (option.equals("2")) {
                return selectComputerByServiceTag(queue);
            } else {
                System.out.println("OPCIÓN INVÁLIDA.");
                waitForEnter();
                return null;
            }
        }
    }
    
    private Computer findComputerByServiceTag(String serviceTag) {
        for (ComputerQueue queue : queues.values()) {
            Computer computer = queue.findByServiceTag(serviceTag);
            if (computer != null) {
                return computer;
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
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }
    
    private void waitForEnter() {
        System.out.println("\nPRESIONE ENTER PARA CONTINUAR...");
        scanner.nextLine();
    }
    
    public static void main(String[] args) {
        // Configurar UTF-8 para mostrar tildes correctamente
        System.setProperty("file.encoding", "UTF-8");
        WarrantySystem system = new WarrantySystem();
        system.run();
    }
}