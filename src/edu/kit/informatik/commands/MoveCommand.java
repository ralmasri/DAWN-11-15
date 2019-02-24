package edu.kit.informatik.commands;

import edu.kit.informatik.Terminal;

import edu.kit.informatik.data.Cell;
import edu.kit.informatik.data.DawnGame;
import edu.kit.informatik.data.DawnGameExecutor;
import edu.kit.informatik.data.Piece;
import edu.kit.informatik.exceptions.GameMechanicException;
import edu.kit.informatik.exceptions.InvalidInputException;
import edu.kit.informatik.ui.InputChecker;
import edu.kit.informatik.util.StringList;

/**
 * Class that represents the move command. When the move command is successful, OK will be printed.
 * 
 * @author Rakan Zeid Al Masri
 * @version 1.0
 *
 */

public class MoveCommand extends Command {

    /**
     * Constructor for the command.
     * @param gameExecutor The game from which all methods are called.
     */
    
    public MoveCommand(DawnGameExecutor gameExecutor) {
        super(gameExecutor);
    }
    
    /**
     * Helper method to get the number of colons in a string.
     * @param str The string to check.
     * @return The number of colons in the string.
     */
    
    private static int getCountofColon(String str) {
        int count = 0;

        for (int i = 0; i < str.length(); i++)
        {    if (str.charAt(i) == ':')
                count++;
        }

        return count;
    }

    @Override
    public String getNameofCommand() {
        return "move";
    }
    
    /**
     * Since the move method only represents one elementary step, this run method handles the logic for
     * multiple moves.
     */
    @Override
    public void run(String parameters) throws InvalidInputException, GameMechanicException {
        if (!gameExecutor.getGame().hasPlaced()) {
            throw new GameMechanicException("you must first place a Mission Control piece before moving.");
        }
        InputChecker.checkMove(parameters, gameExecutor.getGame().getCurrentpiecelength() - 1);
        int coordinateCount = getCountofColon(parameters) + 1;
        String[] inputSplit = parameters.split(StringList.COORDINATE_SEPARATOR.toString());
        String origin = "";
        Piece nature = gameExecutor.getGame().getCurrentGameStage().getNaturePiece();
        Cell cellofNaturePiece = gameExecutor.getGame().getBoard().getCellofPiece(nature);
        if (gameExecutor.getGame().areThereNoFreeSpaces(cellofNaturePiece)) { // The case where (iii) is skipped.
            if (!parameters.isEmpty()) {
                throw new GameMechanicException("a move is not possible, because "
                        + nature.getName() + " is blocked from all directions.");
            } 
        }
        for (int i = 0; i < coordinateCount; i++) {
            if (i == 0) {
                origin = "nature"; // The first move has the origin as the Nature piece.
            } else {
                origin = inputSplit[i - 1]; // Every other move, has the origin as the move parameters before it.
            }
            if (!gameExecutor.checkMovementValidity(origin, inputSplit[i])) {
                String[] coordinateSplit = inputSplit[i].split(StringList.COMPONENT_SEPARATOR.toString());
                int m = Integer.parseInt(coordinateSplit[0]);
                int n = Integer.parseInt(coordinateSplit[1]);
                throw new InvalidInputException("a movement to " + "field " + m 
                        + StringList.COMPONENT_SEPARATOR.toString() + n + " is not valid.");
            }
            
        }
        for (int i = 0; i < coordinateCount; i++) { // Executes all the moves.
            gameExecutor.move(inputSplit[i]);
        }
        gameExecutor.getGame().getCurrentGameStage().goToNextRound();
        Terminal.printLine(StringList.OK.toString());
        // When a stage is over.
        if (gameExecutor.getGame().getCurrentGameStage().getRound() == DawnGame.getPhaseIsDone()) {
            gameExecutor.getGame().getFinishedstages().add(gameExecutor.getGame().getCurrentGameStage());
            gameExecutor.getGame().getStages().remove();
        }
        gameExecutor.getGame().setPlaced(false);
        gameExecutor.getGame().setRolled(false);
    } 
}
