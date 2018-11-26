package games.naughtsAndCrosses;

import monteCarlo.Move;

public class NaughtsAndCrossesMove extends Move {

    private int x;
    private int y;
    private int player;

    public NaughtsAndCrossesMove(int x, int y, int player) {
        this.x = x;
        this.y = y;
        this.player = player;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getPlayer() {
        return player;
    }

    @Override
    public String toString() {
        return "x: " + x + "  y: " + y + "  player: " + player;
    }
}
