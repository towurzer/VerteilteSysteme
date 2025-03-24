package homework.assignment_02.philipp.ex1.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class GameClient {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter host ip address: ");
        final String hostName = scanner.nextLine();

        System.out.println("Please enter port number: ");
        final int portNumber = Integer.parseInt(scanner.nextLine());

        try (Socket echoSocket = new Socket(hostName, portNumber);
             PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
             BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {

            // Thread to receive messages from the Server
            new Thread(() -> {
                try {
                    String serverMessage;
                    while ((serverMessage = in.readLine()) != null) {
                        System.out.println(serverMessage);
                    }
                } catch (IOException e) {
                    System.err.println("Could not connect to: " + hostName);
                    System.exit(1);
                }
            }).start();

            String input;
            while ((input = stdIn.readLine()) != null) {
                out.println(input);
            }

        } catch (UnknownHostException e) {
            System.err.println("Host not known: " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Could not connect to: " + hostName);
            System.exit(1);
        } finally {
            scanner.close();
        }
    }
}
