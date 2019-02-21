package edu.kit.informatik.ui;

import edu.kit.informatik.exceptions.*;

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
     * @param parameters The parameters of the command.
     * @throws InvalidInputException If the input is not in correct format.
     * @throws GameMechanicException If the input breaks the game's rules.
     */
    
    void run(String parameters) throws InvalidInputException, GameMechanicException;

}
