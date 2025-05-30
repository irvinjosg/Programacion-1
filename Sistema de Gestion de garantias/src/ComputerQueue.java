import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

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
}