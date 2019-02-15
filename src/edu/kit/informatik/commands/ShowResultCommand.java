package edu.kit.informatik.commands;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.data.DawnGame;
import edu.kit.informatik.exceptions.GameMechanicException;
import edu.kit.informatik.exceptions.InvalidInputException;

/**
 * Class the represents the show-result command. Prints the result if the game is over.
 * 
 * @author Rakan Zeid Al Masri
 * @version 1.0
 */

public class ShowResultCommand extends Command {

    /**
     * Constructor for the command.
     * @param game The game from which all methods are called.
     */
    
    public ShowResultCommand(DawnGame game) {
        super(game);
    }

    @Override
    public String getNameofCommand() {
        return "show-result";
    }

    @Override
    public void run(String parameters) throws InvalidInputException, GameMechanicException {
        Terminal.printLine(game.showresult());
    }
}
