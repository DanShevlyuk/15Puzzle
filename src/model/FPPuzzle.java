package model;

import java.util.List;

public class FPPuzzle {

    private List<FPCell> cells;
    private int emptyCellIndex;
    //TODO: добавить размеры поля (константа 16 клеток) + геттер

    public FPPuzzle() {
        //TODO: init cells randomly
        //TODO: init emptyCellIndex
    }

    //TODO: написать геттер и сеттер (нужен ли он?) для emptyCellIndex

    /*
     * FPCell вызывает этот метод, когда на нее кликнули
     *
     * return true, когда все ок и false, когда передивнуть не удалось
     */
    //TODO: имплементировать moveMePlease
    public boolean moveMePlease(int index) {
        //проверить можно ли двигать
        // подвинуть, если можно и вернуть true

        // как двигать: присвоить значение index-а пустой
        // и сделать index пустой (очевидно)

        // если нельзя, то false
        return false;
    }


}
