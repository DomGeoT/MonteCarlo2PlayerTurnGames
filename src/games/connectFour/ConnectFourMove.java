package games.connectFour;

import monteCarlo.Move;

public class ConnectFourMove extends Move {
    private int player;
    private int column;

    public ConnectFourMove(int column, int player) {
        this.column = column;
        this.player = player;
    }

    public int getColumn() {
        return column;
    }

    public int getPlayer() {
        return player;
    }

    @Override
    public String toString() {
        return "player: " + player + "    column: " + column;
    }
}
