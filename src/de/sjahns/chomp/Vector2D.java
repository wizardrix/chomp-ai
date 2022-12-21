package de.sjahns.chomp;

/**
 * Two-dimensional vector
 */
public class Vector2D {

    private final int x;
    private final int y;

    public Vector2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Vector2D other)) {
            return false;
        }
        return this.x == other.getX() && this.y == other.getY();
    }

    @Override
    public int hashCode() {
        return this.x * 7 + this.y * 5;
    }

    @Override
    public String toString() {
        return "[" + this.x + ", " + this.y + "]";
    }
}
