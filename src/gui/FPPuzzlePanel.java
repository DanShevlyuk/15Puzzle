package gui;

import model.FPPuzzle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


/**
 * JPanel with Puzzle. The place where all magic happens!
 */
public class FPPuzzlePanel extends JPanel implements MouseListener, ComponentListener, KeyListener {
    protected FPPuzzle puzzle;
    protected FPCellView cellViews[];
    private MainFrame parent;
    private final int panelSize;
    private boolean stopWatchIsRunning;

    private WinEventListener listener = null;

    public void setWinListener(WinEventListener list) {
        listener = list;
    }

    // best method ever made
    public void notifyAboutWin() {
        listener.youWon();
    }

    public FPPuzzlePanel(int size) {
        panelSize = size;
        stopWatchIsRunning = false; //stop watch is not running at the beginning
        GridLayout layout = new GridLayout(panelSize, panelSize);
        setLayout(layout);
    }

    public void setParent(MainFrame parent) {
        this.parent = parent;
        parent.resetStepsCount();
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
        puzzle = new FPPuzzle(panelSize);
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

    //region MouseListener methods
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
                notifyAboutWin();
            }
        }
        updateUI();
        parent.setCountLabel("" + puzzle.getSteps());
        if (!stopWatchIsRunning) {
            parent.runStopWatch();
            stopWatchIsRunning = true;
        }
        repaint();
    }

    //region Shutters
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
        //Shutter
    }
    //endregion of Shutters

    //endregion of MouseListener methods


    //region ComponentListener methods
    @Override
    public void componentResized(ComponentEvent e) {
        JPanel source = (JPanel)e.getComponent();
        setSize(new Dimension(source.getWidth(), source.getHeight()));
        repaint();

    }

    //region Shutters
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
    //endregion Shutters

    //endregion ComponentListener methods


    //region KeyListener methods

    @Override
    public void keyPressed(KeyEvent e) {
        int moveMe;

        System.out.println("Key pressed code=" + e.getKeyCode());
        System.out.println("empty >>> " + puzzle.getEmptyCellIndex());

        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                moveMe = puzzle.getRightIndexFromEmpty();
                break;

            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                moveMe = puzzle.getDownFromEmpty();
                break;

            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                moveMe = puzzle.getLeftIndexFromEmpty();
                break;

            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                moveMe = puzzle.getUpFromEmpty();
                break;

            default:
                return;
        }

        if (moveMe == -1) {
            return;
        } else {
            System.out.println("move to >>> " + moveMe);
            if (puzzle.moveMePlease(moveMe)) {
                fillPaneSortedByPosition();
                if (puzzle.isComplete()) {
                    notifyAboutWin();
                }
            }
            updateUI();
            parent.setCountLabel("" + puzzle.getSteps());
            repaint();

            //DRY??? WHTA FUCK IS THAT?
        }


        updateUI();
        parent.setCountLabel("" + puzzle.getSteps());
        if (!stopWatchIsRunning) {
            parent.runStopWatch();
            stopWatchIsRunning = true;
        }
        repaint();

    }


    //region Shutters
    @Override
    public void keyReleased(KeyEvent e) {
        //shutter
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //shutter
    }
    //endregion Shutters

    //endregion KeyListener methods
}