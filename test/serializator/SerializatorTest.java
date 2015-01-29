package serializator;

import model.FPPuzzle;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class SerializatorTest {

    private Serializator ser;
    private FPPuzzle puzzle;
    @Before
    public void setUp() throws Exception {
        ser = new Serializator();
        puzzle = new FPPuzzle();
    }

    @After
    public void tearDown() throws Exception {
        File f = new File("test.puz");
        f.delete();
    }

    @Test
    public void testSave() throws Exception {
        boolean res = ser.save(puzzle, "test.puz");
        assertEquals(true, res);
        File f = new File("test.puz");
        f.delete();
    }

    @Test
    public void testOpen() throws Exception {
        save();
        FPPuzzle puzl = ser.open("test.puz");
        assertEquals(puzl.getSize(), puzzle.getSize());
        for (int i  = 0; i < puzl.getSize(); i++) {
            assertEquals(puzl.get(i).compareTo(puzzle.get(i)), 0);
        }
    }

    private void save() {
        ser.save(puzzle, "test.puz");
    }
}