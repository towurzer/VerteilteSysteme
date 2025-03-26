package homework.assignment_03.ex1;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
    private static final int PORT = 8080;

    private static final Registry registry;
    private static final FileSystem fileSystem;

    static {
        try {
            registry = LocateRegistry.getRegistry(PORT);
            fileSystem = (FileSystem) registry.lookup("fileSystem");
        } catch (RemoteException | NotBoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        try {

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Enter command: ");
                String command = scanner.nextLine();
                if (command.equals("exit")) {
                    break;
                } else if (command.equals("list")) {
                    System.out.println("Here are all files in the directory:");
                    fileSystem.listFiles().forEach(System.out::println);
                } else if (command.startsWith("get")) {
                    String filename = command.substring(3).trim();
                    try {
                        var content = fileSystem.loadFile(filename);
                        System.out.printf("Content of file %s:", filename);
                        System.out.println(content);
                    } catch (RemoteException re) {
                        System.out.println("File not found, please try another filename");
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Client exception: " + e);
            e.printStackTrace();
        }
    }
}
