package gui;

import com.apple.eawt.Application;
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
public class MainFrame extends JFrame implements WinEventListener {
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

        // dock icon for Mac
        Application.getApplication().setDockIconImage(new ImageIcon("resources/icon.png").getImage());

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

                timer.stop();
                stopWatchUpdater.drop();
                startNewGame();
                stopwatch.setText("00:00:00");
                puzzlePanel.addWinListener(MainFrame.this);
            }
        });

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
                        if (result == JOptionPane.NO_OPTION || result == JOptionPane.CLOSED_OPTION) {
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
                    buildPuzzlePanel(puzzle);
                    stopwatch.setText(time);
                    stepsCounter.setText(String.valueOf(puzzlePanel.getPuzzle().getSteps()));

                }
                else if (!stopwatch.getText().equals("00:00:00")) {
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
        puzzlePanel.addWinListener(MainFrame.this);
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

        info = new JCheckBoxMenuItem("Additional info");

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
        fileChooser.setMultiSelectionEnabled(false);

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

        stopWatchUpdater = new StopWatchUpdater(stopwatch);
        timer = new Timer(1000, stopWatchUpdater);

        puzzlePanel = new FRPuzzlePanel(panelSize);

        if (path.equals("")) {
            puzzlePanel.initComponents();
        }
        else {
            serializer = Serializer.open(path);
            puzzlePanel.initComponents(serializer.getPuzzle());
            String time = serializer.getTime();
            stopWatchUpdater.setTime(time);
            stopwatch.setText(time);
            stepsCounter.setText(String.valueOf(puzzlePanel.getPuzzle().getSteps()));
        }

        contentPanel.add(toolPanel);
        contentPanel.add(puzzlePanel);

        contentPanel.addComponentListener(puzzlePanel);
        puzzlePanel.setParent(this);

        puzzlePanel.addWinListener(this);
        
        add(contentPanel);
    }

    public void setCountLabel(String text) {
        this.stepsCounter.setText(text);
    }


    public void startNewGame(){
        contentPanel.removeAll();
        if (info.isSelected()) {
            contentPanel.add(toolPanel);
        }
        buildPuzzlePanel();
        contentPanel.repaint();
        stepsCounter.setText("0");
    }

    public static void main(String[] args) {
        System.out.println("Open with " + args.length + " arguments");
        if (args.length == 1) {
            new MainFrame(args[0]);
        }
        else {
            new MainFrame();
        }
    }

    public void runStopWatch() {
        timer.start();
    }

    public void stopStopWatch() {
        timer.stop();
    }

    private class DialogWindow  extends JDialog{

        JPanel buttonPanel;
        JPanel contentPanel;

        public DialogWindow(MainFrame owner) {
            super(owner, "sexy winner bra", true);
            contentPanel = new JPanel();
            setContentPane(contentPanel);
            buttonPanel = new JPanel();
            JButton button1 = new JButton("New Game");
            JButton button2 = new JButton("Exit");
            JLabel label = new JLabel("Your Dick is Big! Wanna More?");

            buttonPanel.add(button1);
            buttonPanel.add(button2);
            contentPanel.add(label, BorderLayout.CENTER);
            contentPanel.add(buttonPanel, BorderLayout.SOUTH);


            button1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    MainFrame parent = (MainFrame) owner;
                    parent.startNewGame();
                }
            });

            button2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setVisible(false);
                    owner.dispose();
                }
            });

            pack();

            setVisible(false);

        }

    }


    @Override
    public void youWon() {
        DialogWindow dialogWindow = new DialogWindow(this);
        dialogWindow.setVisible(true);
        ImageIcon icon = new ImageIcon();

    }
}
