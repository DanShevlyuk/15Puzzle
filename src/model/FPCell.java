package model;

public class FPCell {
    private int value;
    private boolean isEmpty;

    private FPPuzzle puzzle;

    public FPCell(int value, FPPuzzle puzzle) {
        this.value = value;
        this.puzzle = puzzle;
        isEmpty = value == -1;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }
}
