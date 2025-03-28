package homework.assignment_03.publish_subscribe_system.client;

import homework.assignment_03.publish_subscribe_system.server.NewsPlattform;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {

    private static final int PORT = 2020;
    private Client(){}

    public static void main(String[] args) {
        try{
            // Getting the registry
            //Registry registry = LocateRegistry.getRegistry(PORT);
            Registry registry = LocateRegistry.getRegistry("192.168.236.23", PORT);

            // looking up the registry for the remote object
            NewsPlattform stub = (NewsPlattform) registry.lookup("Hello");

            // Calling the remote method using the obtained object
            //stub.printMSg();

            System.out.println("Remote method invoded");
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
