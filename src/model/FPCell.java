package model;

import java.io.Serializable;

public class FPCell implements Comparable<FPCell>, Serializable {
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

    public boolean isEmpty() {
        return isEmpty;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public int compareTo(FPCell o) {
        return this.getValue() < o.getValue() ? -1 : (this.getValue() == o.getValue() ? 0 : 1);
    }
}
