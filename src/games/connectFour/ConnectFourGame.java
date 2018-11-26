package games.connectFour;

import monteCarlo.Game;
import monteCarlo.GameState;
import monteCarlo.Move;

import java.util.Scanner;

public class ConnectFourGame extends Game{
    public ConnectFourGame(int monteCarloIterations, int numGames, boolean humanPlayer, boolean visualise) {
        super(new ConnectFourGameState(8, 8), monteCarloIterations, numGames, humanPlayer, visualise);
    }

    @Override
    public Move randomMove(GameState clonedState)  {
        return clonedState.getMoves().get(random.nextInt(clonedState.getMoves().size()));
    }

    @Override
    public Move humanMove() {
        Scanner scanner = new Scanner(System.in);
        return new ConnectFourMove(Integer.parseInt(scanner.next()), 2);
    }

    public static void main(String[] args) {
        new ConnectFourGame(2, 10000, false, false);
    }
}
