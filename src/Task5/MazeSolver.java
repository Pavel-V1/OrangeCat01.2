package Task5;

import javafx.scene.layout.Border;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class MazeSolver extends JPanel {
    private static final int CELL_SIZE = 20;
    private static final int GRID_WIDTH = 20;
    private static final int GRID_HEIGHT = 20;

    private int[][] grid = new int[GRID_HEIGHT][GRID_WIDTH];
    private Point start = null;
    private Point end = null;

    private MazeSolver() {
        setPreferredSize(new Dimension(GRID_WIDTH * CELL_SIZE, GRID_HEIGHT * CELL_SIZE));
        setBackground(Color.WHITE);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX() / CELL_SIZE;
                int y = e.getY() / CELL_SIZE;

                if (x >= 0 && x < GRID_WIDTH && y >= 0 && y < GRID_HEIGHT) {
                    if (SwingUtilities.isRightMouseButton(e)) {
                        /* вложенные if для игнорирования стенок */
                        if (start == null || start != null && grid[y][x] == 1) {
                            if (grid[y][x] != 1) {
                                start = new Point(x, y);
                            }
                        } else if (end == null || end != null && grid[y][x] == 1) {
                            if (grid[y][x] != 1) {
                                end = new Point(x, y);
                            }
                        } else {
                            start = new Point(x, y);
                            end = null;
                        }
                    } else if (SwingUtilities.isLeftMouseButton(e)) {
                        toggleCell(x, y);
                    }
                }
                repaint();
            }
        });
    }

    private void toggleCell(int x, int y) {
        grid[y][x] = grid[y][x] == 0 ? 1 : 0;
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
    }

    private void findPath() {
        if (start == null || end == null) return;

        clearPreviousPath();

//        PathFinder.findPath();

        Queue<Point> queue = new LinkedList<>();
        boolean[][] visited = new boolean[GRID_HEIGHT][GRID_WIDTH];
        Map<Point, Point> parentMap = new HashMap<>();

        queue.add(start);
        visited[start.y][start.x] = true;

        while (!queue.isEmpty()) {
            Point current = queue.poll();

            if (current.equals(end)) break;

            for (Point neighbor : getNeighbors(current)) {
                if (!visited[neighbor.y][neighbor.x] && grid[neighbor.y][neighbor.x] == 0) {
                    queue.add(neighbor);
                    visited[neighbor.y][neighbor.x] = true;
                    parentMap.put(neighbor, current);
                }
            }
        }

        Point step = end;
        while (parentMap.containsKey(step)) {
            grid[step.y][step.x] = -1;
            step = parentMap.get(step);
        }

        repaint();
    }

    private void clearPreviousPath() {
        for (int y = 0; y < GRID_HEIGHT; y++) {
            for (int x = 0; x < GRID_WIDTH; x++) {
                if (grid[y][x] == -1) {
                    grid[y][x] = 0;
                }
            }
        }
    }

    private List<Point> getNeighbors(Point cell) {
        List<Point> neighbors = new ArrayList<>();

        if (cell.x > 0) neighbors.add(new Point(cell.x - 1, cell.y));
        if (cell.x < GRID_WIDTH - 1) neighbors.add(new Point(cell.x + 1, cell.y));
        if (cell.y > 0) neighbors.add(new Point(cell.x, cell.y - 1));
        if (cell.y < GRID_HEIGHT - 1) neighbors.add(new Point(cell.x, cell.y + 1));

        return neighbors;
    }

    public static void main(String[] args) throws Exception {

        JFrame frame = new JFrame("Maze Solver");
        MazeSolver mazeSolver = new MazeSolver();
        JPanel panel = new JPanel();
        LayoutManager layout = new FlowLayout();
        panel.setLayout(layout);
        JButton findPathButton = new JButton("Найти путь");
        findPathButton.addActionListener(e -> mazeSolver.findPath());
        JSpinner spinByX = new JSpinner();
        JSpinner spinByY = new JSpinner();
        JRadioButton roadButton = new JRadioButton("Дорога");
        JRadioButton groundButton = new JRadioButton("Земля");
        JRadioButton sandButton = new JRadioButton("Песок");
        JRadioButton obstacleButton = new JRadioButton("Препятствие");
        spinByX.setValue(GRID_WIDTH);
        spinByY.setValue(GRID_HEIGHT);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(findPathButton);

        frame.setLayout(new BorderLayout());
        panel.add(spinByX);
        panel.add(spinByY);
        panel.add(roadButton);
        panel.add(groundButton);
        panel.add(sandButton);
        panel.add(obstacleButton);
        frame.add(mazeSolver);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.getContentPane().add(panel, BorderLayout.BEFORE_FIRST_LINE);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        roadButton.addActionListener(e -> {
            if (groundButton.isSelected()) {
                groundButton.setSelected(false);
            }
            if (sandButton.isSelected()) {
                sandButton.setSelected(false);
            }
            if (obstacleButton.isSelected()) {
                obstacleButton.setSelected(false);
            }
        });

        groundButton.addActionListener(e -> {
            if (roadButton.isSelected()) {
                roadButton.setSelected(false);
            }
            if (sandButton.isSelected()) {
                sandButton.setSelected(false);
            }
            if (obstacleButton.isSelected()) {
                obstacleButton.setSelected(false);
            }
        });

        sandButton.addActionListener(e -> {
            if (groundButton.isSelected()) {
                groundButton.setSelected(false);
            }
            if (roadButton.isSelected()) {
                roadButton.setSelected(false);
            }
            if (obstacleButton.isSelected()) {
                obstacleButton.setSelected(false);
            }
        });

        obstacleButton.addActionListener(e -> {
            if (groundButton.isSelected()) {
                groundButton.setSelected(false);
            }
            if (sandButton.isSelected()) {
                sandButton.setSelected(false);
            }
            if (roadButton.isSelected()) {
                roadButton.setSelected(false);
            }
        });
    }
}