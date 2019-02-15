package edu.kit.informatik.data;

import java.util.ArrayList;


import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import edu.kit.informatik.exceptions.*;
import edu.kit.informatik.util.StringList;

/**
 * Represents the KIT version of DAWN 11/15.
 *
 * @author Rakan Zeid Al Masri
 * @version 1.0
 */

public class DawnGame {

    private static final int BOARD_HEIGHT = 11;
    private static final int BOARD_WIDTH = 15;
    private static final int DAWN_NUMBER = 7;
    private static final int MIN_MC_LENGTH = 2;
    private int currentroll;
    private int currentpiecelength;
    private boolean hasrolled;
    private boolean hasplaced;
    private Board board;
    private Map<Integer, Piece> firstset;
    private Map<Integer, Piece> secondset;
    private Queue<GameStage> stages;
    private List<GameStage> finishedstages;
    private FreeSpaces spaces;
    
    
    public DawnGame() {
        reset();
    }
        
    public void reset() {
        this.board = new Board(BOARD_HEIGHT, BOARD_WIDTH);
        Piece two = new Piece(2);
        Piece three = new Piece(3);
        Piece four = new Piece(4);
        Piece five = new Piece(5);
        Piece six = new Piece(6);
        Piece dawn = new Piece(DAWN_NUMBER);
        Piece vesta = new Piece('V', "Vesta");
        Piece ceres = new Piece('C', "Ceres");
        this.firstset = new HashMap<Integer, Piece>();
        this.secondset = new HashMap<Integer, Piece>();
        this.firstset.put(two.getLength(), two);
        this.firstset.put(three.getLength(), three);
        this.firstset.put(four.getLength(), four);
        this.firstset.put(five.getLength(), five);
        this.firstset.put(six.getLength(), six);
        this.firstset.put(dawn.getLength(), dawn);
        this.secondset.putAll(firstset);
        this.stages = new LinkedList<>();
        this.stages.add(new GameStage(vesta, firstset, 1)); // Phase 1.
        this.stages.add(new GameStage(ceres, secondset, 2)); // Phase 2.
        this.spaces = new FreeSpaces();
        this.finishedstages = new ArrayList<GameStage>();
        this.hasrolled = false;
        this.hasplaced = false;
    }
    public String state(int mcoordinate, int ncoordinate) throws InvalidInputException {
        Cell cell = board.getCell(mcoordinate, ncoordinate);
        if(cell == null) {
            throwInvalidCoordinate("");
        }
        return cell.toString();
    }
    
    public String print() {
        return board.toString();
    }
    
    public String setVC(int mcoordinate, int ncoordinate) throws InvalidInputException, GameMechanicException {
        if(getCurrentGameStage().isVCPlaced()) {
            throw new GameMechanicException(getCurrentGameStage().getNaturePiece().getName() + " has already been placed.");
        }
        if (isGameOver()) {
            throwGameOver();
        }

        Cell cell = board.getCell(mcoordinate, ncoordinate);
        if (cell == null) {
            throwInvalidCoordinate("");
        }
        if (cell.isOccupied()) {
            throw new GameMechanicException("the cell is already occupied");
        }
        cell.setPiece(getCurrentGameStage().getNaturePiece());
        getCurrentGameStage().placeVC();
        return StringList.OK.toString();
        
    }
    
    public String roll(String symbol) throws GameMechanicException {
      
      if(isGameOver() || getCurrentGameStage().isFinished()) {
          throwGameOver();
      }
      if (getCurrentGameStage().getRound() == 7) {
          finishedstages.add(getCurrentGameStage());
          stages.remove();
      }
      if(!getCurrentGameStage().isVCPlaced()) {
          throw new GameMechanicException("you must first place " + getCurrentGameStage().getNaturePiece().getName() + " before rolling.");
      }
      if(hasrolled) {
          throw new GameMechanicException("you have already rolled.");
      }
      if(symbol.equals("DAWN")) {
          currentroll = DAWN_NUMBER;
      } else {
          currentroll = Integer.parseInt(symbol);
      }
      return StringList.OK.toString();
    }
    
    
    /**
     * @return the hasrolled
     */
    public boolean isHasrolled() {
        return hasrolled;
    }

    /**
     * @return the hasplaced
     */
    public boolean isHasplaced() {
        return hasplaced;
    }

