package gui;

import model.FPCell;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Created by reqwy on 29.01.2015.
 */
public class CellWithAString extends FPCellView {

    private String symbol = "";

    public CellWithAString(FPCell cell) {
        super(cell);
    }

    public CellWithAString(String str) {
        super(null);
        symbol = str;
    }

    @Override
    public void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D)g;
        g2.draw(new Rectangle2D.Double(1, 1, componentWidth - 2, componentHeight - 2));
        Rectangle2D.Double rect = new Rectangle2D.Double(1, 1, componentWidth-2, componentHeight-2);
        g2.setColor(Color.GREEN);
        g2.fill(rect);
        g2.setColor(Color.black);
        g2.drawString(symbol, componentWidth/2, componentHeight/2);
    }
}
