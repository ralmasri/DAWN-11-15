package edu.kit.informatik.commands;

import edu.kit.informatik.data.DawnGameExecutor;
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
     * @param gameExecutor The game from which all methods are called.
     */
    
    public ShowResultCommand(DawnGameExecutor gameExecutor) {
        super(gameExecutor);
    }

    @Override
    public String getNameofCommand() {
        return "show-result";
    }

    @Override
    public String run(String parameters) throws InvalidInputException, GameMechanicException {
        if (!gameExecutor.getGame().isGameOver()) {
            throw new GameMechanicException("you cannot view the result if the game is not over yet.");
        }
        return gameExecutor.showresult();
    }
}
