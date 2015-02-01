package gui;

import model.FPPuzzle;
import serializer.Serializer;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

/**
 * Main game window
 *
 */
public class MainFrame extends JFrame implements WinEventListener {
    protected JPanel contentPanel;
    protected FPPuzzlePanel puzzlePanel;
    protected JPanel toolPanel;

    private JMenuItem newGame4;
    private JMenuItem newGame6;
    private JMenuItem newGame8;
    private JMenuItem newGame10;

    private JMenuItem saveGame;
    private JMenuItem openGame;
    private JMenuItem info;
    private JFileChooser fileChooser;
    private JLabel stepsCounter;
    private Serializer serializer;

    private Timer timer;
    private StopWatchUpdater stopWatchUpdater;
    private JLabel stopwatch;

    private String path;
    private int panelSize = 4;

    public MainFrame() {
        this("");
    }

    public MainFrame(String path) {
        this.path = path;
        setTitle("15Puzzle");

        // image for Windows ans Linux
        this.setIconImage(new ImageIcon("resources/icon.png").getImage());

        /*
        // dock icon for Mac
        com.apple.eawt.Application.Application.getApplication().setDockIconImage(
                new ImageIcon("resources/icon.png").getImage());
        */
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        initComponents();

        setContentPane(contentPanel);
        Toolkit tk;
        tk = Toolkit.getDefaultToolkit();

        Dimension screen = tk.getScreenSize();
        setLocation((int) screen.getWidth() / 4, (int) screen.getHeight() / 4);
        setVisible(true);
        puzzlePanel.makeCellViewsResizable();

        setMinimumSize(new Dimension(670, 440));
        setPreferredSize(new Dimension(800, 500));

        pack();

        newGame4.addActionListener(new NewGameListener());
        newGame6.addActionListener(new NewGameListener());
        newGame8.addActionListener(new NewGameListener());
        newGame10.addActionListener(new NewGameListener());

        saveGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser.setSelectedFile(null);
                timer.stop();
                if (fileChooser.showSaveDialog(saveGame) == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();

                    if (file.exists()) {
                        int result = JOptionPane.showConfirmDialog(fileChooser,
                                "File already exists\n Rewrite file?", "File already exists",
                                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if (result == JOptionPane.NO_OPTION ||
                                result == JOptionPane.CLOSED_OPTION) {
                            actionPerformed(e);
                            return;
                        }
                    }

                    String name = file.getAbsolutePath();
                    if (!name.endsWith(".puz")) {
                        name += ".puz";
                    }
                    serializer = new Serializer(puzzlePanel.getPuzzle(),
                            stopWatchUpdater.getTime());
                    Serializer.save(serializer, name);
                }
                if (!stopwatch.getText().equals("00:00:00")) {
                    timer.start();
                }
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
                    panelSize = puzzle.getSideSize();
                    stopwatch.setText(time);
                    buildPuzzlePanel(puzzle);
                    stepsCounter.setText(String.valueOf(puzzlePanel.getPuzzle().getSteps()));
                } else if (!stepsCounter.getText().equals("0")) {
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

        //тут должен меняться размер лэйблов
        //но меняется он хуево
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                stepsCounter.setFont(new Font("Arial", Font.PLAIN,
                        2 * (getHeight() / 100) * (getWidth() / 100)));

                stopwatch.setFont(new Font("Arial", Font.PLAIN,
                        3 * (toolPanel.getHeight() / 100) * (toolPanel.getWidth() / 100)));
            }
        });
    }

    private void buildPuzzlePanel(FPPuzzle puzzle) {
        puzzlePanel = new FPPuzzlePanel(panelSize);
        contentPanel.addComponentListener(puzzlePanel);
        contentPanel.add(puzzlePanel);
        if (puzzle == null) {
            puzzlePanel.initComponents();
        }
        else {
            puzzlePanel.initComponents(puzzle);
        }
        puzzlePanel.makeCellViewsResizable();
        puzzlePanel.setParent(this);
        puzzlePanel.setWinListener(MainFrame.this);
        this.addKeyListener(puzzlePanel);
    }

    private void initComponents() {
        contentPanel = new JPanel(new GridLayout(1, 2));
        toolPanel = new JPanel(new GridLayout(2, 1));

        fileChooser = new JFileChooser();

        JMenuBar menuBar = new JMenuBar();
        JMenuItem newGame = new JMenu("New Game");
        JMenu menu = new JMenu("Menu");

        newGame4 = new JMenuItem("4x4");
        newGame6 = new JMenuItem("6x6");
        newGame8 = new JMenuItem("8x8");
        newGame10 = new JMenuItem("10x10");

        saveGame = new JMenuItem("Save");
        openGame = new JMenuItem("Open");
        info = new JCheckBoxMenuItem("Additional info");

        info.setEnabled(true);
        info.setSelected(true);

        newGame.add(newGame4);
        newGame.add(newGame6);
        newGame.add(newGame8);
        newGame.add(newGame10);
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
        fileChooser.setMultiSelectionEnabled(false);

        toolPanel.setPreferredSize(new Dimension(100, 100));
        toolPanel.setVisible(true);

        stepsCounter = new JLabel("0");
        stepsCounter.setHorizontalAlignment(SwingConstants.CENTER);

        stopwatch = new JLabel("00:00:00");
        stopwatch.setHorizontalAlignment(SwingConstants.CENTER);

        toolPanel.add(stepsCounter);
        toolPanel.add(stopwatch);

        stopWatchUpdater = new StopWatchUpdater(stopwatch);
        timer = new Timer(1000, stopWatchUpdater);

        puzzlePanel = new FPPuzzlePanel(panelSize);

        if (path.equals("")) {
            puzzlePanel.initComponents();
        } else {
            serializer = Serializer.open(path);
            puzzlePanel.initComponents(serializer.getPuzzle());
            String time = serializer.getTime();
            stopWatchUpdater.setTime(time);
            stopwatch.setText(time);
            int intSteps = puzzlePanel.getPuzzle().getSteps();
            stepsCounter.setText(String.valueOf(intSteps));
        }

        contentPanel.add(toolPanel);
        contentPanel.add(puzzlePanel);

        contentPanel.addComponentListener(puzzlePanel);
        puzzlePanel.setParent(this);
        puzzlePanel.setWinListener(this);
        this.addKeyListener(puzzlePanel);

        add(contentPanel);
    }

    public void setCountLabel(String text) {
        this.stepsCounter.setText(text);
    }

    public void resetStepsCount() {
        this.stepsCounter.setText("0");
    }


    public void startNewGame() {
        timer.stop();
        stopWatchUpdater.drop();
        contentPanel.removeAll();
        if (info.isSelected()) {
            contentPanel.add(toolPanel);
        }
        buildPuzzlePanel(null);
        resetStepsCount();
        stopwatch.setText("00:00:00");
        contentPanel.repaint();
        repaint();
    }

    public static void main(String[] args) {
        System.out.println("Open with " + args.length + " arguments");
        if (args.length == 1) {
            new MainFrame(args[0]);
        } else {
            new MainFrame();
        }
    }

    public void runStopWatch() {
        timer.start();
    }

    class NewGameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == newGame4) {
                panelSize = 4;
            } else if (e.getSource() == newGame6) {
                panelSize = 6;
            } else if (e.getSource() == newGame8) {
                panelSize = 8;
            } else {
                panelSize = 10;
            }
            startNewGame();
        }
    }

    private class DialogWindow extends JDialog {

        private JPanel buttonPanel;
        private JPanel contentPanel;

        public DialogWindow(final MainFrame owner) {
            super(owner, "Congratulations", true);

            contentPanel = new JPanel();
            setContentPane(contentPanel);
            buttonPanel = new JPanel();
            JButton newGameButton = new JButton("New Game");
            JButton exitButton = new JButton("Quite");
            JLabel label = new JLabel("You are awesome! Wanna play more?");

            buttonPanel.add(newGameButton);
            buttonPanel.add(exitButton);
            contentPanel.add(label, BorderLayout.CENTER);
            contentPanel.add(buttonPanel, BorderLayout.SOUTH);


            newGameButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setVisible(false);
                    owner.startNewGame();
                }
            });

            exitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setVisible(false);
                    owner.dispose();
                    owner.dispatchEvent(new WindowEvent(owner, WindowEvent.WINDOW_CLOSING));
                }
            });

            pack();

            setVisible(false);
        }
    }

    @Override
    public void youWon() {
        DialogWindow dialogWindow = new DialogWindow(this);
        Toolkit tk;
        tk = Toolkit.getDefaultToolkit();
        Dimension screen = tk.getScreenSize();
        dialogWindow.setLocation(((int) screen.getWidth() / 4) + (this.getWidth() / 4),
                ((int) screen.getHeight() / 4) + (this.getHeight() / 4));
        dialogWindow.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        dialogWindow.setVisible(true);
        //ImageIcon icon = new ImageIcon();
    }
}