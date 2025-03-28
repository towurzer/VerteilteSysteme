package homework.assignment_03.tobi.fileService.server;

import class_example.javaMRI_03.Hello;
import class_example.javaMRI_03.ImplExample;
import homework.assignment_03.tobi.fileService.client.Client;

import java.nio.file.Paths;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server extends FileServiceImpl {

    private Server(String path) {
        super(path);
    }

    public static void main(String[] args) {
        int port = (args.length > 0) ? Integer.parseInt(args[0]) : 8080;

        try{
            // Instanciating the iimplementation class
            String path = "%s/%s/%s".formatted(Paths.get("").toAbsolutePath().toString(), "src/homework/assignment_03/tobi/fileService/server", "fileServiceFolder");

            FileServiceImpl fileService = new FileServiceImpl(path);

            // Exporting the object of implementation class
            // here we are exporting the remote object to the stub
            FileService fileServiceObject = (FileService) UnicastRemoteObject.exportObject(fileService, 0);

            // Binding the remote object (stub) in the registry
            Registry registry = LocateRegistry.createRegistry(port);

            registry.bind("fileService", fileServiceObject);
            System.err.println("File Service started...");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
