package homework.assignment_03.ex1;

import homework.assignment_03.ex1.dto.File;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class FileServer {

    private static final int PORT = 8080;
    private static final Registry registry;

    private static final FileSystemImpl fileSystem = new FileSystemImpl();

    static {
        // Binding the remote object (stub) in the registry
        try {
            System.out.println("Adding files....");
            for (int i = 0; i < 10; i++) {
                var file = new File();
                file.setFilename("File_" + i);
                file.setContent("This is file number " + i);
                fileSystem.addFile(file);
                System.out.println("Added file " + file.getFilename());
            }

            registry = LocateRegistry.createRegistry(PORT);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws RemoteException {
        try {
            FileSystem fs = (FileSystem) UnicastRemoteObject.exportObject(fileSystem, 0);
            registry.bind("fileSystem", fs);
            System.out.println("File Service started....");
        } catch (AlreadyBoundException e) {
            throw new RuntimeException(e);
        }
    }

}
