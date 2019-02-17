package edu.kit.informatik.commands;

import edu.kit.informatik.Terminal;


import edu.kit.informatik.data.DawnGame;
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
     * @param game The game from which all methods are called.
     */
    
    public MoveCommand(DawnGame game) {
        super(game);
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

    @Override
    public void run(String parameters) throws InvalidInputException, GameMechanicException {
        if (!game.isHasplaced()) {
            throw new GameMechanicException("you must place a piece before moving a Nature piece.");
        }
        InputChecker.checkMove(parameters, game.getCurrentpiecelength() - 1);
        int coordinatecount = getCountofColon(parameters) + 1;
        String[] inputsplit = parameters.split(":");
        String origin = "";
        for (int i = 0; i < coordinatecount; i++) {
            if (i == 0) {
                origin = "nature";
            } else {
                origin = inputsplit[i - 1];
            }
            if (!game.checkMovementValidity(origin, inputsplit[i])) {
                String[] coordinatesplit = inputsplit[i].split(";");
                int m = Integer.parseInt(coordinatesplit[0]);
                int n = Integer.parseInt(coordinatesplit[1]);
                throw new InvalidInputException("a movement to " + "field " + m + ";" + n + " is not valid.");
            }
            
        }
        for (int i = 0; i < coordinatecount; i++) {
            game.move(inputsplit[i]);
        }
        game.getCurrentGameStage().goToNextRound();
        Terminal.printLine(StringList.OK.toString());
        if (game.getCurrentGameStage().getRound() == 7) {
            game.getFinishedstages().add(game.getCurrentGameStage());
            game.getStages().remove();
        }
        game.setPlaced(false);
        game.setRolled(false);
    } 
}
