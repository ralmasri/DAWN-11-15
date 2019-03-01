package edu.kit.informatik.commands;

import edu.kit.informatik.data.DawnGameExecutor;
import edu.kit.informatik.exceptions.GameMechanicException;
import edu.kit.informatik.exceptions.InvalidInputException;
import edu.kit.informatik.util.StringList;

/**
 * Class that represents the reset command. Prints "OK" if successful.
 * 
 * @author Rakan Zeid Al Masri
 * @version 1.0
 */

public class ResetCommand extends Command {

    /**
     * Constructor for the command.
     * @param gameExecutor The game from which all methods are called.
     */
    
    public ResetCommand(DawnGameExecutor gameExecutor) {
        super(gameExecutor);
    }

    @Override
    public String getNameofCommand() {
       return "reset";
    }

    @Override
    public String run(String parameters) throws InvalidInputException, GameMechanicException {
        gameExecutor.getGame().reset();
        return StringList.OK.toString();
    }
}
