package gui;

import model.FPPuzzle;
import serializer.Serializator;
import serializer.Serializer;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Main game window
 *
 */
public class MainFrame extends JFrame  {
    protected JPanel contentPanel;
    protected FRPuzzlePanel puzzlePanel;
    private AwesomeButton newGameButton;
    protected JPanel toolPanel;
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem newGame;
    private JMenuItem saveGame;
    private JMenuItem openGame;
    private JMenuItem stuff;
    private JFileChooser fileChooser;
    private JButton hideButton;

    private boolean stuffOn = true;
    private Serializer serializer;


    public MainFrame() {
        setTitle("I love this game.");
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

        setPreferredSize(new Dimension(800, 500));
        pack();

        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentPanel.removeAll();
                contentPanel.add(toolPanel);
                buildPuzzlePanel();
                contentPanel.repaint();
            }
        });

        saveGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser.setSelectedFile(null);
                if (fileChooser.showSaveDialog(saveGame) == JFileChooser.APPROVE_OPTION) {
                    String name = fileChooser.getSelectedFile().getName() + ".puz";
                    serializer.save(puzzlePanel.getPuzzle(), name);
                }
            }
        });

        openGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser.setSelectedFile(null);
                if (fileChooser.showOpenDialog(openGame) == JFileChooser.APPROVE_OPTION) {
                    String name = fileChooser.getSelectedFile().getName();
                    FPPuzzle puzzle = serializer.open(name);
                    contentPanel.remove(puzzlePanel);
                    buildPuzzlePanel(puzzle);
                }
            }

        });

        hideButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentPanel.remove(toolPanel);
                contentPanel.repaint();
                contentPanel.updateUI();
                stuffOn = false;
                stuff.setEnabled(true);
            }
        });

        stuff.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!stuffOn) {
                    contentPanel.removeAll();
                    contentPanel.add(toolPanel);
                    contentPanel.add(puzzlePanel);
                    contentPanel.repaint();
                    contentPanel.updateUI();
                    stuffOn = true;
                    stuff.setEnabled(false);
                }
            }
        });
    }

    private void buildPuzzlePanel(){
        puzzlePanel = new FRPuzzlePanel();
        contentPanel.addComponentListener(puzzlePanel);
        contentPanel.add(puzzlePanel);
        puzzlePanel.initComponents();
        puzzlePanel.makeCellViewsResizable();
    }

    private void buildPuzzlePanel(FPPuzzle puzzle) {
        puzzlePanel = new FRPuzzlePanel();
        contentPanel.addComponentListener(puzzlePanel);
        contentPanel.add(puzzlePanel);
        puzzlePanel.initComponents(puzzle);
        puzzlePanel.makeCellViewsResizable();
    }

    private void initComponents() {
        contentPanel = new JPanel(new GridLayout(1, 2));
        newGameButton = new AwesomeButton("New Game Bro");
        toolPanel = new JPanel(new GridLayout(1, 1));

        fileChooser = new JFileChooser();
        menuBar = new JMenuBar();
        menu = new JMenu("Menu");
        newGame = new JMenuItem("New Game");
        saveGame = new JMenuItem("Save");
        openGame = new JMenuItem("Open");
        stuff = new JMenuItem("Return stuff");
        menu.add(stuff);
        menu.add(newGame);
        menu.addSeparator();
        menu.add(openGame);
        menu.addSeparator();
        menu.add(saveGame);
        menuBar.add(menu);
        this.setJMenuBar(menuBar);

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Puzzle files", "puz");
        serializer = new Serializer();
        fileChooser.removeChoosableFileFilter(fileChooser.getFileFilter());
        fileChooser.setFileFilter(filter);
        fileChooser.setCurrentDirectory(new File(""));
        toolPanel.setPreferredSize(new Dimension(100, 100));
        toolPanel.setVisible(true);


        JLabel stepsCounter = new JLabel("SEX");

        stepsCounter.setFont(new Font("Arial", Font.PLAIN, 72));
        stepsCounter.setHorizontalAlignment(SwingConstants.CENTER);
        toolPanel.add(stepsCounter);

        puzzlePanel = new FRPuzzlePanel();
        contentPanel.add(toolPanel);
        contentPanel.add(puzzlePanel);
        puzzlePanel.initComponents();
        contentPanel.addComponentListener(puzzlePanel);
        hideButton = new JButton("Switch to full-size puzzle");

        toolPanel.setPreferredSize(new Dimension(hideButton.getWidth(),
                newGameButton.getHeight()));
        if (stuffOn) {
            stuff.setEnabled(false);
        }

        add(contentPanel);
    }

    // removed extramethod

    public static void main(String[] args) {
        new MainFrame();
    }


}
