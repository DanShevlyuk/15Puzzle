package gui;

import model.FPPuzzle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Игровое поле с клетками
 */
public class FRPuzzlePanel extends JPanel implements MouseListener, ComponentListener {
    protected FPPuzzle puzzle;
    protected FPCellView cellViews[];
    private MainFrame parent;
    private final int panelSize;

    public FRPuzzlePanel(int size) {
        panelSize = size;
        GridLayout layout = new GridLayout(panelSize, panelSize);
        setLayout(layout);
    }

    public void setParent(MainFrame parent) {
        this.parent = parent;
    }

    public void fillPaneSortedByPosition(){
        this.
        removeAll();
        sortCellsByPosition();
        for (FPCellView cellView : cellViews) {
            add(cellView);
            cellView.countTheSize();
        }
        repaint();
        validate();
        updateUI();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension((int)(panelSize * cellViews[0].getPreferredSize().getWidth()),
                (int)(panelSize * cellViews[0].getPreferredSize().getHeight()) + 2);
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

    public FPPuzzle getPuzzle() {
        return puzzle;
    }

    public void makeCellViewsResizable() {
        for (FPCellView cellView : cellViews) {
            addComponentListener(cellView);
        }
    }


    public void panelListenToCells() {
        for (FPCellView cellView : cellViews) {
            cellView.addMouseListener(this);
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
        updateUI();
        parent.setCountLabel("" + puzzle.getSteps());
        repaint();
    }


    public void fillWithCongratulations() {
        String str = "CONGRATULATIONS!";

        removeAll();

        int panel = panelSize * panelSize;
        for (int i = 0; i < panel; i ++) {
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
        setSize(new Dimension(source.getWidth(), source.getHeight()));
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

    public void keyTyped(KeyEvent e) {
        System.out.println("Key pressed code=" + e.getKeyCode() + ", char=" + e.getKeyChar());

        int moveMe = -1;

        if (e.getKeyCode() == 37) {
            moveMe = puzzle.getEmptyCellIndex() - 1;
        } else if (e.getKeyCode() == 38) {
            moveMe = puzzle.getEmptyCellIndex() - puzzle.getSize();
        } else if (e.getKeyCode() == 39) {
            moveMe = puzzle.getEmptyCellIndex() + 1;
        } else if (e.getKeyCode() == 40) {
            moveMe = puzzle.getEmptyCellIndex() + puzzle.getSize();
        } else {
            System.out.println("Dude!");
        }

        if (puzzle.moveMePlease(moveMe)){
            fillPaneSortedByPosition();
            if (puzzle.isComplete()){
                fillWithCongratulations();
            }
        }
        updateUI();
        parent.setCountLabel("" + puzzle.getSteps());
        repaint();
    }
}
