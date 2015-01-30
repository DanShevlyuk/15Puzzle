package gui;

public class CheatedMainFrame extends MainFrame {

    public CheatedMainFrame() {
        super();
        puzzlePanel = new CheatedPuzzleView(4);
        puzzlePanel.initComponents();
        contentPanel.removeAll();
        puzzlePanel.makeCellViewsResizable();

        contentPanel.add(toolPanel);
        contentPanel.add(puzzlePanel);
    }
}


