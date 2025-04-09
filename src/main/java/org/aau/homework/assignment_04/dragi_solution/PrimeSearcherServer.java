package org.aau.homework.assignment_04.dragi_solution;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class PrimeSearcherServer {
    public static final int rmiRegistryPort = 1099;
    private static final int rmiObjectPort = 9999;

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(rmiRegistryPort);
            System.out.println("Started RMI registry on port " + rmiRegistryPort);

            PrimeSearcherModel psModelImpl = new PrimeSearcherModelImpl();
            PrimeSearcherModel psModel = (PrimeSearcherModel) UnicastRemoteObject.exportObject(psModelImpl, rmiObjectPort);
            registry.bind(PrimeSearcherModel.registeredName, psModel);
            System.out.println("Serving PrimeSearcherModel via RMI on port " + rmiObjectPort);
            System.out.println("Enter 'quit'<ENTER> to terminate.");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String input = reader.readLine();
            while (! "quit".equals(input)) {
                input = reader.readLine();
            }
            psModel.terminateWorkers();
            // Sleep for 1 second
            Thread.sleep(1000);
            System.exit(0);
        } catch (RemoteException | AlreadyBoundException ex) {
            System.out.println("RMI ERROR: " + ex.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.out.println("I/O ERROR: " + e.getMessage());
            System.exit(1);
        } catch (InterruptedException e) {
            // Ignore
        }
    }
}
