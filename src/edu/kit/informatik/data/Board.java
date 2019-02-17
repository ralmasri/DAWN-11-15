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
                board[i][j] = new Cell(i, j);
            }
        }
        makeNeighbors(); // generates the neighbors.
    }
    
    /**
     * @return the board.
     */
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
     * @param mcomponent The row component.
     * @param ncomponent The column component.
     * @return The cell at those specific coordinates if the coordinates are ,
     * otherwise null. 
     */
    
    public Cell getCell(int mcomponent, int ncomponent) {
        if (!isInBounds(mcomponent, ncomponent)) {
            return null;
        }
        return board[mcomponent][ncomponent];
    }
    
    /**
     * Returns the cell of a piece.
     * @param piece The piece.
     * @return The cell of that piece.
     */
    
    public Cell getCellofPiece(Piece piece) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (board[i][j].getPiece() != null && board[i][j].getPiece().equals(piece)) {
                    return getCell(i, j);
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
    
    /**
     * Generates the neighbors of a cell.
     */
    
    private void makeNeighbors() {
        
        // Array of additions we have to do to get the north, south, east and west neighbors of a cell.
        
        int[] dm = {-1, 1, 0, 0};
        int[] dn = {0, 0, 1, -1};
        
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                for (int x = 0; x < 4; x++) { 
                    // loops through the array of additions. Four operations because diagonal movement is not allowed.
                    Cell cell = new Cell(i + dm[x], j + dn[x]);
                    if (isInBounds(i + dm[x], j + dn[x])) {
                        board[i][j].addNeighborCell(board[cell.getMCoord()][cell.getNCoord()]);
                    }
                }
            }
        }
    }
    
    /**
     * Textual represention of the grid.
     */
   
    @Override
    public String toString() {
        String outcome = "";
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                outcome += board[i][j].toString();
            }
            outcome += "\n";
        }
        return outcome;
    }
    
}
