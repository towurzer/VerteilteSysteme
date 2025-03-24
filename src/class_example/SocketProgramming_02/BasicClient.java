package class_example.SocketProgramming_02;
import java.io.*;
import java.net.*;

public class BasicClient {
    public static void main(String[] args) throws IOException {
        args = (args.length == 0) ? new String[]{"127.0.0.1", "8080"} : args;
        //args = (args.length == 0) ? new String[]{"192.168.132.8", "8080"} : args;

        if (args.length < 2) {
            System.out.println("Usage: java Client <hostname> <port>");
            return;
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try (Socket echoSocket = new Socket(hostName, portNumber);
             PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
             BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {

            String userInput;
            while ((userInput = stdIn.readLine()) != null && !userInput.equals("exit")) {
                out.println(userInput); // Send input to server
                System.out.println("echo: " + in.readLine()); // Read and print response from server
            }
        } catch (UnknownHostException e) {
            System.err.println("Host not known: " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Could not connect to: " + hostName);
            System.exit(1);
        }
    }
}
