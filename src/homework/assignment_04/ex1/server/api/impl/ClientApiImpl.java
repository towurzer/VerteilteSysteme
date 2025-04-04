package homework.assignment_04.ex1.server.api.impl;

import homework.assignment_04.ex1.api.ClientApi;
import homework.assignment_04.ex1.server.task.TaskQueueService;
import homework.assignment_04.ex1.server.worker.WorkerService;

import java.util.List;
import java.util.UUID;

public class ClientApiImpl implements ClientApi {

    private final TaskQueueService taskQueueService = TaskQueueService.getInstance();
    private final WorkerService workerService = WorkerService.getInstance();

    @Override
    public UUID createTaskQueue(List<Long> numbers) {
        UUID taskQueueId = taskQueueService.addTaskQueue(numbers);
        workerService.activateWorkers();
        workerService.notifyAll();
        System.out.printf("Created task queue %s for a list of %s numbers\n", taskQueueId, numbers.size());
        return taskQueueId;
    }

    @Override
    public void processTaskQueue(UUID taskQueueId) {
        try {
            System.out.printf("Started processing of task queue %s\n", taskQueueId);
            taskQueueService.processTaskQueue(taskQueueId);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Long> getPrimes(UUID taskQueueId) {
        System.out.printf("Retrieving primes for task queue %s\n", taskQueueId);
        return taskQueueService.getPrimeNumbers(taskQueueId);
    }
}
