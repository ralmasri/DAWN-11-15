package edu.kit.informatik.commands;

import edu.kit.informatik.data.DawnGame;
import edu.kit.informatik.ui.InputChecker;
import edu.kit.informatik.util.StringList;
import edu.kit.informatik.exceptions.InvalidInputException;
import edu.kit.informatik.exceptions.GameMechanicException;

/**
 * Class the represents the state command. 
 * 
 * @author Rakan Zeid Al Masri
 * @version 1.0
 */
public class StateCommand extends Command {

    /**
     * Constructor for the command.
     * @param gameExecutor The game from which all methods are called.
     */
    public StateCommand(final DawnGame gameExecutor) {
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
