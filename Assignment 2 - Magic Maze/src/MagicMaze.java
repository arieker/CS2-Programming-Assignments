import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class MagicMaze {
    private char[][] mazeArray;
    private int numRows;
    private int numColumns;
    private HashMap<Character, int[]> teleportationPairs;

    // Constructor
    public MagicMaze(String mazeFileName, int numRows, int numColumns) {
        this.numRows = numRows;
        this.numColumns = numColumns;
        mazeArray = new char[numRows][numColumns];
        this.teleportationPairs = new HashMap<>();
        readMazeFile(mazeFileName);
    }

    // Reads the maze file and stores the maze in mazeArray
    private void readMazeFile(String fileName) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            int row = 0;
            while ((line = br.readLine()) != null) {
                for (int col = 0; col < line.length(); col++) {
                    mazeArray[row][col] = line.charAt(col);
                }
                row++;
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error reading maze file: " + e.getMessage());
        }

        // Store coord pairs in the HashMap teleportationPairs
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numColumns; c++) {
                char cell = mazeArray[r][c];
                if (Character.isDigit(cell)) {
                    int[] pair = teleportationPairs.get(cell);
                    if (pair == null) {
                        int[] myArray = new int[4];
                        myArray[0] = r;
                        myArray[1] = c;
                        teleportationPairs.put(cell, myArray);
                    } else {
                        int[] tempArr = teleportationPairs.get(cell);
                        tempArr[2] = r;
                        tempArr[3] = c;
                        teleportationPairs.put(cell, tempArr);
                    }
                }
            }
        }
    }
    
    public boolean solveMagicMaze() {
        return solveMagicMazeHelper(numRows-1, 0);
    }

    public boolean solveMagicMazeHelper(int row, int col) {
        // Check if the current cell is within the bounds of the maze
        if (row < 0 || row >= numRows || col < 0 || col >= numColumns) {
            return false;
        }
    
        // Check if the current cell is the destination cell
        if (mazeArray[row][col] == 'X') {
            return true;
        }
    
        // Check if the current cell is a valid move
        char cell = mazeArray[row][col];
        if (cell == '@' || cell == 'V') {
            return false;
        }
        
        // If the current cell is a teleportation cell, teleport to the corresponding cell
        boolean flag = false;// Make sure you don't set both teleporter numbers as V otherwise it breaky break
        if (Character.isDigit(cell)) {
            int[] pair = teleportationPairs.get(cell);
            mazeArray[row][col] = 'V';
            if (pair == null) {
                return false;
            }
            if (row == pair[0] && col == pair[1])
            {
                row = pair[2];
                col = pair[3];
                flag = true;
            }
            else {
                row = pair[0];
                col = pair[1];
                flag = true;
            }
        }

        // Mark the current cell as visited
        if (!flag) {
            mazeArray[row][col] = 'V';
        }
        char tempChar = mazeArray[row][col];
    
        // Recursively call solveMagicMazeHelper for each neighboring cell
        if (solveMagicMazeHelper(row - 1, col)) { // up
            return true;
        }
        if (solveMagicMazeHelper(row + 1, col)) { // down
            return true;
        }
        if (solveMagicMazeHelper(row, col - 1)) { // left
            return true;
        }
        if (solveMagicMazeHelper(row, col + 1)) { // right
            return true;
        }

        // If no solution is found, backtrack and mark the current cell as unvisited
        mazeArray[row][col] = tempChar;
        return false;
    }
}