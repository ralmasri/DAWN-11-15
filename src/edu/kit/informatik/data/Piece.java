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
    
    private static final char MISSION_CONTROL_SYMBOL = '+';
    private static final int LENGTH_OF_NATURE_PIECE = 1;

    /**
     * Single character symbol that represents a piece.
     */
    
    private char symbol;
    
    /**
     * The name of the piece.
     */
    
    private String name;
    
    /**
     * The length of the piece. No width mentioned because all pieces are of equal width.
     */
    
    private int length;
    
    /**
     * Constructor for a Mission Control piece, because they all have the same symbol.
     * 
     * @param length The length of the piece.
     */
    
    public Piece(int length) {
        this.symbol = MISSION_CONTROL_SYMBOL;
        this.length = length;
        if (length == DawnGame.getDawnNumber()) {
            this.name = StringList.DAWN.toString();
        } else {
            this.name = String.valueOf(length);
        }
    }
        
       
    /**
     * Constructor for a Nature piece, because they both have the same length.
     * 
     * @param symbol The symbol of the piece.
     * @param name The name of the piece.
     */
    
    public Piece(char symbol, String name) {
        this.symbol = symbol;
        this.name = name;
        this.length = LENGTH_OF_NATURE_PIECE;
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
        if (obj == null) {
            return false;
        }
        if (getClass().equals(obj.getClass())) {
            final Piece otherPiece = (Piece) obj;
            return (this.symbol == otherPiece.symbol 
                    && this.length == otherPiece.length && this.name.equals(otherPiece.name));
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(length, name, symbol);
    }
}
