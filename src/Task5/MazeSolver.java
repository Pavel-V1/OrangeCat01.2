package Task5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class MazeSolver extends JPanel {
    public static final int CELL_SIZE = 20;
    public static int GRID_WIDTH = 20;
    public static int GRID_HEIGHT = 20;
    public static int settingValue = 1;

    private static Grid2D grid2D = new Grid2D(GRID_WIDTH, GRID_HEIGHT);
    private static int[][] grid = grid2D.getGrid2D();
    private Point start = null;
    private Point end = null;
    private ArrayList<Point> pointArrayList = new ArrayList<>();

    MazeSolver() {
        setPreferredSize(new Dimension(GRID_WIDTH * CELL_SIZE, GRID_HEIGHT * CELL_SIZE));
        setBackground(Color.WHITE);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX() / CELL_SIZE;
                int y = e.getY() / CELL_SIZE;

                if (x >= 0 && x < GRID_WIDTH && y >= 0 && y < GRID_HEIGHT) {
                    if (SwingUtilities.isRightMouseButton(e)) {
                        if (start == null) {
                            start = new Point(x, y);
                        } else if (end == null) {
                            end = new Point(x, y);
                        } else {
                            start = new Point(x, y);
                            end = null;
                        }
                    } else if (SwingUtilities.isLeftMouseButton(e)) {
                        grid2D.setCell(x, y, settingValue);
                    }
                }
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i <= GRID_WIDTH; i++) {
            g.drawLine(i * CELL_SIZE, 0, i * CELL_SIZE, GRID_HEIGHT * CELL_SIZE);
        }
        for (int i = 0; i <= GRID_HEIGHT; i++) {
            g.drawLine(0, i * CELL_SIZE, GRID_WIDTH * CELL_SIZE, i * CELL_SIZE);
        }

        for (int y = 0; y < GRID_HEIGHT; y++) {
            for (int x = 0; x < GRID_WIDTH; x++) {
                if (grid[x][y] == 1) {
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillRect(x * CELL_SIZE + 1, y * CELL_SIZE + 1, CELL_SIZE - 1, CELL_SIZE - 1);
                } else if (grid[x][y] == 5) {
                    g.setColor(new Color(55, 117, 21));
                    g.fillRect(x * CELL_SIZE + 1, y * CELL_SIZE + 1, CELL_SIZE - 1, CELL_SIZE - 1);
                } else if (grid[x][y] == 9) {
                    g.setColor(Color.ORANGE);
                    g.fillRect(x * CELL_SIZE + 1, y * CELL_SIZE + 1, CELL_SIZE - 1, CELL_SIZE - 1);
                } else if (grid[x][y] == Integer.MAX_VALUE) {
                    g.setColor(Color.BLACK);
                    g.fillRect(x * CELL_SIZE + 1, y * CELL_SIZE + 1, CELL_SIZE - 1, CELL_SIZE - 1);
                }
            }
        }

        for (Point p : pointArrayList) {
            g.setColor(Color.BLUE);
            g.fillRect(p.x * CELL_SIZE + CELL_SIZE * 2 / 7, p.y * CELL_SIZE + CELL_SIZE * 2 / 7, CELL_SIZE / 2, CELL_SIZE / 2);
        }

        if (start != null) {
            g.setColor(Color.GREEN);
            g.fillRect(start.x * CELL_SIZE + 2, start.y * CELL_SIZE + 2, CELL_SIZE - 3, CELL_SIZE - 3);
        }
        if (end != null) {
            g.setColor(Color.RED);
            g.fillRect(end.x * CELL_SIZE + 2, end.y * CELL_SIZE + 2, CELL_SIZE - 3, CELL_SIZE - 3);
        }
    }

    void findPath() {
        if (start == null || end == null) return;

        clearPreviousPath();

//        PathFinder.findPath();

        Queue<Point> queue = new LinkedList<>();
        boolean[][] visited = new boolean[GRID_HEIGHT][GRID_WIDTH];
        Map<Point, Point> parentMap = new HashMap<>();

        queue.add(start);
        visited[start.x][start.y] = true;

        while (!queue.isEmpty()) {
            Point current = queue.poll();

            if (current.equals(end)) break;

            for (Point neighbor : getNeighbors(current)) {
                if (!visited[neighbor.x][neighbor.y] && grid[neighbor.x][neighbor.y] != Integer.MAX_VALUE) {
                    queue.add(neighbor);
                    visited[neighbor.x][neighbor.y] = true;
                    parentMap.put(neighbor, current);
                }
            }
        }

        Point step = end;
        while (parentMap.containsKey(step)) {
            pointArrayList.add(step);
            step = parentMap.get(step);
        }

        repaint();
    }

    private void clearPreviousPath() {
        pointArrayList.clear();
    }

    private List<Point> getNeighbors(Point cell) {
        List<Point> neighbors = new ArrayList<>();

        if (cell.x > 0) neighbors.add(new Point(cell.x - 1, cell.y));
        if (cell.x < GRID_WIDTH - 1) neighbors.add(new Point(cell.x + 1, cell.y));
        if (cell.y > 0) neighbors.add(new Point(cell.x, cell.y - 1));
        if (cell.y < GRID_HEIGHT - 1) neighbors.add(new Point(cell.x, cell.y + 1));

        return neighbors;
    }
}