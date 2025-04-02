package homework.assignment_04.ex1.server;

import homework.assignment_04.ex1.server.api.ClientApiImpl;
import homework.assignment_04.ex1.server.api.WorkerApiImpl;
import homework.assignment_04.ex1.dto.WorkerApi;
import homework.assignment_04.ex1.dto.ClientApi;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {

    private static final int PORT = 8080;
    private static final Registry registry;

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
            ClientApi clientApi = (ClientApi) UnicastRemoteObject.exportObject(new ClientApiImpl(), 0);
            WorkerApi workerApi = (WorkerApi) UnicastRemoteObject.exportObject(new WorkerApiImpl(), 0);
            registry.bind(ClientApi.registeredName, clientApi);
            registry.bind(WorkerApi.registeredName, workerApi);
            System.out.println("Prime Searcher Model started....");
        } catch (AlreadyBoundException e) {
            throw new RuntimeException(e);
        }
    }

}
