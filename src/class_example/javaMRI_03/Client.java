package class_example.javaMRI_03;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Locale;

public class Client {

    private static final int PORT = 2020;
    private Client(){}

    public static void main(String[] args) {
        try{
            // Getting the registry
            //Registry registry = LocateRegistry.getRegistry(PORT);
            Registry registry = LocateRegistry.getRegistry("192.168.236.23", PORT);

            // looking up the registry for the remote object
            Hello stub = (Hello) registry.lookup("Hello");

            // Calling the remote method using the obtained object
            stub.printMSg();

            System.out.println("Remote method invoded");
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
