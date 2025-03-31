package homework.assignment_04.dragi_solution;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class PrimeSearcherModelImpl implements PrimeSearcherModel {

    private class TaskQState {
        boolean active = false;
        int numIncompleteTasks = 0;   // number of incomplete tasks
        Queue<PrimeSearcherTask> taskQ = new ConcurrentLinkedQueue<PrimeSearcherTask>();
        List<Long> primes = new LinkedList<Long>();
    }

    private int numWorkers = 0;
    private boolean terminateWorkers = false;
    private ArrayList<TaskQState> taskQArray = new ArrayList<TaskQState>();

    @Override
    public void terminateWorkers() throws RemoteException {
        synchronized(this) {
            this.terminateWorkers = true;
            this.notify();    // release workers blocked by getTask()
        }
    }

    @Override
    public synchronized int registerWorker() throws RemoteException {
        numWorkers++;
        return numWorkers;
    }

    @Override
    public PrimeSearcherTask getTask() throws RemoteException {
        while (!terminateWorkers) {
            for (TaskQState state : taskQArray) {
                if (state.active) {
                    PrimeSearcherTask task = state.taskQ.poll();
                    if (task != null) {
                        return task;
                    }
                }
            }
            // All task queues are empty - block until new task is added.
            synchronized (this) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    return null;
                }
            }
        }
        return null;
    }

    @Override
    public void reportTaskComplete(PrimeSearcherTask task) throws RemoteException {
        TaskQState state = taskQArray.get(task.queueID);
        synchronized (state) {
            state.numIncompleteTasks--;
            if (state.numIncompleteTasks <= 0) {
                state.notify();   // release clients blocked by processTaskQueue()
            }
        }
    }

    @Override
    public void reportPrime(PrimeSearcherTask task) throws RemoteException {
        TaskQState state = taskQArray.get(task.queueID);
        synchronized (state.primes) {
            state.primes.add(task.N);
        }
    }

    @Override
    public int numWorkers() throws RemoteException {
        return numWorkers;
    }

    @Override
    public synchronized int createTaskQueue() throws RemoteException {
        int queueID = taskQArray.size();
        taskQArray.add(queueID, new TaskQState());
        return queueID;
    }

    @Override
    public void addTask(int queueID, long N) throws RemoteException {
        TaskQState state = taskQArray.get(queueID);
        synchronized (state) {
            state.taskQ.add(new PrimeSearcherTask(queueID, N));
            state.numIncompleteTasks++;
        }
        synchronized (this) {
            this.notify();    // release workers blocked by getTask()
        }
    }

    @Override
    public void processTaskQueue(int queueID) throws RemoteException, InterruptedException {
        TaskQState state = taskQArray.get(queueID);
        synchronized (state) {
            state.active = true;
            // Block until all tasks are completed.
            while (state.numIncompleteTasks > 0) {
                state.wait();
            }
            state.active = false;
        }
    }

    @Override
    public Long[] getPrimes(int queueID) throws RemoteException {
        Long[] primes;
        TaskQState state = taskQArray.get(queueID);
        synchronized (state.primes) {
            primes = state.primes.toArray(new Long[0]);
        }
        return primes;
    }
}
