package de.sjahns.chomp;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The actual game of Chomp (see for example <a href="https://en.wikipedia.org/wiki/Chomp">here</a>).
 * The user always plays first, followed by the {@link AIPlayer}. As soon as there is no more chocolate, the game
 * ends and the player who took the last piece loses.
 * The width and height of the starting chocolate bar is restricted to 16 considering that the complexity for the AI
 * is exponential in these dimensions.
 */
public class Game {

    private static final int MINIMUM_NUMBER = 1;
    private static final int MAXIMUM_NUMBER = 16;

    private final Scanner scanner = new Scanner(System.in);
    ChocolateBar cb;
    AIPlayer aiPlayer = new AIPlayer();

    private int getNumberWithMinAndMax(String msg) {
        int number;
        while (true) {
            System.out.print(msg);
            try {
                number = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Not a number");
                scanner.nextLine();
                continue;
            }
            if (number < MINIMUM_NUMBER) {
                System.out.println("Too small");
                continue;
            }
            if (number > MAXIMUM_NUMBER) {
                System.out.println("Too large");
                continue;
            }
            return number;
        }
    }

    private int getWidth() {
        return getNumberWithMinAndMax("width: ");
    }

    private int getHeight() {
        return getNumberWithMinAndMax("height: ");
    }

    private boolean processPlayersTurn() {
        System.out.println("Your turn. At which position do you want to chomp?");
        int x = getNumberWithMinAndMax("x: ");
        int y = getNumberWithMinAndMax("y: ");
        return cb.chomp(new GameTurn(x, y));
    }

    private void processAiTurn() {
        System.out.println("AI's turn");
        GameTurn aiTurn = aiPlayer.minimax(cb, 0, true);
        System.out.println("AI chomps at " + aiTurn);
        cb.chomp(aiTurn);
    }

    private void playGame(int width, int height) {
        cb = new ChocolateBar(width, height);
        boolean playersTurn = true;
        while (!cb.getVectors().isEmpty()) {
            cb.draw();
            if (playersTurn) {
                if (!processPlayersTurn()) {
                    System.out.println("Invalid turn");
                    continue;
                }
                playersTurn = false;
            } else {
                processAiTurn();
                playersTurn = true;
            }
        }
        System.out.println("Game over. " + (playersTurn ? "You" : "AI") + " won.");
    }

    public void start() {
        System.out.println("Choose board size.");
        int width = getWidth();
        int height = getHeight();
        playGame(width, height);
    }
}
