package games.naughtsAndCrosses;

import monteCarlo.Game;
import monteCarlo.GameState;
import monteCarlo.Move;

import java.util.Scanner;

public class NaughtsAndCrossesGame extends Game {
    public NaughtsAndCrossesGame(int monteCarloIterations, int numGames, boolean humanPlayer, boolean visualise) {
        super(new NaughtsAndCrossesGameState(), monteCarloIterations, numGames, humanPlayer, visualise);
    }

    @Override
    public Move randomMove(GameState clonedState)  {
        return clonedState.getMoves().get(random.nextInt(clonedState.getMoves().size()));
    }

    @Override
    public Move humanMove() {
        Scanner scanner = new Scanner(System.in);
        return  new NaughtsAndCrossesMove(Integer.parseInt(scanner.next()), Integer.parseInt(scanner.next()), 2);
    }

    public static void main(String[] args) {
        for (int i = 1; i < 100; i++) {
            new NaughtsAndCrossesGame(i, 1000, false, false);
        }
    }
}
