//TicTacToe Game

import java.util.Scanner;

public class Main {
    private char[][] board;
    private char currentPlayer;
    private Scanner scanner;
    private GameStats stats;

    // Game statistics tracking
    private static class GameStats {
        int playerXWins = 0;
        int playerOWins = 0;
        int draws = 0;

        void displayStats() {
            System.out.println("\n=== GAME STATISTICS ===");
            System.out.println("Player X Wins: " + playerXWins);
            System.out.println("Player O Wins: " + playerOWins);
            System.out.println("Draws: " + draws);
            System.out.println("========================");
        }
    }

    public Main() {
        board = new char[3][3];
        currentPlayer = 'X';
        scanner = new Scanner(System.in);
        stats = new GameStats();
        initializeBoard();
    }

    /**
     * Initialize the game board with empty spaces
     */
    private void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
    }

    /**
     * Display the current game board with grid numbers for easy reference
     */
    private void displayBoard() {
        System.out.println("\n   |   |   ");
        System.out.println(" " + board[0][0] + " | " + board[0][1] + " | " + board[0][2]);
        System.out.println("___|___|___");
        System.out.println("   |   |   ");
        System.out.println(" " + board[1][0] + " | " + board[1][1] + " | " + board[1][2]);
        System.out.println("___|___|___");
        System.out.println("   |   |   ");
        System.out.println(" " + board[2][0] + " | " + board[2][1] + " | " + board[2][2]);
        System.out.println("   |   |   ");

        // Display position guide
        System.out.println("\nPosition Guide:");
        System.out.println(" 1 | 2 | 3 ");
        System.out.println("___|___|___");
        System.out.println(" 4 | 5 | 6 ");
        System.out.println("___|___|___");
        System.out.println(" 7 | 8 | 9 ");
    }

    /**
     * Check if the current player has won the game
     * @return true if current player has won, false otherwise
     */
    private boolean checkWin() {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == currentPlayer &&
                    board[i][1] == currentPlayer &&
                    board[i][2] == currentPlayer) {
                return true;
            }
        }

        // Check columns
        for (int j = 0; j < 3; j++) {
            if (board[0][j] == currentPlayer &&
                    board[1][j] == currentPlayer &&
                    board[2][j] == currentPlayer) {
                return true;
            }
        }

        // Check diagonals
        if (board[0][0] == currentPlayer &&
                board[1][1] == currentPlayer &&
                board[2][2] == currentPlayer) {
            return true;
        }

        if (board[0][2] == currentPlayer &&
                board[1][1] == currentPlayer &&
                board[2][0] == currentPlayer) {
            return true;
        }

        return false;
    }

    /**
     * Check if the game board is full (draw condition)
     * @return true if board is full, false otherwise
     */
    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Get player input for their move
     * @return true if move was valid and placed, false otherwise
     */
    private boolean makeMove() {
        System.out.print("\nPlayer " + currentPlayer + ", enter your move (1-9): ");

        try {
            int position = scanner.nextInt();

            if (position < 1 || position > 9) {
                System.out.println("Invalid position! Please enter a number between 1-9.");
                return false;
            }

            // Convert position to row and column
            int row = (position - 1) / 3;
            int col = (position - 1) % 3;

            if (board[row][col] != ' ') {
                System.out.println("That position is already taken! Choose another.");
                return false;
            }

            board[row][col] = currentPlayer;
            return true;

        } catch (Exception e) {
            System.out.println("Invalid input! Please enter a number.");
            scanner.nextLine(); // Clear the scanner buffer
            return false;
        }
    }

    /**
     * Switch to the other player
     */
    private void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    /**
     * Reset the game board for a new game
     */
    private void resetGame() {
        initializeBoard();
        currentPlayer = 'X';
        System.out.println("\n🎮 Starting a new game!");
    }

    /**
     * Display the game title and instructions
     */
    private void displayWelcome() {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║            TIC TAC TOE GAME          ║");
        System.out.println("║         Classic Two-Player Game      ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.println("\nHow to Play:");
        System.out.println("• Players take turns placing X's and O's");
        System.out.println("• First to get 3 in a row wins!");
        System.out.println("• Use numbers 1-9 to choose your position");
        System.out.println("• Enter 'q' anytime to quit the game\n");
    }

    /**
     * Check if user wants to play another game
     * @return true if user wants to continue, false to quit
     */
    private boolean playAgain() {
        System.out.print("\nWould you like to play again? (y/n): ");
        String response = scanner.next().toLowerCase();
        return response.equals("y") || response.equals("yes");
    }

    /**
     * Main game loop
     */
    public void playGame() {
        displayWelcome();

        do {
            resetGame();

            while (true) {
                displayBoard();
                System.out.println("\nCurrent Player: " + currentPlayer);

                if (makeMove()) {
                    if (checkWin()) {
                        displayBoard();
                        System.out.println("\n🎉 Congratulations! Player " + currentPlayer + " wins! 🎉");

                        // Update statistics
                        if (currentPlayer == 'X') {
                            stats.playerXWins++;
                        } else {
                            stats.playerOWins++;
                        }
                        break;
                    }

                    if (isBoardFull()) {
                        displayBoard();
                        System.out.println("\n🤝 It's a draw! Good game!");
                        stats.draws++;
                        break;
                    }

                    switchPlayer();
                }
            }

            stats.displayStats();

        } while (playAgain());

        System.out.println("\nThanks for playing Tic Tac Toe! Goodbye! 👋");
        scanner.close();
    }

    /**
     * Main method to start the game
     */
    public static void main(String[] args) {
        Main game = new Main();
        game.playGame();
    }
}