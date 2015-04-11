package org.ufpr.cbio.poc.test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.ufpr.cbio.poc.domain.Residue;
import org.ufpr.cbio.poc.domain.Residue.Point;
import org.ufpr.cbio.poc.domain.ResidueType;
import org.ufpr.cbio.poc.main.CornerTest;
import org.ufpr.cbio.poc.utils.EnumMovements;

/**
 *
 *
 * @author vfontoura
 */
public class CrankshaftMovementsTest {

    /**
     * Test the following scenario
     * 
     * <pre>
     * Input:
     * -1   -1  -1  -1  -1   -1  -1  -1
     * -1    0   1   4   5   -1  -1  -1  
     * -1   -1   2   3  -1   -1  -1  -1  
     * -1   -1  -1  -1  -1   -1  -1  -1
     * </pre>
     * 
     * <pre>
     * Expected:
     * -1   -1   2   3   -1  -1  -1  -1  
     * -1    0   1   4    5  -1  -1  -1  
     * -1   -1  -1  -1   -1  -1  -1  -1  
     * -1   -1  -1  -1   -1  -1  -1  -1
     * </pre>
     * 
     */
    @Test
    public void test1() {

        List<Residue> residues = new ArrayList<Residue>();
        // 0 to n left bottom corner in matrix
        residues.add(new Residue(new Point(1, 3), ResidueType.H));
        residues.add(new Residue(new Point(2, 3), ResidueType.H));
        residues.add(new Residue(new Point(2, 4), ResidueType.H));
        residues.add(new Residue(new Point(3, 4), ResidueType.H));
        residues.add(new Residue(new Point(3, 3), ResidueType.H));
        residues.add(new Residue(new Point(4, 3), ResidueType.H));

        EnumMovements[] movements =
            new EnumMovements[] { EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT,
                EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT, };

        List<Residue> applySolution = CornerTest.applyMovements(residues, movements);

        Assert.assertEquals(1, applySolution.get(0).getPoint().getX());
        Assert.assertEquals(3, applySolution.get(0).getPoint().getY());

        Assert.assertEquals(2, applySolution.get(1).getPoint().getX());
        Assert.assertEquals(3, applySolution.get(1).getPoint().getY());

        Assert.assertEquals(2, applySolution.get(2).getPoint().getX());
        Assert.assertEquals(2, applySolution.get(2).getPoint().getY());

        Assert.assertEquals(3, applySolution.get(3).getPoint().getX());
        Assert.assertEquals(2, applySolution.get(3).getPoint().getY());

        Assert.assertEquals(3, applySolution.get(4).getPoint().getX());
        Assert.assertEquals(3, applySolution.get(4).getPoint().getY());

        Assert.assertEquals(4, applySolution.get(5).getPoint().getX());
        Assert.assertEquals(3, applySolution.get(5).getPoint().getY());

    }

    /**
     * Test the following scenario
     * 
     * <pre>
     * Input:
     * -1  -1  -1  -1  -1  -1  -1  -1  
     * -1   5   4   1   0  -1  -1  -1  
     * -1  -1   3   2  -1  -1  -1  -1  
     * -1  -1  -1  -1  -1  -1  -1  -1
     * </pre>
     * 
     * <pre>
     * Expected:
     * 
     * -1  -1  -1  -1  -1  -1  -1  -1  
     * -1  -1   3   2  -1  -1  -1  -1  
     * -1   5   4   1   0  -1  -1  -1  
     * -1  -1  -1  -1  -1  -1  -1  -1
     *
     * </pre>
     * 
     */
    @Test
    public void test2() {

        List<Residue> residues = new ArrayList<Residue>();
        // n to 0 right bottom corner in matrix
        residues.add(new Residue(new Point(4, 3), ResidueType.H));
        residues.add(new Residue(new Point(3, 3), ResidueType.H));
        residues.add(new Residue(new Point(3, 4), ResidueType.H));
        residues.add(new Residue(new Point(2, 4), ResidueType.H));
        residues.add(new Residue(new Point(2, 3), ResidueType.H));
        residues.add(new Residue(new Point(1, 3), ResidueType.H));

        EnumMovements[] movements =
            new EnumMovements[] { EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT,
                EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT, };

        List<Residue> applySolution = CornerTest.applyMovements(residues, movements);

        Assert.assertEquals(4, applySolution.get(0).getPoint().getX());
        Assert.assertEquals(3, applySolution.get(0).getPoint().getY());

        Assert.assertEquals(3, applySolution.get(1).getPoint().getX());
        Assert.assertEquals(3, applySolution.get(1).getPoint().getY());

        Assert.assertEquals(3, applySolution.get(2).getPoint().getX());
        Assert.assertEquals(2, applySolution.get(2).getPoint().getY());

        Assert.assertEquals(2, applySolution.get(3).getPoint().getX());
        Assert.assertEquals(2, applySolution.get(3).getPoint().getY());

        Assert.assertEquals(2, applySolution.get(4).getPoint().getX());
        Assert.assertEquals(3, applySolution.get(4).getPoint().getY());

        Assert.assertEquals(1, applySolution.get(5).getPoint().getX());
        Assert.assertEquals(3, applySolution.get(5).getPoint().getY());

    }

    /**
     * Test the following scenario
     * 
     * <pre>
     *  Input:
     * -1  -1  -1  -1  -1  -1  -1  -1  
     * -1  -1   2   3  -1  -1  -1  -1  
     * -1   0   1   4   5   -1  -1  -1  
     * -1  -1  -1  -1  -1  -1  -1  -1
     * </pre>
     * 
     * <pre>
     *  Expected:
     *  
     * -1  -1  -1  -1  -1  -1  -1  -1  
     * -1   0   1   4   5  -1  -1  -1  
     * -1  -1   2   3  -1  -1  -1  -1  
     * -1  -1  -1  -1  -1  -1  -1  -1
     * 
     *
     * </pre>
     * 
     */
    @Test
    public void test3() {

        List<Residue> residues = new ArrayList<Residue>();

        // 0 to n left upper corner in matrix
        residues.add(new Residue(new Point(1, 2), ResidueType.H));
        residues.add(new Residue(new Point(2, 2), ResidueType.H));
        residues.add(new Residue(new Point(2, 1), ResidueType.H));
        residues.add(new Residue(new Point(3, 1), ResidueType.H));
        residues.add(new Residue(new Point(3, 2), ResidueType.H));
        residues.add(new Residue(new Point(4, 2), ResidueType.H));

        EnumMovements[] movements =
            new EnumMovements[] { EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT,
                EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT, };

        List<Residue> applySolution = CornerTest.applyMovements(residues, movements);

        Assert.assertEquals(1, applySolution.get(0).getPoint().getX());
        Assert.assertEquals(2, applySolution.get(0).getPoint().getY());

        Assert.assertEquals(2, applySolution.get(1).getPoint().getX());
        Assert.assertEquals(2, applySolution.get(1).getPoint().getY());

        Assert.assertEquals(2, applySolution.get(2).getPoint().getX());
        Assert.assertEquals(3, applySolution.get(2).getPoint().getY());

        Assert.assertEquals(3, applySolution.get(3).getPoint().getX());
        Assert.assertEquals(3, applySolution.get(3).getPoint().getY());

        Assert.assertEquals(3, applySolution.get(4).getPoint().getX());
        Assert.assertEquals(2, applySolution.get(4).getPoint().getY());

        Assert.assertEquals(4, applySolution.get(5).getPoint().getX());
        Assert.assertEquals(2, applySolution.get(5).getPoint().getY());

    }

