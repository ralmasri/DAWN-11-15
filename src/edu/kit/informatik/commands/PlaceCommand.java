package edu.kit.informatik.commands;

import edu.kit.informatik.data.Cell;
import edu.kit.informatik.data.DawnGame;
import edu.kit.informatik.data.DawnGameExecutor;
import edu.kit.informatik.exceptions.GameMechanicException;
import edu.kit.informatik.exceptions.InvalidInputException;
import edu.kit.informatik.ui.InputChecker;
import edu.kit.informatik.util.StringList;

/**
 * Class that represents the place command. Prints "OK" if successful.
 * 
 * @author Rakan Zeid Al Masri
 * @version 1.0
 */

public class PlaceCommand extends Command {

    /**
     * Constructor for the command.
     * @param gameExecutor The game from which all methods are called.
     */
    
    public PlaceCommand(DawnGameExecutor gameExecutor) {
        super(gameExecutor);
    }

    @Override
    public String getNameofCommand() {
        return "place";
    }

    @Override
    public String run(String parameters) throws InvalidInputException, GameMechanicException {
        InputChecker.checkPlace(parameters);
        DawnGame game = gameExecutor.getGame();
        if (game.isGameOver()) {
            gameExecutor.throwGameOver();
        }
        String[] coordinates = parameters.split(StringList.COORDINATE_SEPARATOR.toString());
        String start = coordinates[0];
        String end = coordinates[1];
        String[] startsplit = start.split(StringList.COMPONENT_SEPARATOR.toString());
        String[] endsplit = end.split(StringList.COMPONENT_SEPARATOR.toString());
        int startm = Integer.parseInt(startsplit[0]);
        int startn = Integer.parseInt(startsplit[1]);
        int endm = Integer.parseInt(endsplit[0]);
        int endn = Integer.parseInt(endsplit[1]);
        gameExecutor.place(startm, startn, endm, endn);
        if (game.getCurrentGameStage().getNaturePiece() == null) {
            throw new InvalidInputException("super strange.");
        }
        Cell cellofnaturepiece = game.getBoard()
                .getCellofPiece(game.getCurrentGameStage().getNaturePiece());
        if (game.areThereNoFreeSpaces(cellofnaturepiece)) { // When (iii) is skipped.
                game.setRolled(false); // Set the roll to false, because a round now consists of roll and place.
                game.getCurrentGameStage().goToNextRound();
                if (game.isStageOver()) {
                    game.goToNextStage();
                }
        }
        game.setPlaced(true);
        return StringList.OK.toString();
    }
}
