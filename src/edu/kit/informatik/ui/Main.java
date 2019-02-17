package edu.kit.informatik.ui;


import java.util.List;

import java.util.Arrays;
import edu.kit.informatik.Terminal;
import edu.kit.informatik.commands.*;
import edu.kit.informatik.data.DawnGame;
import edu.kit.informatik.exceptions.*;
import edu.kit.informatik.util.StringList;

/**
 * Main class that executes all the commands and runs the program.
 * 
 * @author Rakan Zeid Al Masri
 * @version 1.0
 */

public class Main {

    /**
     * Main method to run the program.
     * @param args Arguments.
     */
    
    public static void main(String[] args) {
        final DawnGame game = new DawnGame();
        final List<CommandInterface> commands = Main.initializeAllCommands(game);
        for (String input = Terminal.readLine(); !input.equals("quit"); input = Terminal.readLine()) {
            final String[] inputArray = input.split(StringList.COMMAND_SEPARATOR.toString());
            try {
                final CommandInterface command = commands.stream()
                        .filter(c -> c.getNameofCommand().equals(inputArray[0]))
                        .findAny()
                        .orElseThrow(() -> new InvalidInputException(StringList.COMMAND_DOESNT_EXIST.toString()));
                final String parameters = Main.getParameters(inputArray, command);
                command.run(parameters);
            } catch (ArrayIndexOutOfBoundsException e) {
                Terminal.printError("you must enter a command.");
            } catch (GameMechanicException | InvalidInputException e) {
                Terminal.printError(e.getMessage());
            }
        }
    }
    
    /**
     * Initializes all the commands as a list.
     * 
     * @param game The game that has all the methods to play the game.
     * @return The list of commands.
     */
    
    private static List<CommandInterface> initializeAllCommands(final DawnGame game) {
        return Arrays.asList(new MoveCommand(game), new PlaceCommand(game), new PrintCommand(game),
                new ResetCommand(game), new RollCommand(game), new SetvcCommand(game), new StateCommand(game),
                new ShowResultCommand(game));
    }
    
    /**
     * Getter method for parameters.
     * 
     * @param inputArray The array of inputs after being split.
     * @param command The command that the user input.
     * @return The parameters or an empty string, if the command doesn't require parameters.
     * @throws InvalidInputException If the user inputed wrong number of parameters.
     */
    
    private static String getParameters(final String[] inputArray, final CommandInterface command) 
            throws InvalidInputException {
        if (inputArray.length > 2) {
            throw new InvalidInputException("wrong input format.");
        }
        if (inputArray.length == 1) {
            if (command.getNameofCommand().equals("reset") || command.getNameofCommand().equals("print") 
                    ||  command.getNameofCommand().equals("show-result")) {
                return "";
            } else {
                throw new InvalidInputException("you have to give parameters.");
            }
        }
        return inputArray[1];
    }
}
