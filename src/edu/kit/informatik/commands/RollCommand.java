package edu.kit.informatik.commands;

import edu.kit.informatik.Terminal;



import edu.kit.informatik.data.DawnGameExecutor;
import edu.kit.informatik.exceptions.GameMechanicException;
import edu.kit.informatik.exceptions.InvalidInputException;
import edu.kit.informatik.ui.InputChecker;

/**
 * Class the represents the roll command. Prints "OK", when successful.
 * 
 * @author Rakan Zeid Al Masri
 * @version 1.0
 */

public class RollCommand extends Command {

    /**
     * Constructor for the command.
     * @param gameExecutor The game from which all methods are called.
     */
    
    public RollCommand(DawnGameExecutor gameExecutor) {
        super(gameExecutor);
    }

    @Override
    public String getNameofCommand() {
        return "roll";
    }

    @Override
    public void run(String parameters) throws InvalidInputException, GameMechanicException {
        InputChecker.checkSymbol(parameters);
        Terminal.printLine(gameExecutor.roll(parameters));
        gameExecutor.getGame().setRolled(true);
    }
}
