package gui;

import model.FPPuzzle;
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
    protected JPanel toolPanel;
    private JMenuItem newGame;
    private JMenuItem saveGame;
    private JMenuItem openGame;
    private JMenuItem info;
    private JFileChooser fileChooser;
    private JLabel stepsCounter;
    private Serializer serializer;

    private Timer timer;
    private StopWatchUpdater stopWatchUpdater;
    private JLabel stopwatch;

    private int panelSize = 4;

    public MainFrame() {
        setTitle("I love this game.");
        this.setIconImage(new ImageIcon("rsc/icon.png").getImage());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        initComponents();
        setContentPane(contentPanel);
        Toolkit tk;
        tk = Toolkit.getDefaultToolkit();

        Dimension screen = tk.getScreenSize();
        setLocation((int) screen.getWidth() / 4, (int) screen.getHeight() / 4);
        setVisible(true);
        puzzlePanel.makeCellViewsResizable();

        setPreferredSize(new Dimension(800, 500));
        pack();

        this.addKeyListener(puzzlePanel);

        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentPanel.removeAll();
                if (info.isSelected()) {
                    contentPanel.add(toolPanel);
                }
                timer.stop();
                stopWatchUpdater.drop();
                buildPuzzlePanel();
                contentPanel.repaint();
                stepsCounter.setText("0");
                stopwatch.setText("00:00:00");
            }
        });

        saveGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser.setSelectedFile(null);
                timer.stop();
                if (fileChooser.showSaveDialog(saveGame) == JFileChooser.APPROVE_OPTION) {
                    String name = fileChooser.getSelectedFile().getAbsolutePath();
                    if (!name.endsWith(".puz")) {
                        name += ".puz";
                    }
                    serializer = new Serializer(puzzlePanel.getPuzzle(), stopWatchUpdater.getTime());
                    Serializer.save(serializer, name);
                }
                timer.start();
            }
        });

        openGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser.setSelectedFile(null);
                timer.stop();
                if (fileChooser.showOpenDialog(openGame) == JFileChooser.APPROVE_OPTION) {
                    String name = fileChooser.getSelectedFile().getAbsolutePath();
                    serializer = Serializer.open(name);
                    FPPuzzle puzzle = serializer.getPuzzle();
                    String time = serializer.getTime();
                    stopWatchUpdater.setTime(time);
                    contentPanel.remove(puzzlePanel);
                    buildPuzzlePanel(puzzle);
                    stopwatch.setText(time);
                    stepsCounter.setText(String.valueOf(puzzlePanel.getPuzzle().getSteps()));
                }
                else {
                    timer.start();
                }
            }
        });

        info.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (info.isSelected()) {
                    contentPanel.removeAll();
                    contentPanel.add(toolPanel);
                    contentPanel.add(puzzlePanel);
                } else {
                    contentPanel.remove(toolPanel);
                }
                contentPanel.repaint();
                contentPanel.updateUI();
            }
        });
    }

    private void buildPuzzlePanel(){
        puzzlePanel = new FRPuzzlePanel(panelSize);
        contentPanel.addComponentListener(puzzlePanel);
        contentPanel.add(puzzlePanel);
        puzzlePanel.initComponents();
        puzzlePanel.makeCellViewsResizable();
        puzzlePanel.setParent(this);
    }

    private void buildPuzzlePanel(FPPuzzle puzzle) {
        puzzlePanel = new FRPuzzlePanel(panelSize);
        contentPanel.addComponentListener(puzzlePanel);
        contentPanel.add(puzzlePanel);
        puzzlePanel.initComponents(puzzle);
        puzzlePanel.makeCellViewsResizable();
        puzzlePanel.setParent(this);
    }

    private void initComponents() {
        contentPanel = new JPanel(new GridLayout(1, 2));
        toolPanel = new JPanel(new GridLayout(2, 1));

        fileChooser = new JFileChooser();

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        newGame = new JMenuItem("New Game");
        saveGame = new JMenuItem("Save");
        openGame = new JMenuItem("Open");

        info = new JCheckBoxMenuItem("Stuff");

        info.setEnabled(true);
        info.setSelected(true);

        menu.add(newGame);
        menu.addSeparator();
        menu.add(openGame);
        menu.add(saveGame);
        menu.addSeparator();
        menu.add(info);

        menuBar.add(menu);
        this.setJMenuBar(menuBar);

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Puzzle files", "puz");
        fileChooser.removeChoosableFileFilter(fileChooser.getFileFilter());
        fileChooser.setFileFilter(filter);
        fileChooser.setCurrentDirectory(new File(""));
        toolPanel.setPreferredSize(new Dimension(100, 100));
        toolPanel.setVisible(true);


        stepsCounter = new JLabel("0");
        stepsCounter.setFont(new Font("Arial", Font.PLAIN, 72));
        stepsCounter.setHorizontalAlignment(SwingConstants.CENTER);

        stopwatch = new JLabel("00:00:00");
        stopwatch.setFont(new Font("Arial", Font.PLAIN, 72));
        stopwatch.setHorizontalAlignment(SwingConstants.CENTER);

        toolPanel.add(stepsCounter);
        toolPanel.add(stopwatch);

        puzzlePanel = new FRPuzzlePanel(panelSize);
        contentPanel.add(toolPanel);
        contentPanel.add(puzzlePanel);
        puzzlePanel.initComponents();
        contentPanel.addComponentListener(puzzlePanel);
        puzzlePanel.setParent(this);

        stopWatchUpdater = new StopWatchUpdater(stopwatch);
        timer = new Timer(1000, stopWatchUpdater);

        add(contentPanel);
    }

    public void setCountLabel(String text) {
        this.stepsCounter.setText(text);
    }

    public static void main(String[] args) {
        new MainFrame();
    }

    public void runStopWatch() {
        timer.start();
    }
}
