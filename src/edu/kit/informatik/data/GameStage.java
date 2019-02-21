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

    private int stagenumber;
    private int currentround;
    private boolean vcplaced; // Tracks if a Nature piece has been placed.
    private Piece naturepiece;
    private Map<Integer, Piece> setofmcpieces;
    private Map<Integer, Boolean> mcplaced;
    
    /**
     * Constructor for a game stage (either the first phase or the second).
     * @param naturepiece Either Vesta or Ceres depending on which game stage.
     * @param setofmcpieces The set of Mission Control pieces.
     * @param stagenumber Number that represents which stage.
     */
    
    public GameStage(Piece naturepiece, Map<Integer, Piece> setofmcpieces, int stagenumber) {
        this.naturepiece = naturepiece;
        this.setofmcpieces = setofmcpieces;
        this.stagenumber = stagenumber;
        this.mcplaced = new HashMap<Integer, Boolean>();
        
        /**
         * The point of this is to keep track of the Mission Control pieces (i.e. if they are on the board or not).
         * The integer keys represent each piece (i.e. 2 for the piece of length 2).
         */
        
        mcplaced.put(2, false);
        mcplaced.put(3, false);
        mcplaced.put(4, false);
        mcplaced.put(5, false);
        mcplaced.put(6, false);
        mcplaced.put(7, false);
        this.currentround = 1;
    }
    
    /**
     * Method to check if a Nature piece corresponding to the game phase is already on the board.
     * @return true if the corresponding Nature piece is already on the board, otherwise false.
     */
    
    public boolean isVCPlaced() {
        return vcplaced;
    }
    
    /**
     * Method to assert that the corresponding Nature piece is on the board.
     */
    
    public void placeVC() {
        this.vcplaced = true;
    }
    
    /**
     * Getter method for the corresponding Nature piece in use.
     * @return The Nature piece.
     */
    
    public Piece getNaturePiece() {
        return naturepiece;
    }
    
    /**
     * Getter method for the map of Mission Control pieces.
     * @return The map of Mission Control pieces.
     */
    
    public Map<Integer, Piece> getMCPieces() {
        return setofmcpieces;
    }
    
    /**
     * Getter method for the stage number.
     * @return The stage number.
     */
    
    public int getStageNumber() {
        return stagenumber;
    }
    
    /**
     * Method to check whether a Mission Control piece of a certain length is on the board.
     * @param length The length of the Mission Control piece.
     * @return true if the piece is on the board, otherwise false.
     */
    
    public boolean isMCPlaced(int length) {
        return mcplaced.get(length);
    }
    
    /**
     * Method to assert that a Mission Control piece of a certain length is on the board.
     * @param length The length of the piece.
     */
    
    public void placeMC(int length) {
        mcplaced.put(length, true);
    }
    
    /**
     * Increment the round.
     */
    
    public void goToNextRound() {
        currentround++;
    }
    
    /**
     * Getter method for the current round.
     * @return The current round.
     */
    
    public int getRound() {
        return currentround;
    }
}
