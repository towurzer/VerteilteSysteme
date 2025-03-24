package Homework.Exercise_2.TicTacToe.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CyclicBarrier;

public class GameManager{

    private STATUS status; // current Status

    private final TicTacToeBoard board = new TicTacToeBoard();

    private int indexOfCurrentPlayerToMove = (int)Math.round(Math.random());

    private int playersLeft = 2; // amount players left to acknowledge end of game

    private Character winner = null;

    public CyclicBarrier barrier = new CyclicBarrier(2);

    // ------------------------------------------------------------------
    private final Socket[] clientSockets = new Socket[2];
    private final ClientHandler[] clientHandlers = new ClientHandler[2];

    // --------------------------------------------------------------------

    public GameManager(int portNumber) {
        this.status = STATUS.WAITING_FOR_CONNECTION;
        getPlayers(portNumber); // get two users to connect
        status = STATUS.WAITING_FOR_INPUT;
    }

    /**
     * Waits until two users have connected to the game
     * @param port port to connect to
     */
    public void getPlayers(int port){
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            clientSockets[0] = serverSocket.accept();
            System.out.println("Player 1 connected on Port: " + clientSockets[0].getPort());
            clientHandlers[0] = new ClientHandler(clientSockets[0], 'X', this, barrier);
            new Thread(clientHandlers[0]).start();

            clientSockets[1] = serverSocket.accept();
            System.out.println("Player 2 connected on Port: " + clientSockets[1].getPort());
            clientHandlers[1] = new ClientHandler(clientSockets[1], 'O', this, barrier);
            new Thread(clientHandlers[1]).start();
        } catch (IOException e) {
            System.out.println("Error while checking port: " + port);
        }
    }

    /**
     * tries to play the move given und updates the player to move if successful
     * @return whether the move was successful
     */
    public boolean playMove(char row, int column, char character){
        if (board.playMove(row, column, character)){
            indexOfCurrentPlayerToMove = (indexOfCurrentPlayerToMove+1) % 2;
            return true;
        }
        return false;
    }

    /**
     * Checks if a player has one and updates status if necessary
     */
    public void checkIfGameEnded(){
        Character winner = board.evaluate();
        if (winner == null) return;

        status = STATUS.FINISHED;
        this.winner = winner;
    }

    /**
     * Lets the ClientHandlers acknowledge the end of the game and request the server - shutdown.
     */
    public synchronized void requestEndOfGame(){
        assert status == STATUS.FINISHED: "The game has not finished you can't request the end of the game (= server shutdown)";
        status = (--playersLeft == 0) ? STATUS.ENDED : status;
    }



    //----------------utility getters -----------------------
    public boolean isYourTurn(ClientHandler clientHandlerThatAsks){return clientHandlers[indexOfCurrentPlayerToMove].equals(clientHandlerThatAsks);}
    public STATUS getStatus() {
        return status;
    }
    public String getBoard() {
        return board.toString();
    }
    public String[] getLastPlayedPosition(){
        return board.getLastMove();
    }
    public Character getWinner() {
        return winner;
    }

}
