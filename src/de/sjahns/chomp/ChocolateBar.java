package de.sjahns.chomp;

import java.util.*;
import java.util.stream.StreamSupport;

/**
 * The main game board of the game Chomp. The chocolate bar starts as a rectangular finite grid. At each turn a
 * rectangular part is chomped off, such that the remaining bar stays "convex". See
 * <a href="https://en.wikipedia.org/wiki/Chomp">Wikipedia</a>
 */
public class ChocolateBar implements Iterable<GameTurn> {

    /**
     * All (partial) chocolate bars can be represented by vectors in that each of these vectors span a rectangle. The
     * sum of these rectangles make up the chocolate bar.
     */
    private final TreeSet<ChocolateVector> vectors = new TreeSet<>();

    public ChocolateBar(ChocolateVector... vectors) {
        this.vectors.addAll(List.of(vectors));
    }

    public ChocolateBar(int width, int height) {
        this(new ChocolateVector(width, height));
    }

    public Collection<ChocolateVector> getVectors() {
        return this.vectors;
    }

    public boolean isEmpty() {
        return this.vectors.isEmpty();
    }

    public int getWidth() {
        if (this.vectors.size() == 0) {
            return 0;
        }
        return this.vectors.first().getX();
    }

    public void draw() {
        StringBuilder cbsb = new StringBuilder();
        int row = 0;
        for (ChocolateVector cv : this.vectors) {
            for (; row < cv.getY(); row++) {
                cbsb.append(" ".repeat(getWidth() - cv.getX()));
                cbsb.append("X".repeat(cv.getX()));
                cbsb.append("\n");
            }
        }
        System.out.println(cbsb);
    }

    public boolean chomp(GameTurn turn) {
        if (StreamSupport.stream(this.spliterator(), false).noneMatch(curTurn -> curTurn.equals(turn))) {
            return false;
        }
        TreeSet<ChocolateVector> affectedVectors = new TreeSet<>();

        // Add all vectors to the set that span a rectangle where the turn vector is contained. Since the vectors in
        // the chocolate bar are sorted, as soon as the iteration reaches a point where the condition is
        // not true, it is not true for the remaining vectors.
        for (ChocolateVector v : getVectors()) {
            if (turn.getX() <= v.getX() && turn.getY() <= v.getY()) {
                affectedVectors.add(v);
            } else if (!affectedVectors.isEmpty()) {
                break;
            }
        }

        getVectors().removeAll(affectedVectors);
        if (turn.getY() > 1) {
            getVectors().add(new ChocolateVector(affectedVectors.first().getX(), turn.getY() - 1));
        }
        if (turn.getX() > 1) {
            getVectors().add(new ChocolateVector(turn.getX() - 1, affectedVectors.last().getY()));
        }

        return true;
    }

    @Override
    public Iterator<GameTurn> iterator() {
        return new Iterator<>() {
            private int row = 1;
            private int col = 1;
            private final Iterator<ChocolateVector> vectorIterator = getVectors().iterator();
            private ChocolateVector currentVector = vectorIterator.hasNext() ? vectorIterator.next() : null;

            @Override
            public boolean hasNext() {
                if (currentVector == null) {
                    return false;
                }

                return col <= currentVector.getX() || row <= currentVector.getY();
            }

            @Override
            public GameTurn next() {
                GameTurn nextGameTurn = new GameTurn(col, row);

                if (col == currentVector.getX()) {
                    col = 1;
                } else {
                    col++;
                    return nextGameTurn;
                }

                if (row == currentVector.getY()) {
                    row = 1;
                } else {
                    row++;
                    return nextGameTurn;
                }

                if (col == 1) {
                    currentVector = vectorIterator.hasNext() ? vectorIterator.next() : null;
                }

                return nextGameTurn;
            }
        };
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ChocolateBar other)) {
            return false;
        }
        return getVectors().equals(other.getVectors());
    }

    /**
     * Example for the calculation of the hash code:
     * <table>
     *     <tr><td>0</td><td>1</td><td>1</td><td>0</td><td>1</td><td></td></tr>
     *     <tr><td> </td><td>x</td><td>x</td><td>x</td><td>x</td><td>0</td></tr>
     *     <tr><td> </td><td>o</td><td>x</td><td>x</td><td>x</td><td>1</td></tr>
     *     <tr><td> </td><td> </td><td>o</td><td>x</td><td>x</td><td>1</td></tr>
     *     <tr><td> </td><td> </td><td> </td><td> </td><td>x</td><td>0</td></tr>
     *     <tr><td> </td><td> </td><td> </td><td> </td><td>o</td><td>1</td></tr>
     * </table>
     * On every column and every row where a chocolate vector is, a 1 is put, otherwise a 0.
     * The so produced digits are then intertwined (take first digit from the top, first from the right and so on)
     * and form a binary number.<br>
     * So in the example 0110110110 = 438. This is the resulting hash. This works because there can be at most one
     * chocolate vector on every column and every row.<br>
     * For chocolate bars with width <= 16 and height <= 16 this produces a unique hash (as int has a size of 32 bit).
     * <br>
     * TODO: This could even be an efficient replacement for the treemap of chocolate vectors as a representation of
     * the chocolate bar.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        int hash = 0;
        for (ChocolateVector v : getVectors()) {
            hash += (1 << (v.getX() * 2 - 1)) + (1 << (v.getY() * 2 - 2));
        }
        return hash;
    }
}
