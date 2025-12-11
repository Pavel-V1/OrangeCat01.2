package Task5;

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

    public MazeSolver() {
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

    public void findPath() {
        if (start == null || end == null) return;

        clearPreviousPath();

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

    public void saveMaze(String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (int[] row : grid) {
                for (int cell : row) {
                    writer.write(String.valueOf(cell));
                }
                writer.newLine();
            }
        }
    }

    public void loadMaze(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int y = 0;
            while ((line = reader.readLine()) != null && y < GRID_HEIGHT) {
                for (int x = 0; x < line.length() && x < GRID_WIDTH; x++) {
                    grid[y][x] = Character.getNumericValue(line.charAt(x));
                }
                y++;
            }
            repaint();
        }
    }

    public static void main(String[] args) throws Exception {

        JFrame frame = new JFrame("Maze Solver");
        MazeSolver mazeSolver = new MazeSolver();

        JButton saveButton = new JButton("Сохранить");
        saveButton.addActionListener(e -> {
            try {
                String filename = JOptionPane.showInputDialog("Введите имя файла для сохранения:");
                mazeSolver.saveMaze(filename);
            } catch(IOException ex){
                ex.printStackTrace();
            }
        });

        JButton loadButton = new JButton("Загрузить");
        loadButton.addActionListener(e -> {
            try {
                String filename = JOptionPane.showInputDialog("Введите имя файла для загрузки:");
                mazeSolver.loadMaze(filename);
            } catch(IOException ex){
                ex.printStackTrace();
            }
        });

        JButton findPathButton = new JButton("Найти путь");
        findPathButton.addActionListener(e -> mazeSolver.findPath());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(findPathButton);

        frame.setLayout(new BorderLayout());
        frame.add(mazeSolver, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
}