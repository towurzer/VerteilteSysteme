package homework.assignment_03.publish_subscribe_system.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Server extends NewsPlattformImpl {

    public static void main(String[] args) {
        int port = (args.length > 0) ? Integer.parseInt(args[0]) : 8080;
        try{
            // Instanciating the iimplementation class
            NewsPlattformImpl newsPlattform = new NewsPlattformImpl();

            // Exporting the object of implementation class
            // here we are exporting the remote object to the stub
            NewsPlattform newsPlattformObject = (NewsPlattform) UnicastRemoteObject.exportObject(newsPlattform, 0);

            // Binding the remote object (stub) in the registry
            Registry registry = LocateRegistry.createRegistry(port);

            registry.bind("newsPlattform", newsPlattformObject);
            System.err.println("News Plattform started...");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
