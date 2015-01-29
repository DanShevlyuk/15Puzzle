package model;

import java.util.*;

public class FPPuzzle implements Iterable<FPCell> {

    private List<FPCell> cells;

    private int emptyCellIndex;
    private final int width = 4;
    private final int height = 4;
    private final int puzzleSize = width * height;
    private boolean complete = false;

    private static final Random RANDOM = new Random();

    public FPPuzzle() {
        do {
            cells = new ArrayList<>();
            Set<Integer> usedNumbers = new HashSet<>();
            cells.add(new FPCell(-1, 0));

            int i = 1;
            while (i < puzzleSize) {
                int next = RANDOM.nextInt(puzzleSize - 1) + 1;

                if (usedNumbers.add(next)) {
                    cells.add(new FPCell(next, i));
                    i++;
                }
            }

            emptyCellIndex = RANDOM.nextInt(puzzleSize);
            swap(emptyCellIndex, 0);
            complete = testComplete();
        }
        while (complete);
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
            swap(index, moveto);
            complete = testComplete();
            return true;
        }

        // как двигать: присвоить значение index-а пустой
        // и сделать index пустой (очевидно)
        // если нельзя, то false
        return false;

    }

    private void swap(int pos1, int pos2) {
        FPCell cell1 = cells.get(pos1);
        FPCell cell2 = cells.get(pos2);
        int cell1pos = cell1.getPosition();
        cell1.setPosition(cell2.getPosition());
        cell2.setPosition(cell1pos);
        cells.set(pos1, cell2);
        cells.set(pos2, cell1);
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
        else if (i + width < cells.size() && cells.get(i + width).isEmpty()) {
            return i + width;
        }
        else if (i - width > 0 && cells.get(i - width).isEmpty()) {
            return i - width;
        }
        else {
            return - 1;
        }
    }

    public int getSize() {
        return puzzleSize;
    }

    private boolean testComplete() {
        for (FPCell cell : this) {
            if (cell.getValue() != -1 && cell.getPosition() != cell.getValue()) {
                return false;
            }
        }
        return true;
    }

    public FPCell get (int index) {
        return cells.get(index);
    }

    @Override
    public Iterator<FPCell> iterator() {
        return cells.iterator();
    }

    public boolean isComplete() {
        return complete;
    }
}