package monteCarlo;

public class InvalidMoveException extends Exception {
    public InvalidMoveException(Move move) {
        System.out.println("Could not perform move: " + move.toString());
    }
}