    /**
     * Test the following scenario
     * 
     * <pre>
     *  Input:
     * -1  -1  -1  -1  -1  -1  -1  -1  
     * -1  -1   3   2  -1  -1  -1  -1  
     * -1   5   4   1   0  -1  -1  -1  
     * -1  -1  -1  -1  -1  -1  -1  -1
     * </pre>
     * 
     * <pre>
     *  Expected:
     *      
     * -1  -1  -1  -1  -1  -1  -1  -1  
     * -1  -1  -1  -1  -1  -1  -1  -1  
     * -1   5   4   1   0  -1  -1  -1  
     * -1  -1   3   2  -1  -1  -1  -1
     *
     * </pre>
     * 
     */
    @Test
    public void test4() {

        List<Residue> residues = new ArrayList<Residue>();

        // n to 0 right upper corner in matrix

        residues.add(new Residue(new Point(4, 2), ResidueType.H));
        residues.add(new Residue(new Point(3, 2), ResidueType.H));
        residues.add(new Residue(new Point(3, 1), ResidueType.H));
        residues.add(new Residue(new Point(2, 1), ResidueType.H));
        residues.add(new Residue(new Point(2, 2), ResidueType.H));
        residues.add(new Residue(new Point(1, 2), ResidueType.H));
        EnumMovements[] movements =
            new EnumMovements[] { EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT,
                EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT, };

        List<Residue> applySolution = CornerTest.applyMovements(residues, movements);

        Assert.assertEquals(4, applySolution.get(0).getPoint().getX());
        Assert.assertEquals(2, applySolution.get(0).getPoint().getY());

        Assert.assertEquals(3, applySolution.get(1).getPoint().getX());
        Assert.assertEquals(2, applySolution.get(1).getPoint().getY());

        Assert.assertEquals(3, applySolution.get(2).getPoint().getX());
        Assert.assertEquals(3, applySolution.get(2).getPoint().getY());

        Assert.assertEquals(2, applySolution.get(3).getPoint().getX());
        Assert.assertEquals(3, applySolution.get(3).getPoint().getY());

        Assert.assertEquals(2, applySolution.get(4).getPoint().getX());
        Assert.assertEquals(2, applySolution.get(4).getPoint().getY());

        Assert.assertEquals(1, applySolution.get(5).getPoint().getX());
        Assert.assertEquals(2, applySolution.get(5).getPoint().getY());

    }

    /**
     * Test the following scenario
     * 
     * <pre>
     *  Input: 
     * -1  -1   0   -1   
     * -1   2   1   -1    
     * -1   3   4   -1 
     * -1  -1   5   -1
     * </pre>
     * 
     * <pre>
     *  Expected:
     *      
     * -1  -1  0  -1  
     * -1  -1  1   2    
     * -1  -1  4   3    
     * -1  -1  5  -1
     *
     * </pre>
     * 
     */
    @Test
    public void test5() {

        List<Residue> residues = new ArrayList<Residue>();

        // vertical left upper (0 to n)
        residues.add(new Residue(new Point(2, 1), ResidueType.H));
        residues.add(new Residue(new Point(2, 2), ResidueType.H));
        residues.add(new Residue(new Point(1, 2), ResidueType.H));
        residues.add(new Residue(new Point(1, 3), ResidueType.H));
        residues.add(new Residue(new Point(2, 3), ResidueType.H));
        residues.add(new Residue(new Point(2, 4), ResidueType.H));

        EnumMovements[] movements =
            new EnumMovements[] { EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT,
                EnumMovements.CORNER, EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT, };
        // Applying the same movement to all elements turned out that the
        // movement was being applied and than be unapplied in the subsequent
        // element
        List<Residue> applySolution = CornerTest.applyMovements(residues, movements);

        Assert.assertEquals(2, applySolution.get(0).getPoint().getX());
        Assert.assertEquals(1, applySolution.get(0).getPoint().getY());

        Assert.assertEquals(2, applySolution.get(1).getPoint().getX());
        Assert.assertEquals(2, applySolution.get(1).getPoint().getY());

        Assert.assertEquals(3, applySolution.get(2).getPoint().getX());
        Assert.assertEquals(2, applySolution.get(2).getPoint().getY());

        Assert.assertEquals(3, applySolution.get(3).getPoint().getX());
        Assert.assertEquals(3, applySolution.get(3).getPoint().getY());

        Assert.assertEquals(2, applySolution.get(4).getPoint().getX());
        Assert.assertEquals(3, applySolution.get(4).getPoint().getY());

        Assert.assertEquals(2, applySolution.get(5).getPoint().getX());
        Assert.assertEquals(4, applySolution.get(5).getPoint().getY());

    }

    /**
     * Test the following scenario
     * 
     * <pre>
     *  Input: 
     *        
     * -1  -1  5   -1  
     * -1   3  4   -1    
     * -1   2  1   -1    
     * -1  -1  0   -1
     * </pre>
     * 
     * <pre>
     *  Expected:
     *      
     * -1   -1  5  -1    
     * -1   -1  4   3    
     * -1   -1  1   2    
     * -1   -1  0  -1
     *
     * </pre>
     * 
     */
    @Test
    public void test6() {

        List<Residue> residues = new ArrayList<Residue>();

        // vertical left upper (0 to n)
        residues.add(new Residue(new Point(2, 4), ResidueType.H));
        residues.add(new Residue(new Point(2, 3), ResidueType.H));
        residues.add(new Residue(new Point(1, 3), ResidueType.H));
        residues.add(new Residue(new Point(1, 2), ResidueType.H));
        residues.add(new Residue(new Point(2, 2), ResidueType.H));
        residues.add(new Residue(new Point(2, 1), ResidueType.H));

        EnumMovements[] movements =
            new EnumMovements[] { EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT,
                EnumMovements.CORNER, EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT, };
        // Applying the same movement to all elements turned out that the
        // movement was being applied and than be unapplied in the subsequent
        // element
        List<Residue> applySolution = CornerTest.applyMovements(residues, movements);

        Assert.assertEquals(2, applySolution.get(0).getPoint().getX());
        Assert.assertEquals(4, applySolution.get(0).getPoint().getY());

        Assert.assertEquals(2, applySolution.get(1).getPoint().getX());
        Assert.assertEquals(3, applySolution.get(1).getPoint().getY());

        Assert.assertEquals(3, applySolution.get(2).getPoint().getX());
        Assert.assertEquals(3, applySolution.get(2).getPoint().getY());

        Assert.assertEquals(3, applySolution.get(3).getPoint().getX());
        Assert.assertEquals(2, applySolution.get(3).getPoint().getY());

        Assert.assertEquals(2, applySolution.get(4).getPoint().getX());
        Assert.assertEquals(2, applySolution.get(4).getPoint().getY());

        Assert.assertEquals(2, applySolution.get(5).getPoint().getX());
        Assert.assertEquals(1, applySolution.get(5).getPoint().getY());

    }

