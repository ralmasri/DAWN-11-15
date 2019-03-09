package edu.kit.informatik.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.kit.informatik.exceptions.GameMechanicException;
import edu.kit.informatik.exceptions.InvalidInputException;
import edu.kit.informatik.util.StringList;

/**
 * Contains all the methods that allow someone to play DAWN 11/15.
 * 
 * @author Rakan Zeid Al Masri
 * @version 1.0
 */
public class DawnGame {

    /** The game. */
    private GameInitializer game;

    /** Constructor of the executor of the DAWN game */
    public DawnGame() {
        this.game = new GameInitializer();
    }

    /**
     * Returns a symbol based on the what is on the cell.
     * 
     * @param mcomponent The m-component of the cell.
     * @param ncomponent The n-component of the cell.
     * @return '-' if the cell is empty, 'V' for Vesta, 'C' for Ceres or '+' if a
     *         Mission Control piece is on the cell.
     * @throws InvalidInputException If the coordinate is not on the board.
     */
    public String state(int mcomponent, int ncomponent) {
        Cell cell = game.getBoard().getCell(mcomponent, ncomponent);
        assert cell != null; // Regex should have vetted the out of board coordinates.
        return cell.toString();
    }

    /**
     * Prints the board as a grid.
     * 
     * @return Textual representation of the board.
     */
    public String print() {
        return game.getBoard().toString();
    }

    /**
     * Places the current Nature piece somewhere on the board.
     * @param mcomponent The m-component of the cell we want to place the Nature
     *                   piece on.
     * @param ncomponent The n-component of the cell we want to place the Nature
     *                   piece on.
     * @throws GameMechanicException If the Nature piece for this phase has already
     *                               been placed, if the game is over or if the cell
     *                               is occupied.
     */
    public void setVC(int mcomponent, int ncomponent) throws GameMechanicException {
        Cell cell = game.getBoard().getCell(mcomponent, ncomponent);
        assert cell != null; // Regex should have vetted the out of board coordinates.
        if (cell.isOccupied())
            throw new GameMechanicException("the cell is already occupied");
        cell.setPiece(game.getCurrentGameStage().getNaturePiece());
        game.getCurrentGameStage().placeVC();
    }

    /**
     * Method for rolling the die.
     * @param symbol The number of the roll or DAWN.
     * @throws GameMechanicException If the die has been already rolled during the current round.
     */
    public void roll(String symbol) throws GameMechanicException {
        if (game.hasRolled()) {
            throw new GameMechanicException("you have already rolled.");
        }
        if (symbol.equals(StringList.DAWN.toString())) {
            game.setCurrentRoll(GameInitializer.getDawnNumber());
        } else {
            game.setCurrentRoll(Integer.parseInt(symbol));
        }
    }

    /**
     * The main method to place a Mission Control piece.
     * @param headmcomponent The m-component of the head.
     * @param headncomponent The n-component of the head.
     * @param tailmcomponent The m-component of the tail.
     * @param tailncomponent The n-component of the tail.
     * @throws GameMechanicException If the game is over, if the die hasn't been 
     * rolled yet or if one or more cells are occupied.
     * @throws InvalidInputException Invalid coordinates.
     */
    public void place(int headmcomponent, int headncomponent, int tailmcomponent, int tailncomponent)
            throws GameMechanicException, InvalidInputException {
        int length = computeCoordLength(headmcomponent, headncomponent, tailmcomponent, tailncomponent);
        if (!game.hasRolled())
            throwRolled();
        if (headmcomponent != tailmcomponent && headncomponent != tailncomponent) {
            throwInvalidCoordinate(" Only horizontal or vertical placement is allowed.");
        }
        int roll = game.getCurrentRoll();
        assert !getPossiblePlacements(roll).isEmpty();
        if (!getPossiblePlacements(roll).contains(length)) {
            throwInvalidCoordinate(" These are the possible placements by length: "
                    + getPossiblePlacements(roll).toString().replace("[", "").replace("]", "").trim() + ".");
        }
        if (length == GameInitializer.getDawnNumber() && (!game.getBoard().isInBounds(headmcomponent, headncomponent)
                || !game.getBoard().isInBounds(tailmcomponent, tailncomponent))) {
            // If DAWN's head or tail isn't on the board.
            placeDawn(headmcomponent, headncomponent, tailmcomponent, tailncomponent);
        } else { // Normal placement for DAWN and other pieces.
            placeExecutor(headmcomponent, headncomponent, tailmcomponent, tailncomponent);
        }
        game.setCurrentpiecelength(length);
    }

