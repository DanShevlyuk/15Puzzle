package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class FPPuzzle implements Iterable<FPCell> {

    private List<FPCell> cells;

    private int emptyCellIndex;
    private final int size = 16;

    private static final Random RANDOM = new Random();

    public FPPuzzle() {
        //TODO: init cells randomly
        //TODO: init emptyCellIndex
        cells = new ArrayList<>();

        List<Integer> usedNumbers = new ArrayList<>();

        //emptyCellIndex = RANDOM.nextInt(15) + 1;
        cells.add(new FPCell(-1, this));

        int i = 1;
        while (i < 16) {
            int next = RANDOM.nextInt(15) + 1;

            if (!usedNumbers.contains(next)) {
                cells.add(new FPCell(next, this));
                usedNumbers.add(next);
                i++;
            }
        }

        emptyCellIndex = RANDOM.nextInt(16);
        FPCell cell = cells.get(emptyCellIndex);
        cells.set(emptyCellIndex, cells.get(0));
        cells.set(0, cell);
    }

    //TODO: написать геттер и сеттер (нужен ли он?) для emptyCellIndex

    /*
     * FPCell вызывает этот метод, когда на нее кликнули
     *
     * return true, когда все ок и false, когда передивнуть не удалось
     */
    //TODO: имплементировать moveMePlease
    public boolean moveMePlease(int index) {
        //проверить можно ли двигать
        // подвинуть, если можно и вернуть true

        int moveto = moveTo(index);
        if (moveto != -1) {
            FPCell cell = cells.get(index);
            cells.set(index, cells.get(moveto));
            cells.set(moveto, cell);
            return true;
        }

        // как двигать: присвоить значение index-а пустой
        // и сделать index пустой (очевидно)
        // если нельзя, то false
        return false;

    }

    public int getEmptyCellIndex() {
        return emptyCellIndex;
    }

    private int moveTo(int i) {
        if (i - 1 > 0 && cells.get(i - 1).isEmpty()) {
            return i - 1;
        }
        else if (i + 1 < cells.size() && cells.get(i + 1).isEmpty()) {
            return i + 1;
        }
        else if (i + 4 < cells.size() && cells.get(i + 4).isEmpty()) {
            return i + 4;
        }
        else if (i - 4 > 0 && cells.get(i - 4).isEmpty()) {
            return i - 4;
        }
        else {
            return - 1;
        }
    }

    public int getSize() {
        return size;
    }

    public FPCell get (int index) {
        return cells.get(index);
    }

    @Override
    public Iterator<FPCell> iterator() {
        return cells.iterator();
    }
}