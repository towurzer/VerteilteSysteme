package org.aau.homework.assignment_04.dragi_solution;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;

public class PrimeSearcherClient {

    protected static void usage() {
        System.out.println("Usage: java aau.vs.assignment4.PrimeSearcherClient [OPTIONS] L R");
        System.out.println("Search for prime numbers in the range [L, L+R] of long integers");
        System.out.println("and report execution time in seconds.");
        System.out.println("Needs PrimeSearcherServer and one or more PrimeSearcherWorker processes running,");
        System.out.println("possibly remote (RMI application).");
        System.out.println("OPTIONS:");
        System.out.println("-s HOST          hostname or IP address of node running PrimeSearcherServer");
        System.out.println("                 (defaults to localhost");
    }

    public static void main(String[] args) {
        String registryHost = "127.0.0.1";    // default
        int argc = 0;
        try {
            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("-s")) {
                    registryHost = args[++i];
                } else {
                    argc = i;
                    break;
                }
            }
            if (args.length - argc != 2) {
                usage();
                System.exit(2);
            }
        } catch (NumberFormatException e) {
            usage();
            System.exit(2);
        }

        try {
            long L = Long.parseLong(args[argc]);
            int R = Integer.parseInt(args[argc+1]) + 1;  // number of integers to process

            // Obtain remote object reference
            Registry registry = LocateRegistry.getRegistry(registryHost, PrimeSearcherServer.rmiRegistryPort);
            PrimeSearcherModel psModel = (PrimeSearcherModel) registry.lookup(PrimeSearcherModel.registeredName);

            int queueID = psModel.createTaskQueue();

            long execTime = System.nanoTime();
            // Fill task queue
            for (int i=0; i < R; i++) {
                psModel.addTask(queueID, L+i);
            }
            // Obtain number of registered workers
            int numWorkers = psModel.numWorkers();
            // Start processing and wait until task queue is empty
            psModel.processTaskQueue(queueID);
            Long[] primes = psModel.getPrimes(queueID);
            Arrays.sort(primes);   // sort using natural ordering
            execTime = System.nanoTime() - execTime;

            System.out.format("Prime numbers in range [%d,%d]: (checked %d numbers)\n", L, L+R-1, R);
            for (long p : primes) {
                System.out.format("%d\n", p);
            }
            System.out.format("(%d primes)\n", primes.length);
            System.out.format("Computation took %.3f seconds (using %d workers)\n", (double) execTime / 1.0e9, numWorkers);
        } catch (NumberFormatException e) {
            System.out.format("ERROR: invalid integer: %s or %s\n", args[0], args[1]);
            usage();
            System.exit(2);
        } catch (RemoteException | NotBoundException | InterruptedException e) {
            System.out.format("ERROR: %s\n", e.getMessage());
            System.exit(1);
        }
    }
}
