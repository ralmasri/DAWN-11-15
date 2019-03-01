package edu.kit.informatik.ui;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import edu.kit.informatik.Terminal;
import edu.kit.informatik.commands.PlaceCommand;
import edu.kit.informatik.commands.MoveCommand;
import edu.kit.informatik.commands.PrintCommand;
import edu.kit.informatik.commands.ResetCommand;
import edu.kit.informatik.commands.RollCommand;
import edu.kit.informatik.commands.SetvcCommand;
import edu.kit.informatik.commands.ShowResultCommand;
import edu.kit.informatik.commands.StateCommand;
import edu.kit.informatik.data.DawnGameExecutor;
import edu.kit.informatik.exceptions.GameMechanicException;
import edu.kit.informatik.exceptions.InvalidInputException;
import edu.kit.informatik.util.StringList;

/**
 * Main class that executes all the commands and runs the program.
 * 
 * @author Rakan Zeid Al Masri
 * @version 1.0
 */

public class Main {

    /**
     * List of commands that don't have any parameters.
     */
    private static final List<String> NO_PARAMETER_COMMANDS = new ArrayList<String>(
            Arrays.asList("reset", "show-result", "print"));
    /**
     * The command used to terminate the program.
     */
    private static final String QUIT_COMMAND = "quit";

    /**
     * Main method to run the program.
     * 
     * @param args Arguments.
     */

    public static void main(String[] args) {
        final DawnGameExecutor gameExecutor = new DawnGameExecutor();
        final List<CommandInterface> commands = Main.initializeAllCommands(gameExecutor);
        String input = Terminal.readLine();
        while (!input.equals(QUIT_COMMAND)) {
            int numberofSpaces = input.length() - input.replaceAll(" ", "").length();
            try {
                if (input.isEmpty() || input.trim().isEmpty()) { // If no input or input with only spaces was entered.
                    throw new IllegalArgumentException("you must enter a command.");
                }
                final String[] inputArray = input.split(StringList.COMMAND_SEPARATOR.toString());
                // Too many spaces entered or spaces entered for commands without parameters.
                if (numberofSpaces > 1 || (numberofSpaces != 0 && NO_PARAMETER_COMMANDS.contains(inputArray[0]))) {
                    throw new InvalidInputException("only one white space is allowed in the input and "
                            + "it's between a command and its parameters.");
                }
                final CommandInterface command = commands
                        .stream()
                        .filter(c -> c.getNameofCommand().equals(inputArray[0]))
                        .findAny()
                        .orElseThrow(() -> new InvalidInputException(StringList.COMMAND_DOESNT_EXIST.toString()));
                final String parameters = Main.getParameters(inputArray, command);
                Terminal.printLine(command.run(parameters));
            } catch (IllegalArgumentException | GameMechanicException | InvalidInputException e) {
                Terminal.printError(e.getMessage());
            }
            input = Terminal.readLine();
        }

    }

    /**
     * Initializes all the commands as a list.
     * 
     * @param gameExecutor The game that has all the methods to play the game.
     * @return The list of commands.
     */

    private static List<CommandInterface> initializeAllCommands(final DawnGameExecutor gameExecutor) {
        return Arrays.asList(new MoveCommand(gameExecutor), new PlaceCommand(gameExecutor),
                new PrintCommand(gameExecutor), new ResetCommand(gameExecutor), new RollCommand(gameExecutor),
                new SetvcCommand(gameExecutor), new StateCommand(gameExecutor), new ShowResultCommand(gameExecutor));
    }

    /**
     * Getter method for parameters.
     * 
     * @param inputArray The array of inputs after being split.
     * @param command    The command that the user input.
     * @return The parameters or an empty string, if the command doesn't require
     *         parameters.
     * @throws InvalidInputException If the user input  wrong number of parameters.
     */

    private static String getParameters(final String[] inputArray, final CommandInterface command)
            throws InvalidInputException {
        if (inputArray.length > 2) {
            throw new InvalidInputException("wrong input format.");
        }
        if (inputArray.length == 1) {

            // The only commands that have no parameters.

            if (NO_PARAMETER_COMMANDS.contains(command.getNameofCommand())) {
                return "";
            } else {
                throw new InvalidInputException("you have to give parameters.");
            }
        }
        return inputArray[1];
    }
}
