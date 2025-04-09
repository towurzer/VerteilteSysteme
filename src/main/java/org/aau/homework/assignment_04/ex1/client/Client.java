package org.aau.homework.assignment_04.ex1.client;

import org.aau.homework.assignment_04.ex1.api.ClientApi;
import org.aau.homework.assignment_04.ex1.api.ClientApi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
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

            System.out.println("Please enter lower bound: ");
            final long lowerBound = Long.parseLong(scanner.nextLine());

            System.out.println("Please enter port number: ");
            final long upperBound = Long.parseLong(scanner.nextLine());


            List<Long> numbers = LongStream.range(lowerBound, upperBound).boxed().toList();
            UUID taskQueueId = clientApi.createTaskQueue(numbers);

            long startTime = System.nanoTime();
            // Start processing
            clientApi.processTaskQueue(taskQueueId);

            clientApi.getPrimes(taskQueueId).forEach(System.out::println);

            printElapsedTime(startTime, System.nanoTime());

            int numOfWorkers = clientApi.getNumberOfWorkers();
            System.out.printf("Number of workers used: %s\n", numOfWorkers);

        } catch (Exception e) {
            printError("Client error: " + e);
        }
    }

    private static void printError(String message, Object... args) {
        System.err.printf("\u001B[31m%s\u001B[0m\n", message.formatted(args));
    }

    /**
     * Print the elapsed time to the console
     * @param start Start time in nanoseconds
     * @param end End time in nanoseconds
     */
    public static Duration printElapsedTime(long start, long end){
        var elapsedTime = Duration.of(end - start, ChronoUnit.NANOS);
        System.out.printf("Elapsed time: %s.%s seconds\n", elapsedTime.getSeconds(), elapsedTime.getNano());
        return elapsedTime;
    }
}
