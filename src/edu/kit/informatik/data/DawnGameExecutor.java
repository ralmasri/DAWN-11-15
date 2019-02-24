package edu.kit.informatik.data;

import java.util.ArrayList;
import java.util.Arrays;
import edu.kit.informatik.exceptions.GameMechanicException;
import edu.kit.informatik.exceptions.InvalidInputException;
import edu.kit.informatik.util.StringList;
/**
 * Contains all the methods that allow someone to play DAWN 11/15.
 * @author Rakan Zeid Al Masri
 * @version 1.0
 */
public class DawnGameExecutor {

    private DawnGame game; // The game.
    
    /**
     * Constructor of the executor of the DAWN game
     */
    public DawnGameExecutor() {
        this.game = new DawnGame();
    }
    
    /**
     * Returns a symbol based on the what is on the cell.
     * @param mcomponent The m-component of the cell.
     * @param ncomponent The n-component of the cell.
     * @return  '-' if the cell is empty, 'V' for Vesta, 'C' for Ceres or '+' if a Mission Control piece
     * is on the cell.
     * @throws InvalidInputException If the coordinate is not on the board.
     */
    public String state(int mcomponent, int ncomponent) throws InvalidInputException {
        Cell cell = game.getBoard().getCell(mcomponent, ncomponent);
        if (cell == null) {
            throwInvalidCoordinate("");
        }
        return cell.toString();
    }
    
    /**
     * Prints the board as a grid.
     * @return Textual representation of the board.
     */
    public String print() {
        return game.getBoard().toString();
    }
    
    /**
     * Places the current Nature piece somewhere on the board.
     * @param mcomponent The m-component of the cell we want to place the Nature piece on.
     * @param ncomponent The n-component of the cell we want to place the Nature piece on.
     * @return 'OK' if successful.
     * @throws InvalidInputException If the coordinate is not on the board.
     * @throws GameMechanicException If the Nature piece for this phase has already been placed, if the game is over
     * or if the cell is occupied.
     */
    public String setVC(int mcomponent, int ncomponent) throws InvalidInputException, GameMechanicException {
        if (game.getCurrentGameStage().isVCPlaced()) { // If the Nature piece for this stage has already been placed.
            throw new GameMechanicException(game.getCurrentGameStage().getNaturePiece().getName() 
                    + " has already been placed.");
        }
        if (game.isGameOver()) {
            throwGameOver();
        }

        Cell cell = game.getBoard().getCell(mcomponent, ncomponent);
        if (cell == null) { // If the coordinate is not on the board.
            throwInvalidCoordinate("");
        }
        if (cell.isOccupied()) {
            throw new GameMechanicException("the cell is already occupied");
        }
        cell.setPiece(game.getCurrentGameStage().getNaturePiece());
        game.getCurrentGameStage().placeVC();
        return StringList.OK.toString();
        
    }
    
    /**
     * Method for rolling the die.
     * @param symbol The number of the roll or DAWN.
     * @return 'OK' if successful.
     * @throws GameMechanicException If the game is over or if the Nature piece hasn't been placed yet.
     */
    public String roll(String symbol) throws GameMechanicException {
      
      if (game.isGameOver()) {
          throwGameOver();
      }
      if (game.getCurrentGameStage().getRound() == DawnGame.getPhaseIsDone()) { // If the stage is over.
          game.getFinishedstages().add(game.getCurrentGameStage());
          game.getStages().remove();
      }
      if (!game.getCurrentGameStage().isVCPlaced()) {
          throw new GameMechanicException("you must first place " 
      + game.getCurrentGameStage().getNaturePiece().getName() + " before rolling.");
      }
      if (game.hasRolled()) {
          throw new GameMechanicException("you have already rolled.");
      }
      if (symbol.equals("DAWN")) {
          game.setCurrentRoll(DawnGame.getDawnNumber());
      } else {
          game.setCurrentRoll(Integer.parseInt(symbol));
      }
      return StringList.OK.toString();
    }
   
