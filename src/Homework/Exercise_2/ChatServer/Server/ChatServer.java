package Homework.Exercise_2.ChatServer.Server;

import java.io.*;
import java.net.*;

/**
 * ChatServer class handles multiple clients in a single chat channel.
 */
public class ChatServer {
    private static final ClientHandlerManager manager = new ClientHandlerManager();

    public static void main(String[] args) {
        args = (args.length == 0) ? new String[]{"8080"} : args;
        int port = Integer.parseInt(args[0]);
        ClientHandler clientHandler;


        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                // As soon as a new Client connects, create a new Client-handler and start it
                clientHandler = new ClientHandler(serverSocket.accept(), manager);
                manager.addClientHandler(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException ignored) {}
    }
}