    public String place(int headmcoord, int headncoord, int tailmcoord, int tailncoord) throws GameMechanicException, InvalidInputException{
        int length = computeCoordLength(headmcoord, headncoord, tailmcoord, tailncoord);
        if (isGameOver()) {
            throwGameOver();
        }
        if (!hasrolled) {
            throwRolled();
         }
        if ((!board.isInBounds(headmcoord, headncoord) && !board.isInBounds(tailmcoord, tailncoord)) 
                || (!board.isInBounds(headmcoord, headncoord) && length != DAWN_NUMBER)
                || (!board.isInBounds(tailmcoord, tailncoord) && length != DAWN_NUMBER)) {
            throwInvalidCoordinate("");
        }
        if (length > DAWN_NUMBER || length < MIN_MC_LENGTH) {
            throw new GameMechanicException("this piece isn't a Mission Control piece.");
        }
        if (headmcoord != tailmcoord && headncoord != tailncoord) {
            throwInvalidCoordinate(" Only horizontal or vertical placement is allowed.");
         }
        if (!getPossiblePlacements(currentroll).contains(length)) {
            throwInvalidCoordinate(" This placement is not possible.");
        }
        if (length == DAWN_NUMBER && (!board.isInBounds(headmcoord, headncoord) || !board.isInBounds(tailmcoord, tailncoord))) {
            placeDawn(headmcoord, headncoord, tailmcoord, tailncoord);
        } else {
            placeExecutor(headmcoord, headncoord, tailmcoord, tailncoord);
        }
        currentpiecelength = length;
        return StringList.OK.toString();
     }
    
