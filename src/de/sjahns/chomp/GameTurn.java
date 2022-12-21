package de.sjahns.chomp;

/**
 * A turn in Chomp is to choose a coordinate on the chocolate bar (a two-dimensional vector). Everything left and
 * below that (including the coordinate) is eaten up.
 */
public class GameTurn extends Vector2D {

    /**
     * value 1 means A is winning, 0 means B is winning
     */
    private int value;

    public GameTurn(int x, int y) {
        super(x, y);
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
