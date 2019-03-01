package edu.kit.informatik.commands;

import edu.kit.informatik.data.DawnGameExecutor;
import edu.kit.informatik.ui.InputChecker;
import edu.kit.informatik.util.StringList;
import edu.kit.informatik.exceptions.InvalidInputException;
import edu.kit.informatik.exceptions.GameMechanicException;

/**
 * Class the represents the state command. 
 * Prints "-" if there is no piece on the cell we are checking, "C" for Ceres piece, "V" for Vesta piece,
 * and "+" for a Mission Control piece.
 * 
 * @author Rakan Zeid Al Masri
 * @version 1.0
 */

public class StateCommand extends Command {

    /**
     * Constructor for the command.
     * @param gameExecutor The game from which all methods are called.
     */
    
    public StateCommand(final DawnGameExecutor gameExecutor) {
        super(gameExecutor);
    }
    
    @Override
    public String getNameofCommand() {
        return "state";
    }
    
    @Override
    public String run(String parameters) throws InvalidInputException, GameMechanicException {
        InputChecker.checkCoordinate(parameters);
        String[] inputsplit = parameters.split(StringList.COMPONENT_SEPARATOR.toString());
        int mcomponent = Integer.parseInt(inputsplit[0]);
        int ncomponent = Integer.parseInt(inputsplit[1]);
        return gameExecutor.state(mcomponent, ncomponent);
    }

}