    private ArrayList<Integer> getPossiblePlacements(int roll) throws InvalidInputException, GameMechanicException{
        if(!getCurrentGameStage().isMCPlaced(roll)) {
            return new ArrayList<Integer>(Arrays.asList(roll));
        }
        int loweroption = 0;
        int higheroption = 0;
        ArrayList<Integer> possibleplacements = new ArrayList<Integer>();
        for (int i = roll + 1; i < DAWN_NUMBER ; i++) {
            if(!getCurrentGameStage().isMCPlaced(i)) {
                higheroption = i;
                break;
            }
        }
        for (int i = roll - 1 ; i >= MIN_MC_LENGTH ; i--) {
            if(!getCurrentGameStage().isMCPlaced(i)) {
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
    
    private void placeExecutor(int headmcoord, int headncoord, int tailmcoord, int tailncoord) 
            throws InvalidInputException, GameMechanicException {
        int largercoord = 0;
        int smallercoord = 0;
        int length = computeCoordLength(headmcoord, headncoord, tailmcoord, tailncoord);
  
        if (headmcoord == tailmcoord) {
            if(!isOccupiedIterator(headncoord, tailncoord, headmcoord, true).isEmpty()) {
                throwOccupied(isOccupiedIterator(headncoord, tailncoord, headmcoord, true));
            }
            if (headncoord > tailncoord) {
                largercoord = headncoord;
                smallercoord = tailncoord;
            } else {
                largercoord = tailncoord;
                smallercoord = headncoord;
            }
            for(int i = smallercoord; i <= largercoord; i++) {
                board.getCell(headmcoord, i).setPiece(getCurrentGameStage().getMCPieces().get(length));
            }
        } else {
            if(!isOccupiedIterator(headmcoord, tailmcoord, headncoord, false).isEmpty()) {
                throwOccupied(isOccupiedIterator(headmcoord, tailmcoord, headncoord, false));
            }
            if (headmcoord > tailmcoord) {
                largercoord = headmcoord;
                smallercoord = tailmcoord;
            } else {
                largercoord = tailmcoord;
                smallercoord = headmcoord;
            }
            for(int i = smallercoord; i <= largercoord; i++) {
                board.getCell(i, headncoord).setPiece(getCurrentGameStage().getMCPieces().get(length));
            } 
        }
        getCurrentGameStage().placeMC(length);
    }
    
    private void placeDawn(int headmcoord, int headncoord, int tailmcoord, int tailncoord) 
            throws InvalidInputException, GameMechanicException {
        
        int largercomp;
        int smallercomp;
        int commoncomp;
        int head;
        int tail;
        Piece dawn = getCurrentGameStage().getMCPieces().get(DAWN_NUMBER);
        boolean ishorizontal = false;
      
        // This is to check if the piece is to be placed horizontally or vertically.
        
        if (headmcoord == tailmcoord) { // horizontally placed.
            head = headncoord;
            tail = tailncoord;
            commoncomp = headmcoord;
            ishorizontal = true;
        } else { // vertically placed.
            head = headmcoord;
            tail = tailmcoord;
            commoncomp = headncoord;
        }
           //TODO handle the out of bounds placement.
          if (head < tail) { // This is to determine which one is bigger.
                largercomp = tail;
                smallercomp = head;
            } else {
                largercomp = head;
                smallercomp = tail;
            }
            if (ishorizontal) {
                if (largercomp >= BOARD_WIDTH) {
                    if (!isOccupiedIterator(smallercomp, BOARD_WIDTH - 1, commoncomp, ishorizontal).isEmpty()) {
                        throwOccupied(isOccupiedIterator(smallercomp, BOARD_WIDTH - 1, commoncomp, ishorizontal));
                    }
                    for (int i = smallercomp; i < BOARD_WIDTH; i++) {
                        board.getCell(commoncomp, i).setPiece(dawn);
                    }
                } else { // largercomp is on the board, in which case the smallercomp is negative
                    if (!isOccupiedIterator(0, largercomp, commoncomp, ishorizontal).isEmpty()) {
                        throwOccupied(isOccupiedIterator(0, largercomp, commoncomp, ishorizontal));
                    }
                    for (int i = 0; i <= largercomp; i++) {
                        board.getCell(commoncomp, i).setPiece(dawn);
                    }
                }
            } else { // Same thing as above here but going vertically.
                if (largercomp >= BOARD_HEIGHT) {
                    if (!isOccupiedIterator(smallercomp, BOARD_HEIGHT - 1, commoncomp, ishorizontal).isEmpty()) {
                        throwOccupied(isOccupiedIterator(smallercomp, BOARD_HEIGHT - 1, commoncomp, ishorizontal));
                    }
                    for (int i = smallercomp; i < BOARD_HEIGHT; i++) {
                        board.getCell(i, commoncomp).setPiece(dawn);
                    }
                } else {
                    if (!isOccupiedIterator(0, largercomp, commoncomp, ishorizontal).isEmpty()) {
                        throwOccupied(isOccupiedIterator(0, largercomp, commoncomp, ishorizontal));
                    }
                    for (int i = 0; i <= largercomp; i++) {
                        board.getCell(i, commoncomp).setPiece(dawn);
                    }
                }
            }
            getCurrentGameStage().placeMC(DAWN_NUMBER);
    }
    public void move(String coordinates) throws GameMechanicException, InvalidInputException {
        String[] coordinatesplit = coordinates.split(";");
        int mdestination = Integer.parseInt(coordinatesplit[0]);
        int ndestination = Integer.parseInt(coordinatesplit[1]);
        Piece naturepiece = getCurrentGameStage().getNaturePiece();
        Cell cellofnaturepiece = board.getCellofPiece(naturepiece);
        cellofnaturepiece.setPiece(null);
        board.getCell(mdestination, ndestination).setPiece(naturepiece);
    }
    
    /**
     * @return the stages
     */
    public Queue<GameStage> getStages() {
        return stages;
    }

    /**
     * @return the finishedstages
     */
    public List<GameStage> getFinishedstages() {
        return finishedstages;
    }

    public boolean checkMovementValidity(String origin, String destination) throws GameMechanicException, InvalidInputException {
        int morigin = 0;
        int norigin = 0;
        Cell origincell;
        if (origin.equals("nature")) {
            origincell = board.getCellofPiece(getCurrentGameStage().getNaturePiece());
            morigin = origincell.getMCoord();
            norigin = origincell.getNCoord();
        } else {
            String[] originsplit = origin.split(";");
            morigin = Integer.parseInt(originsplit[0]);
            norigin = Integer.parseInt(originsplit[1]);
            origincell = board.getCell(morigin, norigin);
        }
        String[] destinationsplit = destination.split(";");
        int mdestination = Integer.parseInt(destinationsplit[0]);
        int ndestination = Integer.parseInt(destinationsplit[1]);
        if (isGameOver()) {
            throwGameOver();
        }
        if (!board.isInBounds(mdestination, ndestination)) {
            throwInvalidCoordinate("");
        }
        if (morigin != mdestination && norigin != ndestination) {
            throw new GameMechanicException("a piece can only move horizontally or vertically.");
        }
        if (morigin == mdestination && norigin == ndestination) {
            throw new GameMechanicException(getCurrentGameStage().getNaturePiece().getName() + "is already there. You must move"
                    + " somewhere else.");
        } 
        if (!(board.getCell(mdestination, ndestination).equals(board.getCellofPiece(getCurrentGameStage().getNaturePiece())))
                && board.getCell(mdestination, ndestination).isOccupied()) {
            throw new GameMechanicException("a piece occupies " + mdestination + ";" + ndestination + " , so you cannot move there.");
        }
       return isMovePossible(mdestination, ndestination, origincell);
    }
    public int showresult() throws GameMechanicException {
        if (!isGameOver()) {
            throw new GameMechanicException("you cannot view the result if the game is not over yet.");
        }
        int freespacesceres = 0;
        int freespacesvesta = 0;
        GameStage first = finishedstages.get(0);
        GameStage second = finishedstages.get(1);
        int mvesta = board.getCellofPiece(first.getNaturePiece()).getMCoord();
        int nvesta = board.getCellofPiece(first.getNaturePiece()).getNCoord();
        int mceres = board.getCellofPiece(second.getNaturePiece()).getMCoord();
        int nceres = board.getCellofPiece(second.getNaturePiece()).getNCoord();
        freespacesceres = spaces.getPossibleMoves(board, mceres, nceres);
        freespacesvesta = spaces.getPossibleMoves(board, mvesta, nvesta);
        if(freespacesceres > freespacesvesta) {
            return freespacesceres + (freespacesceres - freespacesvesta);
        } else if (freespacesceres < freespacesvesta) {
            return freespacesvesta + (freespacesvesta - freespacesceres);
        } else {
            return 0; // If they are equal
        }
    }

    private boolean isMovePossible(int mdestination, int ndestination, Cell cellofnaturepiece) {
        int mpiece = cellofnaturepiece.getMCoord();
        int npiece = cellofnaturepiece.getNCoord();
        int mdifference = Math.abs(mpiece - mdestination);
        int ndifference = Math.abs(npiece - ndestination);
        return (mdifference == 1) ^ (ndifference == 1);
    }
    
    private void throwInvalidCoordinate(String extramessage) throws InvalidInputException {
        throw new InvalidInputException(StringList.INVALID_COORDINATES.toString() + extramessage);
    }
    
    private void throwGameOver() throws GameMechanicException{
        throw new GameMechanicException(StringList.GAME_OVER.toString());
    }
    
    private void throwOccupied(String coordinate) throws GameMechanicException{
        throw new GameMechanicException(coordinate + StringList.IS_OCCUPIED.toString());
    }
    
    private void throwRolled() throws GameMechanicException{
        throw new GameMechanicException(StringList.NOT_ROLLED.toString());
    }
    
    private int computeCoordLength(int headmcoord, int headncoord, int tailmcoord, int tailncoord) {
        int length;
        if (headmcoord == tailmcoord) {
            length = tailncoord - headncoord;
        } else {
            length = tailmcoord - headmcoord;
        }
        if (length < 0) {
            return Math.abs(length) + 1;
        } else {
            return length + 1;
        }
    }
    private boolean isGameOver() {
        return finishedstages.size() == 2;
    }
    
    public GameStage getCurrentGameStage() throws GameMechanicException {
        if (stages.peek() == null) {
            throwGameOver();
        }
        return stages.peek();
    }
    
    private String isOccupiedIterator(int start, int destination, int commoncoord, boolean ismcoord) {
        if (ismcoord) {
            for (int i = start; i <= destination; i++) {
                if (board.getCell(commoncoord, i).isOccupied()) {
                    return String.valueOf(board.getCell(commoncoord, i).getMCoord()) + ";" 
                + String.valueOf(board.getCell(commoncoord, i).getNCoord());
                }
            }
        } else {
            for (int i = start; i <= destination; i++) {
                if (board.getCell(i, commoncoord).isOccupied()) {
                    return String.valueOf(board.getCell(i, commoncoord).getMCoord()) + ";" 
                            + String.valueOf(board.getCell(i, commoncoord).getNCoord());
                }
            }
        }
        return "";
    }

    public int getCurrentpiecelength() {
        return currentpiecelength;
    }
   
    public void setRolled(boolean b) {
        this.hasrolled = b;
    }
    
    public void setPlaced(boolean b) {
        this.hasplaced = b;
    }
    
}
