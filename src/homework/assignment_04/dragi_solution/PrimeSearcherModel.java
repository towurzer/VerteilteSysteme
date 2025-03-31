package homework.assignment_04.dragi_solution;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PrimeSearcherModel extends Remote {
    public String registeredName = "PrimeSearcherModel";

    /** Signal workers to terminate. */
    public void terminateWorkers() throws RemoteException;

    //--- Interface used by (remote) workers ---

    /** Return ID of new worker (i.e. caller). */
    public int registerWorker() throws RemoteException;

    /** Get next task - contains number N to check for being prime.
     * Blocks if all task queues are empty.
     * Returns null to signal worker to terminate.
     */
    public PrimeSearcherTask getTask() throws RemoteException;

    /** Report that given task is complete. */
    public void reportTaskComplete(PrimeSearcherTask task) throws RemoteException;

    /** Report number N in given task as being prime. */
    public void reportPrime(PrimeSearcherTask task) throws RemoteException;

    //--- Interface used by clients ---

    /** Return number of workers currently registered. */
    public int numWorkers() throws RemoteException;

    /** Create new task queue and return queue ID. */
    public int createTaskQueue() throws RemoteException;

    /** Add task (determine if N is prime) to given task queue. */
    public void addTask(int queueID, long N) throws RemoteException;

    /** Start processing task queue and block until queue is empty. */
    public void processTaskQueue(int queueID) throws RemoteException, InterruptedException;

    /** Return prime numbers found after processing given task queue. */
    public Long[] getPrimes(int queueID) throws RemoteException;
}
