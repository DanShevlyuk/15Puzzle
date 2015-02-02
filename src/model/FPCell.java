package model;

import java.io.Serializable;

/*
 * Puzzle cell model
 */
public class FPCell implements Comparable<FPCell>, Serializable {
    private int value;
    private boolean isEmpty;
    private int position; // position in puzzle array

    public FPCell(int value, int pos) {
        this.value = value;
        position = pos;
        isEmpty = (value == -1); // -1 means empty cell
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