    /**
     * Test the following scenario
     * 
     * <pre>
     *  Input: 
     *      
     * -1  0  -1  -1    
     * -1  1   2  -1    
     * -1  4   3  -1    
     * -1  5  -1  -1
     * </pre>
     * 
     * <pre>
     *  Expected:
     *      
     * -1  0  -1  -1    
     *  2  1  -1  -1    
     *  3  4  -1  -1    
     * -1  5  -1  -1
     *
     * </pre>
     * 
     */
    @Test
    public void test7() {

        List<Residue> residues = new ArrayList<Residue>();

        // vertical right upper (0 to n)
        residues.add(new Residue(new Point(1, 1), ResidueType.H));
        residues.add(new Residue(new Point(1, 2), ResidueType.H));
        residues.add(new Residue(new Point(2, 2), ResidueType.H));
        residues.add(new Residue(new Point(2, 3), ResidueType.H));
        residues.add(new Residue(new Point(1, 3), ResidueType.H));
        residues.add(new Residue(new Point(1, 4), ResidueType.H));

        EnumMovements[] movements =
            new EnumMovements[] { EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT,
                EnumMovements.CORNER, EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT, };
        // Applying the same movement to all elements turned out that the
        // movement was being applied and than be unapplied in the subsequent
        // element
        List<Residue> applySolution = CornerTest.applyMovements(residues, movements);

        Assert.assertEquals(1, applySolution.get(0).getPoint().getX());
        Assert.assertEquals(1, applySolution.get(0).getPoint().getY());

        Assert.assertEquals(1, applySolution.get(1).getPoint().getX());
        Assert.assertEquals(2, applySolution.get(1).getPoint().getY());

        Assert.assertEquals(0, applySolution.get(2).getPoint().getX());
        Assert.assertEquals(2, applySolution.get(2).getPoint().getY());

        Assert.assertEquals(0, applySolution.get(3).getPoint().getX());
        Assert.assertEquals(3, applySolution.get(3).getPoint().getY());

        Assert.assertEquals(1, applySolution.get(4).getPoint().getX());
        Assert.assertEquals(3, applySolution.get(4).getPoint().getY());

        Assert.assertEquals(1, applySolution.get(5).getPoint().getX());
        Assert.assertEquals(4, applySolution.get(5).getPoint().getY());

    }

    /**
     * Test the following scenario
     * 
     * <pre>
     *  Input: 
     *      
     * -1  5  -1  -1    
     * -1  4   3  -1    
     * -1  1   2  -1    
     * -1  0  -1  -1
     * 
     * </pre>
     * 
     * <pre>
     *   Expected:
     *       
     * -1   5  -1  -1   
     *  3   4  -1  -1    
     *  2   1  -1  -1    
     * -1   0  -1  -1
     *
     * </pre>
     * 
     */
    @Test
    public void test8() {

        List<Residue> residues = new ArrayList<Residue>();

        // vertical right bottom (n to 0)
        residues.add(new Residue(new Point(1, 4), ResidueType.H));
        residues.add(new Residue(new Point(1, 3), ResidueType.H));
        residues.add(new Residue(new Point(2, 3), ResidueType.H));
        residues.add(new Residue(new Point(2, 2), ResidueType.H));
        residues.add(new Residue(new Point(1, 2), ResidueType.H));
        residues.add(new Residue(new Point(1, 1), ResidueType.H));

        EnumMovements[] movements =
            new EnumMovements[] { EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT,
                EnumMovements.CORNER, EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT, };
        // Applying the same movement to all elements turned out that the
        // movement was being applied and than be unapplied in the subsequent
        // element
        List<Residue> applySolution = CornerTest.applyMovements(residues, movements);

        Assert.assertEquals(1, applySolution.get(0).getPoint().getX());
        Assert.assertEquals(4, applySolution.get(0).getPoint().getY());

        Assert.assertEquals(1, applySolution.get(1).getPoint().getX());
        Assert.assertEquals(3, applySolution.get(1).getPoint().getY());

        Assert.assertEquals(0, applySolution.get(2).getPoint().getX());
        Assert.assertEquals(3, applySolution.get(2).getPoint().getY());

        Assert.assertEquals(0, applySolution.get(3).getPoint().getX());
        Assert.assertEquals(2, applySolution.get(3).getPoint().getY());

        Assert.assertEquals(1, applySolution.get(4).getPoint().getX());
        Assert.assertEquals(2, applySolution.get(4).getPoint().getY());

        Assert.assertEquals(1, applySolution.get(5).getPoint().getX());
        Assert.assertEquals(1, applySolution.get(5).getPoint().getY());

    }

    /**
     * Test the following scenario
     * 
     * <pre>
     *   Input: 
     *       
     * -1  -1  -1  -1  -1  -1  
     * -1  -1   2   3  -1  -1  
     * -1   0   1  -1  -1  -1  
     * -1  -1  -1  -1  -1  -1
     * 
     * </pre>
     * 
     * <pre>
     *   Expected:
     *       
     * 
     * -1  -1  -1  -1  -1  -1  
     * -1  -1  2   3   -1  -1  
     * -1  0   1   -1  -1  -1  
     * -1  -1  -1  -1  -1  -1
     *
     * </pre>
     * 
     */
    @Test
    public void test9() {

        List<Residue> residues = new ArrayList<Residue>();

        // vertical right bottom (n to 0)
        residues.add(new Residue(new Point(1, 2), ResidueType.H));
        residues.add(new Residue(new Point(2, 2), ResidueType.H));
        residues.add(new Residue(new Point(2, 1), ResidueType.H));
        residues.add(new Residue(new Point(3, 1), ResidueType.H));

        EnumMovements[] movements =
            new EnumMovements[] { EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT,
                EnumMovements.CRANKSHAFT, };
        // Applying the same movement to all elements turned out that the
        // movement was being applied and than be unapplied in the subsequent
        // element
        List<Residue> applySolution = CornerTest.applyMovements(residues, movements);

        Assert.assertEquals(1, applySolution.get(0).getPoint().getX());
        Assert.assertEquals(2, applySolution.get(0).getPoint().getY());

        Assert.assertEquals(2, applySolution.get(1).getPoint().getX());
        Assert.assertEquals(2, applySolution.get(1).getPoint().getY());

        Assert.assertEquals(2, applySolution.get(2).getPoint().getX());
        Assert.assertEquals(1, applySolution.get(2).getPoint().getY());

        Assert.assertEquals(3, applySolution.get(3).getPoint().getX());
        Assert.assertEquals(1, applySolution.get(3).getPoint().getY());

    }

