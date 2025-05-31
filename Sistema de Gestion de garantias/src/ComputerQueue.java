import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ArrayList;
import java.util.List;

public class ComputerQueue implements Serializable {
    private Queue<Computer> queue;
    private ComputerStatus status;
    
    public ComputerQueue(ComputerStatus status) {
        this.queue = new LinkedList<>();
        this.status = status;
    }
    
    public void enqueue(Computer computer) {
        computer.setCurrentStatus(status);
        queue.add(computer);
    }
    
    public Computer dequeue() {
        return queue.poll();
    }
    
    public Computer peek() {
        return queue.peek();
    }
    
    public boolean isEmpty() {
        return queue.isEmpty();
    }
    
    public int size() {
        return queue.size();
    }
    
    public ComputerStatus getStatus() {
        return status;
    }
    
    public Queue<Computer> getQueue() {
        return queue;
    }
    
    // Método para obtener una lista ordenada de computadoras para selección
    public List<Computer> getComputerList() {
        return new ArrayList<>(queue);
    }
    
    // Método para remover una computadora específica de la cola
    public boolean removeComputer(Computer computer) {
        return queue.remove(computer);
    }
    
    // Método para buscar computadora por service tag
    public Computer findByServiceTag(String serviceTag) {
        for (Computer computer : queue) {
            if (computer.getServiceTag().equalsIgnoreCase(serviceTag.trim())) {
                return computer;
            }
        }
        return null;
    }
    
    // Método para mostrar lista numerada de computadoras
    public void displayComputerList() {
        if (isEmpty()) {
            System.out.println("  NO HAY COMPUTADORAS EN ESTA COLA.");
            return;
        }
        
        int count = 1;
        for (Computer computer : queue) {
            System.out.println("  " + count + ". " + computer.getServiceTag() + " - " + computer.getClientName());
            count++;
        }
    }
}