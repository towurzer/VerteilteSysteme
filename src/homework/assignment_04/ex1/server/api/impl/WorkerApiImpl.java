package homework.assignment_04.ex1.server.api.impl;

import homework.assignment_04.ex1.api.PrimeSearcherTask;
import homework.assignment_04.ex1.api.WorkerApi;
import homework.assignment_04.ex1.server.task.TaskQueueService;
import homework.assignment_04.ex1.server.worker.WorkerService;

import java.rmi.RemoteException;
import java.util.UUID;

public class WorkerApiImpl implements WorkerApi {

    private final WorkerService workerService = WorkerService.getInstance();
    private final TaskQueueService taskQueueService = TaskQueueService.getInstance();

    @Override
    public UUID registerWorker() {
        UUID workerId = workerService.addWorker();
        System.out.printf("Registered worker %s\n", workerId);
        return workerId;
    }

    @Override
    public PrimeSearcherTask getTask() throws RemoteException {
        while(!workerService.isTerminated()){
            PrimeSearcherTask task = taskQueueService.getNextTask();
            if(task != null){
                return task;
            }

            synchronized (workerService) {
                while(taskQueueService.getNextTask() == null){
                    try {
                        workerService.wait();
                    } catch (InterruptedException e) {
                        System.err.printf("Worker encountered error while waiting: %s\n", e.getMessage());
                        return null;
                    }
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
