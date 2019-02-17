package edu.kit.informatik.commands;

import edu.kit.informatik.data.DawnGame;
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
    
    protected DawnGame game;
    
    /**
     * Constructor for the game to execute commands on.
     * @param game The game from which all methods are called.
     */
    
    protected Command(final DawnGame game) {
        this.game = game;
    }
}
