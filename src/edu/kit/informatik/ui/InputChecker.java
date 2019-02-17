package edu.kit.informatik.ui;

import java.util.regex.Pattern;



import edu.kit.informatik.exceptions.InvalidInputException;
import edu.kit.informatik.util.StringList;

/**
 * Class that includes methods to check different inputs for correct format.
 * 
 * @author Rakan Zeid Al Masri
 * @version 1.0
 */

public final class InputChecker {

    private static final Pattern COORDINATE_PATTERN = Pattern.compile(StringList.COORDINATE_PATTERN.toString());
    private static final Pattern SYMBOL_PATTERN = Pattern.compile(StringList.ROLL_PATTERN.toString());
    private static final Pattern PLACE_PATTERN = Pattern.compile(StringList.DAWN_MCOORDINATE_PATTERN.toString()
            + StringList.COMPONENT_SEPARATOR.toString() 
            + StringList.DAWN_NCOORDINATE_PATTERN.toString() 
            + StringList.COORDINATE_SEPARATOR.toString() 
            + StringList.DAWN_MCOORDINATE_PATTERN.toString()
            + StringList.COMPONENT_SEPARATOR.toString()
            + StringList.DAWN_NCOORDINATE_PATTERN.toString());
    private static final String MULTIPLE_COORDINATE_PATTERN = StringList.COORDINATE_PATTERN.toString() 
            + "(" + StringList.COORDINATE_SEPARATOR.toString() + StringList.COORDINATE_PATTERN.toString() + "){";
    
    /**
     * Method to check if coordinate is in correct format.
     * 
     * @param input The coordinate to check.
     * @return The input.
     * @throws InvalidInputException If input is not in the correct format.
     */
    
    public static String checkCoordinate(final String input) throws InvalidInputException {
        if (!COORDINATE_PATTERN.matcher(input).matches()) {
            throw new InvalidInputException(StringList.INVALID_COORDINATES.toString() 
                    + StringList.COORDINATES_CORRECT_FORMAT.toString());
        }
        return input;
    }
    
    /**
     * Method to check if symbol is in correct format.
     * 
     * @param input The symbol to check.
     * @return The symbol.
     * @throws InvalidInputException If the symbol is not in the correct format.
     */
    
    public static String checkSymbol(final String input) throws InvalidInputException {
        if (!SYMBOL_PATTERN.matcher(input).matches()) {
            throw new InvalidInputException("invalid symbol.");
        }
        return input;
    }
    
    /**
     * Method to check if multiple coordinates are in the correct format. 
     * The number of coordinates depends on {@code length}.
     * 
     * @param input The coordinates to check.
     * @param length The upper range of possible number of parameters.
     * @return The coordinates.
     * @throws InvalidInputException If the coordinates are not in the correct format 
     * or wrong number of coordinates given.
     */
    
    public static String checkMove(final String input, int length) throws InvalidInputException {
        Pattern pattern = Pattern.compile(MULTIPLE_COORDINATE_PATTERN + "0," + String.valueOf(length) + "}");
        if (!pattern.matcher(input).matches()) {
            throw new InvalidInputException(StringList.INVALID_COORDINATES.toString() 
                    + StringList.COORDINATES_CORRECT_FORMAT.toString());
        }
        
        return input;
    }
    
    /**
     * Method to check if coordinates used for a place command are in the correct format. This is the different from 
     * checkCoordinate, because DAWN pieces can be partially placed outside of the board.
     * 
     * @param input The coordinates to check.
     * @return The coordinates.
     * @throws InvalidInputException If the coordinates are not in the correct format.
     */
    
    public static String checkPlace(final String input) throws InvalidInputException {
        if (!PLACE_PATTERN.matcher(input).matches()) {
            throw new InvalidInputException(StringList.INVALID_COORDINATES.toString() 
                    + StringList.COORDINATES_CORRECT_FORMAT.toString());
        }
        return input;
    }
}
