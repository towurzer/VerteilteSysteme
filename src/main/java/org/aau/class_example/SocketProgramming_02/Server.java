package org.aau.class_example.SocketProgramming_02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        args = (args.length == 0) ? new String[]{"8080"} : args;
        int portNumber = Integer.parseInt(args[0]);

        try (ServerSocket serverSocket = new ServerSocket(portNumber);
             Socket clientSocket = serverSocket.accept();
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

            System.out.println("Client connected on port: " + portNumber);

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received message: " + inputLine + " from " + clientSocket.toString());
                out.println(inputLine); // Ensures the message is echoed back properly
            }
        } catch (IOException e) {
            System.out.println("Error while checking port: " + portNumber);
        }
    }


}
