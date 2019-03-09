package edu.kit.informatik.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.commands.PlaceCommand;
import edu.kit.informatik.commands.MoveCommand;
import edu.kit.informatik.commands.PrintCommand;
import edu.kit.informatik.commands.ResetCommand;
import edu.kit.informatik.commands.RollCommand;
import edu.kit.informatik.commands.SetvcCommand;
import edu.kit.informatik.commands.ShowResultCommand;
import edu.kit.informatik.commands.StateCommand;
import edu.kit.informatik.data.DawnGame;
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

    /** Commands that don't have any parameters. */
    private static final Collection<String> NO_PARAMETER_COMMANDS = new ArrayList<>(
            Arrays.asList("reset", "show-result", "print"));
    /** The command used to terminate the program. */
    private static final String QUIT_COMMAND = "quit";

    /**
     * Main method to run the program.
     * 
     * @param args Arguments.
     */
    public static void main(String[] args) {
        final DawnGame gameExecutor = new DawnGame();
        final Collection<CommandInterface> commands = initializeAllCommands(gameExecutor);
        String input = Terminal.readLine();
        while (!input.equals(QUIT_COMMAND)) {
            int numberofSpaces = input.length() - input.replaceAll(" ", "").length();
            try {
                if (input.isEmpty() || input.trim().isEmpty()) { // If no input or input with only spaces was entered.
                    throw new InvalidInputException("you must enter a command.");
                }
                final String[] inputArray = input.split(StringList.COMMAND_SEPARATOR.toString());
                // Too many spaces entered or spaces entered for commands without parameters.
                if (numberofSpaces > 1 || (numberofSpaces != 0 && NO_PARAMETER_COMMANDS.contains(inputArray[0]))) {
                    throw new InvalidInputException("only one white space is allowed in the input and "
                            + "it's between a command and its parameters.");
                }
                final CommandInterface command = commands
                        .stream()
                        .filter(order -> order.getNameofCommand().equals(inputArray[0]))
                        .findAny()
                        .orElseThrow(() -> new InvalidInputException(StringList.COMMAND_DOESNT_EXIST.toString()));
                final String parameters = getParameters(inputArray, command);
                Terminal.printLine(command.run(parameters));
            } catch (GameMechanicException | InvalidInputException exception) {
                Terminal.printError(exception.getMessage());
            }
            input = Terminal.readLine();
        }

    }

    /**
     * Initializes all the commands.
     * 
     * @param gameExecutor The game that has all the methods to play the game.
     * @return The list of commands.
     */
    private static Collection<CommandInterface> initializeAllCommands(final DawnGame gameExecutor) {
        return Arrays.asList(new MoveCommand(gameExecutor), new PlaceCommand(gameExecutor),
                new PrintCommand(gameExecutor), new ResetCommand(gameExecutor), new RollCommand(gameExecutor),
                new SetvcCommand(gameExecutor), new StateCommand(gameExecutor), new ShowResultCommand(gameExecutor));
    }

    /**
     * Getter method for parameters.
     * 
     * @param inputArray The array of inputs after being split by the space in the middle.
     * @param command    The command that the user input.
     * @return The parameters or an empty string, if the command doesn't require
     *         parameters.
     * @throws InvalidInputException If the user input  wrong number of parameters.
     */
    private static String getParameters(final String[] inputArray, final CommandInterface command)
            throws InvalidInputException {
        // The input can be a command and its parameter or just a command.
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
