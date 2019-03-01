package edu.kit.informatik.data;

import java.util.ArrayList;



import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import edu.kit.informatik.exceptions.GameMechanicException;
import edu.kit.informatik.util.StringList;

/**
 * Represents the KIT version of DAWN 11/15.
 *
 * @author Rakan Zeid Al Masri
 * @version 1.0
 */
public class DawnGame {

    /**
     * The height of the board.
     */
    private static final int BOARD_HEIGHT = 11;
    
    /**
     * The width of the board.
     */
    private static final int BOARD_WIDTH = 15;
    
    /**
     * Number that represents the DAWN piece.
     */
    private static final int DAWN_NUMBER = 7;
    
    /**
     * The smallest Mission Control piece.
     */
    private static final int MIN_MC_LENGTH = 2;
    
    /**
     * Abstract round that represents the end of a phase.
     */
    private static final int PHASE_IS_DONE = 7;
    
    /**
     * The number of steps allowed per move.
     */
    private static final int MOVE_STEPS_ALLOWED = 1;
    
    private int currentRoll;
    private int currentPieceLength;
    private boolean hasRolled;
    private boolean hasPlaced;
    private Board board;
    
    /* * First set of pieces for Mission Control. */
    private Map<Integer, Piece> firstSet;
    
    /** Second set of pieces for Mission Control.*/
    private Map<Integer, Piece> secondSet; 
    private Queue<GameStage> stages;
    
    /** Once a stage is finished, it is added to this list.*/
    private List<GameStage> finishedStages;
    
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
     * @return true if the die has been rolled, otherwise false.
     */
    public boolean hasRolled() {
        return hasRolled;
    }

    /**
     * @return true if a Mission Control piece has been placed, otherwise false.
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
            throw new GameMechanicException(StringList.GAME_OVER.toString());
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
     * Checks if there are any free spaces around a piece (a.k.a if movement is possible).
     * @param cellofnaturepiece The cell of the nature piece.
     * @return true if there aren't any free spaces, otherwise false.
     */
    public boolean areThereNoFreeSpaces(Cell cellofnaturepiece) {
        FreeSpaces dfs = new FreeSpaces();
        return dfs.getFreeSpaces(board, cellofnaturepiece) == 0;
    }

    /**
     * Returns the abstract end round of a stage.
     * @return The end round of a stage (7).
     */
    public static int getPhaseIsDone() {
        return PHASE_IS_DONE;
    }

    /**
     * Returns the allowed steps per elementary move.
     * @return The allowed steps per elementary move.
     */
    public static int getMoveStepsAllowed() {
        return MOVE_STEPS_ALLOWED;
    }
    
    /**
     * When a stage is over, said stage will be added to finished stages and will be removed from the queue.
     * @throws GameMechanicException If the game is over.
     */
    public void goToNextStage() throws GameMechanicException {
        finishedStages.add(getCurrentGameStage());
        stages.remove();
    }
    
    /**
     * Checks if a stage is over.
     * @return true if the current round is equal to the abstract end round, otherwise false.
     * @throws GameMechanicException If game is over.
     */
    public boolean isStageOver() throws GameMechanicException {
        return getCurrentGameStage().getRound() == PHASE_IS_DONE;
    }
}
