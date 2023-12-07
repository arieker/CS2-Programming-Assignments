import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private int[][] board;
    private char[] computerMoves;

    public Game(int boardSize, String movesFile) throws IOException {
        this.board = new int[boardSize][boardSize];
        this.computerMoves = new char[boardSize * boardSize];

        // Initialize the board
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                board[i][j] = 0;
            }
        }
        
        try {
            readMoves(movesFile);
            System.out.println("Worked");
        } catch (IOException e) {
            System.out.println(e);
        }

        for (int i = 0;i < 14; i++)
        {
            System.out.println(computerMoves[i]);
        }
    }  

    public void readMoves(String movesFile) throws IOException {
        // Initialize a StringBuilder to store the characters read from the file.
        StringBuilder stringBuilder = new StringBuilder();
        
        // Create a Scanner to read the file.
        try (Scanner scanner = new Scanner(new File(movesFile))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                
                // Append each character from the line to the StringBuilder.
                for (char c : line.toCharArray()) {
                    stringBuilder.append(c);
                }
            }
        }
        
        // Convert the StringBuilder to a char array.
        computerMoves = stringBuilder.toString().toCharArray();
    }

    public int play(int winner) {
        int curPlayer = 1;
        int movesLeft = 14;
        int x = 0, y = 0, i = 0;

        if (winner == 1) // Player 1 wants to win
        {
            
            while (movesLeft != 0) {
                if (curPlayer == 1) {
                    if (movesLeft % 2 == 0) { // Even number, keep even by moving diagonal
                        if (x == 7) {
                            y++;
                            movesLeft--;
                        }
                        else if (y == 7) {
                            x++;
                            movesLeft--;
                        }
                        else {
                            x++;
                            y++;
                            movesLeft -= 2;
                        }
                        
                    }
                    else { // Odd number, change to even by moving down or right
                        if (x == 7) { 
                            y++;
                            movesLeft--;
                        }
                        else {
                            x++;
                            movesLeft--;
                        }
                    }
                    curPlayer = 2;
                }
                else if (curPlayer == 2) { // Move with text file
                    if (x == 7 || computerMoves[i] == 'b') { // Move down
                        y++;
                        movesLeft--;
                    }
                    else if (y == 7 || computerMoves[i] == 'r') { // Move right
                        x++;
                        movesLeft--;
                    }
                    else if (computerMoves[i] == 'd') { // Diagonal
                        x++;
                        y++;
                        movesLeft -= 2;
                    }
                    curPlayer = 1;
                }
            }
        }
        else if (winner == 2) { // Player 2 wants to win
            Random random = new Random();
            int randomNumber = random.nextInt(3);
            if (curPlayer == 1) {
                if (movesLeft == 14) { // Make player 1 throw by not moving diagonal
                    x++;
                    movesLeft--;
                }
                else {
                    randomNumber = random.nextInt(3); // Generate random num
                    if (randomNumber == 0 && x != 7 && y != 7) { // Diagonal
                        x++;
                        y++;
                        movesLeft -= 2;
                    }
                    else if (randomNumber == 1 || x == 7) { // Down
                        y++;
                        movesLeft--;
                    }
                    else if (randomNumber == 2 || y == 7) { // Right
                        x++;
                        movesLeft--;
                    }
                }
                curPlayer = 2;
            }
            if (curPlayer == 2) { // Player 2 just copies player 1 after first turn, it moves to 1,1 on first turn though
                if (movesLeft == 13) {
                    y++;
                    movesLeft--;
                }
                else {
                    if (randomNumber == 0 && x != 7 && y != 7) { // Diagonal
                        x++;
                        y++;
                        movesLeft -= 2;
                    }
                    else if (randomNumber == 1 || x == 7) { // Down
                        y++;
                        movesLeft--;
                    }
                    else if (randomNumber == 2 || y == 7) { // Right
                        x++;
                        movesLeft--;
                    }
                }
                curPlayer = 1;
            }
        }
        if (curPlayer == 1) { // Turn passed to player 1 after win
            return 2; // Therefore player 2 wins
        }
        else {
            return 1; // Otherwise, player 1 wins
        }
    }
}
