package gui;

import model.FPPuzzle;
import javax.swing.*;

/**
 * Игровое поле с клетками
 */
public class FRPuzzlePanel extends JFrame {
    FPPuzzle puzzle;
    //TODO: добавить размеры пазла и размер одной клетки и использовать их в cellClicked

    public FRPuzzlePanel() {
        initComponents();
        initPanel();
    }

    private void initPanel() {

    }

    public void initComponents() {

    }


    public void cellClicked(java.awt.event.MouseEvent evt) {
        int row = 0;
        int column = 0;
        //TODO: добыть кликнутую строку и столбец из фрейма используя размеры
        //
        // evt.getX() evt.getY()
        //

        //TODO: преобразовать две координаты в индекс используя размеры поля из puzzle
        // чтобы не хардкодить и можно было бы
        // потом сделеать поле не 16 клеток, а больше

        int index = 0;
        puzzle.moveMePlease(index);
    }
}
