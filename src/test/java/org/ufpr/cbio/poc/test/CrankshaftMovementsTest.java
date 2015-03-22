package org.ufpr.cbio.poc.test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.ufpr.cbio.poc.domain.Residue;
import org.ufpr.cbio.poc.domain.Residue.Point;
import org.ufpr.cbio.poc.domain.ResidueType;
import org.ufpr.cbio.poc.main.MainTest;
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

        List<Residue> applySolution = MainTest.applyMovements(residues, movements);

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

        List<Residue> applySolution = MainTest.applyMovements(residues, movements);

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

        List<Residue> applySolution = MainTest.applyMovements(residues, movements);

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

        List<Residue> applySolution = MainTest.applyMovements(residues, movements);

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
        List<Residue> applySolution = MainTest.applyMovements(residues, movements);

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
        List<Residue> applySolution = MainTest.applyMovements(residues, movements);

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
        List<Residue> applySolution = MainTest.applyMovements(residues, movements);

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
        List<Residue> applySolution = MainTest.applyMovements(residues, movements);

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
}
