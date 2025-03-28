package homework.assignment_03.publish_subscribe_system.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server extends NewsPlattformImpl {

    private static final int PORT = 8080;

    public Server() {}

    public static void main(String[] args) {
        try{
            // Instanciating the iimplementation class
            NewsPlattformImpl obj = new NewsPlattformImpl();

            // Exporting the object of implementation class
            // here we are exporting the remote object to the stub
            NewsPlattform stub = (NewsPlattform) UnicastRemoteObject.exportObject(obj, 0);

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
