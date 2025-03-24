package Homework.Exercise_2.TicTacToe.Server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TicTacToeBoard {
    private final String board =
            """
                       a     b     c
                          |     |     
                    1  %c  |  %c  |  %c  
                     _____|_____|_____
                          |     |     
                    2  %c  |  %c  |  %c  
                     _____|_____|_____
                          |     |     
                    3  %c  |  %c  |  %c  
                          |     |     """;

    private final Character[] playedMoves = new Character[]{'-', '-', '-', '-', '-', '-', '-', '-', '-'};

    private final List<String[]> moveList = new ArrayList<>(9);

    public TicTacToeBoard() {}

    @Override
    public String toString() {
        return String.format(board, (Character[]) playedMoves);
    }

    public boolean playMove(char column, int row, char character){
        int offset = column-'a'; // a - a = 0 -> first column ; b - a = 1 -> second column ...
        int pos = offset + 3*(row-1);
        if (pos < 0 || pos > playedMoves.length || playedMoves[pos] != '-') {return false;} // Field already occupied

        assert (moveList.isEmpty() || !moveList.getLast()[1].equals(String.valueOf(character))):
                "You already played the last move, No cheating allowed";

        playedMoves[pos] = character; // finalize move
        moveList.add(new String[]{String.format("%c%d", column,row), String.valueOf(character)}); // add to history
        return true; // return success
    }

    /**
     * @return last move if (pos, player) one has been played otherwise null
     */
    public String[] getLastMove(){
        return (moveList.isEmpty()) ? null : moveList.getLast();
    }
    /**
     * Evaluates whether someone has won
     * @return the character of the winning player if someone has won.
     *          '-' if it's a draw and
     *          'null' if the game isn't over yet
     */
    public Character evaluate(){
        // Draw
        if (Arrays.stream(playedMoves).noneMatch(character -> character.equals('-'))){
            return '-';
        }

        // row + column
        for(int r = 0, c = 0; r < 3; c++, r += 3){
            if (!playedMoves[r].equals('-')
                    && (playedMoves[r].equals(playedMoves[r+1]) && playedMoves[r].equals(playedMoves[r+2]))){
                return playedMoves[r];
            }

            if (!playedMoves[c].equals('-')
                    && (playedMoves[c].equals(playedMoves[c+3]) && playedMoves[c].equals(playedMoves[c+6]))){
                return playedMoves[c];
            }
        }

        // diagonals
        if (!playedMoves[4].equals('-') && (
                   (playedMoves[0].equals(playedMoves[4]) && playedMoves[4].equals(playedMoves[8]))
                || (playedMoves[2].equals(playedMoves[4]) && playedMoves[4].equals(playedMoves[6]))
            )
        ){
            return playedMoves[4];
        }

        return null;
    }
}
