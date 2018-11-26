package games.naughtsAndCrosses;

import monteCarlo.GameResult;
import monteCarlo.GameState;
import monteCarlo.InvalidMoveException;
import monteCarlo.Move;

import java.util.ArrayList;
import java.util.List;

public class NaughtsAndCrossesGameState extends GameState {

    private int[][] board;

    /* 0 = not played yet
     * 1 = player 1 played there
     * 2 = player 2 played there
     */

    public NaughtsAndCrossesGameState(int[][] board, int currentPlayerPointer) {
        super(new int[]{1,2}, currentPlayerPointer);

        this.board = board;
    }

    public NaughtsAndCrossesGameState() {
        super(new int[]{1,2}, 0);

        this.board = new int[][]{   {0,0,0},
                                    {0,0,0},
                                    {0,0,0}};
    }

    @Override
    public GameState clone() {
        int[][] newBoard = new int[][]{ {board[0][0], board[0][1], board[0][2]},
                                        {board[1][0], board[1][1], board[1][2]},
                                        {board[2][0], board[2][1], board[2][2]}};
        return new NaughtsAndCrossesGameState(newBoard, this.getCurrentTurnPointer());
    }

    @Override
    public List<Move> getMoves() {
        List<Move> moves = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0) moves.add(new NaughtsAndCrossesMove(i, j, getPlayers()[getCurrentTurnPointer()]));
            }
        }
        return moves;
    }

    private boolean checkColumnWin(int colNum, int player) {
        if (board[0][colNum] == board[1][colNum] && board[1][colNum] == board[2][colNum] && board[0][colNum] == player) {
            return true;
        }
        return false;
    }

    private boolean checkRowWin(int rowNum, int player) {
        if (board[rowNum][0] == board[rowNum][1] && board[rowNum][1] == board[rowNum][2] && board[rowNum][0] == player) {
            return true;
        }
        return false;
    }

    @Override
    public GameResult getResult() {

        for (int i = 0; i < 3; i++) {
            if (checkRowWin(i, 1)) {
                return GameResult.PLAYER_1_WIN;
            }else if (checkColumnWin(i, 1)) {
                return GameResult.PLAYER_1_WIN;
            }else if (checkRowWin(i, 2)) {
                return GameResult.PLAYER_2_WIN;
            }else if (checkColumnWin(i, 2)) {
                return GameResult.PLAYER_2_WIN;
            }
        }

        if ((board[0][0] == 1 && board[1][1] == 1 && board[2][2] == 1) ||
            (board[0][2] == 1 && board[1][1] == 1 && board[2][0] == 1)) {
            return GameResult.PLAYER_1_WIN;
        }

        if ((board[0][0] == 2 && board[1][1] == 2 && board[2][2] == 2) ||
            (board[0][2] == 2 && board[1][1] == 2 && board[2][0] == 2)) {
            return GameResult.PLAYER_2_WIN;
        }


        if (board[0][0] != 0 && board[1][0] != 0 && board[2][0] != 0 &&
                board[0][1] != 0 && board[1][1] != 0 && board[2][1] != 0 &&
                board[0][2] != 0 && board[1][2] != 0 && board[2][2] != 0) {
            return GameResult.DRAW;
        }

        return GameResult.NOT_TERMINATED;
    }

    @Override
    public boolean doMove(Move move) throws InvalidMoveException {
        NaughtsAndCrossesMove naughtsAndCrossesMove = (NaughtsAndCrossesMove)move;

        if (board[naughtsAndCrossesMove.getX()][naughtsAndCrossesMove.getY()] == 0) {
            board[naughtsAndCrossesMove.getX()][naughtsAndCrossesMove.getY()] = naughtsAndCrossesMove.getPlayer();
        } else {
            throw new InvalidMoveException(move);
        }

        nextPlayerTurn();

        return isGameTerminated();
    }

    @Override
    public void visualiseGame() {
        System.out.println();
        System.out.println(this.toString());
        System.out.println();
    }

    private String boardSpaceToPiece(int boardSpace) {
        if (boardSpace == 0) return " ";
        if (boardSpace == 1) return "0";
        if (boardSpace == 2) return "X";
        return "BAD PIECE";
    }

    @Override
    public String toString() {
        String output = "";

        output += " " + boardSpaceToPiece(board[0][0]) + " | " + boardSpaceToPiece(board[0][1]) + " | " + boardSpaceToPiece(board[0][2]) + "\n";
        output += "---+---+---\n";
        output += " " + boardSpaceToPiece(board[1][0]) + " | " + boardSpaceToPiece(board[1][1]) + " | " + boardSpaceToPiece(board[1][2]) + "\n";
        output += "---+---+---\n";
        output += " " + boardSpaceToPiece(board[2][0]) + " | " + boardSpaceToPiece(board[2][1]) + " | " + boardSpaceToPiece(board[2][2]) + "\n";
        return output;
    }
}
