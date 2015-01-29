package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Main Frame for 15 Puzzle
 *
 */
public class MainFrame extends JFrame  {
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
        Component glassPane = getGlassPane();

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

    // removed extramethod

    public static void main(String[] args) {
        new MainFrame();

    }


}
