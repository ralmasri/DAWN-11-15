package edu.kit.informatik.ui;

import java.util.regex.Pattern;

import edu.kit.informatik.data.DawnGame;
import edu.kit.informatik.exceptions.GameMechanicException;
import edu.kit.informatik.exceptions.InvalidInputException;
import edu.kit.informatik.util.StringList;

/**
 * Class that includes methods to check different inputs for correct format.
 * 
 * @author Rakan Zeid Al Masri
 * @version 1.0
 */

public final class InputChecker {

    /**
     * The pattern for a coordinate.
     */
    private static final Pattern COORDINATE_PATTERN = Pattern.compile(StringList.COORDINATE_REGEX.toString());
    
    /**
     * The pattern of allowed roll symbols.
     */
    private static final Pattern ROLL_PATTERN = Pattern.compile(StringList.ROLL_REGEX.toString());
    
    /**
     * The pattern for allowed placements. Different from {@link COORDINATE_PATTERN}, 
     * because out of board placements are allowed.
     */
    private static final Pattern PLACE_PATTERN = Pattern.compile(StringList.PLACE_MCOMPONENT.toString()
            + StringList.COMPONENT_SEPARATOR.toString() 
            + StringList.PLACE_NCOMPONENT.toString() 
            + StringList.COORDINATE_SEPARATOR.toString() 
            + StringList.PLACE_MCOMPONENT.toString()
            + StringList.COMPONENT_SEPARATOR.toString()
            + StringList.PLACE_NCOMPONENT.toString());
    
    /**
     * The pattern used for multiple coordinates (move).
     */
    private static final String MULTIPLE_COORDINATE_PATTERN = StringList.COORDINATE_REGEX.toString() 
            + "(" 
            + StringList.COORDINATE_SEPARATOR.toString() 
            + StringList.COORDINATE_REGEX.toString() 
            + "){";
    
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
     * Method to check if the symbol for a roll is in correct format.
     * 
     * @param input The symbol to check.
     * @return The roll symbol.
     * @throws InvalidInputException If the symbol is not in the correct format.
     */
    
    public static String checkRoll(final String input) throws InvalidInputException {
        if (!ROLL_PATTERN.matcher(input).matches()) {
            throw new InvalidInputException("invalid symbol.");
        }
        return input;
    }
    
    /**
     * Method to check if multiple coordinates are in the correct format and follow game rules.
     * 
     * It is adjusted based on the length of the last placed piece. Since a piece must move at least once, 
     * the pattern allows for a minimum of one move and a maximum of the length of the placed piece.
     * 
     * @param input The moves to check.
     * @param length The maximum number of allowed moves - 1 (a minimum of one move is already in the pattern).
     * @return The String of destination coordinates.
     * @throws InvalidInputException If the input format is incorrect, 
     * no moves were entered or if more than 7 moves were entered.
     * @throws GameMechanicException If too many moves are entered. 
     */
    
    public static String checkMove(final String input, int length) 
            throws InvalidInputException, GameMechanicException {
        Pattern pattern = Pattern.compile(MULTIPLE_COORDINATE_PATTERN + "0," + String.valueOf(length) + "}");
        if (!pattern.matcher(input).matches()) {
            // The point of this is to return a unique error message if the user enters too many moves.
            // i = length + 1 because the user would have entered x moves over the maximum (being 1 + length)
            for (int i = length + 1; i <= DawnGame.getDawnNumber(); i++) {
                Pattern toolarge = Pattern.compile(MULTIPLE_COORDINATE_PATTERN + String.valueOf(i) + "}");
                if (toolarge.matcher(input).matches()) {
                    throw new GameMechanicException("you are only allowed a maximum of "
                            + String.valueOf(length + 1) + " moves and you have entered "
                            + String.valueOf(i + 1) + " moves.");
                }
            }
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
