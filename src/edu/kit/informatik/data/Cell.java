package edu.kit.informatik.data;

import java.util.LinkedList;
import java.util.Objects;

/**
 * Represents a cell on the board and a String representation of said cell.
 * 
 * @author Rakan Zeid Al Masri
 *
 */

public class Cell {
    
    private static final String EMPTY_CELL_SYMBOL = "-";

    /**
     * M-component of the cell.
     */
    
    private int mcomponent;
    
    /**
     * N-component of the cell.
     */
    
    private int ncomponent;
   
    /**
     * The piece that occupies this cell or null.
     */
    
    private Piece piece;
    
    /**
     * Neighboring cells.
     */
    
    private final LinkedList<Cell> neighborCells;
    
    /**
     * Constructor for a cell when no piece is on it.
     * @param mcomponent The m-component.
     * @param ncomponent The n-component.
     */
    
    public Cell(int mcomponent, int ncomponent) {
        this.piece = null;
        this.mcomponent = mcomponent;
        this.ncomponent = ncomponent;
        this.neighborCells = new LinkedList<>();
     }
    
    /**
     * Getter method for the m-component.
     * @return The m-component.
     */
    
    public int getMCoord() {
        return mcomponent;
    }
    
    /**
     * Getter method for the n-component.
     * @return The n-component.
     */
    
    public int getNCoord() {
        return ncomponent;
    }
    
    /**
     * Getter method for the piece.
     * @return The piece or null.
     */
    
    public Piece getPiece() {
        return piece;
    }
    
    /**
     * Setter method for position a piece on the cell.
     * @param piece the piece to be placed.
     */
    
    public void setPiece(Piece piece) {
        this.piece = piece;
    }
    
    /**
     * Method to check if a cell has a piece on it.
     * @return true if the cell has a piece on it, otherwise false.
     */
    
    public boolean isOccupied() {
        return piece != null;
    }
    
    /**
     * Adds a neighboring cell.
     * @param cell The neighboring cell.
     */
    public void addNeighborCell(Cell cell) {
        neighborCells.add(cell);
    }
    
    /**
     * Gets neighboring cells.
     * @return The neighboring cells.
     */
    
    public LinkedList<Cell> getNeighbors() {
        return neighborCells;
    }
    
    /**
     * Textual representation of a cell.
     */
    
    @Override
    public String toString() {
        return piece != null ? String.valueOf(piece.getSymbol()) : EMPTY_CELL_SYMBOL;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(mcomponent, ncomponent);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass().equals(obj.getClass())) {
            final Cell otherCell = (Cell) obj;
            return (this.mcomponent == otherCell.mcomponent && this.ncomponent == otherCell.ncomponent);
        }
        return false;
    }
}