    /**
     * Test the following scenario
     * 
     * <pre>
     *   Input:
     * 
     *
     * 
     * </pre>
     * 
     * <pre>
     *   Expected:
     * 
     * 
     * 
     *
     * </pre>
     * 
     */
    @Test
    public void test10() {

        List<Residue> residues = new ArrayList<Residue>();

        // vertical right bottom (n to 0)
        residues.add(new Residue(new Point(1, 3), ResidueType.H));
        residues.add(new Residue(new Point(1, 4), ResidueType.H));
        residues.add(new Residue(new Point(2, 4), ResidueType.H));
        residues.add(new Residue(new Point(2, 3), ResidueType.H));
        residues.add(new Residue(new Point(3, 3), ResidueType.H));
        residues.add(new Residue(new Point(3, 4), ResidueType.H));
        residues.add(new Residue(new Point(4, 4), ResidueType.H));
        residues.add(new Residue(new Point(4, 3), ResidueType.H));
        residues.add(new Residue(new Point(4, 2), ResidueType.H));
        residues.add(new Residue(new Point(4, 1), ResidueType.H));
        residues.add(new Residue(new Point(3, 1), ResidueType.H));
        residues.add(new Residue(new Point(3, 2), ResidueType.H));
        residues.add(new Residue(new Point(2, 2), ResidueType.H));
        residues.add(new Residue(new Point(2, 1), ResidueType.H));
        residues.add(new Residue(new Point(1, 1), ResidueType.H));
        residues.add(new Residue(new Point(1, 2), ResidueType.H));

        EnumMovements[] movements =
            new EnumMovements[] { EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT,
                EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT,
                EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT,
                EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT,
                EnumMovements.CRANKSHAFT, };
        // Applying the same movement to all elements turned out that the
        // movement was being applied and than be unapplied in the subsequent
        // element
        List<Residue> applySolution = CornerTest.applyMovements(residues, movements);

        Assert.assertEquals(1, applySolution.get(0).getPoint().getX());
        Assert.assertEquals(3, applySolution.get(0).getPoint().getY());

        Assert.assertEquals(1, applySolution.get(1).getPoint().getX());
        Assert.assertEquals(4, applySolution.get(1).getPoint().getY());

        Assert.assertEquals(2, applySolution.get(2).getPoint().getX());
        Assert.assertEquals(4, applySolution.get(2).getPoint().getY());

        Assert.assertEquals(2, applySolution.get(3).getPoint().getX());
        Assert.assertEquals(5, applySolution.get(3).getPoint().getY());

        Assert.assertEquals(3, applySolution.get(4).getPoint().getX());
        Assert.assertEquals(5, applySolution.get(4).getPoint().getY());

        Assert.assertEquals(3, applySolution.get(5).getPoint().getX());
        Assert.assertEquals(4, applySolution.get(5).getPoint().getY());

        Assert.assertEquals(4, applySolution.get(6).getPoint().getX());
        Assert.assertEquals(4, applySolution.get(6).getPoint().getY());

        Assert.assertEquals(4, applySolution.get(7).getPoint().getX());
        Assert.assertEquals(3, applySolution.get(7).getPoint().getY());

        Assert.assertEquals(4, applySolution.get(8).getPoint().getX());
        Assert.assertEquals(2, applySolution.get(8).getPoint().getY());

        Assert.assertEquals(4, applySolution.get(9).getPoint().getX());
        Assert.assertEquals(1, applySolution.get(9).getPoint().getY());

        Assert.assertEquals(3, applySolution.get(10).getPoint().getX());
        Assert.assertEquals(1, applySolution.get(10).getPoint().getY());

        Assert.assertEquals(3, applySolution.get(11).getPoint().getX());
        Assert.assertEquals(0, applySolution.get(11).getPoint().getY());

        Assert.assertEquals(2, applySolution.get(12).getPoint().getX());
        Assert.assertEquals(0, applySolution.get(12).getPoint().getY());

        Assert.assertEquals(2, applySolution.get(13).getPoint().getX());
        Assert.assertEquals(1, applySolution.get(13).getPoint().getY());

        Assert.assertEquals(1, applySolution.get(14).getPoint().getX());
        Assert.assertEquals(1, applySolution.get(14).getPoint().getY());

        Assert.assertEquals(1, applySolution.get(15).getPoint().getX());
        Assert.assertEquals(2, applySolution.get(15).getPoint().getY());

    }

    /**
     * Test the following scenario NOTE: Is expected for this test that the
     * output is equal to input
     * 
     * <pre>
     *   Input:
     * -1   5   4   3  -1   -1  
     * -1   6   7   2   1   -1    
     * -1  -1  -1  -1   0   -1
     *
     * 
     * </pre>
     * 
     * <pre>
     *   Expected:
     * 
     * -1   5   4   3  -1   -1  
     * -1   6   7   2   1   -1    
     * -1  -1  -1  -1   0   -1
     * 
     *
     * </pre>
     * 
     */
    @Test
    public void test11() {

        List<Residue> residues = new ArrayList<Residue>();

        residues.add(new Residue(new Point(4, 2), ResidueType.H));
        residues.add(new Residue(new Point(4, 1), ResidueType.H));
        residues.add(new Residue(new Point(3, 1), ResidueType.H));
        residues.add(new Residue(new Point(3, 0), ResidueType.H));
        residues.add(new Residue(new Point(2, 0), ResidueType.P));
        residues.add(new Residue(new Point(1, 0), ResidueType.H));
        residues.add(new Residue(new Point(1, 1), ResidueType.H));
        residues.add(new Residue(new Point(2, 1), ResidueType.H));

        EnumMovements[] movements =
            new EnumMovements[] { EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT,
                EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT,
                EnumMovements.CRANKSHAFT };
        // Applying the same movement to all elements turned out that the
        // movement was being applied and than be unapplied in the subsequent
        // element
        List<Residue> applySolution = CornerTest.applyMovements(residues, movements);

        Assert.assertEquals(4, applySolution.get(0).getPoint().getX());
        Assert.assertEquals(2, applySolution.get(0).getPoint().getY());

        Assert.assertEquals(4, applySolution.get(1).getPoint().getX());
        Assert.assertEquals(1, applySolution.get(1).getPoint().getY());

        Assert.assertEquals(3, applySolution.get(2).getPoint().getX());
        Assert.assertEquals(1, applySolution.get(2).getPoint().getY());

        Assert.assertEquals(3, applySolution.get(3).getPoint().getX());
        Assert.assertEquals(0, applySolution.get(3).getPoint().getY());

        Assert.assertEquals(2, applySolution.get(4).getPoint().getX());
        Assert.assertEquals(0, applySolution.get(4).getPoint().getY());

        Assert.assertEquals(1, applySolution.get(5).getPoint().getX());
        Assert.assertEquals(0, applySolution.get(5).getPoint().getY());

        Assert.assertEquals(1, applySolution.get(6).getPoint().getX());
        Assert.assertEquals(1, applySolution.get(6).getPoint().getY());

        Assert.assertEquals(2, applySolution.get(7).getPoint().getX());
        Assert.assertEquals(1, applySolution.get(7).getPoint().getY());

    }

