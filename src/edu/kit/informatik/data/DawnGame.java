package edu.kit.informatik.data;

import java.util.ArrayList;



import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import edu.kit.informatik.exceptions.GameMechanicException;

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
    private static final int PHASE_IS_DONE = 7; // abstract round that represents the end of a stage/phase.
    private int currentroll;
    private int currentpiecelength;
    private boolean hasrolled;
    private boolean hasplaced;
    private Board board;
    private Map<Integer, Piece> firstset; // First set for Mission Control.
    private Map<Integer, Piece> secondset; // Second set for Mission Control.
    private Queue<GameStage> stages;
    private List<GameStage> finishedstages; // Once a stage is finished, it is added to this list.
    
    /**
     * Constructor for a game and all its components.
     */
    public DawnGame() {
        reset();
    }
    
    /**
     * Initializes all parts of the game and is also used to start a new game.
     */
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
        this.finishedstages = new ArrayList<GameStage>();
        this.hasrolled = false;
        this.hasplaced = false;
    }
    
    /**
     * @return the boardHeight
     */
    public static int getBoardHeight() {
        return BOARD_HEIGHT;
    }

    /**
     * @return the boardWidth
     */
    public static int getBoardWidth() {
        return BOARD_WIDTH;
    }

    /**
     * @return the dawnNumber
     */
    public static int getDawnNumber() {
        return DAWN_NUMBER;
    }

    /**
     * @return the minMcLength
     */
    public static int getMinMcLength() {
        return MIN_MC_LENGTH;
    }

    /**
     * @return the currentroll
     */
    public int getCurrentroll() {
        return currentroll;
    }

    /**
     * @return the board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * @param currentpiecelength the currentpiecelength to set
     */
    public void setCurrentpiecelength(int currentpiecelength) {
        this.currentpiecelength = currentpiecelength;
    }

    /**
     * @param currentroll the currentroll to set
     */
    public void setCurrentroll(int currentroll) {
        this.currentroll = currentroll;
    }

    /**
     * Prints the board as a grid.
     * @return Textual representation of the board.
     */
    public String print() {
        return board.toString();
    }
    
    /**
     * @return the hasrolled
     */
    public boolean hasRolled() {
        return hasrolled;
    }

    /**
     * @return the hasplaced
     */
    public boolean hasPlaced() {
        return hasplaced;
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
    
    /**
     * Method to check if the game is over.
     * @return Checks if the game is over.
     */
    public boolean isGameOver() {
        return finishedstages.size() == 2;
    }
    
    /**
     * Getter method for the current game stage.
     * @return The current game stage.
     * @throws GameMechanicException If the game is over.
     */
    public GameStage getCurrentGameStage() throws GameMechanicException {
        if (stages.peek() == null) {
            DawnGameExecutor.throwGameOver();
        }
        return stages.peek();
    }
  
    /**
     * Getter method for the length of the last placed Mission Control piece.
     * @return The current Mission Control piece length.
     */
    public int getCurrentpiecelength() {
        return currentpiecelength;
    }
   
    /**
     * Setter method for hasrolled.
     * @param bool true if the die has been rolled.
     */
    public void setRolled(boolean bool) {
        this.hasrolled = bool;
    }
    
    /**
     * Setter method for hasplaced.
     * @param bool true if the Mission Control piece has been placed.
     */
    public void setPlaced(boolean bool) {
        this.hasplaced = bool;
    }
    
    /**
     * Checks if there any free spaces around a piece (so if movement is possible).
     * @param cellofnaturepiece The cell of the nature piece.
     * @return true if there aren't any free spaces, otherwise false.
     */
    public boolean areThereNoFreeSpaces(Cell cellofnaturepiece) {
        return DepthFirstSearch.getFreeSpaces(board, cellofnaturepiece) == 0;
    }

    /**
     * Returns the abstract end round of a stage.
     * @return The end round of a stage (7).
     */
    public static int getPhaseIsDone() {
        return PHASE_IS_DONE;
    }
}
