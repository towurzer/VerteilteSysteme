package org.aau.homework.assignment_04.ex1.server.api.impl;

import org.aau.homework.assignment_04.ex1.api.PrimeSearcherTask;
import org.aau.homework.assignment_04.ex1.api.WorkerApi;
import org.aau.homework.assignment_04.ex1.server.task.TaskQueueLockManager;
import org.aau.homework.assignment_04.ex1.server.task.TaskQueueService;
import org.aau.homework.assignment_04.ex1.server.worker.WorkerService;
import org.aau.homework.assignment_04.ex1.api.WorkerApi;
import org.aau.homework.assignment_04.ex1.server.task.TaskQueueLockManager;
import org.aau.homework.assignment_04.ex1.server.worker.WorkerService;

import java.rmi.RemoteException;
import java.util.UUID;

public class WorkerApiImpl implements WorkerApi {

    private final WorkerService workerService = WorkerService.getInstance();
    private final TaskQueueService taskQueueService = TaskQueueService.getInstance();

    @Override
    public UUID registerWorker() {
        return workerService.addWorker();
    }

    @Override
    public PrimeSearcherTask getTask() throws RemoteException {
        while (!workerService.isTerminated()) {
            PrimeSearcherTask task = taskQueueService.getNextTask();
            if (task != null) {
                return task;
            }

            synchronized (TaskQueueLockManager.WORKER_TASK_WAIT_LOCK) {
                try {
                    System.out.println("All tasks finished, waiting for new task...");
                    TaskQueueLockManager.WORKER_TASK_WAIT_LOCK.wait();
                } catch (InterruptedException e) {
                    System.err.printf("Worker encountered error while waiting: %s\n", e.getMessage());
                    return null;
                }
            }
        }
        return null;
    }

    @Override
    public void reportPrime(PrimeSearcherTask task) throws RemoteException {
        taskQueueService.reportPrimeNumber(task);
    }

    @Override
    public void reportTaskComplete(PrimeSearcherTask task) throws RemoteException {
        taskQueueService.reportTaskComplete(task);
    }
}
