package serializer;

import model.FPPuzzle;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SerializerTest {

    private Serializer ser;
    private FPPuzzle puzzle;
    private String time;

    @Before
    public void setUp() throws Exception {
        puzzle = new FPPuzzle();
        time = "00:00:25";
        ser = new Serializer(puzzle);
        ser.setTime(time);
    }

    @After
    public void tearDown() throws Exception {
        File f = new File("test.puz");
        f.delete();
    }

    @Test
    public void testSave() throws Exception {
        boolean res = Serializer.save(ser, "test.puz");
        assertEquals(true, res);
        File f = new File("test.puz");
        f.delete();
    }

    @Test
    public void testOpen() throws Exception {
        save();
        Serializer newSer = Serializer.open("test.puz");
        assertNotNull(newSer);
        FPPuzzle puzl = newSer.getPuzzle();
        assertEquals(puzl.getSize(), puzzle.getSize());
        for (int i  = 0; i < puzl.getSize(); i++) {
            assertEquals(puzl.get(i).compareTo(puzzle.get(i)), 0);
        }
        assertEquals(time, ser.getTime());
    }

    private void save() {
        Serializer.save(ser, "test.puz");
    }

}