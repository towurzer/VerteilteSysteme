package Homework.Exercise_2.TicTacToe.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class ClientHandler implements java.lang.Runnable{
    private final PrintWriter out;
    private final BufferedReader in;

    private final CyclicBarrier barrier;

    private final char symbol;

    private final GameManager gameManager;

    public ClientHandler(Socket clientSocket, char symbol, GameManager gameManager, CyclicBarrier barrier) throws IOException {
        this.symbol = symbol;
        this.gameManager = gameManager;
        this.barrier = barrier;

        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        System.out.println("Client connected on port: " + clientSocket.getPort());
        out.println("Welcome Player " + symbol);
        out.println("Waiting for other Player to connect ...");
    }

    /**
     * @return returns user input or null if an exception occurred
     */
    private String getUserInput() {
        try {
            out.println("PleaseSendInput"); // Message to signal client that input is expected (will not be printed)
            return in.readLine();
        } catch (IOException e) {
            e.getStackTrace();
        }
        return null;
    }

    /**
     * Prints that it's the other players turn or collects input depending on whose turn it is
     */
    private void gameManagerWaitsForInput() {
        if (!gameManager.isYourTurn(this)) {
            out.println("Waiting for other Player to make a move ...");
            return;
        }

        // Your Turn
        String[] lastPos = gameManager.getLastPlayedPosition();

        if (lastPos != null) out.printf("Your opponent (%s) made a move at %s.\n", lastPos[1], lastPos[0]);
        out.println("Its your turn to make a move.");

        out.println("The board looks like this:");
        out.println(gameManager.getBoard());
        out.println("Where do you want to place your move? Please input it <r><c>. i.e.: 'a1'");
        place(); // make a move

        gameManager.checkIfGameEnded();
    }

    /**
     * Prompts the user to make a move until it was successful
     */
    private void place() {
        String placement = getUserInput();
        assert placement != null;
        if (!placement.matches("[abc] ?[123] *")) { // invalid syntax try again
            out.println("Invalid Input Syntax please use <r><c>. i.e. 'a1'");
        } else {

            // valid syntax
            placement = placement.trim();
            if (gameManager.playMove(placement.charAt(0), Integer.parseInt(placement.substring(placement.length()-1)), symbol)) {
                out.println("Move Played Successfully - The board now looks like this:");
                out.println(gameManager.getBoard());
                return; // successfully moved
            }
            // else: already occupied try again
            out.println("Sorry but this position is already occupied please try again (Format: <r><c>)");
        }
        place();
    }


    /**
     * The game has finished, inform the user. And request endOfGame
     */
    private void gameFinished(){
        // Game has ended
        Character winner = gameManager.getWinner();
        assert winner != null;

        if (winner.equals('-')) out.println("The game has ended. Its a draw!");
        else if (winner.equals(symbol)) out.println("The game has ended. YOU WON!!!");
        else out.printf("The game has ended and unfortunately you lost :/ (%c won)\n", winner);

        out.println(gameManager.getBoard());

        gameManager.requestEndOfGame();
    }

    /**
     * Runs this operation.
     */
    @Override
    public void run() {
        // Perform action
        switch (gameManager.getStatus()) {
            case WAITING_FOR_INPUT -> gameManagerWaitsForInput();
            case FINISHED -> gameFinished();
            default -> {}
        }

        // wait for other user
        try {
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }

        // and again (until done)
        if (gameManager.getStatus() != STATUS.ENDED){
            run();
        } else {
            // Message to signal client that The game Ended And the Server will Shut down (will not be printed)
            out.println("EXIT");
        }
    }
}