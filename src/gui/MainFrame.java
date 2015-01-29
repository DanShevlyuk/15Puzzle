package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Main Frame for 15 Puzzle
 *
 */
public class MainFrame extends JFrame  {
    private JPanel contentPanel;
    private FRPuzzlePanel puzzlePanel;
    private AwesomeButton newGameButton;
    private JPanel toolPanel;
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem newGame;
    private JMenuItem saveGame;
    private JMenuItem openGame;
    private JFileChooser fileChooser;

    public MainFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        initComponents();
        setContentPane(contentPanel);
        Toolkit tk;
        tk = Toolkit.getDefaultToolkit();

        Dimension screen = tk.getScreenSize();
        setLocation((int) screen.getWidth() / 4, (int) screen.getHeight() / 4);
        setVisible(true);


        puzzlePanel.makeCellViewsResizable();
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentPanel.removeAll();
                contentPanel.add(toolPanel);
                buildPuzzlePanel();
                contentPanel.repaint();
            }
        });
        pack();

        saveGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });

        openGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }


    private void buildPuzzlePanel(){
        puzzlePanel = new FRPuzzlePanel();
        contentPanel.addComponentListener(puzzlePanel);
        puzzlePanel.setParent(this);
        contentPanel.add(puzzlePanel);
        puzzlePanel.initComponents();
        puzzlePanel.makeCellViewsResizable();
    }

    private void initComponents() {
        contentPanel = new JPanel(new GridLayout(1, 2));

        newGameButton = new AwesomeButton("New Game Bro");
        toolPanel = new JPanel(new FlowLayout());

        fileChooser = new JFileChooser();
        menuBar = new JMenuBar();
        menu = new JMenu("Menu");
        newGame = new JMenuItem("New Game");
        saveGame = new JMenuItem("Save");
        openGame = new JMenuItem("Open");
        menu.add(newGame);
        menu.addSeparator();
        menu.add(openGame);
        menu.addSeparator();
        menu.add(saveGame);
        menuBar.add(menu);
        this.setJMenuBar(menuBar);

        toolPanel.setPreferredSize(new Dimension(100, 100));
        toolPanel.add(newGameButton);
        toolPanel.setVisible(true);

        puzzlePanel = new FRPuzzlePanel();
        contentPanel.add(toolPanel);
        contentPanel.add(puzzlePanel);
        puzzlePanel.initComponents();
        contentPanel.addComponentListener(puzzlePanel);

        puzzlePanel.setParent(this);




        add(contentPanel);
    }




    // removed extramethod

    public static void main(String[] args) {
        new MainFrame();
    }


}
