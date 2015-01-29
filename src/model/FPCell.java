package model;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class FPCell  {
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

    public void setEmpty(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }

    // Mouse Events occurred in View

}
