package edu.kit.informatik.exceptions;

/**
 * Represents an exception that is thrown when user input is incorrect, either syntactically or semantically.
 * 
 * @author Rakan Zeid Al Masri
 *
 */
public class InvalidInputException extends Exception {

    private static final long serialVersionUID = 259442120077715015L;

    /**
     * Constructor for InvalidInputException that is thrown when the user input is 
     * incorrect, either syntactically or semantically.
     * 
     * @param message Error message to be shown.
     */
    public InvalidInputException(String message) {
        super(message);
    }
}
