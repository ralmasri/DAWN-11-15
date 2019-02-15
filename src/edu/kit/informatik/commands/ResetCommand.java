package edu.kit.informatik.commands;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.data.DawnGame;
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
     * @param game The game from which all methods are called.
     */
    
    public ResetCommand(DawnGame game) {
        super(game);
    }

    @Override
    public String getNameofCommand() {
       return "reset";
    }

    @Override
    public void run(String parameters) throws InvalidInputException, GameMechanicException {
        game.reset();
        Terminal.printLine(StringList.OK.toString());
    }
}
