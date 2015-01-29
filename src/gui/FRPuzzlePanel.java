package gui;

import model.FPCell;
import model.FPPuzzle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Игровое поле с клетками
 */
public class FRPuzzlePanel extends JPanel implements MouseListener {
    private FPPuzzle puzzle;
    private FPCellView cellViews[];

    private JFrame parent;

    public void setParent(JFrame fr){
        parent = fr;
    }
    //TODO: добавить размеры пазла и размер одной клетки и использовать их в cellClicked

    private GridLayout layout = new GridLayout(4, 4);

    public FRPuzzlePanel() {
        setLayout(layout);
        initComponents();
    }



    public void fillPaneSortedByPosition(){
        removeAll();
        sortCellsByPosition();
        for (int i = 0; i < cellViews.length; i++) {
            add(cellViews[i]);
            cellViews[i].countTheSize();
        }
        repaint();
        validate();
        updateUI();
    }

    @Override
    public Dimension getPreferredSize(){
        return new Dimension((int)(4 * cellViews[0].getPreferredSize().getWidth()),
                (int)(4 * cellViews[0].getPreferredSize().getHeight()));
    }

    public void initComponents() {
        puzzle = new FPPuzzle();
        cellViews = new FPCellView[puzzle.getSize()];
        for (int i = 0; i < cellViews.length; i++) {
            cellViews[i] = new FPCellView(puzzle.get(i));
        }
        for (int i = 0; i < cellViews.length; i++) {
            add(cellViews[i]);
        }
        panelListenToCells();
    }

    public FPPuzzle getPuzzle() {
        return puzzle;
    }

    public void makeCellViewsResizable() {
        for (int i = 0; i < cellViews.length; i++) {
            addComponentListener(cellViews[i]);
        }
    }


    public void panelListenToCells() {
        for (int i = 0; i < cellViews.length; i++) {
            cellViews[i].addMouseListener(this);
        }
    }


    public void sortCellsByPosition() {
        cellViews = new FPCellView[puzzle.getSize()];
        for (int i = 0; i < cellViews.length; i++) {
            cellViews[i] = new FPCellView(puzzle.get(i));
        }
        panelListenToCells();
        makeCellViewsResizable();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //затычка
    }

    @Override
    public void mousePressed(MouseEvent e) {


        FPCellView cellClicked = (FPCellView)e.getComponent();
        System.out.println("Clicked " + cellClicked.getModelValue());
        System.out.println("Pos " + cellClicked.getModelPosition());
        int moveMe = cellClicked.getModelPosition();
        if(puzzle.moveMePlease(moveMe)){
            fillPaneSortedByPosition();
        }
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //
    }
}
