package org.aau.homework.assignment_02.philipp.ex1.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class GameServer {

    public static final int MAX_CONNECTIONS = 2;
    public static final int PORT_NUMBER = 8080;

    public static void main(String[] args) {

        //Using the backlog parameter in the ServerSocket constructor, one can limit the number
        //of clients
        try (ServerSocket serverSocket = new ServerSocket(PORT_NUMBER, MAX_CONNECTIONS)) {

            var gameRunner = new GameRunner();
            var connections = new ArrayList<Socket>();
            System.out.println("Server started. Waiting for two players...");
            while (true) {
                if (connections.size() < MAX_CONNECTIONS) {
                    var socket = serverSocket.accept();
                    var player = new Player();
                    gameRunner.registerPlayer(player);
                    connections.add(socket);
                    new Thread(new GameClientHandler(socket, player, gameRunner)).start();
                } else if (!gameRunner.isRunning()) {
                    gameRunner.start();
                }
            }

        } catch (IOException e) {
            System.out.println("Error while checking port: " + PORT_NUMBER);
        }
    }

}

