package homework.assignment_04.ex1.client;

import homework.assignment_04.ex1.api.PrimeSearcherTask;
import homework.assignment_04.ex1.api.WorkerApi;

import java.math.BigInteger;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class WorkerClient {
    private static WorkerApi workerApi;

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {

            System.out.println("Please enter host ip address: ");
            final String hostAddress = scanner.nextLine();

            System.out.println("Please enter port number: ");
            final int portNumber = Integer.parseInt(scanner.nextLine());

            try {
                Registry registry = LocateRegistry.getRegistry(hostAddress, portNumber);
                workerApi = (WorkerApi) registry.lookup(WorkerApi.registeredName);
            } catch (RemoteException | NotBoundException e) {
                throw new RuntimeException(e);
            }

            System.out.println("\u001B[32mConnection successful!\u001B[0m");

            PrimeSearcherTask task;
            while ((task = workerApi.getTask()) != null) {
                System.out.printf("Received task: queueId=%s, num=%s\n", task.taskQueueId(), task.number());
                if (isPrime(task.number())) {
                    System.out.printf("\\u001B[32mReporting prime number: queueId=%s, num=%s\\u001B[0m \n",
                            task.taskQueueId(), task.number());
                    workerApi.reportPrime(task);
                }
                workerApi.reportTaskComplete(task);
            }

        } catch (Exception e) {
            printError("Client error: " + e.getMessage());
        }
    }

    /**
     * Checks if a given number is prime
     */
    private static boolean isPrime(long num) {
        // Convert long to BigInteger and get the sqrt of the value
        var big = new BigInteger(String.valueOf(num));
        var sqrt = big.sqrt().longValue();
        for (long j = 2; j <= sqrt; j++) {
            if (num % j == 0) {
                return false;
            }
        }

        // Multiply by two since if m is a divisor of N, then also N/m is a divisor
        return true;
    }

    private static void printError(String message, Object... args) {
        System.err.printf("\u001B[31m%s\u001B[0m\n", message.formatted(args));
    }
}
