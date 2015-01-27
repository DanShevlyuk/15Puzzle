package gui;

import javax.swing.*;

/**
 * Main Frame for 15 Puzzle
 *
 */
public class MainFrame extends JFrame {
    private FRPuzzlePanel puzzlePanel;

    public MainFrame() {
        initComponents();
        initFrame();
    }

    private void initComponents() {
        //TODO: add listeners for puzzle panel
        //TODO: add  puzzlePanel to MainFrame
    }

    private void initFrame() {
        //init some

        updateFrame();
    }

    private void updateFrame() {
        //stuff
        //stuff

        repaint();
    }

    private void jPanelPuzzleMouseClicked(java.awt.event.MouseEvent evt) {
        if (evt.getClickCount() == 0) {
            return;
        }
        puzzlePanel.cellClicked(evt);

        updateFrame();
    }

}
