package de.sjahns.chomp;

import java.util.HashMap;
import java.util.Map;

/**
 * Class that utilizes the minimax algorithm to find the best possible turn in a chomp game
 */
public class AIPlayer {

    /**
     * If an optimal turn is found for a {@link ChocolateBar}, it is stored in the cache
     */
    private final Map<ChocolateBar, GameTurn> optimalTurnCache = new HashMap<>();

    /**
     * Implements the Minimax algorithm to search an optimal turn for the given chocolateBar. This is a recursive
     * function, the depth parameter is currently only used for debugging and choosing a move, if no winning move
     * exists.
     *
     * @param chocolateBar     the {@link ChocolateBar} for which a winning move is searched
     * @param depth            used for recursion internally, should always be set to 0
     * @param maximizingPlayer whether the winning move for the current player is searched. When false, the turn is
     *                         optimized for the minimizing player. Should always be set to true
     * @return the winning {@link GameTurn} together with value 1 if it is a winning move for the maximizing player
     * or a 0 otherwise
     */
    public GameTurn minimax(ChocolateBar chocolateBar, int depth, boolean maximizingPlayer) {
        System.out.println("Cache size: " + this.optimalTurnCache.size());
        if (chocolateBar.isEmpty()) {
            GameTurn nullTurn = new GameTurn(0, 0);
            nullTurn.setValue(maximizingPlayer ? 1 : 0);
            return nullTurn;
        }

        int val = maximizingPlayer ? 0 : 1;
        if (optimalTurnCache.containsKey(chocolateBar)) {
            GameTurn optimalTurn = optimalTurnCache.get(chocolateBar);
            // value depends on which player is searching at this depth
            optimalTurn.setValue(1 - val);
            return optimalTurn;
        }
        // apply each turn to the chocolate bar and find an optimal turn for that
        for (GameTurn turn : chocolateBar) {
            ChocolateBar cbCopy = new ChocolateBar(chocolateBar.getVectors().toArray(new ChocolateVector[0]));
            cbCopy.chomp(turn);
            GameTurn optimalTurn = minimax(cbCopy, depth + 1, !maximizingPlayer);
            val = optimalTurn.getValue();
            if (maximizingPlayer && val == 1 || !maximizingPlayer && val == 0) {
                turn.setValue(val);
                optimalTurnCache.put(chocolateBar, turn);
                return turn;
            }
        }

        // no optimal turn
        // if it's depth 0, take the one that leaves the most options (which is the last one for this iterator),
        // otherwise take the first one
        if (depth == 0) {
            GameTurn notTheWorstTurn = null;
            for (GameTurn gameTurn : chocolateBar) {
                notTheWorstTurn = gameTurn;
            }
            return notTheWorstTurn;
        }
        GameTurn firstTurn = chocolateBar.iterator().next();
        firstTurn.setValue(val);
        return firstTurn;
    }
}
