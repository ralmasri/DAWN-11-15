package edu.kit.informatik.commands;

import edu.kit.informatik.Terminal;


import edu.kit.informatik.data.DawnGame;
import edu.kit.informatik.exceptions.GameMechanicException;
import edu.kit.informatik.exceptions.InvalidInputException;
import edu.kit.informatik.ui.InputChecker;

/**
 * Class that represents the set-vc command. Prints "OK", when successful.
 * 
 * @author Rakan Zeid Al Masri
 * @version 1.0
 */

public class SetvcCommand extends Command {

    /**
     * Constructor for the game.
     * @param game The game from which all methods are called.
     */
    
    public SetvcCommand(final DawnGame game) {
        super(game);
    }
    @Override
    public String getNameofCommand() {
        return "set-vc";
    }

    @Override
    public void run(String parameters) throws InvalidInputException, GameMechanicException {
        InputChecker.checkCoordinate(parameters);
        String[] inputsplit = parameters.split(";");
        int mcoordinate = Integer.parseInt(inputsplit[0]);
        int ncoordinate = Integer.parseInt(inputsplit[1]);
        Terminal.printLine(game.setVC(mcoordinate, ncoordinate));
    }
}
