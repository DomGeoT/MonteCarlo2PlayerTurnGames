package games.connectFour;

import monteCarlo.GameResult;
import monteCarlo.GameState;
import monteCarlo.InvalidMoveException;
import monteCarlo.Move;

import java.util.ArrayList;
import java.util.List;

public class ConnectFourGameState extends GameState {
    private int[][] board;
    private int width, height;

    public ConnectFourGameState(int[][] board, int currentPlayerPointer, int width, int height) {
        super(new int[]{1,2}, currentPlayerPointer);

        this.width = width;
        this.height = height;
        this.board = board;
    }

    public ConnectFourGameState(int width, int height) {
        super(new int[]{1, 2}, 0);

        this.width = width;
        this.height = height;
        this.board = new int[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
                board[i][j] = 0;
            }
        }

    }

    @Override
    public GameState clone() {
        int[][] newBoard = new int[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
                newBoard[i][j] = board[i][j];
            }
        }
        return new ConnectFourGameState(newBoard, getCurrentTurnPointer(), width, height);
    }

    @Override
    public List<Move> getMoves() {
        List<Move> moves = new ArrayList<>();

        for(int i = 0; i < height; i++) {
            if (board[0][i] == 0) {
                moves.add(new ConnectFourMove(i, getPlayers()[getCurrentTurnPointer()]));
            }
        }
        return moves;
    }

    @Override
    public GameResult getResult() {
        for (int i = 0; i < width - 4; i++) {
            for (int j = 0; j < height; j++) {
                if (board[i][j] == 1 && board[i + 1][j] == 1&& board[i + 2][j] == 1 && board[i + 3][j] == 1) {
                    return GameResult.PLAYER_1_WIN;
                }
                if (board[i][j] == 2 && board[i + 1][j] == 2&& board[i + 2][j] == 2 && board[i + 3][j] == 2) {
                    return GameResult.PLAYER_2_WIN;
                }
            }
        }


        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height - 4; j++) {
                if (board[i][j] == 1 && board[i][j + 1] == 1&& board[i][j + 2] == 1 && board[i][j + 3] == 1) {
                    return GameResult.PLAYER_1_WIN;
                }
                if (board[i][j] == 2 && board[i][j + 1] == 2&& board[i][j + 2] == 2 && board[i][j + 3] == 2) {
                    return GameResult.PLAYER_2_WIN;
                }
            }
        }

        boolean flag = true;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (board[i][j] == 0) flag = false;
            }
        }

        if (flag) return GameResult.DRAW;
        return GameResult.NOT_TERMINATED;
    }

    @Override
    public boolean doMove(Move move) throws InvalidMoveException {
        ConnectFourMove connectFourMove = (ConnectFourMove)move;
        int row = 0;

        while(row < width && board[row][connectFourMove.getColumn()] == 0) {
            row++;
        }
        if (row == 0) throw new InvalidMoveException(move);

        board[row - 1][connectFourMove.getColumn()] = connectFourMove.getPlayer();

        nextPlayerTurn();
        return isGameTerminated();
    }

    private String boardSpaceToPiece(int boardSpace) {
        if (boardSpace == 0) return " ";
        if (boardSpace == 1) return "0";
        if (boardSpace == 2) return "X";
        return "BAD PIECE";
    }


    @Override
    public void visualiseGame() {
        System.out.println();
        for (int i = 0; i < width; i++) {
            String output = "| ";
            for (int j = 0; j < height; j++) {
                output += boardSpaceToPiece(board[i][j]) + " | ";
            }
            System.out.println(output);
        }

        String bottom = "+-";
        for(int j = 0; j < height; j++) {
            bottom += j + "-+-";
        }

        System.out.println(bottom);
        System.out.println();
    }
}
