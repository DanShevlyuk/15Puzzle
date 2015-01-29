package gui;

import model.FPPuzzle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Игровое поле с клетками
 */
public class FRPuzzlePanel extends JPanel implements MouseListener, ComponentListener {
    protected FPPuzzle puzzle;
    protected FPCellView cellViews[];

    private JFrame parent;

    private boolean youWon = false;
    public void setParent(JFrame fr){
        parent = fr;
    }
    //TODO: добавить размеры пазла и размер одной клетки и использовать их в cellClicked

    private GridLayout layout = new GridLayout(4, 4);

    public FRPuzzlePanel() {
        setLayout(layout);
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
                (int)(4 * cellViews[0].getPreferredSize().getHeight()) + 2);
    }

    public void initComponents() {
        puzzle = new FPPuzzle();
        init();
    }

    public void initComponents(FPPuzzle puzzle) {
        this.puzzle = puzzle;
        init();
    }

    private void init() {
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
        if (puzzle.moveMePlease(moveMe)){
            fillPaneSortedByPosition();
            if (puzzle.isComplete()){
                fillWithCongratulations();
            }
        }
        repaint();
    }


    public void fillWithCongratulations() {

        String str = "CONGRATULATIONS!";

        removeAll();

        for (int i = 0; i < 16; i ++) {
            String letter = Character.toString(str.charAt(i));
            cellViews[i] = new CellWithAString(letter);
            add(cellViews[i]);
            cellViews[i].countTheSize();
        }
        makeCellViewsResizable();
        repaint();
        validate();
        updateUI();
        for (FPCellView cell : cellViews) {
            cell.updateTheView();
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //Shutter
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //Shutter
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //Shutte
    }

    @Override
    public void componentResized(ComponentEvent e) {
        JPanel source = (JPanel)e.getComponent();
        setSize(new Dimension(source.getWidth(), source.getHeight() ));
        repaint();

    }

    @Override
    public void componentMoved(ComponentEvent e) {
        //Затычка
    }

    @Override
    public void componentShown(ComponentEvent e) {
        //Затычка
    }

    @Override
    public void componentHidden(ComponentEvent e) {
        //Затычка
    }
}
