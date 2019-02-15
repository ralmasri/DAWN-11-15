/**
 * 
 */
package edu.kit.informatik.data;

/**
 * 
 * @author Rakan Zeid Al Masri
 * @version 1.0
 */

public class FreeSpaces {

    public int getPossibleMoves(Board board, int m, int n) {
      
        int moves = 0;
        //all possible moves in the up
        for (int i = m + 1; i < board.getHeight(); i++) {
            Cell cell = board.getCell(i, n);
            if (!cell.isOccupied()) {
                moves++;
            }
        }
       
        //all possible moves in the down
        for (int i = m - 1; i >= 0; i--) {
            Cell cell = board.getCell(i, n);
            if (!cell.isOccupied()) {
                moves++;
            }
        }
        //all possible moves to the right
        for (int i = n + 1; i < board.getWidth(); i++) {
            Cell cell = board.getCell(m, i);
            if (!cell.isOccupied()) {
                moves++;
            }
        }
        
        //all possible moves to the left
        for (int i = n - 1; i >= 0 ; i--) {
            Cell cell = board.getCell(m, i);
            if (!cell.isOccupied()) {
                moves++;
            }
        }
        return moves;
    }
}
