package edu.kit.informatik.commands;

import edu.kit.informatik.data.DawnGame;
import edu.kit.informatik.data.DawnGameExecutor;
import edu.kit.informatik.exceptions.GameMechanicException;
import edu.kit.informatik.exceptions.InvalidInputException;
import edu.kit.informatik.ui.InputChecker;
import edu.kit.informatik.util.StringList;

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
    public String run(String parameters) throws InvalidInputException, GameMechanicException {
        InputChecker.checkRoll(parameters);
        DawnGame game = gameExecutor.getGame();
        if (game.isGameOver()) {
            gameExecutor.throwGameOver();
        }
        gameExecutor.roll(parameters);
        if (game.isStageOver()) {
           game.goToNextStage();
        }
        game.setRolled(true);
        return StringList.OK.toString();
    }
}
