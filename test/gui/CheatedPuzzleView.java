package gui;

import model.FPPuzzle;

/**
 * Created by reqwy on 29.01.2015.
 */
public class CheatedPuzzleView extends FRPuzzlePanel {

    @Override
    public void initComponents(){
        puzzle = new CheatedPuzzleForTest();
        cellViews = new FPCellView[puzzle.getSize()];
        for (int i = 0; i < cellViews.length; i++) {
            cellViews[i] = new FPCellView(puzzle.get(i));
        }
        for (int i = 0; i < cellViews.length; i++) {
            add(cellViews[i]);
        }
        panelListenToCells();
        repaint();
        validate();
        updateUI();
        for (FPCellView cell : cellViews) {
            cell.updateTheView();
        }
    }
}
