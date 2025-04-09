package org.aau.homework.assignment_02.tobi.ChatServer.Server;


import java.util.HashSet;
import java.util.Set;

public class ClientHandlerManager {
    private final Set<ClientHandler> clientHandlers = new HashSet<>(); // List of client output streams

    public ClientHandlerManager() {
    }

    public void addClientHandler(ClientHandler clientHandlerToAdd) {
        clientHandlers.add(clientHandlerToAdd);
    }

    public void removeClientHandler(ClientHandler clientHandlerToRemove) {
        clientHandlers.remove(clientHandlerToRemove);
    }

    public String getActiveUsersInChannel(int channelID) {
        StringBuilder returnString = new StringBuilder();
        for (ClientHandler clientHandler : clientHandlers.stream().filter(clientHandler -> clientHandler.getChannel() == channelID).toList()) {
            returnString.append(clientHandler.getUsername());
            returnString.append(", ");
        }
        returnString.delete(returnString.length() - 2, returnString.length());
        return returnString.toString();
    }

    /**
     * Broadcasts a message to all connected clients that have the same channelId as the given one.
     * Not really secure but its late and it works.
     */
    public void broadcast(String message, int channelID) {
        clientHandlers.stream().filter(clientHandler -> clientHandler.getChannel() == channelID).toList().forEach(clientHandler -> clientHandler.getOut().println(message)); // #cleanCode

        /* Depricated
        for (ClientHandler clientHandler : clientHandlers.stream().filter(clientHandler -> clientHandler.getChannel() == channelID).toList()) {
            clientHandler.getOut().println(message);*/
    }
}
