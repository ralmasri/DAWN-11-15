package edu.kit.informatik.util;

/**
 * This class represents strings or regular expressions that I use.
 *
 * @author Rakan Zeid Al Masri
 * @version 1.0
 */

public enum StringList {

    /**
     * String to be used when command is successfully run.
     */
    OK("OK"),
    
    /**
     * The pattern of possible rolls.
     */
    ROLL_PATTERN("[2-6]|DAWN"),
    
    /**
     * Separates two coordinates.
     */
    COORDINATE_SEPARATOR(":"),
    
    /**
     * Separates the m- and n-components.
     */
    COMPONENT_SEPARATOR(";"),
    
    /**
     * The pattern of possible coordinate inputs.
     */
    COORDINATE_PATTERN("([0-9]|10)(;)([0-9]|[1][1-4])"),
    
    /**
     * The pattern of possible m-components for a DAWN piece.
     */
    DAWN_MCOORDINATE_PATTERN("(-[1-6]|[0-9]|1[0-6])"),
    
    /**
     * The pattern of possible n-components for a DAWN piece.
     */
    DAWN_NCOORDINATE_PATTERN("(-[1-6]|[0-9]|1[0-9]|20)"),
    
    /**
     * Separates the command and its parameters.
     */
    COMMAND_SEPARATOR(" "),
    
    /**
     * Error message when a wrong command is entered.
     */
    COMMAND_DOESNT_EXIST("this command doesn't exist."),
    
    /**
     * Error message when the game is over.
     */
    GAME_OVER("the game is over. Pieces cannot be moved or placed."),
    
    /**
     * Error message if the destination cell is occupied by a piece.
     */
    IS_OCCUPIED(" is occupied, you cannot place a piece there."),
    
    /**
     * Error message if the user hasn't rolled the die yet.
     */
    NOT_ROLLED("you must first roll the die before placing a piece."),
    
    /**
     * Part of an error message if the coordinates are not in the correct format.
     */
    COORDINATES_CORRECT_FORMAT(" The error could be either syntactical or semantical."),
    
    /**
     * Error message if the coordinates the user input are not correct.
     */
    INVALID_COORDINATES("the cell coordinates you have given are not valid.");
    
    /**
     * Textual representation of the output.
     */
    private String output;
    
    /**
     * @param output The string representation of the output.
     */
    StringList(String output) {
        this.output = output;
    }
    
    
    @Override
    public String toString() {
        return output;
    }
}
