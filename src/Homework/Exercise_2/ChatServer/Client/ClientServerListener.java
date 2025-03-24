package Homework.Exercise_2.ChatServer.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ClientServerListener implements Runnable{

    private final BufferedReader serverMessage;

    ClientServerListener (BufferedReader serverMessageReader){
        this.serverMessage = serverMessageReader;
    }

    /**
     * Runs this operation.
     */
    @Override
    public void run() {
        try {
            String message;
            while ((message = serverMessage.readLine()) != null) {
                System.out.println(message); // If there is a Message print in console
            }
        } catch (IOException e) {
            System.out.println("Disconnected from server."); // You left :)
        }
    }
}
