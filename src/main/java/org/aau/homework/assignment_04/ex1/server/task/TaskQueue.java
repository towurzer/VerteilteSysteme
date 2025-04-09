package org.aau.homework.assignment_04.ex1.server.task;

import org.aau.homework.assignment_04.ex1.api.PrimeSearcherTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class TaskQueue {

    private final UUID taskQueueId;
    private final Queue<PrimeSearcherTask> taskQueue;
    private boolean isActive;
    private final List<Long> primeNumbers = new ArrayList<>();
    private final Map<UUID, Boolean> taskState = new ConcurrentHashMap<>();

    public TaskQueue(UUID taskQueueId, Queue<PrimeSearcherTask> taskQueue, boolean isActive) {
        this.taskQueueId = taskQueueId;
        this.taskQueue = taskQueue;
        this.isActive = isActive;
        taskQueue.forEach(task -> taskState.put(task.taskId(), false));
    }

    public UUID getTaskQueueId() {
        return taskQueueId;
    }

    public Queue<PrimeSearcherTask> getTaskQueue() {
        return taskQueue;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public synchronized void reportPrimeNumber(Long number) {
        primeNumbers.add(number);
    }

    public synchronized List<Long> getPrimeNumbers() {
        return primeNumbers;
    }

    public PrimeSearcherTask getTask() {
        return taskQueue.poll();
    }

    public long getTaskCount() {
        return taskState.values().stream().filter(b -> b.equals(Boolean.FALSE)).count();
    }

    public void reportTaskComplete(PrimeSearcherTask task) {
        taskState.put(task.taskId(), true);
    }
}
