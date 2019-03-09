package edu.kit.informatik.commands;

import edu.kit.informatik.data.GameInitializer;
import edu.kit.informatik.data.DawnGame;
import edu.kit.informatik.exceptions.GameMechanicException;
import edu.kit.informatik.exceptions.InvalidInputException;
import edu.kit.informatik.ui.InputChecker;
import edu.kit.informatik.util.StringList;

/**
 * Class the represents the roll command.
 * 
 * @author Rakan Zeid Al Masri
 * @version 1.0
 */
public class RollCommand extends Command {

    /**
     * Constructor for the command.
     * @param gameExecutor The game from which all methods are called.
     */
    public RollCommand(DawnGame gameExecutor) {
        super(gameExecutor);
    }

    @Override
    public String getNameofCommand() {
        return "roll";
    }

    /**
     * Executes the roll method and checks the game state if certain rules have been broken.
     */
    @Override
    public String run(String parameters) throws InvalidInputException, GameMechanicException {
        InputChecker.checkRoll(parameters);
        GameInitializer game = gameExecutor.getGame();
        if (game.isGameOver()) {
            gameExecutor.throwGameOver();
        }
        if (!game.getCurrentGameStage().isVCPlaced()) {
            throw new GameMechanicException("you must first place "
                    + game.getCurrentGameStage().getNaturePiece().getName() + " before rolling.");
        }
        gameExecutor.roll(parameters);
        if (game.isStageOver()) {
           game.goToNextStage();
        }
        game.setRolled(true);
        return StringList.OK.toString();
    }
}
