package edu.kit.informatik.commands;

import edu.kit.informatik.Terminal;

import edu.kit.informatik.data.DawnGame;
import edu.kit.informatik.exceptions.GameMechanicException;
import edu.kit.informatik.exceptions.InvalidInputException;
import edu.kit.informatik.ui.InputParser;

/**
 * Class the represents the roll command. Prints "OK", when successful.
 * 
 * @author Rakan Zeid Al Masri
 * @version 1.0
 */

public class RollCommand extends Command{

    /**
     * Constructor for the command.
     * @param game The game from which all methods are called.
     */
    
    public RollCommand(DawnGame game) {
        super(game);
    }

    @Override
    public String getNameofCommand() {
        return "roll";
    }

    @Override
    public void run(String parameters) throws InvalidInputException, GameMechanicException {
        InputParser.parseSymbol(parameters);
        Terminal.printLine(game.roll(parameters));
        game.setRolled(true);
    }
}
