package org.aau.homework.assignment_03.philipp.ex1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {

    private static FileSystem fileSystem;


    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {

            System.out.println("Please enter host ip address: ");
            final String hostAddress = scanner.nextLine();

            System.out.println("Please enter port number: ");
            final int portNumber = Integer.parseInt(scanner.nextLine());


            try {
                Registry registry = LocateRegistry.getRegistry(hostAddress, portNumber);
                fileSystem = (FileSystem) registry.lookup("fileSystem");
            } catch (RemoteException | NotBoundException e) {
                throw new RuntimeException(e);
            }

            System.out.println("\u001B[32mConnection successful!\u001B[0m");
            System.out.println(
                    """

                            ----- File System ----

                            Following commands are available:
                            1) "list" -> Displays all files in the server directory
                            2) "get <filename>" -> Fetches the file from the server and saves it in the current working directory. BEWARE: Full filename required!
                            3) "exit" -> Closes the application
                            Please enter desired action:""");
            while (true) {
                String command = scanner.nextLine();
                System.out.print("\n");
                if (command.equals("exit")) {
                    System.out.println("Shutdown application, goodbye!");
                    break;
                } else if (command.equals("list")) {
                    displayListOfFiles();
                } else if (command.startsWith("get")) {
                    String filename = command.substring("get".length()).trim();
                    loadAndStoreFile(filename);
                }
            }
        } catch (Exception e) {
            printError("Client error: " + e.getMessage());
        }
    }

    private static void loadAndStoreFile(String filename) throws IOException {
        try {
            System.out.printf("Loading file %s....\n", filename);
            var content = fileSystem.loadFile(filename);
            String path = "%s/%s/%s".formatted(System.getProperty("user.dir"), "out", filename);
            Path file = Paths.get(path);
            Files.write(file, content);
            System.out.printf("\u001B[32mStored file locally under %s\u001B[0m\n", file.toAbsolutePath());
        } catch (Exception e) {
            if (e.getCause() instanceof FileNotFoundException) {
                printError("File %s not found, please try another filename", filename);
                return;
            }
            throw e;
        }
    }

    private static void displayListOfFiles() throws RemoteException {
        System.out.println("---- Following files are available: -----");
        fileSystem.listFiles().forEach(System.out::println);
    }

    private static void printError(String message, Object... args){
        System.err.printf("\u001B[31m%s\u001B[0m\n", message.formatted(args));
    }
}
