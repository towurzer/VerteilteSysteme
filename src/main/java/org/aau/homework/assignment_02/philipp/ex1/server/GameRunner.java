package org.aau.homework.assignment_02.philipp.ex1.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class GameRunner {

    private static final int MAX_PLAYERS = 2;

    private final GameBoard gameboard;

    private boolean isRunning = false;
    private final List<Player> players = new ArrayList<>(2);
    private final HashMap<Player, Action> assignedActions = new HashMap<>();
    private Player activePlayer;
    private Player winner = null;


    public GameRunner() {
        this.gameboard = new GameBoard();
    }

    public void start() {
        System.out.println("Players connected. Starting game...");
        var rand = new Random();
        activePlayer = players.get(rand.nextInt(players.size()));
        this.isRunning = true;
    }

    public void registerPlayer(Player player) {
        if (players.size() >= MAX_PLAYERS) {
            throw new IllegalArgumentException("Maximum number of players reached!");
        }
        players.add(player);
        assignedActions.put(player, players.size() < MAX_PLAYERS ? Action.CROSS : Action.CIRCLE);
    }

    public String identifyPlayer(Player player) {
        return "Player %s".formatted(players.indexOf(player) + 1);
    }

    public Action getAssignedAction(Player player) {
        return assignedActions.get(player);
    }

    public boolean isPlayersTurn(Player player) {
        return Objects.equals(player, activePlayer);
    }

    public synchronized void executeTurn(Player player, int tileNumber) throws IllegalArgumentException {
        if (!isPlayersTurn(player)) {
            throw new IllegalArgumentException("It is not the turn of this player!");
        }

        Action action = getAssignedAction(player);
        System.out.printf("%s set %s on tile %d%n", identifyPlayer(player), action, tileNumber);
        gameboard.fillTile(tileNumber, action);

        if (gameboard.checkWin()) {
            System.out.printf("%s won!%n", identifyPlayer(player));
            winner = player;
            this.isRunning = false;
        }

        if (gameboard.isFull()) {
            System.out.println("Board is full! It is a draw");
            this.isRunning = false;
        }

        activePlayer = players.get(players.indexOf(activePlayer) == 0 ? 1 : 0);
        System.out.println("Current game state");
        System.out.println(gameboard);
        System.out.printf("%s's turn%n", identifyPlayer(player));
    }

    public boolean isRunning() {
        return isRunning;
    }

    public Player getWinner() {
        return winner;
    }

    public String getGameboard() {
        return gameboard.toString();
    }
}
