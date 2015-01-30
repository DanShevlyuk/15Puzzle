package serializer;

import model.FPPuzzle;

import java.io.*;

public class Serializer implements Serializable {

    private FPPuzzle puzzle;
    private String time;

    public Serializer(FPPuzzle puzzle, String time) {
        this.puzzle = puzzle;
        this.time = time;
    }

    public static boolean save(Serializer serialize, String name) {
        try (FileOutputStream fos = new FileOutputStream(name)) {
            try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(serialize);
                oos.flush();
                fos.flush();
                return true;
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static Serializer open(String name) {
        try (FileInputStream fis = new FileInputStream(name)) {
            try (ObjectInputStream oin = new ObjectInputStream(fis)) {
                return (Serializer) oin.readObject();
            }
        }
        catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public FPPuzzle getPuzzle() {
        return puzzle;
    }

    public String getTime() {
        return time;
    }
}