    /**
     * Test the following scenario NOTE: Is expected for this test that the
     * output is equal to input
     * 
     * <pre>
     *   Input:
     * -1  -1  -1  -1   0     
     * -1   6   7   2   1    
     * -1   5   4   3  -1   
     * -1  -1  -1  -1  -1
     *
     * 
     * </pre>
     * 
     * <pre>
     *   Expected:
     * 
     * -1  -1  -1  -1   0     
     * -1   6   7   2   1    
     * -1   5   4   3  -1   
     * -1  -1  -1  -1  -1
     * 
     *
     * </pre>
     * 
     */
    @Test
    public void test12() {

        List<Residue> residues = new ArrayList<Residue>();

        residues.add(new Residue(new Point(4, 4), ResidueType.H));
        residues.add(new Residue(new Point(4, 5), ResidueType.H));
        residues.add(new Residue(new Point(3, 5), ResidueType.H));
        residues.add(new Residue(new Point(3, 6), ResidueType.H));
        residues.add(new Residue(new Point(2, 6), ResidueType.P));
        residues.add(new Residue(new Point(1, 6), ResidueType.H));
        residues.add(new Residue(new Point(1, 5), ResidueType.H));
        residues.add(new Residue(new Point(2, 5), ResidueType.H));

        EnumMovements[] movements =
            new EnumMovements[] { EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT,
                EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT,
                EnumMovements.CRANKSHAFT };
        // Applying the same movement to all elements turned out that the
        // movement was being applied and than be unapplied in the subsequent
        // element
        List<Residue> applySolution = CornerTest.applyMovements(residues, movements);

        Assert.assertEquals(4, applySolution.get(0).getPoint().getX());
        Assert.assertEquals(4, applySolution.get(0).getPoint().getY());

        Assert.assertEquals(4, applySolution.get(1).getPoint().getX());
        Assert.assertEquals(5, applySolution.get(1).getPoint().getY());

        Assert.assertEquals(3, applySolution.get(2).getPoint().getX());
        Assert.assertEquals(5, applySolution.get(2).getPoint().getY());

        Assert.assertEquals(3, applySolution.get(3).getPoint().getX());
        Assert.assertEquals(6, applySolution.get(3).getPoint().getY());

        Assert.assertEquals(2, applySolution.get(4).getPoint().getX());
        Assert.assertEquals(6, applySolution.get(4).getPoint().getY());

        Assert.assertEquals(1, applySolution.get(5).getPoint().getX());
        Assert.assertEquals(6, applySolution.get(5).getPoint().getY());

        Assert.assertEquals(1, applySolution.get(6).getPoint().getX());
        Assert.assertEquals(5, applySolution.get(6).getPoint().getY());

        Assert.assertEquals(2, applySolution.get(7).getPoint().getX());
        Assert.assertEquals(5, applySolution.get(7).getPoint().getY());

    }

    /**
     * Test the following scenario NOTE: Is expected for this test that the
     * output is equal to input
     * 
     * <pre>
     *   Input:
     *  
     * -1  -1  -1  -1  -1  -1    
     * -1  -1   3   4   5  -1    
     * -1   1   2   7   6  -1    
     * -1   0  -1  -1  -1  -1
     * 
     * </pre>
     * 
     * <pre>
     *   Expected:
     * 
     * -1  -1  -1  -1  -1  -1    
     * -1  -1   3   4   5  -1    
     * -1   1   2   7   6  -1    
     * -1   0  -1  -1  -1  -1
     * 
     *
     * </pre>
     * 
     */
    @Test
    public void test13() {

        List<Residue> residues = new ArrayList<Residue>();

        residues.add(new Residue(new Point(1, 3), ResidueType.H));
        residues.add(new Residue(new Point(1, 2), ResidueType.H));
        residues.add(new Residue(new Point(2, 2), ResidueType.H));
        residues.add(new Residue(new Point(2, 1), ResidueType.H));
        residues.add(new Residue(new Point(3, 1), ResidueType.P));
        residues.add(new Residue(new Point(4, 1), ResidueType.H));
        residues.add(new Residue(new Point(4, 2), ResidueType.H));
        residues.add(new Residue(new Point(3, 2), ResidueType.H));

        EnumMovements[] movements =
            new EnumMovements[] { EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT,
                EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT,
                EnumMovements.CRANKSHAFT };
        // Applying the same movement to all elements turned out that the
        // movement was being applied and than be unapplied in the subsequent
        // element
        List<Residue> applySolution = CornerTest.applyMovements(residues, movements);

        Assert.assertEquals(1, applySolution.get(0).getPoint().getX());
        Assert.assertEquals(3, applySolution.get(0).getPoint().getY());

        Assert.assertEquals(1, applySolution.get(1).getPoint().getX());
        Assert.assertEquals(2, applySolution.get(1).getPoint().getY());

        Assert.assertEquals(2, applySolution.get(2).getPoint().getX());
        Assert.assertEquals(2, applySolution.get(2).getPoint().getY());

        Assert.assertEquals(2, applySolution.get(3).getPoint().getX());
        Assert.assertEquals(1, applySolution.get(3).getPoint().getY());

        Assert.assertEquals(3, applySolution.get(4).getPoint().getX());
        Assert.assertEquals(1, applySolution.get(4).getPoint().getY());

        Assert.assertEquals(4, applySolution.get(5).getPoint().getX());
        Assert.assertEquals(1, applySolution.get(5).getPoint().getY());

        Assert.assertEquals(4, applySolution.get(6).getPoint().getX());
        Assert.assertEquals(2, applySolution.get(6).getPoint().getY());

        Assert.assertEquals(3, applySolution.get(7).getPoint().getX());
        Assert.assertEquals(2, applySolution.get(7).getPoint().getY());

    }

