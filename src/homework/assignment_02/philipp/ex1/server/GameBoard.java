package homework.assignment_02.philipp.ex1.server;

import java.util.Arrays;
import java.util.Objects;

public class GameBoard {

    private static final int[][] WIN_PATTERNS = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // Rows
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // Columns
            {0, 4, 8}, {2, 4, 6}             // Diagonals
    };

    private static final String BOARD_TEMPLATE =
            """
                      | %s | %s | %s |
                      | %s | %s | %s |
                      | %s | %s | %s |
                    """;

    private static final String EMPTY_TILE_TEMPLATE = "\u001B[47m %s \u001B[0m";

    private final Action[] GAMEBOARD = new Action[9];

    /**
     * @param index  The index of the tile, 0 to 8 from upper left to lower right
     * @param action The action which should be placed
     * @return True if this was the winning turn and false if not
     */
    public synchronized void fillTile(int index, Action action) {
        if (index < 0 || index >= GAMEBOARD.length) {
            throw new IllegalArgumentException("Tile out of bounds");
        }

        if (GAMEBOARD[index] != null) {
            throw new IllegalArgumentException("Tile already occupied");
        }

        GAMEBOARD[index] = action;
    }

    public boolean checkWin() {
        return Arrays.stream(WIN_PATTERNS).anyMatch(pattern ->
                GAMEBOARD[pattern[0]] != null
                        && GAMEBOARD[pattern[0]] == GAMEBOARD[pattern[1]]
                        && GAMEBOARD[pattern[1]] == GAMEBOARD[pattern[2]]
        );
    }

    public boolean isFull() {
        return Arrays.stream(GAMEBOARD).allMatch(Objects::nonNull);
    }

    @Override
    public String toString() {

        Object[] tmp = new String[GAMEBOARD.length];

        for (int i = 0; i < GAMEBOARD.length; i++) {
            var action = GAMEBOARD[i];
            tmp[i] = action != null
                    ? action.toString()
                    : EMPTY_TILE_TEMPLATE.formatted(i);
        }

        return BOARD_TEMPLATE.formatted(tmp);
    }
}
