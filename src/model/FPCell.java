package model;

public class FPCell implements Comparable<FPCell> {
    private int value;
    private boolean isEmpty;
    private int position;

    public FPCell(int value, int pos) {
        this.value = value;
        position = pos;
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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setEmpty(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }

    @Override
    public int compareTo(FPCell o) {
        return this.getValue() < o.getValue() ? -1 : (this.getValue() == o.getValue() ? 0 : 1);
    }

    // Mouse Events occurred in View

}