    /**
     * Test the following scenario
     * 
     * NOTE: Is expected for this test that the output is equal to input
     * 
     * <pre>
     *   Input:
     * -1  -1  -1  -1  -1    
     * -1   0  -1  -1  -1    
     * -1   1   2   7   6    
     * -1  -1   3   4   5    
     * -1  -1  -1  -1  -1
     * 
     * </pre>
     * 
     * <pre>
     *   Expected:
     *   
     * -1  -1  -1  -1  -1    
     * -1   0  -1  -1  -1    
     * -1   1   2   7   6    
     * -1  -1   3   4   5    
     * -1  -1  -1  -1  -1
     * 
     *
     * </pre>
     * 
     */
    @Test
    public void test14() {

        List<Residue> residues = new ArrayList<Residue>();

        residues.add(new Residue(new Point(1, 1), ResidueType.H));
        residues.add(new Residue(new Point(1, 2), ResidueType.H));
        residues.add(new Residue(new Point(2, 2), ResidueType.H));
        residues.add(new Residue(new Point(2, 3), ResidueType.H));
        residues.add(new Residue(new Point(3, 3), ResidueType.P));
        residues.add(new Residue(new Point(4, 3), ResidueType.H));
        residues.add(new Residue(new Point(4, 2), ResidueType.H));
        residues.add(new Residue(new Point(3, 2), ResidueType.H));

        EnumMovements[] movements =
            new EnumMovements[] { EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT,
                EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT,
                EnumMovements.CRANKSHAFT };
        // Applying the same movement to all elements turned out that the
        // movement was being applied and than be unapplied in the subsequent
        // element
        List<Residue> applySolution = CornerTest.applyMovements(residues, movements);

        Assert.assertEquals(1, applySolution.get(0).getPoint().getX());
        Assert.assertEquals(1, applySolution.get(0).getPoint().getY());

        Assert.assertEquals(1, applySolution.get(1).getPoint().getX());
        Assert.assertEquals(2, applySolution.get(1).getPoint().getY());

        Assert.assertEquals(2, applySolution.get(2).getPoint().getX());
        Assert.assertEquals(2, applySolution.get(2).getPoint().getY());

        Assert.assertEquals(2, applySolution.get(3).getPoint().getX());
        Assert.assertEquals(3, applySolution.get(3).getPoint().getY());

        Assert.assertEquals(3, applySolution.get(4).getPoint().getX());
        Assert.assertEquals(3, applySolution.get(4).getPoint().getY());

        Assert.assertEquals(4, applySolution.get(5).getPoint().getX());
        Assert.assertEquals(3, applySolution.get(5).getPoint().getY());

        Assert.assertEquals(4, applySolution.get(6).getPoint().getX());
        Assert.assertEquals(2, applySolution.get(6).getPoint().getY());

        Assert.assertEquals(3, applySolution.get(7).getPoint().getX());
        Assert.assertEquals(2, applySolution.get(7).getPoint().getY());

    }

    /**
     * Test the following scenario
     * 
     * NOTE: Is expected for this test that the output is equal to input
     * 
     * <pre>
     *    Input:
     * -1  -1   -1  -1    
     * -1   1    0  -1    
     *  3   2   -1  -1    
     *  4   7   -1  -1    
     *  5   6   -1  -1
     * 
     * </pre>
     * 
     * <pre>
     *   Expected:
     *   
     * -1  -1   -1  -1    
     * -1   1    0  -1    
     *  3   2   -1  -1    
     *  4   7   -1  -1    
     *  5   6   -1  -1
     * 
     *
     * </pre>
     * 
     */
    @Test
    public void test15() {

        List<Residue> residues = new ArrayList<Residue>();

        residues.add(new Residue(new Point(2, 1), ResidueType.H));
        residues.add(new Residue(new Point(1, 1), ResidueType.H));
        residues.add(new Residue(new Point(1, 2), ResidueType.H));
        residues.add(new Residue(new Point(0, 2), ResidueType.H));
        residues.add(new Residue(new Point(0, 3), ResidueType.H));
        residues.add(new Residue(new Point(0, 4), ResidueType.P));
        residues.add(new Residue(new Point(1, 4), ResidueType.H));
        residues.add(new Residue(new Point(1, 3), ResidueType.H));

        EnumMovements[] movements =
            new EnumMovements[] { EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT,
                EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT,
                EnumMovements.CRANKSHAFT };
        // Applying the same movement to all elements turned out that the
        // movement was being applied and than be unapplied in the subsequent
        // element
        List<Residue> applySolution = CornerTest.applyMovements(residues, movements);

        Assert.assertEquals(2, applySolution.get(0).getPoint().getX());
        Assert.assertEquals(1, applySolution.get(0).getPoint().getY());

        Assert.assertEquals(1, applySolution.get(1).getPoint().getX());
        Assert.assertEquals(1, applySolution.get(1).getPoint().getY());

        Assert.assertEquals(1, applySolution.get(2).getPoint().getX());
        Assert.assertEquals(2, applySolution.get(2).getPoint().getY());

        Assert.assertEquals(0, applySolution.get(3).getPoint().getX());
        Assert.assertEquals(2, applySolution.get(3).getPoint().getY());

        Assert.assertEquals(0, applySolution.get(4).getPoint().getX());
        Assert.assertEquals(3, applySolution.get(4).getPoint().getY());

        Assert.assertEquals(0, applySolution.get(5).getPoint().getX());
        Assert.assertEquals(4, applySolution.get(5).getPoint().getY());

        Assert.assertEquals(1, applySolution.get(6).getPoint().getX());
        Assert.assertEquals(4, applySolution.get(6).getPoint().getY());

        Assert.assertEquals(1, applySolution.get(7).getPoint().getX());
        Assert.assertEquals(3, applySolution.get(7).getPoint().getY());

    }

