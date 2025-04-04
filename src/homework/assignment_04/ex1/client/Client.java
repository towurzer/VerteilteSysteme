package homework.assignment_04.ex1.client;

import homework.assignment_04.ex1.api.ClientApi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.UUID;
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

            UUID taskQueueId = clientApi.createTaskQueue(LongStream.range(0, 100).boxed().toList());

            // Start processing
            clientApi.processTaskQueue(taskQueueId);

            clientApi.getPrimes(taskQueueId).forEach(System.out::println);

        } catch (Exception e) {
            printError("Client error: " + e);
        }
    }

    private static void printError(String message, Object... args) {
        System.err.printf("\u001B[31m%s\u001B[0m\n", message.formatted(args));
    }
}
