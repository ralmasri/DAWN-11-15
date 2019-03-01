package edu.kit.informatik.commands;

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
    
    private int getCountofColon(String str) {
        int count = 0;

        for (int i = 0; i < str.length(); i++)
        {    if (str.charAt(i) == ':')
                count++;
        }

        return count;
    }
    
    /**
     * This method makes sure that all the moves given by a user are valid.
     * @param parameters The moves.
     * @param nature The current Nature piece.
     * @throws InvalidInputException Invalid move to certain position.
     * @throws GameMechanicException  If the game is over, if the move is diagonal or if the destination is occupied.
     */
    private void checkMovesValidity(String parameters, Piece nature) 
            throws InvalidInputException, GameMechanicException {
        String origin = "";
        int coordinateCount = getCountofColon(parameters) + 1; // The colons are less than the number of moves by 1.
        String[] inputSplit = parameters.split(StringList.COORDINATE_SEPARATOR.toString());
        for (int i = 0; i < coordinateCount; i++) {
            if (i == 0) {
                origin = nature.getName(); // The first move is from the original position of the Nature piece.
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
    public String run(String parameters) throws InvalidInputException, GameMechanicException {
        DawnGame game = gameExecutor.getGame();
        if (game.isGameOver()) {
            gameExecutor.throwGameOver();
        }
        Piece nature = game.getCurrentGameStage().getNaturePiece();
        Cell cellofNaturePiece = game.getBoard().getCellofPiece(nature);
        if (game.areThereNoFreeSpaces(cellofNaturePiece)) { // The case where (iii) is skipped.
            throw new GameMechanicException("a move is not possible, because "
                    + nature.getName() + " is blocked from all directions.");
        }
        if (!game.hasPlaced()) {
            throw new GameMechanicException("you must first place a Mission Control piece before moving.");
        }
        // One less than the piece length, because parser already includes a minimum of one move. 
        InputChecker.checkMove(parameters, game.getCurrentpiecelength() - 1);
        checkMovesValidity(parameters, nature);
        int coordinateCount = getCountofColon(parameters) + 1; // The colons are less than the number of moves by 1.
        String[] inputSplit = parameters.split(StringList.COORDINATE_SEPARATOR.toString());
        for (int i = 0; i < coordinateCount; i++) { // Executes all the moves.
            gameExecutor.move(inputSplit[i]);
        }
        game.getCurrentGameStage().goToNextRound();
        if (game.isStageOver()) {
            game.goToNextStage();
        }
        game.setPlaced(false);
        game.setRolled(false);
        return StringList.OK.toString();
    } 
}
