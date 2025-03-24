package Class_Example.javaMRI_03;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server extends ImplExample{

    private static final int PORT = 8080;

    public Server() {}

    public static void main(String[] args) {
        try{
            // Instanciating the iimplementation class
            ImplExample obj = new ImplExample();

            // Exporting the object of implementation class
            // here we are exporting the remote object to the stub
            Hello stub = (Hello) UnicastRemoteObject.exportObject(obj, 0);

            // Binding the remote object (stub) in the registry
            Registry registry = LocateRegistry.createRegistry(PORT);

            registry.bind("Hello", stub);
            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