    /**
     * Gets all possible placements (next highest and next lowest or the roll itself).
     * @param roll The current roll.
     * @return A list of all possible placements.
     * @throws GameMechanicException If the game is over, so all the phases are removed from the queue.
     */
    private List<Integer> getPossiblePlacements(int roll) throws GameMechanicException {
        if (!game.getCurrentGameStage().isMCPlaced(roll)) // If the piece of length roll hasn't been placed.
            return new ArrayList<>(Arrays.asList(roll)); // Only option is to place piece of length roll.
        int loweroption = 0; // next lowest.
        int higheroption = 0; // next highest.
        List<Integer> possiblePlacements = new ArrayList<>();
        for (int highersymbol = roll + 1; highersymbol <= GameInitializer.getDawnNumber(); highersymbol++) {
            if (!game.getCurrentGameStage().isMCPlaced(highersymbol)) { // The next higher that isn't placed.
                higheroption = highersymbol;
                break;
            }
        }
        for (int lowersymbol = roll - 1; lowersymbol >= GameInitializer.getMinMcLength(); lowersymbol--) {
            if (!game.getCurrentGameStage().isMCPlaced(lowersymbol)) { // The next lower that isn't placed.
                loweroption = lowersymbol;
                break;
            }
        }
        if (loweroption != 0) { // A.K.A when lower options exist.
            possiblePlacements.add(loweroption);
        }
        if (higheroption != 0) { // A.K.A when higher options exist.
            possiblePlacements.add(higheroption);
        }
        return possiblePlacements;
    }

    /**
     * Calculates the length of a piece given its head and tail coordinates.
     * @param headmcomponent The head's m-component.
     * @param headncomponent The head's n-component.
     * @param tailmcomponent The tail's m-component.
     * @param tailncomponent The tail's n-component.
     * @return The length of the piece.
     * @throws InvalidInputException If a piece's coordinates aren't valid
     */
    private int computeCoordLength(int headmcomponent, int headncomponent, int tailmcomponent, int tailncomponent)
            throws InvalidInputException {
        int length;
        int properLength; // The length including the tail and head cells (which is why 1 is added below)
        if (headmcomponent == tailmcomponent) { // If the piece is to be placed horizontally.
            length = tailncomponent - headncomponent;
        } else { // If the piece is to be placed vertically.
            length = tailmcomponent - headmcomponent;
        }
        properLength = (length < 0) ? (Math.abs(length) + 1) : (length + 1);
        // If the head and/or tail aren't/isn't on the board and properlength != 7.
        if ((!game.getBoard().isInBounds(headmcomponent, headncomponent)
                && !game.getBoard().isInBounds(tailmcomponent, tailncomponent))
                || (!game.getBoard().isInBounds(headmcomponent, headncomponent)
                        && properLength != GameInitializer.getDawnNumber())
                || (!game.getBoard().isInBounds(tailmcomponent, tailncomponent)
                        && properLength != GameInitializer.getDawnNumber())) {
            throwInvalidCoordinate("");
        }
        return properLength;
    }

