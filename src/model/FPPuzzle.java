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
        cells = new ArrayList<>();

        // Just fill puzzle with numbers 1,2,3,4,5 ..
        for (int i = 0; i < puzzleSize - 1; i++) {
            cells.add(new FPCell(i + 1, i));
        }

        cells.add(new FPCell(-1, puzzleSize - 1)); // add empty cell
        emptyCellIndex = puzzleSize - 1;

        puzzleShuffle();
        complete = testComplete();

        stepsCounter = 0;
    }

    private void puzzleShuffle() {
        // make 50*4 = 200 random steps for 4x4
        // 300 for 6x6
        // 400 for 8x8
        // and 500 for 10x10
        for (int i = 0; i < 50 * puzzleSize; i++) {
            ArrayList<Integer> directions = new ArrayList<>();
            ArrayList<Integer> resultDir = new ArrayList<>();

            directions.add(getLeftIndexFromEmpty());
            directions.add(getDownFromEmpty());
            directions.add(getRightIndexFromEmpty());
            directions.add(getUpFromEmpty());

            for (int k = 0; k < directions.size(); k++) {
                if (directions.get(k) != -1) {
                    resultDir.add(directions.get(k));
                }
            }

            //get random index from array
            int whichToMove = RANDOM.nextInt(resultDir.size());

            System.out.println("whichToMove  >> " + whichToMove);
            System.out.println("directions.get(whichToMove)  >> " + resultDir.get(whichToMove));

            moveMePlease(resultDir.get(whichToMove));
        }
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
            emptyCellIndex = index;
            return true;
        }

        return false;
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

    private void swap(int pos1, int pos2) {
        FPCell cell1 = cells.get(pos1);
        FPCell cell2 = cells.get(pos2);
        int cell1pos = cell1.getPosition();
        cell1.setPosition(cell2.getPosition());
        cell2.setPosition(cell1pos);
        cells.set(pos1, cell2);
        cells.set(pos2, cell1);
    }

    //region Getters
    public int getEmptyCellIndex() {
        return emptyCellIndex;
    }

    public int getSize() {
        return puzzleSize;
    }

    public int getSteps() {
        return stepsCounter;
    }

    public int getSideSize() {
        return sideSize;
    }
    //endregion

    public FPCell get (int index) {
        return cells.get(index);
    }

    private boolean testComplete() {
        for (FPCell cell : this) {
            if (cell.getValue() != -1 && cell.getPosition() != cell.getValue() - 1) {
                return false;
            }
        }
        return true;
    }


    //region Methods to get cells around the empty cell
    public int getLeftIndexFromEmpty() {
        if (emptyCellIndex > 0) {
            return emptyCellIndex - 1;
        } else {
            return -1;
        }
    }

    public int getRightIndexFromEmpty() {
        if (emptyCellIndex < puzzleSize - 1) {
            return emptyCellIndex + 1;
        } else {
            return -1;
        }
    }

    public int getUpFromEmpty() {
        if (emptyCellIndex >= sideSize) {
            return emptyCellIndex - sideSize;
        } else {
            return -1;
        }
    }

    public int getDownFromEmpty() {
        if (emptyCellIndex <= puzzleSize - sideSize - 1) {
            return emptyCellIndex + sideSize;
        } else {
            return -1;
        }
    }
    //endregion

    @Override
    public Iterator<FPCell> iterator() {
        return cells.iterator();
    }

    public boolean isComplete() {
        return complete;
    }
}