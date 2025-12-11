package Task5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseListener;

public class Main {
    private static final int CELL_SIZE = 20;
    private static final int GRID_WIDTH = 20;
    private static final int GRID_HEIGHT = 20;
    private static Point start = null;
    private static Point end = null;

    public static void main(String[] args)  {
        createWindow();
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

    private static void createUI(final JFrame frame){
        JPanel panel = new JPanel();
        LayoutManager layout = new FlowLayout();
        panel.setLayout(layout);
        final JRadioButton roadButton = new JRadioButton("Дорога");
        final JRadioButton groundButton = new JRadioButton("Земля");
        final JRadioButton sandButton = new JRadioButton("Песок");
        final JRadioButton obstacleButton = new JRadioButton("Препятствие");

        // .getSource()).isSelected()



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
        panel.add(roadButton);
        panel.add(groundButton);
        panel.add(sandButton);
        panel.add(obstacleButton);
        frame.getContentPane().add(panel, BorderLayout.CENTER);
    }
}