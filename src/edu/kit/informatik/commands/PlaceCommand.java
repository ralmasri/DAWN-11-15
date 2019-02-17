package edu.kit.informatik.commands;

import edu.kit.informatik.Terminal;


import edu.kit.informatik.data.DawnGame;
import edu.kit.informatik.exceptions.GameMechanicException;
import edu.kit.informatik.exceptions.InvalidInputException;
import edu.kit.informatik.ui.InputChecker;

/**
 * Class that represents the place command. Prints "OK" if successful.
 * 
 * @author Rakan Zeid Al Masri
 * @version 1.0
 */

public class PlaceCommand extends Command {

    /**
     * Constructor for the command.
     * @param game The game from which all methods are called.
     */
    
    public PlaceCommand(DawnGame game) {
        super(game);
    }

    @Override
    public String getNameofCommand() {
        return "place";
    }

    @Override
    public void run(String parameters) throws InvalidInputException, GameMechanicException {
        InputChecker.checkPlace(parameters);
        String[] coordinates = parameters.split(":");
        String start = coordinates[0];
        String end = coordinates[1];
        String[] startsplit = start.split(";");
        String[] endsplit = end.split(";");
        int startm = Integer.parseInt(startsplit[0]);
        int startn = Integer.parseInt(startsplit[1]);
        int endm = Integer.parseInt(endsplit[0]);
        int endn = Integer.parseInt(endsplit[1]);
        Terminal.printLine(game.place(startm, startn, endm, endn));
        game.setPlaced(true);
    }
}
