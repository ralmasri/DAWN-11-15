package edu.kit.informatik.exceptions;

/**
 * Represents an exception that is thrown when a game rule is broken.
 * 
 * @author Rakan Zeid Al Masri
 *
 */
public class GameMechanicException extends Exception {

    private static final long serialVersionUID = 7606110865706698729L;

    /**
     * The constructor of the GameMechanicException that is thrown if a game rule
     * is broken. 
     * 
     * @param message The error message to be displayed.
     */
    public GameMechanicException(String message) {
        super(message);
    }
}
