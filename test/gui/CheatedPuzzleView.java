package gui;

public class CheatedPuzzleView extends FPPuzzlePanel {

    public CheatedPuzzleView(int size) {
        super(size);
    }

    @Override
    public void initComponents(){
        puzzle = new CheatedPuzzleForTest();
        cellViews = new FPCellView[puzzle.getSize()];
        for (int i = 0; i < cellViews.length; i++) {
            cellViews[i] = new FPCellView(puzzle.get(i));
        }
        for (FPCellView cellView : cellViews) {
            add(cellView);
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
