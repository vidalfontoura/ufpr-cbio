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
public class CornerMovementsTest {

    // Tests if a simple upper left corner move
    @Test
    public void testUpperLeftCornerMove1() {

        List<Residue> residues = new ArrayList<Residue>();
        residues.add(new Residue(new Point(2, 4), ResidueType.H));
        residues.add(new Residue(new Point(1, 4), ResidueType.H));
        residues.add(new Residue(new Point(1, 3), ResidueType.H));

        // int[] solution = new int[] { 3, 3, 3 };
        EnumMovements[] movements =
            new EnumMovements[] { EnumMovements.CORNER, EnumMovements.CORNER, EnumMovements.CORNER };
        List<Residue> applySolution = CornerTest.applyMovements(residues, movements);

        Assert.assertEquals(2, applySolution.get(0).getPoint().getX());
        Assert.assertEquals(4, applySolution.get(0).getPoint().getY());

        Assert.assertEquals(2, applySolution.get(1).getPoint().getX());
        Assert.assertEquals(3, applySolution.get(1).getPoint().getY());

        Assert.assertEquals(1, applySolution.get(2).getPoint().getX());
        Assert.assertEquals(3, applySolution.get(2).getPoint().getY());

    }

    // Tests if a simple upper left corner move changing position from previous
    // and next
    @Test
    public void testUpperLeftCornerMove2() {

        List<Residue> residues = new ArrayList<Residue>();
        residues.add(new Residue(new Point(1, 3), ResidueType.H));
        residues.add(new Residue(new Point(1, 4), ResidueType.H));
        residues.add(new Residue(new Point(2, 4), ResidueType.H));

        EnumMovements[] movements =
            new EnumMovements[] { EnumMovements.CORNER, EnumMovements.CORNER, EnumMovements.CORNER };
        List<Residue> applySolution = CornerTest.applyMovements(residues, movements);

        Assert.assertEquals(1, applySolution.get(0).getPoint().getX());
        Assert.assertEquals(3, applySolution.get(0).getPoint().getY());

        Assert.assertEquals(2, applySolution.get(1).getPoint().getX());
        Assert.assertEquals(3, applySolution.get(1).getPoint().getY());

        Assert.assertEquals(2, applySolution.get(2).getPoint().getX());
        Assert.assertEquals(4, applySolution.get(2).getPoint().getY());

    }

    // Tests if a simple bottom left corner move
    @Test
    public void testBottomLeftCornerMove1() {

        List<Residue> residues = new ArrayList<Residue>();
        residues.add(new Residue(new Point(2, 1), ResidueType.H));
        residues.add(new Residue(new Point(1, 1), ResidueType.H));
        residues.add(new Residue(new Point(1, 2), ResidueType.H));

        EnumMovements[] movements =
            new EnumMovements[] { EnumMovements.CORNER, EnumMovements.CORNER, EnumMovements.CORNER };
        List<Residue> applySolution = CornerTest.applyMovements(residues, movements);

        Assert.assertEquals(2, applySolution.get(0).getPoint().getX());
        Assert.assertEquals(1, applySolution.get(0).getPoint().getY());

        Assert.assertEquals(2, applySolution.get(1).getPoint().getX());
        Assert.assertEquals(2, applySolution.get(1).getPoint().getY());

        Assert.assertEquals(1, applySolution.get(2).getPoint().getX());
        Assert.assertEquals(2, applySolution.get(2).getPoint().getY());

    }

    // Tests if a simple bottom left corner move changing previous and next
    // positions
    @Test
    public void testBottomLeftCornerMove2() {

        List<Residue> residues = new ArrayList<Residue>();
        residues.add(new Residue(new Point(1, 2), ResidueType.H));
        residues.add(new Residue(new Point(1, 1), ResidueType.H));
        residues.add(new Residue(new Point(2, 1), ResidueType.H));

        EnumMovements[] movements =
            new EnumMovements[] { EnumMovements.CORNER, EnumMovements.CORNER, EnumMovements.CORNER };
        List<Residue> applySolution = CornerTest.applyMovements(residues, movements);

        Assert.assertEquals(1, applySolution.get(0).getPoint().getX());
        Assert.assertEquals(2, applySolution.get(0).getPoint().getY());

        Assert.assertEquals(2, applySolution.get(1).getPoint().getX());
        Assert.assertEquals(2, applySolution.get(1).getPoint().getY());

        Assert.assertEquals(2, applySolution.get(2).getPoint().getX());
        Assert.assertEquals(1, applySolution.get(2).getPoint().getY());

    }

