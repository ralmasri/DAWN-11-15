package edu.kit.informatik.data;

import java.util.Objects;

import edu.kit.informatik.util.StringList;


/**
 * Class that represents a piece.
 * 
 * @author Rakan Zeid Al Masri
 * @version 1.0
 */
public class Piece {
    
    /** Symbol for a Mission Control piece.*/
    private static final char MISSION_CONTROL_SYMBOL = '+';
    
    /** Length of a Nature piece. */
    private static final int LENGTH_OF_NATURE_PIECE = 1;

    /* Single character symbol that represents a piece. */
    private char symbol;
    
    /** The name of the piece. */
    private String name;
    
    /** The length of the piece. */
    private int length;
    
    /**
     * Constructor for a Mission Control piece.
     * 
     * @param length The length of the piece.
     */
    public Piece(int length) {
        this.symbol = MISSION_CONTROL_SYMBOL; // All of them have the same symbol.
        this.length = length;
        if (length == GameInitializer.getDawnNumber()) {
            this.name = StringList.DAWN.toString();
        } else {
            this.name = String.valueOf(length);
        }
    }
        
       
    /**
     * Constructor for a Nature piece.
     * 
     * @param symbol The symbol of the piece.
     * @param name The name of the piece.
     */
    public Piece(char symbol, String name) {
        this.symbol = symbol;
        this.name = name;
        this.length = LENGTH_OF_NATURE_PIECE; // Both have the same length.
    }
    
    /**
     * Getter method for the symbol.
     * @return The symbol.
     */
    public char getSymbol() {
        return symbol;
    }
    
    /**
     * Getter method for the length.
     * @return The length.
     */
    public int getLength() {
        return length;
    }
    
    /**
     * Getter method for the name.
     * @return The name.
     */
    public String getName() {
        return name;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            Piece otherPiece = (Piece) obj;
            return symbol == otherPiece.symbol 
                    && length == otherPiece.length 
                    && name.equals(otherPiece.name);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(length, name, symbol);
    }
}
