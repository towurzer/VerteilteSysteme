package homework.assignment_04.ex2.client;

import homework.assignment_04.ex2.api.DocumentStoreApi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Client {

    private static DocumentStoreApi documentStoreApi;


    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {

            System.out.println("Please enter host ip address: ");
            final String hostAddress = scanner.nextLine();

            System.out.println("Please enter port number: ");
            final int portNumber = Integer.parseInt(scanner.nextLine());


            try {
                Registry registry = LocateRegistry.getRegistry(hostAddress, portNumber);
                documentStoreApi = (DocumentStoreApi) registry.lookup(DocumentStoreApi.registeredName);
            } catch (RemoteException | NotBoundException e) {
                throw new RuntimeException(e);
            }

            System.out.println("\u001B[32mConnection successful!\u001B[0m");
            printMenu();
            while (true) {
                String command = scanner.nextLine();
                System.out.print("\n");
                if (command.equals("exit")) {
                    System.out.println("Shutdown application, goodbye!");
                    break;
                } else if (command.equals("create")) {
                    System.out.println("Welcome to the Document Creator:\n-------------------------");
                    System.out.println("Please enter title of document: ");
                    String title = scanner.nextLine();
                    System.out.println("Please enter the names of the authors (seperated by ','): ");
                    String[] authors = scanner.nextLine().trim().split(",");
                    System.out.print("""
                            Please enter the content of the document now.
                            (Hint: It can be multiline, to submit please enter an empty row)
                            """);
                    String content = getText(scanner);

                    System.out.println("Thank you, creating document now...");
                    UUID documentId = documentStoreApi.createDocument(title, List.of(authors), content);
                    System.out.printf("Successfully created document (Available under ID %s)\n", documentId);
                } else if (command.equals("list")) {
                    listDocuments();
                } else if (command.startsWith("find")) {
                    String titlePattern = command.substring("find".length()).trim();
                    findDocuments(titlePattern);
                } else if (command.startsWith("get")) {
                    try{
                        UUID id = UUID.fromString(command.substring("get".length()).trim());
                        getDocument(id);
                    }catch (IllegalArgumentException e){
                        printError("Invalid document id, please try again!");
                    }
                } else if (command.equals("help")) {
                    printMenu();
                } else {
                    printError("Unknown command: " + command);
                }
            }
        } catch (Exception e) {
            printError("Client error: " + e.getMessage());
        }
    }

    private static void printMenu() {
        System.out.println(
                """

                        ----- Simple Document Store ----

                        Following commands are available:
                        1) "create" -> Opens document creator
                        2) "list" -> Lists all available documents
                        3) "find <title>" -> Finds all documents matching the given title
                        4) "get <id>" -> Retrieves the document with the given Id
                        5) "help" -> Print this message
                        6) "exit" -> Closes the application
                        Please enter desired action:""");
    }

    private static void listDocuments() throws RemoteException {
        System.out.println("---- All available documents ----");
        var documentsList = documentStoreApi.listDocuments();
        if(documentsList.isEmpty()) {
            System.out.println("<No documents found>");
            return;
        }
        documentsList.stream().map(d -> "%s (ID: %s)".formatted(d.title(), d.id())).forEach(System.out::println);
    }

    private static void findDocuments(String titlePattern) throws RemoteException {
        System.out.printf("Trying to find documents matching the given title: %s... \n", titlePattern);
        var documentsList = documentStoreApi.findDocumentByTitle(titlePattern);
        if(documentsList.isEmpty()) {
            System.out.println("<No documents found>");
            return;
        }
        documentsList.forEach(System.out::println);
    }

    private static void getDocument(UUID id) throws RemoteException {
        System.out.printf("Trying to find documents matching the given id: %s... \n", id);
        var document = documentStoreApi.getDocument(id);
        if (document == null) {
            System.out.println("<No document with given id found>");
        }
        System.out.println(document);
    }

    private static String getText(Scanner scanner) {
        StringBuilder content = new StringBuilder();
        String line;

        while (true) {
            line = scanner.nextLine();
            if (line.isEmpty()) {  // empty line -> done
                break;
            }
            content.append(line).append("\n");
        }
        return content.toString().trim(); // Remove trailing newline and return
    }


    private static void printError(String message, Object... args) {
        System.err.printf("\u001B[31m%s\u001B[0m\n", message.formatted(args));
    }
}
