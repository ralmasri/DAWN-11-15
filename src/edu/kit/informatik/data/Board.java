package edu.kit.informatik.data;

import edu.kit.informatik.data.Cell;

/**
 * Class that represents the DAWN 11/15 board.
 * 
 * @author Rakan Zeid Al Masri
 * @version 1.0
 */
public class Board {
    
    /** Four in this case because only horizontal or vertical movement is allowed */
    private static final int NUMBER_OF_DIRECTIONS = 4;

    /** Two-dimensional array that represents a matrix of cells. */
    private Cell[][] board;
    
    /** The width of the board. */
    private int width;
    
    /** The height of the board. */
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
        for (int mcomp = 0; mcomp < height; mcomp++) {
            for (int ncomp = 0; ncomp < width; ncomp++) {
                board[mcomp][ncomp] = new Cell(mcomp, ncomp);
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
     * @return The cell at those specific coordinates if the coordinates are valid,
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
        for (int mcomp = 0; mcomp < height; mcomp++) {
            for (int ncomp = 0; ncomp < width; ncomp++) {
                // Iterates through the board until the piece is found.
                if (piece.equals(board[mcomp][ncomp].getPiece())) {
                    return getCell(mcomp, ncomp);
                }
            }
        }
        return null;
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
     * Generates the neighbors of a cell by performing additions to get the north, south, east and west 
     * neighbors of a cell.
     */
    private void makeNeighbors() {
        
        // Array of additions we have to do to get the north, south, east and west neighbors of a cell respectively.
        int[] addm = {-1, 1, 0, 0};
        int[] addn = {0, 0, 1, -1};
        
        for (int mcomp = 0; mcomp < height; mcomp++) {
            for (int ncomp = 0; ncomp < width; ncomp++) {
             // loops through the array of additions.
                for (int index = 0; index < NUMBER_OF_DIRECTIONS; index++) { 
                    Cell cell = new Cell(mcomp + addm[index], ncomp + addn[index]);
                    // If the neighbor is valid (on the board), then add it.
                    if (isInBounds(mcomp + addm[index], ncomp + addn[index])) {
                        board[mcomp][ncomp].addNeighborCell(board[cell.getMCoord()][cell.getNCoord()]);
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
        StringBuilder grid = new StringBuilder();
        for (int mcomp = 0; mcomp < board.length; mcomp++) {
            for (int ncomp = 0; ncomp < board[mcomp].length; ncomp++) {
                grid.append(board[mcomp][ncomp].toString());
            }
            // To not add a line break after the last row.
            if (mcomp == board.length - 1) {
                break;
            }
            grid.append("\n");
        }
        return grid.toString();
    }
    
}
