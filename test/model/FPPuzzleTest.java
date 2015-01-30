package model;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FPPuzzleTest {

    private FPPuzzle puzzle;

    @Before
    public void setUp() throws Exception {
        puzzle = new FPPuzzle();
    }

    @Test
    public void testMoveMePlease() throws Exception {
        int emptyIndex = puzzle.getEmptyCellIndex();
        System.out.println("Empty element index: " + emptyIndex);
        boolean moved = puzzle.moveMePlease(emptyIndex + 1);
        assertEquals(true, moved);
    }

    @Test(timeout = 100)
    public void testCreating() {
        Set<Integer> set = new HashSet<>();
        int emptyIndex = -1;
        int emptyCount = 0;
        for (int i = 0; i < puzzle.getSize(); i++) {
            FPCell cell = puzzle.get(i);
            if (cell.isEmpty()) {
                emptyIndex = i;
                emptyCount++;
            }
            set.add(cell.getValue());
            System.out.println(cell.getValue());
        }

        assertEquals(puzzle.getSize(), set.size());
        assertEquals(emptyIndex, puzzle.getEmptyCellIndex());
        assertEquals(emptyCount, 1);
    }

//    @Test
//    public void testCanSolve() {
//        System.out.println("testCanSolve");
//        puzzle = new FPPuzzle(4, true);
//        assertTrue(puzzle.canSolve());
//        puzzle.swap(puzzle.getSize() - 1, puzzle.getSize() - 2);
//        assertFalse(puzzle.canSolve());
//        puzzle.swap(puzzle.getSize() - 1, puzzle.getSize() - 2);
//        assertTrue(puzzle.canSolve());
//    }
}