    /**
     * Test the following scenario
     * 
     * NOTE: Is expected for this test that the output is equal to input
     * 
     * <pre>
     *   Input:
     * -1  -1  -1  -1   
     * -1   5   6  -1    
     * -1   4   7  -1    
     * -1   3   2  -1    
     * -1  -1   1   0
     * 
     * </pre>
     * 
     * <pre>
     *   Expected:
     *   
     * -1  -1  -1  -1   
     * -1   5   6  -1    
     * -1   4   7  -1    
     * -1   3   2  -1    
     * -1  -1   1   0
     * 
     *
     * </pre>
     * 
     */
    @Test
    public void test16() {

        List<Residue> residues = new ArrayList<Residue>();

        residues.add(new Residue(new Point(3, 4), ResidueType.H));
        residues.add(new Residue(new Point(2, 4), ResidueType.H));
        residues.add(new Residue(new Point(2, 3), ResidueType.H));
        residues.add(new Residue(new Point(1, 3), ResidueType.H));
        residues.add(new Residue(new Point(1, 2), ResidueType.H));
        residues.add(new Residue(new Point(1, 1), ResidueType.P));
        residues.add(new Residue(new Point(2, 1), ResidueType.H));
        residues.add(new Residue(new Point(2, 2), ResidueType.H));

        EnumMovements[] movements =
            new EnumMovements[] { EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT,
                EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT,
                EnumMovements.CRANKSHAFT };
        // Applying the same movement to all elements turned out that the
        // movement was being applied and than be unapplied in the subsequent
        // element
        List<Residue> applySolution = CornerTest.applyMovements(residues, movements);

        Assert.assertEquals(3, applySolution.get(0).getPoint().getX());
        Assert.assertEquals(4, applySolution.get(0).getPoint().getY());

        Assert.assertEquals(2, applySolution.get(1).getPoint().getX());
        Assert.assertEquals(4, applySolution.get(1).getPoint().getY());

        Assert.assertEquals(2, applySolution.get(2).getPoint().getX());
        Assert.assertEquals(3, applySolution.get(2).getPoint().getY());

        Assert.assertEquals(1, applySolution.get(3).getPoint().getX());
        Assert.assertEquals(3, applySolution.get(3).getPoint().getY());

        Assert.assertEquals(1, applySolution.get(4).getPoint().getX());
        Assert.assertEquals(2, applySolution.get(4).getPoint().getY());

        Assert.assertEquals(1, applySolution.get(5).getPoint().getX());
        Assert.assertEquals(1, applySolution.get(5).getPoint().getY());

        Assert.assertEquals(2, applySolution.get(6).getPoint().getX());
        Assert.assertEquals(1, applySolution.get(6).getPoint().getY());

        Assert.assertEquals(2, applySolution.get(7).getPoint().getX());
        Assert.assertEquals(2, applySolution.get(7).getPoint().getY());

    }

    /**
     * Test the following scenario
     * 
     * NOTE: Is expected for this test that the output is equal to input
     * 
     * <pre>
     *   Input:
     *   
     * -1  -1  -1  -1    
     * -1  -1  -1  -1    
     * -1   0   1  -1    
     * -1  -1   2   3    
     * -1  -1   7   4     
     * -1  -1   6   5
     * 
     * </pre>
     * 
     * <pre>
     *   Expected:
     *   
     * -1  -1  -1  -1    
     * -1  -1  -1  -1    
     * -1   0   1  -1    
     * -1  -1   2   3    
     * -1  -1   7   4     
     * -1  -1   6   5
     * 
     * </pre>
     * 
     */
    @Test
    public void test17() {

        List<Residue> residues = new ArrayList<Residue>();

        residues.add(new Residue(new Point(2, 2), ResidueType.H));
        residues.add(new Residue(new Point(3, 2), ResidueType.H));
        residues.add(new Residue(new Point(3, 3), ResidueType.H));
        residues.add(new Residue(new Point(4, 3), ResidueType.H));
        residues.add(new Residue(new Point(4, 4), ResidueType.H));
        residues.add(new Residue(new Point(4, 5), ResidueType.P));
        residues.add(new Residue(new Point(3, 5), ResidueType.H));
        residues.add(new Residue(new Point(3, 4), ResidueType.H));

        EnumMovements[] movements =
            new EnumMovements[] { EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT,
                EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT,
                EnumMovements.CRANKSHAFT };
        // Applying the same movement to all elements turned out that the
        // movement was being applied and than be unapplied in the subsequent
        // element
        List<Residue> applySolution = CornerTest.applyMovements(residues, movements);

        Assert.assertEquals(2, applySolution.get(0).getPoint().getX());
        Assert.assertEquals(2, applySolution.get(0).getPoint().getY());

        Assert.assertEquals(3, applySolution.get(1).getPoint().getX());
        Assert.assertEquals(2, applySolution.get(1).getPoint().getY());

        Assert.assertEquals(3, applySolution.get(2).getPoint().getX());
        Assert.assertEquals(3, applySolution.get(2).getPoint().getY());

        Assert.assertEquals(4, applySolution.get(3).getPoint().getX());
        Assert.assertEquals(3, applySolution.get(3).getPoint().getY());

        Assert.assertEquals(4, applySolution.get(4).getPoint().getX());
        Assert.assertEquals(4, applySolution.get(4).getPoint().getY());

        Assert.assertEquals(4, applySolution.get(5).getPoint().getX());
        Assert.assertEquals(5, applySolution.get(5).getPoint().getY());

        Assert.assertEquals(3, applySolution.get(6).getPoint().getX());
        Assert.assertEquals(5, applySolution.get(6).getPoint().getY());

        Assert.assertEquals(3, applySolution.get(7).getPoint().getX());
        Assert.assertEquals(4, applySolution.get(7).getPoint().getY());

    }

    /**
     * Test the following scenario
     * 
     * NOTE: Is expected for this test that the output is equal to input
     * 
     * <pre>
     *    Input:
     *    
     * -1  -1  -1  -1    
     * -1  -1   6   5     
     * -1  -1   7   4     
     * -1  -1   2   3     
     * -1   0   1  -1
     * 
     * </pre>
     * 
     * <pre>
     *    Expected:
     *    
     *   1  -1  -1  -1    
     *  -1  -1   6   5     
     *  -1  -1   7   4     
     *  -1  -1   2   3     
     *  -1   0   1  -1
     * 
     * </pre>
     * 
     */
    @Test
    public void test18() {

        List<Residue> residues = new ArrayList<Residue>();

        residues.add(new Residue(new Point(1, 4), ResidueType.H));
        residues.add(new Residue(new Point(2, 4), ResidueType.H));
        residues.add(new Residue(new Point(2, 3), ResidueType.H));
        residues.add(new Residue(new Point(3, 3), ResidueType.H));
        residues.add(new Residue(new Point(3, 2), ResidueType.H));
        residues.add(new Residue(new Point(3, 1), ResidueType.P));
        residues.add(new Residue(new Point(2, 1), ResidueType.H));
        residues.add(new Residue(new Point(2, 2), ResidueType.H));

        EnumMovements[] movements =
            new EnumMovements[] { EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT,
                EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT,
                EnumMovements.CRANKSHAFT };
        // Applying the same movement to all elements turned out that the
        // movement was being applied and than be unapplied in the subsequent
        // element
        List<Residue> applySolution = CornerTest.applyMovements(residues, movements);

        Assert.assertEquals(1, applySolution.get(0).getPoint().getX());
        Assert.assertEquals(4, applySolution.get(0).getPoint().getY());

        Assert.assertEquals(2, applySolution.get(1).getPoint().getX());
        Assert.assertEquals(4, applySolution.get(1).getPoint().getY());

        Assert.assertEquals(2, applySolution.get(2).getPoint().getX());
        Assert.assertEquals(3, applySolution.get(2).getPoint().getY());

        Assert.assertEquals(3, applySolution.get(3).getPoint().getX());
        Assert.assertEquals(3, applySolution.get(3).getPoint().getY());

        Assert.assertEquals(3, applySolution.get(4).getPoint().getX());
        Assert.assertEquals(2, applySolution.get(4).getPoint().getY());

        Assert.assertEquals(3, applySolution.get(5).getPoint().getX());
        Assert.assertEquals(1, applySolution.get(5).getPoint().getY());

        Assert.assertEquals(2, applySolution.get(6).getPoint().getX());
        Assert.assertEquals(1, applySolution.get(6).getPoint().getY());

        Assert.assertEquals(2, applySolution.get(7).getPoint().getX());
        Assert.assertEquals(2, applySolution.get(7).getPoint().getY());

    }

