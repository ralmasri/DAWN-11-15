/**
 * 
 */
package edu.kit.informatik.tests;

import org.junit.Assert;
import org.junit.Test;

import edu.kit.informatik.data.Cell;

/**
 * 
 * @author Rakan Zeid Al Masri
 * @version 1.0
 */

public class CellTest {

    /**
     * Reflexive test.
     */
    @Test
    public void equalsReflexiveTest() {
        Cell cell = new Cell(6, 6);
     
        Assert.assertTrue(cell.equals(cell));
   }
    
    /**
     * Symmetric Test
     */
    @Test
    public void equalsSymmetricTest() {
        Cell cell1 = new Cell(6, 6);
        Cell cell2 = new Cell(6, 6);
        Assert.assertTrue(cell1.equals(cell2));
        Assert.assertTrue(cell2.equals(cell1));
    }
    
    /**
     * Null test
     */
    @Test
    public void equalsNullTest() {
        Cell cell = new Cell(10, 6);
        Assert.assertFalse(cell.equals(null));
    }
    
    /**
     * Test.
     */
    @Test
    public void equalsUnequalTest() {
        Cell cell1 = new Cell(4, 5);
        Cell cell2 = new Cell(3, 5);
        Assert.assertFalse(cell1.equals(cell2));
    }
    
    /**
     * hash code test.
     */
    @Test
    public void hashCodeTest() {
        Cell cell1 = new Cell(4, 4);
        Cell cell2 = new Cell(4, 4);
        Assert.assertEquals(cell1.hashCode(), cell2.hashCode());
    }
}
