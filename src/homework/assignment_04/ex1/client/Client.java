package homework.assignment_04.ex1.client;

import homework.assignment_04.ex1.dto.ClientApi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.stream.LongStream;

public class Client {

    private static ClientApi clientApi;


    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {

            System.out.println("Please enter host ip address: ");
            final String hostAddress = scanner.nextLine();

            System.out.println("Please enter port number: ");
            final int portNumber = Integer.parseInt(scanner.nextLine());


            try {
                Registry registry = LocateRegistry.getRegistry(hostAddress, portNumber);
                clientApi = (ClientApi) registry.lookup(ClientApi.registeredName);
            } catch (RemoteException | NotBoundException e) {
                throw new RuntimeException(e);
            }

            System.out.println("\u001B[32mConnection successful!\u001B[0m");

            clientApi.createTaskQueue(LongStream.range(0, 100).boxed().toList());

            while(true){
                Thread.sleep(5000);
                System.out.println("Still here");
            }

        } catch (Exception e) {
            printError("Client error: " + e.getMessage());
        }
    }

    private static void printError(String message, Object... args){
        System.err.printf("\u001B[31m%s\u001B[0m\n", message.formatted(args));
    }
}