    /**
     * Places a Mission Control piece.
     * @param headmcomponent The m-component of the head.
     * @param headncomponent The n-component of the head.
     * @param tailmcomponent The m-component of the tail.
     * @param tailncomponent The n-component of the tail.
     * @return 'OK' if successful.
     * @throws GameMechanicException If the game is over, if the die hasn't been rolled yet 
     * or if one or more cells are occupied.
     * @throws InvalidInputException Invalid coordinates.
     */
    public String place(int headmcomponent, int headncomponent, int tailmcomponent, int tailncomponent) 
            throws GameMechanicException, InvalidInputException {
        int length = computeCoordLength(headmcomponent, headncomponent, tailmcomponent, tailncomponent);
        if (game.isGameOver()) {
            throwGameOver();
        }
        if (!game.hasRolled()) { // If the player hasn't rolled the die yet.
            throwRolled();
         } // If the head and tail aren't on the board or either head or tail aren't on the board, but length != 7.
        if ((!game.getBoard().isInBounds(headmcomponent, headncomponent) 
                && !game.getBoard().isInBounds(tailmcomponent, tailncomponent)) 
                || (!game.getBoard().isInBounds(headmcomponent, headncomponent) 
                        && length != DawnGame.getDawnNumber())
                || (!game.getBoard().isInBounds(tailmcomponent, tailncomponent) 
                        && length != DawnGame.getDawnNumber())) {
            if (length == DawnGame.getDawnNumber()) { // Unique error message for DAWN piece.
                throw new InvalidInputException("either the tail or head of the DAWN piece must be on the "
                        + "board for a valid placement.");
            }
            if (length > DawnGame.getDawnNumber()) {
                throwInvalidCoordinate(" This piece doesn't exist.");
            }
            throwInvalidCoordinate("");
        }
        if (length > DawnGame.getDawnNumber() || length < DawnGame.getMinMcLength()) {
            throw new GameMechanicException("this piece isn't a Mission Control piece.");
        }
        if (headmcomponent != tailmcomponent && headncomponent != tailncomponent) {
            throwInvalidCoordinate(" Only horizontal or vertical placement is allowed.");
         }
        if (!getPossiblePlacements(game.getCurrentRoll()).contains(length)) {
            throwInvalidCoordinate(" This placement is not possible.");
        }
        if (length == DawnGame.getDawnNumber() && (!game.getBoard().isInBounds(headmcomponent, headncomponent) 
                || !game.getBoard().isInBounds(tailmcomponent, tailncomponent))) {
         // If DAWN's head or tail isn't on the board.
            placeDawn(headmcomponent, headncomponent, tailmcomponent, tailncomponent); 
        } else { // Normal placement for DAWN and other pieces.
            placeExecutor(headmcomponent, headncomponent, tailmcomponent, tailncomponent);
        }
        
        game.setCurrentpiecelength(length);
        return StringList.OK.toString();
     }
    
    /**
     * Gets all possible placements (next highest and next lowest)
     * @param roll The current roll.
     * @return A list of all possible placements.
     * @throws GameMechanicException If the game is over, so all the phases are removed from the queue.
     */
    private ArrayList<Integer> getPossiblePlacements(int roll) throws GameMechanicException {
        if (!game.getCurrentGameStage().isMCPlaced(roll)) { // If the piece of length roll hasn't been placed.
            return new ArrayList<Integer>(Arrays.asList(roll));
        }
        int loweroption = 0; // next lowest.
        int higheroption = 0; // next highest.
        ArrayList<Integer> possibleplacements = new ArrayList<Integer>();
        for (int i = roll + 1; i <= DawnGame.getDawnNumber(); i++) {
            if (!game.getCurrentGameStage().isMCPlaced(i)) {
                higheroption = i;
                break;
            }
        }
        for (int i = roll - 1; i >= DawnGame.getMinMcLength(); i--) {
            if (!game.getCurrentGameStage().isMCPlaced(i)) {
                loweroption = i;
                break;
            }
        }
        if (loweroption != 0) {
            possibleplacements.add(loweroption);
        }
        if (higheroption != 0) {
            possibleplacements.add(higheroption);
        }
        return possibleplacements;
    }
    
