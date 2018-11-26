package monteCarlo;

import java.util.Random;

public abstract class Game {
    public Random random = new Random();

    public Game(GameState ogGameState, int monteCarloIterations, int numGames, boolean humanPlayer, boolean visualise) {
        int p0Wins = 0;
        int p1Wins = 0;
        int draws = 0;

        for (int i = 0; i < numGames; i++) {
            GameState gameState = ogGameState.clone();
            while (!gameState.isGameTerminated()) {
                Move nextMove;

                if (gameState.getCurrentTurnPointer() == 0) {
                    nextMove = monteCarloGetNextMove(monteCarloIterations, gameState.clone());
                } else {
                    nextMove = humanPlayer ? humanMove() :randomMove(gameState.clone());
                }

                try {
                    gameState.doMove(nextMove);
                } catch (InvalidMoveException e) {
                    e.printStackTrace();
                }
                if (visualise) gameState.visualiseGame();

            }

            switch (gameState.getResult()) {

                case NOT_TERMINATED:
                    break;
                case DRAW:
                    draws++;
                    break;
                case PLAYER_1_WIN:
                    p0Wins++;
                    break;
                case PLAYER_2_WIN:
                    p1Wins++;
                    break;
            }
        }

        System.out.println(monteCarloIterations + ", " + p0Wins + ", " + p1Wins + ", " + draws);
    }

    public abstract Move randomMove(GameState clonedState);

    public abstract Move humanMove();

    private Move monteCarloGetNextMove(int iterations, GameState clonedState) {
        Node root = new Node(clonedState);

        for (int i = 0; i < iterations; i++) {
            Node node = root;
            node = selectionPhase(node);
            node = expansionPhase(node);
            rolloutPhase(node);

        }
        return selectMove(root);
    }

    private Node selectionPhase(Node node) {
        if (!node.isFullyExpanded() || node.getNodeGameState().isGameTerminated()) {
            return node;
        }

        Node bestChild = null;
        float highestUBC1 = -999999999;

        for (Node child : node.getChildren()) {
            if (node.calcUCB1() > highestUBC1) {
                bestChild = child;
                highestUBC1 = node.calcUCB1();
            }
        }
        return selectionPhase(bestChild);
    }

    private Node expansionPhase(Node node) {
        if (node.getUntriedMoves().size() > 0) {
            node.addChild(random.nextInt(node.getUntriedMoves().size()));
            return node.getChildren().get(node.getChildren().size() - 1);
        }
        return node;
    }

    private void rolloutPhase(Node node) {
        GameState rolloutState = node.getNodeGameState().clone();
        while (!rolloutState.isGameTerminated()) {
            try {
                rolloutState.doMove(rolloutState.getMoves().get(random.nextInt(rolloutState.getMoves().size())));
            } catch (InvalidMoveException e) {
                e.printStackTrace();
            }
        }
        backPropagationPhase(node, rolloutState.getResult());
    }

    private void backPropagationPhase(Node node, GameResult result) {
        node.update(result);
        if (node.getParent() != null) backPropagationPhase(node.getParent(), result);
    }

    private Move selectMove(Node node) {
        Node bestNode = null;
        float highestWinRate = -999999999;
        for (Node child : node.getChildren()) {

            float winRate = (float) child.getWins() / (float)child.getVisits();
            if (winRate > highestWinRate) {
                bestNode = child;
                highestWinRate = child.getWins() / child.getVisits();
            }
        }

        return bestNode.getMoveToEnterState();
    }
}
