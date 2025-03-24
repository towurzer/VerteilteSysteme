package Homework.Exercise_2.TicTacToe.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public static void main(String[] args) {
        args = (args.length == 0) ? new String[]{"127.0.0.1", "8080"} : args;
        //args = new String[]{"192.168.132.8", "8080"};

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try (
                Socket serverSocket = new Socket(hostName, portNumber);
                BufferedReader serverMessage = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
                PrintWriter outToServer = new PrintWriter(serverSocket.getOutputStream(), true);
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))
        ) {

            String message;
            while (true){
                message = serverMessage.readLine(); // get message from server ...
                if(message.equals("PleaseSendInput")){
                    String userInput = stdIn.readLine(); // get user input
                    outToServer.println(userInput); // send it to server
                } else if (message.equals("EXIT")) {
                    break;
                } else {
                    System.out.println(message); // ... and print it if it*s not the input keyword.
                }
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
