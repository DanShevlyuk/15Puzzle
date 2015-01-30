package serializer;

import model.FPPuzzle;

import java.io.*;

public class Serializer {

    public boolean save(FPPuzzle puzzle, String name) {
        try (FileOutputStream fos = new FileOutputStream(name)) {
            try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(puzzle);
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

    public FPPuzzle open(String name) {
        try (FileInputStream fis = new FileInputStream(name)) {
            try (ObjectInputStream oin = new ObjectInputStream(fis)) {
                return (FPPuzzle) oin.readObject();
            }
        }
        catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
