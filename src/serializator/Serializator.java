package serializator;

import model.FPPuzzle;

import java.io.*;

public class Serializator {

    public boolean save(FPPuzzle puzzle, String name) {
        try {
            FileOutputStream fos = new FileOutputStream(name);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(puzzle);
            oos.flush();
            oos.close();
            fos.close();
            return true;
        }
        catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public FPPuzzle open(String name) {
        try {
            FileInputStream fis = new FileInputStream(name);
            ObjectInputStream oin = new ObjectInputStream(fis);
            FPPuzzle puzzle = (FPPuzzle) oin.readObject();
            oin.close();
            fis.close();
            return puzzle;
        }
        catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
