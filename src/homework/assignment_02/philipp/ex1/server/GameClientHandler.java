package homework.assignment_02.philipp.ex1.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class GameClientHandler implements Runnable {

    private final Socket clientSocket;
    private final Player player;
    private final GameRunner gameRunner;
    private PrintWriter clientOut;
    private BufferedReader clientIn;

    public GameClientHandler(Socket clientSocket, Player player, GameRunner gameRunner) {
        System.out.printf("Player connected: %s:%s\n", clientSocket.getInetAddress(), clientSocket.getPort());
        this.clientSocket = clientSocket;
        this.player = player;
        this.gameRunner = gameRunner;
    }

    @Override
    public void run() {
        try {
            clientOut = new PrintWriter(clientSocket.getOutputStream(), true);
            clientIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            clientOut.printf("Connection successful! You are %s\n", gameRunner.identifyPlayer(player));
            if (gameRunner.isRunning()) {
                playRound();
            } else {
                clientOut.println("Waiting on other player...");
                while (!gameRunner.isRunning()) {
                    //Busy waiting on another player
                    Thread.sleep(1000);
                    clientOut.println('.');
                }
                playRound();
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void playRound() throws IOException, InterruptedException {
        //Execute turns
        boolean isWaiting = false;
        while (gameRunner.isRunning() && !clientSocket.isClosed()) {
            if (!gameRunner.isPlayersTurn(player)) {
                if (!isWaiting) {
                    clientOut.println("Waiting on opposition turn...");
                }
                isWaiting = true;
                continue;
            }

            isWaiting = false;
            clientOut.println("------- YOUR TURN -------");
            clientOut.println(gameRunner.getGameboard());
            clientOut.printf("Choose field (0-8) to set %s\n", gameRunner.getAssignedAction(player));
            String input = clientIn.readLine();

            if ("exit".equalsIgnoreCase(input)) {
                closeConnection();
                return;
            }

            try {
                int fieldNumber = Integer.parseInt(input);
                gameRunner.executeTurn(player, fieldNumber);
                clientOut.printf("Turn registered: Placed %s on tile %s\n", gameRunner.getAssignedAction(player), fieldNumber);
            } catch (NumberFormatException nfe) {
                System.err.printf("Invalid tile number: %s \n", nfe);
                clientOut.println("\u001B[31mMalformed input, please try again\u001B[0m");
                continue;
            } catch (IllegalArgumentException iae) {
                System.err.printf("Tile invalid: %s \n", iae);
                clientOut.println("\u001B[31mSelected tile already occupied or out of bounds, please select another\u001B[0m");
                continue;
            }

            System.out.printf("%s (Client: %s) -> received input: %s\n",
                    gameRunner.identifyPlayer(player), clientSocket.getRemoteSocketAddress(), input);
        }


        clientOut.println("----- GAME ENDED -------");
        clientOut.println(gameRunner.getGameboard());

        if (player.equals(gameRunner.getWinner())) {
            clientOut.println("\u001B[32mCONGRATULATIONS! You are the winner!\u001B[0m");
        } else if (gameRunner.getWinner() == null) {
            clientOut.println("\u001B[33mIT IS A DRAW\u001B[0m");
        } else {
            clientOut.println("\u001B[31mSORRY, you lost!\u001B[0m");
        }

        closeConnection();
    }

    private void closeConnection() throws IOException {
        clientOut.println("Closed connection.");
        System.out.printf("Client disconnected: %s:%s\n", clientSocket.getInetAddress(), clientSocket.getPort());
        clientOut.close();
        clientIn.close();
        clientSocket.close();
    }
}
