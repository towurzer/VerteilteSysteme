package homework.assignment_04.ex1.server.task;

import homework.assignment_04.ex1.api.PrimeSearcherTask;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TaskQueueService {

    Map<UUID, TaskQueue> taskQueues = new ConcurrentHashMap<>();

    private static TaskQueueService INSTANCE;

    private TaskQueueService() {
    }

    public static TaskQueueService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TaskQueueService();
        }
        return INSTANCE;
    }

    public synchronized UUID addTaskQueue(List<Long> numbers) {
        UUID taskQueueId = UUID.randomUUID();
        Queue<PrimeSearcherTask> queue = new ConcurrentLinkedQueue<>(numbers.stream().map(n -> new PrimeSearcherTask(taskQueueId, n)).toList());
        var taskQueue = new TaskQueue(taskQueueId, queue, false);
        taskQueues.put(taskQueueId, taskQueue);
        return taskQueueId;
    }

    public synchronized TaskQueue getTaskQueue(UUID taskQueueId) {
        return taskQueues.get(taskQueueId);
    }

    public void processTaskQueue(UUID taskQueueId) throws InterruptedException {
        var taskQueue = taskQueues.get(taskQueueId);
        if (taskQueue != null) {
            synchronized (taskQueue) {
                taskQueue.setActive(true);

                while (taskQueue.getTaskCount() > 0) {
                    taskQueue.wait();
                }

                taskQueue.setActive(false);
                taskQueues.remove(taskQueueId);
            }
        }
    }

    public synchronized void reportTaskComplete(PrimeSearcherTask task) {
        var taskQueue = taskQueues.get(task.taskQueueId());
        if (taskQueue != null) {
            synchronized (taskQueue) {
                taskQueue.reportTaskComplete();
                if (taskQueue.getTaskCount() == 0) {
                    taskQueue.notifyAll();
                }
            }

        }
    }

    public synchronized void reportPrimeNumber(PrimeSearcherTask task) {
        var taskQueue = taskQueues.get(task.taskQueueId());
        if (taskQueue != null) {
            taskQueue.reportPrimeNumber(task.number());
        }
    }

    /**
     * Returns a task from any open task queue
     */
    public synchronized PrimeSearcherTask getNextTask() {
        var activeQueues = getActiveTaskQueues();
        if (activeQueues.isEmpty()) {
            return null;
        }
        return activeQueues.getFirst().getTask();
    }

    public List<TaskQueue> getActiveTaskQueues() {
        return taskQueues.values().stream().filter(TaskQueue::isActive).toList();
    }

    public synchronized List<Long> getPrimeNumbers(UUID taskQueueId) {
        var taskQueue = getTaskQueue(taskQueueId);
        if (taskQueue == null) {
            return null;
        }
        return taskQueue.getPrimeNumbers()
                .stream()
                .sorted(Long::compareTo)
                .toList();
    }
}