    /**
     * Test the following scenario
     * 
     * NOTE: Is expected for this test that the output is equal to input
     * 
     * <pre>
     *    Input:
     * 
     * 
     * </pre>
     * 
     * <pre>
     *    Expected:
     * 
     * 
     * </pre>
     * 
     */
    @Test
    public void test19() {

        List<Residue> residues = new ArrayList<Residue>();

        residues.add(new Residue(new Point(1, 1), ResidueType.H));
        residues.add(new Residue(new Point(1, 2), ResidueType.H));
        residues.add(new Residue(new Point(2, 2), ResidueType.H));
        residues.add(new Residue(new Point(2, 1), ResidueType.H));
        residues.add(new Residue(new Point(3, 1), ResidueType.H));
        residues.add(new Residue(new Point(3, 0), ResidueType.H));
        residues.add(new Residue(new Point(2, 0), ResidueType.H));
        residues.add(new Residue(new Point(1, 0), ResidueType.H));
        residues.add(new Residue(new Point(0, 0), ResidueType.H));
        residues.add(new Residue(new Point(0, 1), ResidueType.H));
        residues.add(new Residue(new Point(0, 2), ResidueType.H));

        EnumMovements[] movements =
            new EnumMovements[] { EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT,
                EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT,
                EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT };
        // Applying the same movement to all elements turned out that the
        // movement was being applied and than be unapplied in the subsequent
        // element
        List<Residue> applySolution = CornerTest.applyMovements(residues, movements);

        Assert.assertEquals(1, applySolution.get(0).getPoint().getX());
        Assert.assertEquals(1, applySolution.get(0).getPoint().getY());

        Assert.assertEquals(1, applySolution.get(1).getPoint().getX());
        Assert.assertEquals(2, applySolution.get(1).getPoint().getY());

        Assert.assertEquals(2, applySolution.get(2).getPoint().getX());
        Assert.assertEquals(2, applySolution.get(2).getPoint().getY());

        Assert.assertEquals(2, applySolution.get(3).getPoint().getX());
        Assert.assertEquals(1, applySolution.get(3).getPoint().getY());

        Assert.assertEquals(3, applySolution.get(4).getPoint().getX());
        Assert.assertEquals(1, applySolution.get(4).getPoint().getY());

        Assert.assertEquals(3, applySolution.get(5).getPoint().getX());
        Assert.assertEquals(0, applySolution.get(5).getPoint().getY());

        Assert.assertEquals(2, applySolution.get(6).getPoint().getX());
        Assert.assertEquals(0, applySolution.get(6).getPoint().getY());

        Assert.assertEquals(1, applySolution.get(7).getPoint().getX());
        Assert.assertEquals(0, applySolution.get(7).getPoint().getY());

        Assert.assertEquals(0, applySolution.get(8).getPoint().getX());
        Assert.assertEquals(0, applySolution.get(8).getPoint().getY());

        Assert.assertEquals(0, applySolution.get(9).getPoint().getX());
        Assert.assertEquals(1, applySolution.get(9).getPoint().getY());

        Assert.assertEquals(0, applySolution.get(10).getPoint().getX());
        Assert.assertEquals(2, applySolution.get(10).getPoint().getY());

    }

    /**
     * Test the following scenario
     * 
     * <pre>
     *   Input:
     *
     * 
     * </pre>
     * 
     * <pre>
     *   Expected:
     * 
     * 
     *
     * </pre>
     * 
     */
    @Test
    public void testNAn() {

        List<Residue> residues = new ArrayList<Residue>();
        residues.add(new Residue(new Point(2, 5), ResidueType.H));
        residues.add(new Residue(new Point(1, 5), ResidueType.H));
        residues.add(new Residue(new Point(1, 6), ResidueType.H));
        residues.add(new Residue(new Point(2, 6), ResidueType.P));
        residues.add(new Residue(new Point(3, 6), ResidueType.H));
        residues.add(new Residue(new Point(3, 5), ResidueType.H));
        residues.add(new Residue(new Point(4, 5), ResidueType.H));
        residues.add(new Residue(new Point(4, 4), ResidueType.H));

        EnumMovements[] movements =
            new EnumMovements[] { EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT,
                EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT, EnumMovements.CRANKSHAFT,
                EnumMovements.CRANKSHAFT };
        // Applying the same movement to all elements turned out that the
        // movement was being applied and than be unapplied in the subsequent
        // element
        List<Residue> applySolution = CornerTest.applyMovements(residues, movements);

        Assert.assertEquals(2, applySolution.get(0).getPoint().getX());
        Assert.assertEquals(5, applySolution.get(0).getPoint().getY());

        Assert.assertEquals(1, applySolution.get(1).getPoint().getX());
        Assert.assertEquals(5, applySolution.get(1).getPoint().getY());

        Assert.assertEquals(1, applySolution.get(2).getPoint().getX());
        Assert.assertEquals(6, applySolution.get(2).getPoint().getY());

        Assert.assertEquals(2, applySolution.get(3).getPoint().getX());
        Assert.assertEquals(6, applySolution.get(3).getPoint().getY());

        Assert.assertEquals(3, applySolution.get(4).getPoint().getX());
        Assert.assertEquals(6, applySolution.get(4).getPoint().getY());

        Assert.assertEquals(3, applySolution.get(5).getPoint().getX());
        Assert.assertEquals(5, applySolution.get(5).getPoint().getY());

        Assert.assertEquals(4, applySolution.get(6).getPoint().getX());
        Assert.assertEquals(5, applySolution.get(6).getPoint().getY());

        Assert.assertEquals(4, applySolution.get(7).getPoint().getX());
        Assert.assertEquals(4, applySolution.get(7).getPoint().getY());

    }

}
