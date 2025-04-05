package homework.assignment_04.ex1.server.task;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class TaskQueueLockManager {
    public static final Object WORKER_TASK_WAIT_LOCK = new Object();
    private final ConcurrentHashMap<UUID, Object> locks = new ConcurrentHashMap<>();

    public Object getLockForTaskQueue(UUID taskQueueId) {
        return locks.computeIfAbsent(taskQueueId, k -> new Object());
    }

    public void removeLock(UUID taskQueueId) {
        locks.remove(taskQueueId);
    }
}