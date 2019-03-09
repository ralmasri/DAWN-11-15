package edu.kit.informatik.commands;

import edu.kit.informatik.data.DawnGame;
import edu.kit.informatik.data.GameInitializer;
import edu.kit.informatik.exceptions.GameMechanicException;
import edu.kit.informatik.exceptions.InvalidInputException;
import edu.kit.informatik.ui.InputChecker;
import edu.kit.informatik.util.StringList;

/**
 * Class that represents the set-vc command.
 * 
 * @author Rakan Zeid Al Masri
 * @version 1.0
 */
public class SetvcCommand extends Command {

    /**
     * Constructor for the game.
     * @param gameExecutor The game from which all methods are called.
     */
    public SetvcCommand(final DawnGame gameExecutor) {
        super(gameExecutor);
    }
    
    @Override
    public String getNameofCommand() {
        return "set-vc";
    }

    /**
     * Executes the set-vc method and also checks the game state if rules have been broken.
     */
    @Override
    public String run(String parameters) throws InvalidInputException, GameMechanicException {
        InputChecker.checkCoordinate(parameters);
        GameInitializer game = gameExecutor.getGame();
        if (game.isGameOver()) {
            gameExecutor.throwGameOver();
        }
        if (game.getCurrentGameStage().isVCPlaced()) { // If the Nature piece for this stage has already been placed.
            throw new GameMechanicException(
                    game.getCurrentGameStage().getNaturePiece().getName() + " has already been placed.");
        }
        String[] inputsplit = parameters.split(StringList.COMPONENT_SEPARATOR.toString());
        int mcomponent = Integer.parseInt(inputsplit[0]);
        int ncomponent = Integer.parseInt(inputsplit[1]);
        gameExecutor.setVC(mcomponent, ncomponent);
        return StringList.OK.toString();
    }
}
