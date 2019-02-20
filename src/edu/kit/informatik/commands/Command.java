package edu.kit.informatik.commands;

import edu.kit.informatik.data.DawnGameExecutor;
import edu.kit.informatik.ui.CommandInterface;

/**
 * Abstract command class that stores the game.
 * 
 * 
 * @author Rakan Zeid Al Masri
 * @version 1.0
 */

public abstract class Command implements CommandInterface {

    /**
     * The game on which all methods are to be applied.
     */
    
    protected DawnGameExecutor gameExecutor;
    
    /**
     * Constructor for the game to execute commands on.
     * @param gameExecutor The game from which all methods are called.
     */
    
    protected Command(final DawnGameExecutor gameExecutor) {
        this.gameExecutor = gameExecutor;
    }
}
