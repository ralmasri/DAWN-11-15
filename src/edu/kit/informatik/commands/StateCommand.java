package edu.kit.informatik.commands;

import edu.kit.informatik.Terminal;

import edu.kit.informatik.data.DawnGame;
import edu.kit.informatik.ui.InputParser;
import edu.kit.informatik.exceptions.*;

/**
 * Class the represents the state command. 
 * Prints "-" if there is no piece on the cell we are checking, "C" for Ceres piece, "V" for Vesta piece,
 * and "+" for a Mission Control piece.
 * 
 * @author Rakan Zeid Al Masri
 * @version 1.0
 */

public class StateCommand extends Command {

    /**
     * Constructor for the command.
     * @param game The game from which all methods are called.
     */
    
    public StateCommand(final DawnGame game) {
        super(game);
    }
    
    @Override
    public String getNameofCommand() {
        return "state" ;
    }
    
    @Override
    public void run(String parameters) throws InvalidInputException, GameMechanicException{
        InputParser.parseCoordinate(parameters);
        String[] inputsplit = parameters.split(";");
        int mcoordinate = Integer.parseInt(inputsplit[0]);
        int ncoordinate = Integer.parseInt(inputsplit[1]);
        Terminal.printLine(game.state(mcoordinate, ncoordinate));
    }
}
