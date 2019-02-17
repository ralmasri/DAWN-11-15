package edu.kit.informatik.data;



/**
 * 
 * @author Rakan Zeid Al Masri
 * @version 1.0
 */

public class DepthFirstSearch {

    public static int getFreeSpaces(Board board, Cell source) {
        boolean[][] visited = new boolean[board.getHeight()][board.getWidth()];
        
        return traverse(source, visited) - 1;
    }
    
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
