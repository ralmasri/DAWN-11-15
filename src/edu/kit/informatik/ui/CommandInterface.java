package edu.kit.informatik.ui;

import edu.kit.informatik.exceptions.GameMechanicException;
import edu.kit.informatik.exceptions.InvalidInputException;

/**
 * Interface for commands. Includes the execution method and a Getter for the name of the command.
 * 
 * @author Rakan Zeid Al Masri
 * @version 1.0
 */
public interface CommandInterface {

    /**
     * Getter method for the name of the command.
     * @return The name of the command.
     */
    String getNameofCommand();
    
    
    /**
     * Runs the command.
     * 
     * For some commands, it handles some higher logic (see documentation for each override).
     * 
     * @param parameters The parameters of the command.
     * @return The output.
     * @throws InvalidInputException If the input is not in the correct format.
     * @throws GameMechanicException If the input breaks the game's rules.
     */
    String run(String parameters) throws InvalidInputException, GameMechanicException;
}
