package homework.assignment_04.ex1.server.task;

import homework.assignment_04.ex1.dto.PrimeSearcherTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.UUID;

public class TaskQueue {

    private UUID taskQueueId;
    private Queue<PrimeSearcherTask> taskQueue;
    private boolean isActive;
    private final List<Long> primeNumbers = new ArrayList<>();

    public TaskQueue(UUID taskQueueId, Queue<PrimeSearcherTask> taskQueue, boolean isActive) {
        this.taskQueueId = taskQueueId;
        this.taskQueue = taskQueue;
        this.isActive = isActive;
    }

    public UUID getTaskQueueId() {
        return taskQueueId;
    }

    public void setTaskQueueId(UUID taskQueueId) {
        this.taskQueueId = taskQueueId;
    }

    public Queue<PrimeSearcherTask> getTaskQueue() {
        return taskQueue;
    }

    public void setTaskQueue(Queue<PrimeSearcherTask> taskQueue) {
        this.taskQueue = taskQueue;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public synchronized void reportPrimeNumber(Long number){
        primeNumbers.add(number);
    }

    public synchronized List<Long> getPrimeNumbers() {
        return primeNumbers;
    }

    public synchronized PrimeSearcherTask getTask(){
        return taskQueue.poll();
    }

}
