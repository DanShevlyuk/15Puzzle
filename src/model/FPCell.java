package model;

public class FPCell {
    private int value;
    private boolean isEmpty;

    public FPCell(int value) {
        this.value = value;
        isEmpty = (value == -1);
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
