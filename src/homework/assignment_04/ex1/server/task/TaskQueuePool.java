package homework.assignment_04.ex1.server.task;

import homework.assignment_04.ex1.dto.PrimeSearcherTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TaskQueuePool {

    Map<UUID, TaskQueue> taskQueues = new HashMap<>();

    private static TaskQueuePool INSTANCE;

    private TaskQueuePool(){}

    public static TaskQueuePool getInstance() {
        if(INSTANCE == null){
            INSTANCE = new TaskQueuePool();
        }
        return INSTANCE;
    }

    public synchronized UUID addTaskQueue(List<Long> numbers){
        UUID taskQueueId = UUID.randomUUID();
        Queue<PrimeSearcherTask> queue = new ConcurrentLinkedQueue<>(numbers.stream().map(n -> new PrimeSearcherTask(taskQueueId, n)).toList());
        var taskQueue = new TaskQueue(taskQueueId, queue, false);
        taskQueues.put(taskQueueId, taskQueue);
        return taskQueueId;
    }

    public synchronized TaskQueue getTaskQueue(UUID taskQueueId){
        return taskQueues.get(taskQueueId);
    }

    /**
     * Returns a task from any open task queue
     */
    public synchronized PrimeSearcherTask getNextTask(){
        var activeQueues = taskQueues.values().stream().filter(TaskQueue::isActive).toList();
        if(activeQueues.isEmpty()){
            return null;
        }
        return activeQueues.getFirst().getTask();
    }

}