    // Tests if a simple upper right corner move
    @Test
    public void testUpperRightCornerMove1() {

        List<Residue> residues = new ArrayList<Residue>();
        residues.add(new Residue(new Point(3, 4), ResidueType.H));
        residues.add(new Residue(new Point(4, 4), ResidueType.H));
        residues.add(new Residue(new Point(4, 3), ResidueType.H));

        EnumMovements[] movements =
            new EnumMovements[] { EnumMovements.CORNER, EnumMovements.CORNER, EnumMovements.CORNER };
        List<Residue> applySolution = CornerTest.applyMovements(residues, movements);

        Assert.assertEquals(3, applySolution.get(0).getPoint().getX());
        Assert.assertEquals(4, applySolution.get(0).getPoint().getY());

        Assert.assertEquals(3, applySolution.get(1).getPoint().getX());
        Assert.assertEquals(3, applySolution.get(1).getPoint().getY());

        Assert.assertEquals(4, applySolution.get(2).getPoint().getX());
        Assert.assertEquals(3, applySolution.get(2).getPoint().getY());

    }

    // Tests if a simple upper right corner move changing previous and next
    // positions
    @Test
    public void testUpperRightCornerMove2() {

        List<Residue> residues = new ArrayList<Residue>();
        residues.add(new Residue(new Point(4, 3), ResidueType.H));
        residues.add(new Residue(new Point(4, 4), ResidueType.H));
        residues.add(new Residue(new Point(3, 4), ResidueType.H));

        EnumMovements[] movements =
            new EnumMovements[] { EnumMovements.CORNER, EnumMovements.CORNER, EnumMovements.CORNER };
        List<Residue> applySolution = CornerTest.applyMovements(residues, movements);

        Assert.assertEquals(4, applySolution.get(0).getPoint().getX());
        Assert.assertEquals(3, applySolution.get(0).getPoint().getY());

        Assert.assertEquals(3, applySolution.get(1).getPoint().getX());
        Assert.assertEquals(3, applySolution.get(1).getPoint().getY());

        Assert.assertEquals(3, applySolution.get(2).getPoint().getX());
        Assert.assertEquals(4, applySolution.get(2).getPoint().getY());

    }

    // Tests if a simple bottom right corner move
    @Test
    public void testBottomRightCornerMove1() {

        List<Residue> residues = new ArrayList<Residue>();
        residues.add(new Residue(new Point(3, 2), ResidueType.H));
        residues.add(new Residue(new Point(3, 1), ResidueType.H));
        residues.add(new Residue(new Point(2, 1), ResidueType.H));

        EnumMovements[] movements =
            new EnumMovements[] { EnumMovements.CORNER, EnumMovements.CORNER, EnumMovements.CORNER };
        List<Residue> applySolution = CornerTest.applyMovements(residues, movements);

        Assert.assertEquals(3, applySolution.get(0).getPoint().getX());
        Assert.assertEquals(2, applySolution.get(0).getPoint().getY());

        Assert.assertEquals(2, applySolution.get(1).getPoint().getX());
        Assert.assertEquals(2, applySolution.get(1).getPoint().getY());

        Assert.assertEquals(2, applySolution.get(2).getPoint().getX());
        Assert.assertEquals(1, applySolution.get(2).getPoint().getY());

    }

    // Tests if a simple bottom right corner move changing previous and next
    // positions
    @Test
    public void testBottomRightCornerMove2() {

        List<Residue> residues = new ArrayList<Residue>();
        residues.add(new Residue(new Point(2, 1), ResidueType.H));
        residues.add(new Residue(new Point(3, 1), ResidueType.H));
        residues.add(new Residue(new Point(3, 2), ResidueType.H));

        EnumMovements[] movements =
            new EnumMovements[] { EnumMovements.CORNER, EnumMovements.CORNER, EnumMovements.CORNER };
        List<Residue> applySolution = CornerTest.applyMovements(residues, movements);

        Assert.assertEquals(2, applySolution.get(0).getPoint().getX());
        Assert.assertEquals(1, applySolution.get(0).getPoint().getY());

        Assert.assertEquals(2, applySolution.get(1).getPoint().getX());
        Assert.assertEquals(2, applySolution.get(1).getPoint().getY());

        Assert.assertEquals(3, applySolution.get(2).getPoint().getX());
        Assert.assertEquals(2, applySolution.get(2).getPoint().getY());

    }