    /**
     * Checks if any cells between a start and destination are occupied.
     * @param start           The start component.
     * @param destination     The destination component.
     * @param commoncomponent For vertical placement, the n-component. For horizontal, the m-component.
     * @param ismcomp         If the common component is a m-component or not.
     * @return If a cell is occupied, returns an error message, otherwise empty String.
     */
    private String isOccupiedIterator(int start, int destination, int commoncomponent, boolean ismcomp) {
        if (ismcomp) { // Horizontal.
            for (int ncomp = start; ncomp <= destination; ncomp++) {
                if (game.getBoard().getCell(commoncomponent, ncomp).isOccupied()) {
                    return String.valueOf(game.getBoard().getCell(commoncomponent, ncomp).getMCoord())
                            + StringList.COMPONENT_SEPARATOR.toString()
                            + String.valueOf(game.getBoard().getCell(commoncomponent, ncomp).getNCoord());
                }
            }
        } else { // Vertical.
            for (int mcomp = start; mcomp <= destination; mcomp++) {
                if (game.getBoard().getCell(mcomp, commoncomponent).isOccupied()) {
                    return String.valueOf(game.getBoard().getCell(mcomp, commoncomponent).getMCoord())
                            + StringList.COMPONENT_SEPARATOR.toString()
                            + String.valueOf(game.getBoard().getCell(mcomp, commoncomponent).getNCoord());
                }
            }
        }
        return "";
    }

    /**
     * Executes the placement for pieces of length 2-6 or DAWN when part of it is
     * not outside the board.
     * @param headmcomponent The head's m-component.
     * @param headncomponent The head's n-component.
     * @param tailmcomponent The tail's m-component.
     * @param tailncomponent The tail's n-component
     * @throws GameMechanicException If a cell is occupied, so the piece cannot be placed there.
     * @throws InvalidInputException If the piece coordinates are not valid.
     */
    private void placeExecutor(int headmcomponent, int headncomponent, int tailmcomponent, int tailncomponent)
            throws GameMechanicException, InvalidInputException {
        int head; // the head component that isn't equal to the tail counterpart.
        int tail; // the tail component that isn't equal to the head counterpart.
        int length = computeCoordLength(headmcomponent, headncomponent, tailmcomponent, tailncomponent);
        boolean isHorizontal = false;
        if (headmcomponent == tailmcomponent) { // horizontally placed.
            head = headncomponent;
            tail = tailncomponent;
            isHorizontal = true;
        } else { // vertically placed.
            head = headmcomponent;
            tail = tailmcomponent;
        }
        int largercomp = (head > tail) ? head : tail;
        int smallercomp = (head > tail) ? tail : head;
        if (isHorizontal) { // Horizontal placement.
            placeHorizontally(smallercomp, largercomp, headmcomponent, length);
        } else { // Vertical placement.
            placeVertically(smallercomp, largercomp, headncomponent, length);
        }
        game.getCurrentGameStage().placeMC(length);
    }

    /**
     * Places a DAWN piece when part of it is not on the board.
     * @param headmcomponent The head m-component.
     * @param headncomponent The head n-component.
     * @param tailmcomponent The tail m-component.
     * @param tailncomponent The tail n-component.
     * @throws GameMechanicException When a cell is occupied, so the piece cannot be placed there.
     */
    private void placeDawn(int headmcomponent, int headncomponent, int tailmcomponent, int tailncomponent)
            throws GameMechanicException {
        int head; // the head component that isn't equal to the tail counterpart.
        int tail; // the tail component that isn't equal to the head counterpart.
        int maxMComp = GameInitializer.getBoardHeight() - 1;
        int maxNComp = GameInitializer.getBoardWidth() - 1;
        boolean isHorizontal = false;
        if (headmcomponent == tailmcomponent) { // horizontally placed.
            head = headncomponent;
            tail = tailncomponent;
            isHorizontal = true;
        } else { // vertically placed.
            head = headmcomponent;
            tail = tailmcomponent;
        }
        int largercomp = (head > tail) ? head : tail;
        int smallercomp = (head > tail) ? tail : head;
        if (isHorizontal) {
            if (largercomp >= GameInitializer.getBoardWidth()) { // largercomp is outside the board.
                placeHorizontally(smallercomp, maxNComp, headmcomponent, GameInitializer.getDawnNumber());
            } else { // largercomp is on the board, in which case the smallercomp is negative.
                // Starts at 0, because smallercomp is negative.
                placeHorizontally(0, largercomp, headmcomponent, GameInitializer.getDawnNumber());
            }
        } else { // Same thing as above here but going vertically.
            if (largercomp >= GameInitializer.getBoardHeight()) { // The largercomp is outside the board.
                placeVertically(smallercomp, maxMComp, headncomponent, GameInitializer.getDawnNumber());
            } else {
             // Starts at 0, because smallercomp is negative.
                placeVertically(0, largercomp, headncomponent, GameInitializer.getDawnNumber());
            }
        }
        game.getCurrentGameStage().placeMC(GameInitializer.getDawnNumber());
    }

