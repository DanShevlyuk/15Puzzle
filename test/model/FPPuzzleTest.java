package model;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class FPPuzzleTest {

    private FPPuzzle puzzle;

    @Before
    public void setUp() throws Exception {
        puzzle = new FPPuzzle();
    }

    @Test
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
        System.out.println("\n" + puzzle.getEmptyCellIndex());
        assertEquals(emptyCount, 1);
    }
}