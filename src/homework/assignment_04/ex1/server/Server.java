package homework.assignment_04.ex1.server;

import homework.assignment_04.ex1.server.api.impl.ClientApiImpl;
import homework.assignment_04.ex1.server.api.impl.WorkerApiImpl;
import homework.assignment_04.ex1.api.WorkerApi;
import homework.assignment_04.ex1.api.ClientApi;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {

    private static final int PORT = 8080;
    private static final Registry registry;
    private static final WorkerApi workerApiImpl = new WorkerApiImpl();
    private static final ClientApi clientApiImpl = new ClientApiImpl();

    static {
        // Binding the remote object (stub) in the registry
        try {
            registry = LocateRegistry.createRegistry(PORT);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws RemoteException {
        try {
            ClientApi clientApi = (ClientApi) UnicastRemoteObject.exportObject(clientApiImpl, 0);
            WorkerApi workerApi = (WorkerApi) UnicastRemoteObject.exportObject(workerApiImpl, 0);
            registry.bind(ClientApi.registeredName, clientApi);
            registry.bind(WorkerApi.registeredName, workerApi);
            System.out.println("Prime Searcher Model started....");
        } catch (AlreadyBoundException e) {
            throw new RuntimeException(e);
        }
    }

}
