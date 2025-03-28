package homework.assignment_03.tobi.fileService.client;

import homework.assignment_03.tobi.fileService.server.FileService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
    private static final String DEFAULTIP = "127.0.0.1";
    private static final int DEFAULTPORT = 8080;


    public static void main(String[] args) {
        String hostIp = (args.length == 2) ? args[0] : DEFAULTIP;
        int port = (args.length == 2) ? Integer.parseInt(args[1]) : DEFAULTPORT;

        try (Scanner scanner = new Scanner(System.in)) {
            // ------------ Setup ---------------
            // Getting the registry
            //Registry registry = LocateRegistry.getRegistry(PORT);
            Registry registry = LocateRegistry.getRegistry(hostIp, port);

            // looking up the registry for the remote object
            FileService fileService = (FileService) registry.lookup("fileService");


            System.out.println("Connection successful");

            // ----------- actions ------------
            help();

            while (true) {
                String command = scanner.nextLine().trim();
                System.out.print("\n");
                if (command.equals("exit")) {
                    System.out.println("Shutdown application, goodbye!");
                    break;
                } else if (command.equals("list")) {
                    printFileList(fileService);
                } else if (command.startsWith("get")) {
                    String filename = command.substring("get".length()).trim();
                    loadAndStoreFile(fileService, filename);
                } else if (command.equals("help")) {
                    help();
                } else {
                    System.out.println("Unknown command (To get more information input help)");
                    continue;
                }
                System.out.println("Anything else? Enter here: ");
            }
        } catch (Exception e) {
            System.err.println("Client error: " + e.getMessage());
        }
    }

    private static void help() {
        System.out.println(
                """

                        ----- File System ----

                        Following commands are available:
                        1) "list" -> Displays all files in the server directory
                        2) "get <filename>" -> Fetches the file from the server and saves it in the current working directory. BEWARE: Full filename required!
                        3) "help" -> Print this message
                        4) "exit" -> Closes the application
                        Please enter desired action:"""
        );
    }

    private static void printFileList(FileService fileService) throws RemoteException {
        System.out.println("---- Following files are available: -----");
        fileService.listFiles().forEach(System.out::println);
        System.out.println("-----------------------------------------\n");
    }

    private static void loadAndStoreFile(FileService fileService, String filename) throws IOException {
        try {
            System.out.printf("Loading file %s....\n", filename);
            byte[] content = fileService.loadFile(filename);


            String path = "%s/%s%s/".formatted(Paths.get("").toAbsolutePath().toString(), "src/homework/assignment_03/tobi/fileService/client/output/files_of_", Client.class.getSimpleName());
            Path dirPath = Paths.get(path);
            Files.createDirectories(dirPath);
            Path filePath = Paths.get("%s/%s".formatted( path, filename));
            Files.write(filePath, content);

            System.out.printf("Stored file locally under %s\n", filePath);
        } catch (Exception e) {
            if (e.getCause() instanceof FileNotFoundException) {
                System.err.printf("File %s not found, please try another filename\n", filename);
                return;
            }
            throw e;
        }

    }
}
