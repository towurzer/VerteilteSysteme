package homework.assignment_04.ex1.server.task;

import homework.assignment_04.ex1.api.PrimeSearcherTask;
import homework.assignment_04.ex1.server.worker.WorkerService;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TaskQueueService {

    private final WorkerService workerService = WorkerService.getInstance();
    private final TaskQueueLockManager lockManager = new TaskQueueLockManager();
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

    public UUID addTaskQueue(List<Long> numbers) {
        UUID taskQueueId = UUID.randomUUID();
        var lock = lockManager.getLockForTaskQueue(taskQueueId);
        synchronized (lock) {
            Queue<PrimeSearcherTask> queue = new ConcurrentLinkedQueue<>(numbers.stream().map(n -> new PrimeSearcherTask(taskQueueId, n)).toList());
            var taskQueue = new TaskQueue(taskQueueId, queue, false);
            taskQueues.put(taskQueueId, taskQueue);
            return taskQueueId;
        }
    }

    public TaskQueue getTaskQueue(UUID taskQueueId) {
        return taskQueues.get(taskQueueId);
    }

    public void processTaskQueue(UUID taskQueueId) throws InterruptedException {
        var taskQueue = taskQueues.get(taskQueueId);
        if (taskQueue != null) {
            taskQueue.setActive(true);
            synchronized (TaskQueueLockManager.WORKER_TASK_WAIT_LOCK) {
                workerService.activateWorkers();
                System.out.printf("Started to process task queue, notifying workers: id=%s\n",taskQueueId);
                TaskQueueLockManager.WORKER_TASK_WAIT_LOCK.notifyAll();
            }

            var lock = lockManager.getLockForTaskQueue(taskQueueId);
            synchronized (lock) {
                while (taskQueue.getTaskCount() > 0) {
                    System.out.printf("Waiting for task queue to finish: id=%s\n", taskQueueId);
                    lock.wait();
                }

                System.out.printf("Task queue finished: id=%s\n", taskQueueId);
                taskQueue.setActive(false);
                lockManager.removeLock(taskQueueId);
            }
        }
    }

    public void reportTaskComplete(PrimeSearcherTask task) {
        var lock = lockManager.getLockForTaskQueue(task.taskQueueId());
        synchronized (lock) {
            var taskQueue = taskQueues.get(task.taskQueueId());
            if (taskQueue != null) {
                taskQueue.reportTaskComplete(task);
                System.out.printf("Task completed: id=%s, number=%s, remainingTasks=%s\n", task.taskQueueId(), task.number(), taskQueue.getTaskCount());
                if (taskQueue.getTaskCount() == 0) {
                    System.out.printf("Task queue finished, notifying waiting threads: id=%s\n", task.taskQueueId());
                    lock.notifyAll();
                }
            }
        }
    }

    public void reportPrimeNumber(PrimeSearcherTask task) {
        var taskQueue = taskQueues.get(task.taskQueueId());
        if (taskQueue != null) {
            taskQueue.reportPrimeNumber(task.number());
        }
    }

    public synchronized List<TaskQueue> getActiveQueues() {
        return taskQueues.values().stream().filter(TaskQueue::isActive).toList();
    }

    /**
     * Returns a task from any open task queue
     */
    public PrimeSearcherTask getNextTask() {
        var activeQueues = getActiveQueues();
        System.out.println("Active queues: " + activeQueues.stream().map(TaskQueue::getTaskQueueId).toList());
        if (activeQueues.isEmpty()) {
            return null;
        }

        var taskQueue = activeQueues.getFirst();
        var lock = lockManager.getLockForTaskQueue(taskQueue.getTaskQueueId());
        synchronized (lock) {
            return taskQueue.getTask();
        }
    }

    public List<Long> getPrimeNumbers(UUID taskQueueId) {
        var lock = lockManager.getLockForTaskQueue(taskQueueId);
        synchronized (lock) {
            var taskQueue = getTaskQueue(taskQueueId);
            if (taskQueue == null) {
                System.err.printf("No task queue found for id: %s\n", taskQueueId);
                return List.of();
            }
            var primes = taskQueue.getPrimeNumbers()
                    .stream()
                    .sorted(Long::compareTo)
                    .toList();
            System.out.printf("Prime numbers: %s\n", primes);
            return primes;
        }
    }
}
