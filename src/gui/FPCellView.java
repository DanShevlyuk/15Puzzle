package gui;

import model.FPCell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;

public class FPCellView extends JComponent implements ComponentListener {
    FPCell model;
    private int componentWidth = 50;
    private int componentHeight = 50;

    public FPCellView(FPCell  cell) {
        model = cell;
    }


    /**
     * Вызывать только после add
     */
    public void countTheSize() {
        FRPuzzlePanel parent =  (FRPuzzlePanel)getParent();
        if (parent != null) {
            componentWidth = parent.getWidth() / (int)Math.sqrt(parent.getPuzzle().getSize());
            componentHeight = parent.getHeight() / (int)Math.sqrt(parent.getPuzzle().getSize());
        }
    }

    public void updateTheView() {
        countTheSize();
        repaint();
    }

    public int getModelValue() {
        return model.getValue();
    }

    public int getModelPosition() {
        return model.getPosition();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(componentWidth, componentHeight);
    }

    @Override
    public void paintComponent(Graphics g){

        Graphics2D g2 = (Graphics2D)g;
        g2.draw(new Rectangle2D.Double(0, 0, componentWidth , componentHeight ));
        String str = Integer.toString(model.getValue());
        g2.drawString(str, componentWidth/2, componentHeight/2);
    }

    @Override
    public void componentResized(ComponentEvent e) {
        updateTheView();
    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }







}