    /**
     * Executes the placement for pieces of length 2-6 or DAWN when part of it is not outside the board.
     * @param headmcomponent The head's m-component.
     * @param headncomponent The head's n-component.
     * @param tailmcomponent The tail's m-component.
     * @param tailncomponent The tail's n-component
     * @throws GameMechanicException If a cell is occupied, so the piece cannot be placed there.
     */
    private void placeExecutor(int headmcomponent, int headncomponent, int tailmcomponent, int tailncomponent) 
            throws GameMechanicException {
        int largercomponent = 0;
        int smallercomponent = 0;
        int length = computeCoordLength(headmcomponent, headncomponent, tailmcomponent, tailncomponent);
  
        if (headmcomponent == tailmcomponent) { // Horizontal placement.
            if (headncomponent > tailncomponent) {
                largercomponent = headncomponent;
                smallercomponent = tailncomponent;
            } else {
                largercomponent = tailncomponent;
                smallercomponent = headncomponent;
            }
            if (!isOccupiedIterator(smallercomponent, largercomponent, headmcomponent, true).isEmpty()) {
                throwOccupied(isOccupiedIterator(smallercomponent, largercomponent, headmcomponent, true));
            }
            for (int i = smallercomponent; i <= largercomponent; i++) {
                game.getBoard().getCell(headmcomponent, i)
                .setPiece(game.getCurrentGameStage().getMCPieces().get(length));
            }
        } else { // Vertical placement.
            if (headmcomponent > tailmcomponent) {
                largercomponent = headmcomponent;
                smallercomponent = tailmcomponent;
            } else {
                largercomponent = tailmcomponent;
                smallercomponent = headmcomponent;
            }
            if (!isOccupiedIterator(smallercomponent, largercomponent, headncomponent, false).isEmpty()) {
                throwOccupied(isOccupiedIterator(smallercomponent, largercomponent, headncomponent, false));
            }
            for (int i = smallercomponent; i <= largercomponent; i++) {
                game.getBoard().getCell(i, headncomponent)
                .setPiece(game.getCurrentGameStage().getMCPieces().get(length));
            } 
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
        
        int largercomp;
        int smallercomp;
        int commoncomp;
        int head;
        int tail;
        Piece dawn = game.getCurrentGameStage().getMCPieces().get(DawnGame.getDawnNumber());
        boolean isHorizontal = false;
      
        // This is to check if the piece is to be placed horizontally or vertically.
        
        if (headmcomponent == tailmcomponent) { // horizontally placed.
            head = headncomponent;
            tail = tailncomponent;
            commoncomp = headmcomponent; // With horizontal placement, the m-coordinate stays the same.
            isHorizontal = true;
        } else { // vertically placed.
            head = headmcomponent;
            tail = tailmcomponent;
            commoncomp = headncomponent; // With vertical placement, the n-coordinate stays the same.
        }
          if (head < tail) { // This is to determine which one is bigger.
                largercomp = tail;
                smallercomp = head;
            } else {
                largercomp = head;
                smallercomp = tail;
            }
            if (isHorizontal) {
                if (largercomp >= DawnGame.getBoardWidth()) { // largercomp is outside the board.
                    if (!isOccupiedIterator(smallercomp, DawnGame.getBoardWidth() - 1, commoncomp, isHorizontal)
                            .isEmpty()) {
                        int smallerWidth = DawnGame.getBoardWidth() - 1;
                        throwOccupied(isOccupiedIterator(smallercomp, smallerWidth, commoncomp, isHorizontal));
                    } // Iterate from the smallercomp till the width - 1.
                    for (int i = smallercomp; i < DawnGame.getBoardWidth(); i++) {
                        game.getBoard().getCell(commoncomp, i).setPiece(dawn); 
                    }
                } else { // largercomp is on the board, in which case the smallercomp is negative
                    if (!isOccupiedIterator(0, largercomp, commoncomp, isHorizontal).isEmpty()) {
                        throwOccupied(isOccupiedIterator(0, largercomp, commoncomp, isHorizontal));
                    }
                    for (int i = 0; i <= largercomp; i++) {
                        game.getBoard().getCell(commoncomp, i).setPiece(dawn);
                    }
                }
            } else { // Same thing as above here but going vertically.
                if (largercomp >= DawnGame.getBoardHeight()) { // The largercomp is outside the board.
                    if (!isOccupiedIterator(smallercomp, DawnGame.getBoardHeight() - 1, commoncomp, isHorizontal)
                            .isEmpty()) {
                        int smallerHeight = DawnGame.getBoardHeight() - 1;
                        throwOccupied(isOccupiedIterator(smallercomp, smallerHeight, commoncomp, isHorizontal));
                    } // Iterate from the smallercomp till the height - 1.
                    for (int i = smallercomp; i < DawnGame.getBoardHeight(); i++) {
                        game.getBoard().getCell(i, commoncomp).setPiece(dawn); 
                    }
                } else {
                    if (!isOccupiedIterator(0, largercomp, commoncomp, isHorizontal).isEmpty()) {
                        throwOccupied(isOccupiedIterator(0, largercomp, commoncomp, isHorizontal));
                    }
                    for (int i = 0; i <= largercomp; i++) {
                        game.getBoard().getCell(i, commoncomp).setPiece(dawn);
                    }
                }
            }
            game.getCurrentGameStage().placeMC(DawnGame.getDawnNumber());
    }
    
    /**
     * @return the game
     */
    public DawnGame getGame() {
        return game;
    }
    
    /**
     * Method to move a Nature piece. Represents one elementary move.
     * @param coordinate The coordinate to move the piece to.
     * @throws GameMechanicException If the game is over or a Mission Control piece
     * hasn't been placed yet.
     */
    public void move(String coordinate) throws GameMechanicException {
        if (game.isGameOver()) {
            throwGameOver();
        }
        if (!game.hasPlaced()) {
            throw new GameMechanicException("you must place a Mission Control piece before moving a Nature piece.");
        }
        String[] coordinateSplit = coordinate.split(StringList.COMPONENT_SEPARATOR.toString());
        int mdestination = Integer.parseInt(coordinateSplit[0]);
        int ndestination = Integer.parseInt(coordinateSplit[1]);
        Piece naturePiece = game.getCurrentGameStage().getNaturePiece();
        Cell cellofNaturePiece = game.getBoard().getCellofPiece(naturePiece);
        cellofNaturePiece.setPiece(null);
        game.getBoard().getCell(mdestination, ndestination).setPiece(naturePiece);
    }
    
    /**
     * Checks if a movement is valid or not.
     * @param origin The current cell we are moving from.
     * @param destination The destination cell we are moving to.
     * @return true if the move is valid, otherwise false.
     * @throws GameMechanicException If the game is over, if the move is diagonal or
     * if the destination is occupied.
     * @throws InvalidInputException If the coordinates aren't on the board.
     */
    public boolean checkMovementValidity(String origin, String destination) 
            throws GameMechanicException, InvalidInputException {
        int morigin = 0;
        int norigin = 0;
        Cell originCell;
        if (origin.equals("nature")) {
            originCell = game.getBoard().getCellofPiece(game.getCurrentGameStage().getNaturePiece());
            morigin = originCell.getMCoord();
            norigin = originCell.getNCoord();
        } else {
            String[] originSplit = origin.split(";");
            morigin = Integer.parseInt(originSplit[0]);
            norigin = Integer.parseInt(originSplit[1]);
            originCell = game.getBoard().getCell(morigin, norigin);
        }
        String[] destinationSplit = destination.split(";");
        int mdestination = Integer.parseInt(destinationSplit[0]);
        int ndestination = Integer.parseInt(destinationSplit[1]);
        if (game.isGameOver()) {
            throwGameOver();
        }
        if (!game.getBoard().isInBounds(mdestination, ndestination)) {
            throwInvalidCoordinate("");
        }
        if (morigin != mdestination && norigin != ndestination) {
            throw new GameMechanicException("a piece can only move one step either horizontally or vertically.");
        }
        if (morigin == mdestination && norigin == ndestination) {
            throw new GameMechanicException(game.getCurrentGameStage().getNaturePiece().getName() 
                    + " is already there. You must move"
                    + " somewhere else.");
        } 
        if (!(game.getBoard().getCell(mdestination, ndestination).equals(
                game.getBoard().getCellofPiece(game.getCurrentGameStage().getNaturePiece())))
                && game.getBoard().getCell(mdestination, ndestination).isOccupied()) {
            throw new GameMechanicException("a piece occupies " 
                + mdestination + ";" + ndestination + " , so you cannot move there.");
        }
       return isMovePossible(mdestination, ndestination, originCell);
    }
    
    /**
     * Print the result of the game.
     * @return The result of the game.
     * @throws GameMechanicException If the game isn't over yet.
     */
    public int showresult() throws GameMechanicException {
        if (!game.isGameOver()) {
            throw new GameMechanicException("you cannot view the result if the game is not over yet.");
        }
        int freeSpacesCeres = 0;
        int freeSpacesVesta = 0;
        GameStage first = game.getFinishedstages().get(0);
        GameStage second = game.getFinishedstages().get(1);
        Cell vesta = game.getBoard().getCellofPiece(first.getNaturePiece());
        Cell ceres = game.getBoard().getCellofPiece(second.getNaturePiece());
        freeSpacesCeres = DepthFirstSearch.getFreeSpaces(game.getBoard(), ceres);
        freeSpacesVesta = DepthFirstSearch.getFreeSpaces(game.getBoard(), vesta);
        if (freeSpacesCeres > freeSpacesVesta) { // Applying the formula from the question.
            return freeSpacesCeres + (freeSpacesCeres - freeSpacesVesta);
        } else if (freeSpacesCeres < freeSpacesVesta) {
            return freeSpacesVesta + (freeSpacesVesta - freeSpacesCeres);
        } else {
            return 0; // If F(V) = F(C)
        }
    }

    private boolean isMovePossible(int mdestination, int ndestination, Cell cellofNaturePiece) {
        int mpiece = cellofNaturePiece.getMCoord();
        int npiece = cellofNaturePiece.getNCoord();
        int mdifference = Math.abs(mpiece - mdestination);
        int ndifference = Math.abs(npiece - ndestination);
        return (mdifference == 1) ^ (ndifference == 1); // A move can only be horizontal or vertical and only one step.
    }
    
    private void throwInvalidCoordinate(String extraMessage) throws InvalidInputException {
        throw new InvalidInputException(StringList.INVALID_COORDINATES.toString() + extraMessage);
    }
    
    /**
     * Thrown when the game is over.
     * @throws GameMechanicException Game is over.
     */
    static void throwGameOver() throws GameMechanicException {
        throw new GameMechanicException(StringList.GAME_OVER.toString());
    }
    
    private void throwOccupied(String coordinate) throws GameMechanicException {
        throw new GameMechanicException(coordinate + StringList.IS_OCCUPIED.toString());
    }
    
    private void throwRolled() throws GameMechanicException {
        throw new GameMechanicException(StringList.NOT_ROLLED.toString());
    }
    
    private int computeCoordLength(int headmcomponent, int headncomponent, int tailmcomponent, int tailncomponent) {
        int length;
        if (headmcomponent == tailmcomponent) { // If the piece is to be placed horizontally.
            length = tailncomponent - headncomponent;
        } else { // If the piece is to be placed vertically.
            length = tailmcomponent - headmcomponent;
        }
        if (length < 0) {
            return Math.abs(length) + 1;
        } else {
            return length + 1;
        }
    }
    
    /**
     * Checks if any of the cells where a piece is going to be placed is occupied.
     * @param start The start component.
     * @param destination The destination component.
     * @param commoncomponent For vertical placement, the n-component. For horizontal, the m-component.
     * @param ismcomp If the common component is a m-component or not.
     * @return If a cell is occupied, returns an error message, otherwise empty string.
     */
    private String isOccupiedIterator(int start, int destination, int commoncomponent, boolean ismcomp) {
        if (ismcomp) { // Horizontal.
            for (int i = start; i <= destination; i++) {
                if (game.getBoard().getCell(commoncomponent, i).isOccupied()) {
                    return String.valueOf(game.getBoard().getCell(commoncomponent, i).getMCoord()) 
                            + StringList.COMPONENT_SEPARATOR.toString()
                            + String.valueOf(game.getBoard().getCell(commoncomponent, i).getNCoord());
                }
            }
        } else { // Vertical.
            for (int i = start; i <= destination; i++) {
                if (game.getBoard().getCell(i, commoncomponent).isOccupied()) {
                    return String.valueOf(game.getBoard().getCell(i, commoncomponent).getMCoord()) 
                            + StringList.COMPONENT_SEPARATOR.toString()
                            + String.valueOf(game.getBoard().getCell(i, commoncomponent).getNCoord());
                }
            }
        }
        return "";
    }
}