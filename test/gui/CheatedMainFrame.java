package gui;

/**
 * Created by reqwy on 29.01.2015.
 */
public class CheatedMainFrame extends MainFrame {

    public CheatedMainFrame() {
        super();
        puzzlePanel = new CheatedPuzzleView();
        puzzlePanel.initComponents();
        contentPanel.removeAll();
        puzzlePanel.makeCellViewsResizable();

        contentPanel.add(toolPanel);
        contentPanel.add(puzzlePanel);
    }
}


