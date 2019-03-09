package edu.kit.informatik.commands;

import edu.kit.informatik.data.DawnGame;
import edu.kit.informatik.exceptions.GameMechanicException;
import edu.kit.informatik.exceptions.InvalidInputException;

/**
 * Class that represents the print command.
 * 
 * @author Rakan Zeid Al Masri
 * @version 1.0
 */
public class PrintCommand extends Command {

    /**
     * Constructor for the command.
     * @param gameExecutor The game from which all methods are called.
     */
    public PrintCommand(final DawnGame gameExecutor) {
        super(gameExecutor);
    }
 

    @Override
    public String getNameofCommand() {
       return "print";
    }

    @Override
    public String run(String parameters) throws InvalidInputException, GameMechanicException {
        return gameExecutor.print();
    }
}
