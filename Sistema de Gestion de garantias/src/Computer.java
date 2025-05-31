import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        this.serviceTag = serviceTag.toUpperCase();
        this.problemDescription = problemDescription.toUpperCase();
        this.receptionDate = receptionDate;
        this.clientName = clientName.toUpperCase();
        this.clientEmail = clientEmail.toLowerCase(); // Email mantiene formato estándar
        this.clientPhone = clientPhone;
        this.history = new ArrayList<>();
        this.currentStatus = ComputerStatus.RECEPTION;
        
        // Add first history record
        addHistoryRecord("COMPUTADORA RECIBIDA CON PROBLEMA: " + this.problemDescription);
    }
    
    public void addHistoryRecord(String description) {
        history.add(new HistoryRecord(LocalDate.now(), description.toUpperCase(), currentStatus));
    }
    
    public String getServiceTag() {
        return serviceTag;
    }
    
    public String getClientName() {
        return clientName;
    }
    
    public String getProblemDescription() {
        return problemDescription;
    }
    
    public LocalDate getReceptionDate() {
        return receptionDate;
    }
    
    public String getClientEmail() {
        return clientEmail;
    }
    
    public String getClientPhone() {
        return clientPhone;
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
        this.diagnosis = diagnosis.toUpperCase();
    }
    
    public String getRepairDetails() {
        return repairDetails;
    }
    
    public void setRepairDetails(String repairDetails) {
        this.repairDetails = repairDetails.toUpperCase();
    }
    
    public String getTechnicianName() {
        return technicianName;
    }
    
    public void setTechnicianName(String technicianName) {
        this.technicianName = technicianName.toUpperCase();
    }
    
    public List<HistoryRecord> getHistory() {
        return history;
    }
    
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return "SERVICE TAG: " + serviceTag + 
               "\nCLIENTE: " + clientName +
               "\nESTADO ACTUAL: " + currentStatus +
               "\nFECHA DE RECEPCIÓN: " + receptionDate.format(formatter) +
               "\nDESCRIPCIÓN DEL PROBLEMA: " + problemDescription +
               "\nCONTACTO: " + clientEmail + " / " + clientPhone;
    }
    
    public String getFullDetails() {
        StringBuilder details = new StringBuilder();
        details.append(toString()).append("\n");
        
        if (diagnosis != null && !diagnosis.isEmpty()) {
            details.append("DIAGNÓSTICO: ").append(diagnosis).append("\n");
        }
        
        if (repairDetails != null && !repairDetails.isEmpty()) {
            details.append("DETALLES DE REPARACIÓN: ").append(repairDetails).append("\n");
            details.append("TÉCNICO: ").append(technicianName).append("\n");
        }
        
        details.append("\nHISTORIAL:\n");
        details.append("FECHA      | CONTENIDO                                    | EQUIPO\n");
        details.append("-----------|----------------------------------------------|----------\n");
        for (HistoryRecord record : history) {
            details.append(record.getFormattedRecord()).append("\n");
        }
        
        return details.toString();
    }
}

enum ComputerStatus {
    RECEPTION("RECEPCIÓN"),
    INSPECTION("INSPECCIÓN"),
    REPAIR("REPARACIÓN"),
    QUALITY_CONTROL("CONTROL DE CALIDAD"),
    DELIVERY("ENTREGA");
    
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
    
    public String getFormattedRecord() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dateStr = date.format(formatter);
        String content = description.length() > 44 ? description.substring(0, 41) + "..." : description;
        return String.format("%-10s | %-44s | %-8s", dateStr, content, status.toString());
    }
    
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(formatter) + " - [" + status + "] " + description;
    }
}