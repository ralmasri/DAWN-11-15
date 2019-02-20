package edu.kit.informatik.commands;

import edu.kit.informatik.Terminal;
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
    public void run(String parameters) throws InvalidInputException, GameMechanicException {
        InputChecker.checkPlace(parameters);
        String[] coordinates = parameters.split(StringList.COORDINATE_SEPARATOR.toString());
        String start = coordinates[0];
        String end = coordinates[1];
        String[] startsplit = start.split(StringList.COMPONENT_SEPARATOR.toString());
        String[] endsplit = end.split(StringList.COMPONENT_SEPARATOR.toString());
        int startm = Integer.parseInt(startsplit[0]);
        int startn = Integer.parseInt(startsplit[1]);
        int endm = Integer.parseInt(endsplit[0]);
        int endn = Integer.parseInt(endsplit[1]);
        Terminal.printLine(gameExecutor.place(startm, startn, endm, endn));
        Cell cellofnaturepiece = gameExecutor.getGame().getBoard()
                .getCellofPiece(gameExecutor.getGame().getCurrentGameStage().getNaturePiece());
        if (gameExecutor.getGame().areThereNoFreeSpaces(cellofnaturepiece)) {
                gameExecutor.getGame().setRolled(false);
                gameExecutor.getGame().getCurrentGameStage().goToNextRound();
                if (gameExecutor.getGame().getCurrentGameStage().getRound() == DawnGame.getPhaseIsDone()) {
                    gameExecutor.getGame().getFinishedstages().add(gameExecutor.getGame().getCurrentGameStage());
                    gameExecutor.getGame().getStages().remove();
                }
        }
        gameExecutor.getGame().setPlaced(true);
    }
}
