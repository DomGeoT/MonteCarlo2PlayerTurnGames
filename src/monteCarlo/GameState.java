package monteCarlo;

import java.util.List;

public abstract class GameState {

    private int[] players;
    private int currentTurnPointer;
    private GameResult gameResult;

    public GameState(int[] players, int startingPlayer) {
        this.players = players;
        this.currentTurnPointer = startingPlayer;
    }

    public int[] getPlayers() {
        return players;
    }

    public int getCurrentTurnPointer() {
        return currentTurnPointer;
    }

    /**
     *
     * @return a deep copy of this game's gameState
     */
    public abstract GameState clone();

    /**
     *
     * @return array of all valid moves the current player could take
     */
    public abstract List<Move> getMoves();

    /**
     *
     * @return game result
     */
    public abstract GameResult getResult();

    /**
     *
     * @return true if end of game reached; else false
     */
    public boolean isGameTerminated() {
        GameResult result = getResult();
        switch (result) {
            case NOT_TERMINATED:
                return false;
            case DRAW:
            case PLAYER_1_WIN:
            case PLAYER_2_WIN:
                return true;
        }
        return true;
    }

    /**
     * executes the provided move on this game's gameState
     *
     * @param move the move to perform
     * @return true if the end of a game has been reached; else false
     */
    public abstract boolean doMove(Move move) throws InvalidMoveException;


    public abstract void visualiseGame();

    public void nextPlayerTurn() {
        currentTurnPointer = (currentTurnPointer + 1) % players.length;
    }

}