    /**
     * Standard method to place a piece horizontally.
     * @param smallerncomp The smaller n-component between the head and tail.
     * @param largerncomp The larger n-component between the head and tail.
     * @param headmcomp Horizontal placement means that the m-component is the same for the tail and head.
     * @param length The length of the piece being placed.
     * @throws GameMechanicException If a cell between the head and tail (inclusive) is occupied.
     */
    private void placeHorizontally(int smallerncomp, int largerncomp, int headmcomp, int length)
            throws GameMechanicException {
        if (!isOccupiedIterator(smallerncomp, largerncomp, headmcomp, true).isEmpty()) {
            throwOccupied(isOccupiedIterator(smallerncomp, largerncomp, headmcomp, true));
        }
        // Fills every cell between the head and tail with the piece.
        for (int ncomp = smallerncomp; ncomp <= largerncomp; ncomp++) {
            game.getBoard().getCell(headmcomp, ncomp).setPiece(game.getCurrentGameStage().getMCPieces().get(length));
        }
    }

    /**
     * Standard method to place a piece vertically.
     * @param smallermcomp The smaller m-component between the head and tail.
     * @param largermcomp The larger m-component between the head and tail.
     * @param headncomp Vertical placement means that the n-component is the same for the tail and head.
     * @param length The length of the piece being placed.
     * @throws GameMechanicException If a cell between the head and tail (inclusive) is occupied.
     */
    private void placeVertically(int smallermcomp, int largermcomp, int headncomp, int length)
            throws GameMechanicException {
        if (!isOccupiedIterator(smallermcomp, largermcomp, headncomp, false).isEmpty()) {
            throwOccupied(isOccupiedIterator(smallermcomp, largermcomp, headncomp, false));
        }
        // Fills every cell between the head and tail with the piece.
        for (int mcomp = smallermcomp; mcomp <= largermcomp; mcomp++) {
            game.getBoard().getCell(mcomp, headncomp).setPiece(game.getCurrentGameStage().getMCPieces().get(length));
        }
    }

    /**
     * Method to move a Nature piece. Represents one elementary move.
     * @param coordinate The coordinate to move the piece to.
     * @throws GameMechanicException If the game is over or a Mission Control piece
     *                               hasn't been placed yet.
     */
    public void move(String coordinate) throws GameMechanicException {
        String[] coordinateSplit = coordinate.split(StringList.COMPONENT_SEPARATOR.toString());
        int mdestination = Integer.parseInt(coordinateSplit[0]);
        int ndestination = Integer.parseInt(coordinateSplit[1]);
        Piece naturePiece = game.getCurrentGameStage().getNaturePiece();
        Cell cellofNaturePiece = game.getBoard().getCellofPiece(naturePiece);
        cellofNaturePiece.setPiece(null);
        Cell destinationCell = game.getBoard().getCell(mdestination, ndestination);
        destinationCell.setPiece(naturePiece);
    }

