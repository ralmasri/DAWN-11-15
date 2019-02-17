package edu.kit.informatik.data;

/**
 * Represents the depth first search (DFS) algorithm I'm using to calculate the free spaces of a Nature piece.
 * 
 * @author Rakan Zeid Al Masri
 * @version 1.0
 */

public class DepthFirstSearch {

    /**
     * Method to get the the free spaces of a piece.
     * @param board The DAWN board.
     * @param source The cell of the Nature piece.
     * @return The number of free spaces.
     */
    
    public static int getFreeSpaces(Board board, Cell source) {
        boolean[][] visited = new boolean[board.getHeight()][board.getWidth()];
        
        return traverse(source, visited) - 1; // without source cell
    }
    
    /**
     * DFS algorithm to get the count of all cells that can be visited.
     * @param source The cell of the Nature piece.
     * @param visited The board grid represented as a boolean of visited and non-visited cells.
     * @return The number of non-occupied cells neighboring the current cell.
     */
    
    private static int traverse(Cell source, boolean[][] visited) {
        visited[source.getMCoord()][source.getNCoord()] = true;
        int visitedCells = 0;
        
        for (Cell neighbor : source.getNeighbors()) {
            if (!neighbor.isOccupied() && !visited[neighbor.getMCoord()][neighbor.getNCoord()]) {
                visitedCells += traverse(neighbor, visited);
            }
        }
        return visitedCells + 1;
    }      
}
