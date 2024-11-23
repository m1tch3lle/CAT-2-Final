import java.util.Scanner;

public class Ayoayo {

    private static final int PITS = 6; // Number of pits per player
    private static final int INITIAL_STONES = 4; // Initial stones in each pit
    private int[] player1Pits = new int[PITS];
    private int[] player2Pits = new int[PITS];
    private int player1Store = 0;
    private int player2Store = 0;
    private boolean player1Turn = true;

    public Ayoayo() {
        // Initialize the board
        for (int i = 0; i < PITS; i++) {
            player1Pits[i] = INITIAL_STONES;
            player2Pits[i] = INITIAL_STONES;
        }
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Ayoayo!");
        while (!isGameOver()) {
            displayBoard();
            System.out.println("Player " + (player1Turn ? "1" : "2") + ", choose a pit (1-" + PITS + "):");
            int pit = scanner.nextInt() - 1;

            if (isValidMove(pit)) {
                makeMove(pit);
            } else {
                System.out.println("Invalid move. Try again.");
            }
        }
        displayBoard();
        determineWinner();
        scanner.close();
    }

    private void displayBoard() {
        System.out.println("\nCurrent Board:");
        System.out.print("Player 2 Store: " + player2Store + " | ");
        for (int i = PITS - 1; i >= 0; i--) {
            System.out.print(player2Pits[i] + " ");
        }
        System.out.println();
        System.out.print("Player 1 Store: " + player1Store + " | ");
        for (int i = 0; i < PITS; i++) {
            System.out.print(player1Pits[i] + " ");
        }
        System.out.println("\n");
    }

    private boolean isValidMove(int pit) {
        if (pit < 0 || pit >= PITS) {
            return false;
        }
        if (player1Turn && player1Pits[pit] == 0) {
            return false;
        }
        if (!player1Turn && player2Pits[pit] == 0) {
            return false;
        }
        return true;
    }

    private void makeMove(int pit) {
        int[] currentPits = player1Turn ? player1Pits : player2Pits;
        int[] opponentPits = player1Turn ? player2Pits : player1Pits;
        int currentStore = player1Turn ? player1Store : player2Store;

        int stones = currentPits[pit];
        currentPits[pit] = 0;

        int position = pit;
        while (stones > 0) {
            position++;
            if (position == PITS) {
                if (player1Turn) {
                    player1Store++;
                    if (--stones == 0) {
                        System.out.println("Player 1 gets another turn!");
                        return;
                    }
                } else {
                    player2Store++;
                    if (--stones == 0) {
                        System.out.println("Player 2 gets another turn!");
                        return;
                    }
                }
                position = -1; // Reset position
                currentPits = opponentPits;
                opponentPits = player1Turn ? player1Pits : player2Pits;
            } else {
                currentPits[position]++;
                stones--;
            }
        }

        player1Turn = !player1Turn; // Switch turns
    }

    private boolean isGameOver() {
        boolean player1Empty = true, player2Empty = true;

        for (int i = 0; i < PITS; i++) {
            if (player1Pits[i] > 0) player1Empty = false;
            if (player2Pits[i] > 0) player2Empty = false;
        }

        if (player1Empty || player2Empty) {
            for (int i = 0; i < PITS; i++) {
                player1Store += player1Pits[i];
                player2Store += player2Pits[i];
                player1Pits[i] = 0;
                player2Pits[i] = 0;
            }
            return true;
        }

        return false;
    }

    private void determineWinner() {
        System.out.println("Game Over!");
        System.out.println("Player 1 Store: " + player1Store);
        System.out.println("Player 2 Store: " + player2Store);

        if (player1Store > player2Store) {
            System.out.println("Player 1 wins!");
        } else if (player2Store > player1Store) {
            System.out.println("Player 2 wins!");
        } else {
            System.out.println("It's a tie!");
        }
    }

    public static void main(String[] args) {
        Ayoayo game = new Ayoayo();
        game.play();
    }
}
