package homework.assignment_04.ex1.server.api;

import homework.assignment_04.ex1.server.task.TaskQueue;
import homework.assignment_04.ex1.server.task.TaskQueuePool;
import homework.assignment_04.ex1.dto.PrimeSearcherTask;
import homework.assignment_04.ex1.server.worker.WorkerPool;
import homework.assignment_04.ex1.dto.WorkerApi;

import java.rmi.RemoteException;
import java.util.List;
import java.util.UUID;

public class WorkerApiImpl implements WorkerApi {

    private final WorkerPool workerPool = WorkerPool.getInstance();
    private final TaskQueuePool taskQueuePool = TaskQueuePool.getInstance();

    @Override
    public UUID registerWorker() {
        UUID workerId = workerPool.addWorker();
        System.out.printf("Registered worker %s\n", workerId);
        return workerId;
    }

    @Override
    public synchronized PrimeSearcherTask getTask() throws RemoteException {
        return taskQueuePool.getNextTask();
    }

    @Override
    public void reportPrime(PrimeSearcherTask task) throws RemoteException {
        taskQueuePool.getTaskQueue(task.taskQueueId()).reportPrimeNumber(task.number());
    }
}
