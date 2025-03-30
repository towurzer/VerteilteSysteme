package homework.assignment_03.publish_subscribe_system.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server extends NewsPlatformImpl {

    public static void main(String[] args) {
        int port = (args.length > 0) ? Integer.parseInt(args[0]) : 8080;
        try {
            // Instantiating the implementation class
            NewsPlatformImpl newsPlatform = new NewsPlatformImpl();

            // Exporting the object of implementation class
            // here we are exporting the remote object to the stub
            NewsPlatform newsPlatformObject = (NewsPlatform) UnicastRemoteObject.exportObject(newsPlatform, 0);

            // Binding the remote object (stub) in the registry
            Registry registry = LocateRegistry.createRegistry(port);

            registry.bind("newsPlatform", newsPlatformObject);
            System.err.println("News Platform started...");
        } catch (Exception e) {
            System.err.println("Server exception: " + e);
            e.printStackTrace();
        }
    }
}
