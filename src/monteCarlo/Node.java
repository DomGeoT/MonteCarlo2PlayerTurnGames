package monteCarlo;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private static final float EXPLORATION_CONSTANT = 5;

    private GameState nodeGameState;
    private Move moveToEnterState;

    private Node parent;
    private List<Node> children;

    private int wins;
    private int visits;
    private List<Move> untriedMoves;


    public Node(GameState nodeGameState) {
        this.wins = 0;
        this.visits = 0;
        this.nodeGameState = nodeGameState;
        this.untriedMoves = nodeGameState.getMoves();
        this.children = new ArrayList<>();

        this.update(getNodeGameState().getResult());
    }

    public Node(Node parent, GameState nodeGameState, Move moveToEnterState) {
        this(nodeGameState);

        this.parent = parent;
        this.moveToEnterState = moveToEnterState;
    }

    public boolean isFullyExpanded() {
        return untriedMoves.isEmpty();
    }

    public void addChild(int moveIndex) {
        GameState childState = nodeGameState.clone();

        try {
            childState.doMove(untriedMoves.get(moveIndex));
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        }

        Node child = new Node(this, childState, untriedMoves.get(moveIndex));
        children.add(child);
        untriedMoves.remove(moveIndex);
    }

    public void update(GameResult result) {
        switch (result) {
            case PLAYER_1_WIN:
                if (nodeGameState.getPlayers()[nodeGameState.getCurrentTurnPointer()] == 2) this.wins++;
                break;
            case PLAYER_2_WIN:
                if (nodeGameState.getPlayers()[nodeGameState.getCurrentTurnPointer()] == 1) this.wins++;
                break;
        }
        this.visits++;
    }

    public int getWins() {
        return wins;
    }

    public int getVisits() {
        return visits;
    }

    public List<Node> getChildren() {
        return children;
    }

    public List<Move> getUntriedMoves() {
        return untriedMoves;
    }

    public float calcUCB1() {
        if (parent == null) return 1;
        return (float)(((float)getWins()/(float)getVisits()) + EXPLORATION_CONSTANT * Math.sqrt(Math.log(parent.getVisits()) / (float)getVisits()));
    }

    public GameState getNodeGameState() {
        return nodeGameState;
    }

    public Node getParent() {
        return parent;
    }

    public Move getMoveToEnterState() {
        return moveToEnterState;
    }


    @Override
    public String toString() {
        String output = "";

        output += "THIS wins: " + wins + "  visits: " + visits;
        for (Node n : children) {
            output += "\n    " + n.toString();
        }


        return output;
    }
}
