import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Computer implements Serializable {
    private String serviceTag;
    private String problemDescription;
    private LocalDate receptionDate;
    private String clientName;
    private String clientEmail;
    private String clientPhone;
    private List<HistoryRecord> history;
    private ComputerStatus currentStatus;
    private String diagnosis;
    private String repairDetails;
    private String technicianName;
    
    public Computer(String serviceTag, String problemDescription, LocalDate receptionDate,
                    String clientName, String clientEmail, String clientPhone) {
        this.serviceTag = serviceTag;
        this.problemDescription = problemDescription;
        this.receptionDate = receptionDate;
        this.clientName = clientName;
        this.clientEmail = clientEmail;
        this.clientPhone = clientPhone;
        this.history = new ArrayList<>();
        this.currentStatus = ComputerStatus.RECEPTION;
        
        // Add first history record
        addHistoryRecord("Computadora recibida con problema: " + problemDescription);
    }
    
    public void addHistoryRecord(String description) {
        history.add(new HistoryRecord(LocalDate.now(), description, currentStatus));
    }
    
    public String getServiceTag() {
        return serviceTag;
    }
    public String getClientName() {
        return serviceTag;
    }    
    public ComputerStatus getCurrentStatus() {
        return currentStatus;
    }
    
    public void setCurrentStatus(ComputerStatus status) {
        this.currentStatus = status;
    }
    
    public String getDiagnosis() {
        return diagnosis;
    }
    
    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }
    
    public String getRepairDetails() {
        return repairDetails;
    }
    
    public void setRepairDetails(String repairDetails) {
        this.repairDetails = repairDetails;
    }
    
    public String getTechnicianName() {
        return technicianName;
    }
    
    public void setTechnicianName(String technicianName) {
        this.technicianName = technicianName;
    }
    
    public List<HistoryRecord> getHistory() {
        return history;
    }
    
    @Override
    public String toString() {
        return "Service Tag: " + serviceTag + 
               "\nCliente: " + clientName +
               "\nEstado actual: " + currentStatus +
               "\nFecha de recepción: " + receptionDate +
               "\nDescripción del problema: " + problemDescription +
               "\nContacto: " + clientEmail + " / " + clientPhone;
    }
    
    public String getFullDetails() {
        StringBuilder details = new StringBuilder();
        details.append(toString()).append("\n");
        
        if (diagnosis != null && !diagnosis.isEmpty()) {
            details.append("Diagnóstico: ").append(diagnosis).append("\n");
        }
        
        if (repairDetails != null && !repairDetails.isEmpty()) {
            details.append("Detalles de reparación: ").append(repairDetails).append("\n");
            details.append("Técnico: ").append(technicianName).append("\n");
        }
        
        details.append("\nHistorial:\n");
        for (HistoryRecord record : history) {
            details.append(record).append("\n");
        }
        
        return details.toString();
    }
}

enum ComputerStatus {
    RECEPTION("Recepción"),
    INSPECTION("Inspección"),
    REPAIR("Reparación"),
    QUALITY_CONTROL("Control de Calidad"),
    DELIVERY("Entrega");
    
    private String displayName;
    
    ComputerStatus(String displayName) {
        this.displayName = displayName;
    }
    
    @Override
    public String toString() {
        return displayName;
    }
}

class HistoryRecord implements Serializable {
    private LocalDate date;
    private String description;
    private ComputerStatus status;
    
    public HistoryRecord(LocalDate date, String description, ComputerStatus status) {
        this.date = date;
        this.description = description;
        this.status = status;
    }
    
    @Override
    public String toString() {
        return date + " - [" + status + "] " + description;
    }
}