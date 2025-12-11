package Task5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends JPanel{
    private static final int CELL_SIZE = 20;
    private static final int GRID_WIDTH = 20;
    private static final int GRID_HEIGHT = 20;
    protected Integer[][] grid = new Grid2D().initGrid2D(GRID_WIDTH, GRID_HEIGHT);
    private static Point start = null;
    private static Point end = null;

    public static void main(String[] args)  {
        createWindow();
    }

    public Main() {
        setPreferredSize(new Dimension(GRID_WIDTH * CELL_SIZE, GRID_HEIGHT * CELL_SIZE));
        setBackground(Color.WHITE);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX() / CELL_SIZE;
                int y = e.getY() / CELL_SIZE;

                if (SwingUtilities.isLeftMouseButton(e)) {
                    // Установка стартовой точки
                    if (start == null) {
                        start = new Point(x, y);
                    } else if (end == null) {
                        end = new Point(x, y);
                    } else {
                        // Если обе точки установлены, сбрасываем их
                        start = new Point(x, y);
                        end = null;
                    }
                } else if (SwingUtilities.isRightMouseButton(e)) {
                    // Переключение стены или пустой клетки
                    toggleCell(x, y);
                }
                repaint();
            }
        });
    }

    private void toggleCell(int x, int y) {
        if (x >= 0 && x < GRID_WIDTH && y >= 0 && y < GRID_HEIGHT) {
            grid[y][x] = grid[y][x] == 0 ? 1 : 0;
        }
    }

    private static void createWindow() {
        JFrame frame = new JFrame("Поиск пути");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createUI(frame);
        frame.setSize(492, 200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Отрисовка сетки
        g.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i <= GRID_WIDTH; i++) {
            g.drawLine(i * CELL_SIZE, 0, i * CELL_SIZE, GRID_HEIGHT * CELL_SIZE);
        }
        for (int i = 0; i <= GRID_HEIGHT; i++) {
            g.drawLine(0, i * CELL_SIZE, GRID_WIDTH * CELL_SIZE, i * CELL_SIZE);
        }

        // Отрисовка клеток
        for (int y = 0; y < GRID_HEIGHT; y++) {
            for (int x = 0; x < GRID_WIDTH; x++) {
                if (grid[y][x] == 1) {
                    g.setColor(Color.BLACK);
                    g.fillRect(x * CELL_SIZE + 1, y * CELL_SIZE + 1, CELL_SIZE - 1, CELL_SIZE - 1);
                } else if (grid[y][x] == -1) {
                    g.setColor(Color.BLUE);
                    g.fillRect(x * CELL_SIZE + CELL_SIZE * 2 / 7, y * CELL_SIZE + CELL_SIZE * 2 / 7, CELL_SIZE / 2, CELL_SIZE / 2);
                }
            }
        }

        // Отрисовка стартовой и конечной клеток
        if (start != null) {
            g.setColor(Color.GREEN);
            g.fillRect(start.x * CELL_SIZE + 2, start.y * CELL_SIZE + 2, CELL_SIZE - 3, CELL_SIZE - 3);
        }
        if (end != null) {
            g.setColor(Color.RED);
            g.fillRect(end.x * CELL_SIZE + 2, end.y * CELL_SIZE + 2, CELL_SIZE - 3, CELL_SIZE - 3);
        }

        // Отрисовка меток по краям сетки
        g.setColor(Color.BLACK);
        for (int i = 0; i < GRID_WIDTH; i++) {
            char label = (char) ('A' + i);
            g.drawString(String.valueOf(label), i * CELL_SIZE + (CELL_SIZE / 2) - 5, GRID_HEIGHT * CELL_SIZE + 15);
        }

        for (int i = 0; i < GRID_HEIGHT; i++) {
            g.drawString(String.valueOf(i + 1), GRID_WIDTH * CELL_SIZE + 5, i * CELL_SIZE + (CELL_SIZE / 2) + 5);
        }
    }

    private static void createUI(final JFrame frame){
        JPanel panel = new JPanel();
        LayoutManager layout = new FlowLayout();
        panel.setLayout(layout);
        final JSpinner spinByX = new JSpinner();
        final JSpinner spinByY = new JSpinner();
        final JRadioButton roadButton = new JRadioButton("Дорога");
        final JRadioButton groundButton = new JRadioButton("Земля");
        final JRadioButton sandButton = new JRadioButton("Песок");
        final JRadioButton obstacleButton = new JRadioButton("Препятствие");
        spinByX.setValue(GRID_WIDTH);
        spinByY.setValue(GRID_HEIGHT);

        roadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (groundButton.isSelected()) {
                    groundButton.setSelected(false);
                }
                if (sandButton.isSelected()) {
                    sandButton.setSelected(false);
                }
                if (obstacleButton.isSelected()) {
                    obstacleButton.setSelected(false);
                }
            }
        });

        groundButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (roadButton.isSelected()) {
                    roadButton.setSelected(false);
                }
                if (sandButton.isSelected()) {
                    sandButton.setSelected(false);
                }
                if (obstacleButton.isSelected()) {
                    obstacleButton.setSelected(false);
                }
            }
        });

        sandButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (groundButton.isSelected()) {
                    groundButton.setSelected(false);
                }
                if (roadButton.isSelected()) {
                    roadButton.setSelected(false);
                }
                if (obstacleButton.isSelected()) {
                    obstacleButton.setSelected(false);
                }
            }
        });

        obstacleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (groundButton.isSelected()) {
                    groundButton.setSelected(false);
                }
                if (sandButton.isSelected()) {
                    sandButton.setSelected(false);
                }
                if (roadButton.isSelected()) {
                    roadButton.setSelected(false);
                }
            }
        });

        panel.add(spinByX);
        panel.add(spinByY);
        panel.add(roadButton);
        panel.add(groundButton);
        panel.add(sandButton);
        panel.add(obstacleButton);
        frame.getContentPane().add(panel, BorderLayout.CENTER);
    }
}