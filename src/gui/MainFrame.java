package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * Main Frame for 15 Puzzle
 *
 */
public class MainFrame extends JFrame {
    private FRPuzzlePanel puzzlePanel;

    public MainFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        initComponents();
        Toolkit tk;
        tk = Toolkit.getDefaultToolkit();
        Dimension screen = tk.getScreenSize();
        setLocation((int) screen.getWidth() / 4, (int) screen.getHeight() / 4);
        pack();
        setVisible(true);
        puzzlePanel.makeCellViewsResizable();

    }

    private void initComponents() {
        //TODO: add listeners for puzzle panel
        //TODO: add  puzzlePanel to MainFrame
        puzzlePanel = new FRPuzzlePanel();
        add(puzzlePanel);
    }



    private void updateFrame() {
        //stuff
        //stuff

        repaint();
    }

    private void jPanelPuzzleMouseClicked(java.awt.event.MouseEvent evt) {
        System.out.println("sexyAreYou?");
        if (evt.getClickCount() == 0) {
            return;
        }
        puzzlePanel.cellClicked(evt);

        updateFrame();
    }

    public static void main(String[] args) {
        new MainFrame();

    }

}
