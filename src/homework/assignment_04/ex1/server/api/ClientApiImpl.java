package homework.assignment_04.ex1.server.api;

import homework.assignment_04.ex1.server.task.TaskQueue;
import homework.assignment_04.ex1.server.task.TaskQueuePool;
import homework.assignment_04.ex1.dto.ClientApi;

import java.util.List;
import java.util.UUID;

public class ClientApiImpl implements ClientApi {

    private final TaskQueuePool taskQueuePool = TaskQueuePool.getInstance();


    @Override
    public UUID createTaskQueue(List<Long> numbers) {
        UUID taskQueueId = taskQueuePool.addTaskQueue(numbers);
        System.out.printf("Created task queue %s for a list of %s numbers\n", taskQueueId, numbers.size());
        return taskQueueId;
    }

    @Override
    public void processTaskQueue(UUID taskQueueId) {
        TaskQueue taskQueue = taskQueuePool.getTaskQueue(taskQueueId);
        taskQueue.setActive(true);
        System.out.printf("Started processing of task queue %s\n", taskQueueId);
    }

    @Override
    public List<Long> getPrimes(UUID taskQueueId) {
        System.out.printf("Retrieving primes for task queue %s\n", taskQueueId);
        var taskQueue = taskQueuePool.getTaskQueue(taskQueueId);
        return taskQueue.getPrimeNumbers();
    }
}
