package homework.assignment_04.ex1.dto;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.UUID;

public interface ClientApi extends Remote {
    String registeredName = "clientApi";

    UUID createTaskQueue(List<Long> numbers) throws RemoteException;
    void processTaskQueue(UUID taskQueueId) throws RemoteException;
    List<Long> getPrimes(UUID taskQueueId) throws RemoteException;
}
