package edu.kit.informatik.tests;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import edu.kit.informatik.data.Piece;
/**
 * 
 * @author Rakan Zeid Al Masri
 * @version 1.0
 */

public class PieceTest {

    /**
     * Test for reflexive.
     */
    @Test
    public void equalsReflexiveTestN() {
        Piece ceres = new Piece('C', "Ceres");
        Piece vesta = new Piece('V', "Vesta");
        Piece missioncontrol = new Piece(2);
        Assert.assertTrue(ceres.equals(ceres));
        Assert.assertTrue(vesta.equals(vesta));
        Assert.assertTrue(missioncontrol.equals(missioncontrol));
    }
    
    /**
     * Symmetric test.
     */
    @Test
    public void equalsSymmetricTest() {
        Piece ceres = new Piece('C', "Ceres");
        Piece vesta = new Piece('C', "Vesta");
        Assert.assertFalse(ceres.equals(vesta));
    }
    
    /**
     * Null test.
     */
    @Test
    public void equalsNullTest() {
        Piece ceres = new Piece('C', "Ceres");
        Assert.assertFalse(ceres.equals(null));
    }
    
    /**
     * Hash code test.
     */
    @Test
    public void hashCodeTest() {
        Piece two = new Piece(2);
        Piece three = new Piece(2);
        Assert.assertEquals(two.hashCode(), three.hashCode());
    }
    
}
