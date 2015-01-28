package model;

import java.util.*;

public class FPPuzzle implements Iterable<FPCell> {

    private List<FPCell> cells;

    private int emptyCellIndex;
    private final int width = 4;
    private final int height = 4;
    private final int puzzleSize = width * height;

    private static final Random RANDOM = new Random();

    public FPPuzzle() {
        cells = new ArrayList<>();

        Set<Integer> usedNumbers = new HashSet<>();
        cells.add(new FPCell(-1));

        int i = 1;
        while (i < 16) {
            int next = RANDOM.nextInt(15) + 1;

            if (usedNumbers.add(next)) {
                cells.add(new FPCell(next));
                i++;
            }
        }

        emptyCellIndex = RANDOM.nextInt(16);
        FPCell cell = cells.get(emptyCellIndex);
        cells.set(emptyCellIndex, cells.get(0));
        cells.set(0, cell);
    }

    /*
     *  Вызывается после того, как FPPuzzlePanel обработал нажатие
     *  мышкой по клетке и определил её индекс.
     *  Метод двигает клетку, если это возможно и возвращает
     *  boolean, который говорит о том, перепестилась клетка или нет.
     *
     * return true, когда все ок и false, когда передивнуть не удалось
     */
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
        if ((i % width != 0) && cells.get(i - 1).isEmpty()) {
            return i - 1;
        }
        else if (((i + 1) % width != 0) && cells.get(i + 1).isEmpty()) {
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
        return puzzleSize;
    }

    public FPCell get (int index) {
        return cells.get(index);
    }

    @Override
    public Iterator<FPCell> iterator() {
        return cells.iterator();
    }
}