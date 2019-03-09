package edu.kit.informatik.data;

/**
 * Represents the depth first search (DFS) algorithm and free spaces formula used to calculate the result of the game.
 * 
 * @author Rakan Zeid Al Masri
 * @version 1.0
 */
public class FreeSpaces {
    
    /**
     * Method that calculates the result.
     * @param vesta The cell of vesta.
     * @param ceres The cell of ceres.
     * @param board The game board.
     * @return The free spaces result by using the formula given in the question sheet.
     */
    public static int computeResult(Cell vesta, Cell ceres, Board board) {
        int freeSpacesCeres = getFreeSpaces(board, ceres);
        int freeSpacesVesta = getFreeSpaces(board, vesta);
        int result = 0;
        if (freeSpacesCeres > freeSpacesVesta) { // Applying the formula from the question.
            result = freeSpacesCeres + (freeSpacesCeres - freeSpacesVesta);
        } else if (freeSpacesCeres <= freeSpacesVesta) {
            result = freeSpacesVesta + (freeSpacesVesta - freeSpacesCeres);
        }
        return result;
    }

    /**
     * Method to get the the free spaces of a piece.
     * @param board The DAWN 11/15 board.
     * @param source The cell of the Nature piece.
     * @return The number of free spaces.
     */
    public static int getFreeSpaces(Board board, Cell source) {
        boolean[][] visited = new boolean[board.getHeight()][board.getWidth()];
        
        return traverse(source, visited) - 1; // subtracted 1 here to not count the Nature cell.
    }
    
    /**
     * DFS algorithm to get the count of all cells that can be visited.
     * @param source The cell of the Nature piece.
     * @param visited The board grid represented as a 2d array of boolean.
     * True if the cell of said coordinate has been visited, otherwise false.
     * @return The number of non-occupied cells neighboring the current cell.
     */
    private static int traverse(Cell source, boolean[][] visited) {
        visited[source.getMCoord()][source.getNCoord()] = true;
        int visitedCells = 0; // Keeps track of many cells were visited.
        
        for (Cell neighbor : source.getNeighbors()) { // Iteration through neighbor cells.
            if (!neighbor.isOccupied() && !visited[neighbor.getMCoord()][neighbor.getNCoord()]) {
                // Checking every neighbor and its neighbor etc. until no more neighbors can be visited.
                visitedCells += traverse(neighbor, visited);
            }
        }
        return visitedCells + 1; // Added 1 to count each source.
    }      
}
