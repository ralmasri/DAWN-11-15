package edu.kit.informatik.data;

import java.util.HashMap;
import java.util.Map;

/**
 * Class that represents a stage/phase of DAWN 11/15.
 * 
 * @author Rakan Zeid Al Masri
 * @version 1.0
 */
public class GameStage {

    /** The stage number. */
    private int stageNumber;
    
    /** The current round. */
    private int currentRound;
    
    /** Tracks if a Nature piece has been placed. */
    private boolean vcPlaced;
    
    /** Nature piece for the game stage. */
    private Piece naturePiece;
    
    /** A set of Mission Control pieces. The key is the length of the piece. */
    private Map<Integer, Piece> setofMCPieces;
    
    /**
     * Keeps track of the Mission Control pieces and if they have been placed.
     * The key is the length of the piece and the value is true when the piece has been placed.
     */
    private Map<Integer, Boolean> mcPlaced;
    
    /**
     * Constructor for a game stage (either the first phase or the second).
     * @param naturePiece Either Vesta or Ceres depending on which game stage.
     * @param setofMCPieces The set of Mission Control pieces.
     * @param stageNumber Number that represents which stage.
     */
    public GameStage(Piece naturePiece, Map<Integer, Piece> setofMCPieces, int stageNumber) {
        this.naturePiece = naturePiece;
        this.setofMCPieces = setofMCPieces;
        this.stageNumber = stageNumber;
        this.mcPlaced = new HashMap<>(); 
        fillMCPlaced(this.mcPlaced);
        this.currentRound = 1; 
    }
    
    /**
     * Fills the mcPlaced map with all the piece lengths as keys and false for the values, 
     * because at the beginning of the game pieces aren't on the board yet.
     * @param mcplaced The map that keeps track of Mission Control pieces.
     */
    private void fillMCPlaced(Map<Integer, Boolean> mcplaced) {
        mcPlaced.put(2, false);
        mcPlaced.put(3, false);
        mcPlaced.put(4, false);
        mcPlaced.put(5, false);
        mcPlaced.put(6, false);
        mcPlaced.put(7, false); // DAWN piece.
    }
    
    /**
     * Method to check if a Nature piece corresponding to the game phase is already on the board.
     * @return true if the corresponding Nature piece is already on the board, otherwise false.
     */
    public boolean isVCPlaced() {
        return vcPlaced;
    }
    
    /**
     * Method to assert that the corresponding Nature piece is on the board.
     */
    public void placeVC() {
        this.vcPlaced = true;
    }
    
    /**
     * Getter method for the corresponding Nature piece in use.
     * @return The Nature piece.
     */
    public Piece getNaturePiece() {
        return naturePiece;
    }
    
    /**
     * Getter method for the map of Mission Control pieces.
     * @return The map of Mission Control pieces.
     */
    public Map<Integer, Piece> getMCPieces() {
        return setofMCPieces;
    }
    
    /**
     * Getter method for the stage number.
     * @return The stage number.
     */
    public int getStageNumber() {
        return stageNumber;
    }
    
    /**
     * Method to check whether a Mission Control piece of a certain length is on the board.
     * @param length The length of the Mission Control piece.
     * @return true if the piece is on the board, otherwise false.
     */
    public boolean isMCPlaced(int length) {
        return mcPlaced.get(length);
    }
    
    /**
     * Method to assert that a Mission Control piece of a certain length is on the board.
     * @param length The length of the piece.
     */
    public void placeMC(int length) {
        mcPlaced.put(length, true);
    }
    
    /**
     * Increment the round.
     */
    public void goToNextRound() {
        currentRound++;
    }
    
    /**
     * Getter method for the current round.
     * @return The current round.
     */
    public int getRound() {
        return currentRound;
    }
}
