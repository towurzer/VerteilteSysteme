package Homework.Exercise_2.ChatServer.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * ClientHandler manages individual client connections.
 */
public class ClientHandler implements Runnable {
    private final Socket socket;
    private final ClientHandlerManager manager;
    private String username;
    private int channel;

    private PrintWriter out;
    private BufferedReader in;

    public ClientHandler(Socket socket, ClientHandlerManager manager) {
        this.socket = socket;
        this.manager = manager;
    }
    public PrintWriter getOut() {
        return out;
    }
    public int getChannel() {
        return channel;
    }
    public String getUsername() {
        return username;
    }
    private void setUp() throws IOException {
        // setup
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        // login
        out.println("Enter your username:");
        username = in.readLine();
        out.println("Enter the Channel Number you want to join");
        try {
            channel = Integer.parseInt(in.readLine());
        } catch (NumberFormatException e) {
            channel = 1;
        }

        out.println("Joining channel " + channel);
        out.println("Currently Active Users in this Channel: " + manager.getActiveUsersInChannel(channel));
        out.println("To quit Enter 'QUIT', To change Channel enter 'change a' where a is the channel you want to move to\n");
        manager.broadcast(username + " has joined the chat.", channel);
    }
    private void chat(String message){
        // change channel
        if (message.matches("change [0-9]+")) {
            try {
                int newChannel = Integer.parseInt(message.split(" ")[1]);
                if (newChannel != this.channel) {

                    manager.broadcast(username + " has left the chat.", this.channel);


                    this.channel = newChannel;
                    out.println("Joining channel " + this.channel);
                    out.println("Currently Active Users in this Channel: " + manager.getActiveUsersInChannel(channel));

                    manager.broadcast(username + " has joined the chat.", this.channel);
                }
            } catch (NumberFormatException e) {
                out.println("Invalid channel number.");
            }
        } else{
            // general chatting
            manager.broadcast(username + ": " + message, channel);
        }
    }
    private void leave(){
        manager.broadcast(username + " has left the chat.", channel);
        manager.removeClientHandler(this);
    }

    public void run() {
        String message;
        try {


            setUp();
            while ((message = in.readLine()) != null && !message.equals("QUIT")){
                chat(message);
            }
            leave();


        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                socket.close();
            } catch (IOException ignored) {
            }
        }
    }
}