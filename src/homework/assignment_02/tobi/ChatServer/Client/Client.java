package homework.assignment_02.tobi.ChatServer.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public static void main(String[] args) throws IOException {
        args = (args.length == 0) ? new String[]{"127.0.0.1", "8080"} : args;
        //args = (args.length == 0) ? new String[]{"192.168.132.8", "8080"} : args;

        if (args.length < 2) {
            System.out.println("Usage: java Client <hostname> <port>");
            return;
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        // ----------------------- connect and chat -------------------------------
        try (Socket chatServerSocket = new Socket(hostName, portNumber);
             PrintWriter outToServer = new PrintWriter(chatServerSocket.getOutputStream(), true);
             BufferedReader serverMessage = new BufferedReader(new InputStreamReader(chatServerSocket.getInputStream()));
             BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {

            // Create second thread that constantly listens to the server
            Thread serverListener = new Thread(new ClientServerListener(serverMessage));
            serverListener.start();



            // Send Input
            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                outToServer.println(userInput); // Send input to server
                if (userInput.equals("QUIT")){
                    break;
                }
            }
            // stop listening
            serverListener.interrupt();

        } catch (UnknownHostException e) {
            System.err.println("Host not known: " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Could not connect to: " + hostName);
            System.exit(1);
        }
    }
}
