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
    private int currentRoll;
    private int currentPieceLength;
    private boolean hasRolled;
    private boolean hasPlaced;
    private Board board;
    private Map<Integer, Piece> firstSet; // First set for Mission Control.
    private Map<Integer, Piece> secondSet; // Second set for Mission Control.
    private Queue<GameStage> stages;
    private List<GameStage> finishedStages; // Once a stage is finished, it is added to this list.
    
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
        this.firstSet = new HashMap<Integer, Piece>();
        this.secondSet = new HashMap<Integer, Piece>();
        this.firstSet.put(two.getLength(), two);
        this.firstSet.put(three.getLength(), three);
        this.firstSet.put(four.getLength(), four);
        this.firstSet.put(five.getLength(), five);
        this.firstSet.put(six.getLength(), six);
        this.firstSet.put(dawn.getLength(), dawn);
        this.secondSet.putAll(firstSet);
        this.stages = new LinkedList<>();
        this.stages.add(new GameStage(vesta, firstSet, 1)); // Phase 1.
        this.stages.add(new GameStage(ceres, secondSet, 2)); // Phase 2.
        this.finishedStages = new ArrayList<GameStage>();
        this.hasRolled = false;
        this.hasPlaced = false;
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
     * @return the current roll.
     */
    public int getCurrentRoll() {
        return currentRoll;
    }

    /**
     * @return the board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * @param currentPieceLength the current piece length to set.
     */
    public void setCurrentpiecelength(int currentPieceLength) {
        this.currentPieceLength = currentPieceLength;
    }

    /**
     * @param currentRoll the current roll to set.
     */
    public void setCurrentRoll(int currentRoll) {
        this.currentRoll = currentRoll;
    }

    /**
     * Prints the board as a grid.
     * @return Textual representation of the board.
     */
    public String print() {
        return board.toString();
    }
    
    /**
     * @return if the die has been rolled.
     */
    public boolean hasRolled() {
        return hasRolled;
    }

    /**
     * @return if the Mission Control piece has been placed.
     */
    public boolean hasPlaced() {
        return hasPlaced;
    }
    
    /**
     * @return the stages.
     */
    public Queue<GameStage> getStages() {
        return stages;
    }

    /**
     * @return the finished stages.
     */
    public List<GameStage> getFinishedstages() {
        return finishedStages;
    }
    
    /**
     * Method to check if the game is over.
     * @return Checks if the game is over.
     */
    public boolean isGameOver() {
        return finishedStages.size() == 2;
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
        return currentPieceLength;
    }
   
    /**
     * Setter method for hasrolled.
     * @param bool true if the die has been rolled.
     */
    public void setRolled(boolean bool) {
        this.hasRolled = bool;
    }
    
    /**
     * Setter method for hasplaced.
     * @param bool true if the Mission Control piece has been placed.
     */
    public void setPlaced(boolean bool) {
        this.hasPlaced = bool;
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
