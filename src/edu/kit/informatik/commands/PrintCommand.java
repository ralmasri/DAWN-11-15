package edu.kit.informatik.commands;

import edu.kit.informatik.Terminal;

import edu.kit.informatik.data.DawnGameExecutor;
import edu.kit.informatik.exceptions.GameMechanicException;
import edu.kit.informatik.exceptions.InvalidInputException;

/**
 * Class that represents the print command. Prints the current state of the board as a grid.
 * 
 * @author Rakan Zeid Al Masri
 * @version 1.0
 */

public class PrintCommand extends Command {

    /**
     * Constructor for the command.
     * @param gameExecutor The game from which all methods are called.
     */
    
    public PrintCommand(final DawnGameExecutor gameExecutor) {
        super(gameExecutor);
    }
 

    @Override
    public String getNameofCommand() {
       return "print";
    }

    @Override
    public void run(String parameters) throws InvalidInputException, GameMechanicException {
        Terminal.printLine(gameExecutor.print());
    }
}
