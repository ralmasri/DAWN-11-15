package edu.kit.informatik.data;

import edu.kit.informatik.data.Cell;

/**
 * Class that represents the DAWN 11/15 board.
 * 
 * @author Rakan Zeid Al Masri
 * @version 1.0
 */

public class Board {

    /**
     * Two-dimensional array that represents a matrix of cells.
     */
    
    private Cell[][] board;
    
    /**
     * The width of the board.
     */
    
    private int width;
    
    /**
     * The height of the board.
     */
    
    private int height;
    
    
    /**
     * Constructor of a board.
     * @param width The width of said board.
     * @param height The height of said board.
     */
    
    public Board(int height, int width) {
        this.width = width;
        this.height = height;
        this.board = new Cell[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                board[i][j] = new Cell(i,j);
            }
        }
    }
    
    public Cell[][] getBoard() {
        return board;
    }
    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Getter method for a cell within m,n matrix.
     * @param mcoordinate The row coordinate.
     * @param ncoordinate The column coordinate.
     * @return The cell at those specific coordinates if the coordinates are ,
     * otherwise null. 
     */
    
    public Cell getCell(int mcoordinate, int ncoordinate) {
        if(!isInBounds(mcoordinate, ncoordinate)) {
            return null;
        }
        return board[mcoordinate][ncoordinate];
    }
    
    public Cell getCellofPiece(Piece piece) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if(board[i][j].getPiece() != null && board[i][j].getPiece().equals(piece)) {
                    return getCell(i,j);
                } 
            }
        }
        return board[0][0];
     }
    
    /**
     * Method to check if coordinates are within the confines of the board.
     * @param m The row coordinate.
     * @param n The column coordinate.
     * @return true if the coordinates are correct, otherwise false.
     */
    
    public boolean isInBounds(int m, int n) {
        return m >= 0 && m < height && n >= 0 && n < width;
    }
    
    @Override
    public String toString() {
        String outcome = "";
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                outcome += board[i][j].toString();
            }
            outcome += "\n";
        }
        return outcome;
    }
    
}