    /**
     * Checks if a movement is valid or not.
     * @param origin      The current cell we are moving from.
     * @param destination The destination cell we are moving to.
     * @return true if the move is valid, otherwise false.
     * @throws GameMechanicException If the game is over, if the move is diagonal or
     *                               if the destination is occupied.
     */
    public boolean checkMovementValidity(String origin, String destination)
            throws GameMechanicException {
        int morigin = 0;
        int norigin = 0;
        Cell originCell;
        Piece nature = game.getCurrentGameStage().getNaturePiece();
        Cell natureCell = game.getBoard().getCellofPiece(nature);
        if (origin.equals(nature.getName())) { // First movement.
            originCell = natureCell;
            morigin = originCell.getMCoord();
            norigin = originCell.getNCoord();
        } else { // Every other movement.
            String[] originSplit = origin.split(StringList.COMPONENT_SEPARATOR.toString());
            morigin = Integer.parseInt(originSplit[0]);
            norigin = Integer.parseInt(originSplit[1]);
            originCell = game.getBoard().getCell(morigin, norigin);
        }
        String[] destinationSplit = destination.split(StringList.COMPONENT_SEPARATOR.toString());
        int mdestination = Integer.parseInt(destinationSplit[0]);
        int ndestination = Integer.parseInt(destinationSplit[1]);
        if (morigin == mdestination && norigin == ndestination) { // Not a movement.
            throw new GameMechanicException(game.getCurrentGameStage().getNaturePiece().getName()
                    + " is already there. You must move somewhere else.");
        }
        Cell destinationCell = game.getBoard().getCell(mdestination, ndestination);
        if (!(destinationCell.equals(natureCell)) && destinationCell.isOccupied()) {
            throw new GameMechanicException("a piece occupies " + mdestination
                    + StringList.COMPONENT_SEPARATOR.toString() + ndestination + ", so you cannot move there.");
        }
        return isMoveElementary(mdestination, ndestination, originCell);
    }

    /**
     * Method to check if a move is one step (either horizontally or vertically).
     * @param mdestination The m-component of the destination cell.
     * @param ndestination The n-component of the destination cell.
     * @param originCell The origin cell.
     * @return true if the move is valid, otherwise false.
     * @throws GameMechanicException If the movement is diagonal.
     */
    private boolean isMoveElementary(int mdestination, int ndestination, Cell originCell) throws GameMechanicException {
        int mpiece = originCell.getMCoord();
        int npiece = originCell.getNCoord();
        int mdifference = Math.abs(mpiece - mdestination);
        int ndifference = Math.abs(npiece - ndestination);
        // A move can only be horizontal or vertical and only one step.
        if (mpiece != mdestination && npiece != ndestination) {
            throw new GameMechanicException("a piece can only move one step either horizontally or vertically.");
        }
        return (mdifference == GameInitializer.getMoveStepsAllowed()) 
                ^ (ndifference == GameInitializer.getMoveStepsAllowed());
    }

    /**
     * Gets the result if the game is over.
     * 
     * @return The result of the game.
     * @throws GameMechanicException If the game isn't over yet.
     */
    public String showresult() throws GameMechanicException {
        // First stage has index 0, because it was the first one to finish (and thus be added to finishedStages).
        GameStage firstStage = game.getFinishedStages().get(0);
        GameStage secondStage = game.getFinishedStages().get(1);
        Cell vesta = game.getBoard().getCellofPiece(firstStage.getNaturePiece());
        Cell ceres = game.getBoard().getCellofPiece(secondStage.getNaturePiece());
        return String.valueOf(FreeSpaces.computeResult(vesta, ceres, game.getBoard()));
    }

    private void throwInvalidCoordinate(String extraMessage) throws InvalidInputException {
        throw new InvalidInputException(StringList.INVALID_COORDINATES.toString() + extraMessage);
    }

    /** @throws GameMechanicException When game is over. */
    public void throwGameOver() throws GameMechanicException {
        throw new GameMechanicException(StringList.GAME_OVER.toString());
    }

    private void throwOccupied(String coordinate) throws GameMechanicException {
        throw new GameMechanicException(coordinate + StringList.IS_OCCUPIED.toString());
    }

    private void throwRolled() throws GameMechanicException {
        throw new GameMechanicException(StringList.NOT_ROLLED.toString());
    }

    /** @return the game */
    public GameInitializer getGame() {
        return game;
    }
}