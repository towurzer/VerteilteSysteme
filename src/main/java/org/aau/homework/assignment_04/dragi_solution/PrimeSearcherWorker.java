package org.aau.homework.assignment_04.dragi_solution;

import java.math.BigInteger;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class PrimeSearcherWorker {

    protected static void usage() {
        System.out.println("Usage: java aau.vs.assignment4.PrimeSearcherWorker [OPTIONS]");
        System.out.println("Register as worker process with PrimeSearcherServer (RMI application).");
        System.out.println("OPTIONS:");
        System.out.println("-s HOST          hostname or IP address of node running PrimeSearcherServer");
        System.out.println("                 (defaults to localhost");
    }

    public static void main(String[] args) {
        String registryHost = "127.0.0.1";    // default
        int argc = 0;
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-s")) {
                registryHost = args[++i];
            } else {
                argc = i;
                break;
            }
        }
        if (args.length - argc != 0) {
            usage();
            System.exit(2);
        }

        try {
            // Obtain remote object reference
            Registry registry = LocateRegistry.getRegistry(registryHost, PrimeSearcherServer.rmiRegistryPort);
            PrimeSearcherModel psModel = (PrimeSearcherModel) registry.lookup(PrimeSearcherModel.registeredName);

            int workerID = psModel.registerWorker();
            System.out.println("Registered as PrimeSearcherWorker - ID: " + workerID);

            // Process task queues until null value is returned
            int numTasks = 0;
            PrimeSearcherTask task = psModel.getTask();    // blocks if all queues are empty
            while (task != null) {
                long N = task.N;
                long upper = BigInteger.valueOf(N).sqrt().longValue();
                boolean isPrime = true;
                for (long d = 2; d <= upper; d++) {
                    if (N % d == 0) {
                        // divisor found, N is not prime
                        isPrime = false;
                        break;
                    }
                }
                if (isPrime) {
                    psModel.reportPrime(task);
                }
                psModel.reportTaskComplete(task);
                numTasks++;
                task = psModel.getTask();    // blocks if all queues are empty
            }
            System.out.format("PrimeSearcherWorker %d terminating, processed %d tasks\n", workerID, numTasks);
        } catch (RemoteException | NotBoundException e) {
            System.out.format("ERROR: %s\n", e.getMessage());
            System.exit(1);
        }
    }
}