package homework.assignment_04.ex1.dto;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;

public interface WorkerApi extends Remote {
    String registeredName = "workerApi";

    UUID registerWorker() throws RemoteException;
    PrimeSearcherTask getTask() throws RemoteException;
    void reportPrime(PrimeSearcherTask task) throws RemoteException;

}
