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

    /**
     * M-component of the cell.
     */
    
    private int mcoordinate;
    
    /**
     * N-component of the cell.
     */
    
    private int ncoordinate;
   
    /**
     * The piece that occupies this cell or null.
     */
    
    private Piece piece;
    
    /**
     * Constructor for a cell when no piece is on it.
     */
    private final LinkedList<Cell> neighborCells;
    
    
    public Cell(int mcoordinate, int ncoordinate) {
        this.piece = null;
        this.mcoordinate = mcoordinate;
        this.ncoordinate = ncoordinate;
        this.neighborCells = new LinkedList<>();
     }
    
    /**
     * Getter method for the m-component.
     * @return The m-component.
     */
    
    public int getMCoord() {
        return mcoordinate;
    }
    
    /**
     * Getter method for the n-component.
     * @return The n-component.
     */
    
    public int getNCoord() {
        return ncoordinate;
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
    
    public void addNeighborCell(Cell cell) {
        neighborCells.add(cell);
    }
    
    public LinkedList<Cell> getNeighbors(){
        return neighborCells;
    }
    /**
     * Textual representation of a cell as a grid.
     */
    
    @Override
    public String toString() {
        return piece != null ? String.valueOf(piece.getSymbol()) : "-";
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(mcoordinate, ncoordinate);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (getClass().equals(obj.getClass())) {
            final Cell otherCell = (Cell) obj;
            return (this.mcoordinate == otherCell.mcoordinate && this.ncoordinate == otherCell.ncoordinate);
        }
        return false;
    }
}
