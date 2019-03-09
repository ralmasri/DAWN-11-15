package edu.kit.informatik.commands;

import edu.kit.informatik.data.DawnGame;
import edu.kit.informatik.exceptions.GameMechanicException;
import edu.kit.informatik.exceptions.InvalidInputException;
import edu.kit.informatik.util.StringList;

/**
 * Class that represents the reset command.
 * 
 * @author Rakan Zeid Al Masri
 * @version 1.0
 */
public class ResetCommand extends Command {

    /**
     * Constructor for the command.
     * @param gameExecutor The game from which all methods are called.
     */
    public ResetCommand(DawnGame gameExecutor) {
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
