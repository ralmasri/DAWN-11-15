package edu.kit.informatik.commands;

import edu.kit.informatik.Terminal;



import edu.kit.informatik.data.DawnGameExecutor;
import edu.kit.informatik.exceptions.GameMechanicException;
import edu.kit.informatik.exceptions.InvalidInputException;
import edu.kit.informatik.ui.InputChecker;
import edu.kit.informatik.util.StringList;

/**
 * Class that represents the set-vc command. Prints "OK", when successful.
 * 
 * @author Rakan Zeid Al Masri
 * @version 1.0
 */

public class SetvcCommand extends Command {

    /**
     * Constructor for the game.
     * @param gameExecutor The game from which all methods are called.
     */
    
    public SetvcCommand(final DawnGameExecutor gameExecutor) {
        super(gameExecutor);
    }
    
    @Override
    public String getNameofCommand() {
        return "set-vc";
    }

    @Override
    public void run(String parameters) throws InvalidInputException, GameMechanicException {
        InputChecker.checkCoordinate(parameters);
        String[] inputsplit = parameters.split(StringList.COMPONENT_SEPARATOR.toString());
        int mcomponent = Integer.parseInt(inputsplit[0]);
        int ncomponent = Integer.parseInt(inputsplit[1]);
        Terminal.printLine(gameExecutor.setVC(mcomponent, ncomponent));
    }
}