    // Test with 17 Residues reference conformation with a lot of corners
    @Test
    public void testWith17ResiduesReference() {

        List<Residue> residues = new ArrayList<Residue>();
        residues.add(new Residue(new Point(1, 1), ResidueType.H));
        residues.add(new Residue(new Point(1, 2), ResidueType.H));
        residues.add(new Residue(new Point(1, 3), ResidueType.H));
        residues.add(new Residue(new Point(2, 3), ResidueType.H));
        residues.add(new Residue(new Point(2, 4), ResidueType.H));
        residues.add(new Residue(new Point(2, 5), ResidueType.H));
        residues.add(new Residue(new Point(3, 5), ResidueType.H));
        residues.add(new Residue(new Point(4, 5), ResidueType.H));
        residues.add(new Residue(new Point(4, 4), ResidueType.H));
        residues.add(new Residue(new Point(4, 3), ResidueType.H));
        residues.add(new Residue(new Point(5, 3), ResidueType.H));
        residues.add(new Residue(new Point(5, 2), ResidueType.H));
        residues.add(new Residue(new Point(5, 1), ResidueType.H));
        residues.add(new Residue(new Point(4, 1), ResidueType.H));
        residues.add(new Residue(new Point(3, 1), ResidueType.H));
        residues.add(new Residue(new Point(3, 2), ResidueType.H));
        residues.add(new Residue(new Point(2, 2), ResidueType.H));

        // int[] solution = new int[] { 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
        // 3, 3, 3, 3 };
        EnumMovements[] movements =
            new EnumMovements[] { EnumMovements.CORNER, EnumMovements.CORNER, EnumMovements.CORNER,
                EnumMovements.CORNER, EnumMovements.CORNER, EnumMovements.CORNER, EnumMovements.CORNER,
                EnumMovements.CORNER, EnumMovements.CORNER, EnumMovements.CORNER, EnumMovements.CORNER,
                EnumMovements.CORNER, EnumMovements.CORNER, EnumMovements.CORNER, EnumMovements.CORNER,
                EnumMovements.CORNER, EnumMovements.CORNER };
        List<Residue> applySolution = CornerTest.applyMovements(residues, movements);

        Assert.assertEquals(1, applySolution.get(0).getPoint().getX());
        Assert.assertEquals(1, applySolution.get(0).getPoint().getY());

        Assert.assertEquals(1, applySolution.get(1).getPoint().getX());
        Assert.assertEquals(2, applySolution.get(1).getPoint().getY());

        Assert.assertEquals(1, applySolution.get(2).getPoint().getX());
        Assert.assertEquals(3, applySolution.get(2).getPoint().getY());

        Assert.assertEquals(1, applySolution.get(3).getPoint().getX());
        Assert.assertEquals(4, applySolution.get(3).getPoint().getY());

        Assert.assertEquals(1, applySolution.get(4).getPoint().getX());
        Assert.assertEquals(5, applySolution.get(4).getPoint().getY());

        Assert.assertEquals(2, applySolution.get(5).getPoint().getX());
        Assert.assertEquals(5, applySolution.get(5).getPoint().getY());

        Assert.assertEquals(3, applySolution.get(6).getPoint().getX());
        Assert.assertEquals(5, applySolution.get(6).getPoint().getY());

        Assert.assertEquals(3, applySolution.get(7).getPoint().getX());
        Assert.assertEquals(4, applySolution.get(7).getPoint().getY());

        Assert.assertEquals(3, applySolution.get(8).getPoint().getX());
        Assert.assertEquals(3, applySolution.get(8).getPoint().getY());

        Assert.assertEquals(4, applySolution.get(9).getPoint().getX());
        Assert.assertEquals(3, applySolution.get(9).getPoint().getY());

        Assert.assertEquals(4, applySolution.get(10).getPoint().getX());
        Assert.assertEquals(2, applySolution.get(10).getPoint().getY());

        Assert.assertEquals(5, applySolution.get(11).getPoint().getX());
        Assert.assertEquals(2, applySolution.get(11).getPoint().getY());

        Assert.assertEquals(5, applySolution.get(12).getPoint().getX());
        Assert.assertEquals(1, applySolution.get(12).getPoint().getY());

        Assert.assertEquals(4, applySolution.get(13).getPoint().getX());
        Assert.assertEquals(1, applySolution.get(13).getPoint().getY());

        Assert.assertEquals(3, applySolution.get(14).getPoint().getX());
        Assert.assertEquals(1, applySolution.get(14).getPoint().getY());

        Assert.assertEquals(2, applySolution.get(15).getPoint().getX());
        Assert.assertEquals(1, applySolution.get(15).getPoint().getY());

        Assert.assertEquals(2, applySolution.get(16).getPoint().getX());
        Assert.assertEquals(2, applySolution.get(16).getPoint().getY());

    }
}
