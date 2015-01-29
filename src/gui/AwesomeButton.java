package gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by reqwy on 29.01.2015.
 */
public class AwesomeButton extends JButton {

    private int width = 50;
    private int height = 50;

    public AwesomeButton(String str) {
        super(str);
        ImageIcon icon = new ImageIcon("src/gui/buttonIcon.png");
        setIcon(icon);

        width = icon.getIconWidth() - 20;
        height = icon.getIconHeight();
        setVisible(true);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    @Override
    public int getWidth() {
        return width;
    }

}
