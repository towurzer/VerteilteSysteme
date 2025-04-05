package homework.assignment_04.ex2.server;

import homework.assignment_04.ex1.api.WorkerApi;
import homework.assignment_04.ex1.api.ClientApi;
import homework.assignment_04.ex2.api.DocumentStoreApi;
import homework.assignment_04.ex2.server.api.impl.DocumentStoreApiImpl;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {

    private static final int PORT = 8080;
    private static final Registry registry;
    private static final DocumentStoreApi documentStoreApiImpl = new DocumentStoreApiImpl();

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
            DocumentStoreApi documentStoreApi = (DocumentStoreApi) UnicastRemoteObject.exportObject(documentStoreApiImpl, 0);
            registry.bind(DocumentStoreApi.registeredName, documentStoreApi);
            System.out.println("Document store started....");
        } catch (AlreadyBoundException e) {
            throw new RuntimeException(e);
        }
    }

}
