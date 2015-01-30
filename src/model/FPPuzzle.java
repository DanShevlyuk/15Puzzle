package model;

import java.io.Serializable;
import java.util.*;

public class FPPuzzle implements Iterable<FPCell>, Serializable {
    protected List<FPCell> cells;

    private int emptyCellIndex;
    private final int sideSize;
    private final int puzzleSize;
    private boolean complete = false;
    private int stepsCounter;

    private static final Random RANDOM = new Random();

    public FPPuzzle() {
        this(4);
    }

    public FPPuzzle(int sideSize) {
        this.sideSize = sideSize;
        puzzleSize = sideSize * sideSize;
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

            stepsCounter = 0;
            emptyCellIndex = RANDOM.nextInt(puzzleSize);
            swap(emptyCellIndex, 0);
            complete = testComplete();
        } while (complete || !canSolve());
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
        int whereToMove = moveTo(index);
        if (whereToMove != -1) {
            swap(index, whereToMove);
            complete = testComplete();
            stepsCounter++;
            return true;
        }

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

    private boolean canSolve() {
        int chaosCount = 0;
        for (int i = 0; i < puzzleSize - 1; i++) {
            FPCell cell = cells.get(i);
            if (i == emptyCellIndex) {
                continue;
            }

            for (int j = i + 1; j < puzzleSize; j++) {
                if (j != emptyCellIndex && cell.compareTo(cells.get(j)) > 0) {
                    chaosCount++;
                }
            }
        }

        return chaosCount % 2 == 0;
    }

    public int getEmptyCellIndex() {
        return emptyCellIndex;
    }

    private int moveTo(int i) {
        if ((i % sideSize != 0) && cells.get(i - 1).isEmpty()) {
            return i - 1;
        }
        else if (((i + 1) % sideSize != 0) && cells.get(i + 1).isEmpty()) {
            return i + 1;
        }
        else if (i + sideSize < cells.size() && cells.get(i + sideSize).isEmpty()) {
            return i + sideSize;
        }
        else if (i - sideSize >= 0 && cells.get(i - sideSize).isEmpty()) {
            return i - sideSize;
        }
        else {
            return - 1;
        }
    }

    public int getSize() {
        return puzzleSize;
    }

    public int getSteps() {
        return stepsCounter;
    }

    private boolean testComplete() {
        for (FPCell cell : this) {
            if (cell.getValue() != -1 && cell.getPosition() != cell.getValue() - 1) {
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