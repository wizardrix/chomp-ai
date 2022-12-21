package de.sjahns.chomp;

/**
 * This class is a two-dimensional vector that is used to make up a chocolate bar. See there for more details.
 * It implements {@link Comparable} so it can be used in a {@link java.util.TreeSet}. Vectors in a
 * {@link ChocolateBar} are always ordered from highest to lowest x value.
 */
public class ChocolateVector extends Vector2D implements Comparable<ChocolateVector> {

    public ChocolateVector(int x, int y) {
        super(x, y);
    }

    @Override
    public int compareTo(ChocolateVector o) {
        if (o == null) {
            return 1;
        }
        if (equals(o)) {
            return 0;
        }

        return o.getX() - getX();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ChocolateVector other)) {
            return false;
        }
        return getX() >= other.getX() && getY() >= other.getY() || getX() <= other.getX() && getY() <= other.getY();
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
