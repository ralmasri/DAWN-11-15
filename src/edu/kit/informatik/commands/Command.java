package edu.kit.informatik.commands;

import edu.kit.informatik.data.DawnGame;
import edu.kit.informatik.ui.CommandInterface;

/**
 * Abstract command class that stores the game executor.
 * 
 * 
 * @author Rakan Zeid Al Masri
 * @version 1.0
 */
public abstract class Command implements CommandInterface {

    /**
     * The game executor from which all methods are called.
     */
    protected DawnGame gameExecutor;
    
    /**
     * Constructor for the game to execute commands on.
     * @param gameExecutor The game executor from which all methods are called.
     */
    protected Command(final DawnGame gameExecutor) {
        this.gameExecutor = gameExecutor;
    }
}
