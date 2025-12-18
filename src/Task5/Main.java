package Task5;

import javax.swing.*;
import java.awt.*;

class Main {
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
        spinByX.setValue(mazeSolver.GRID_WIDTH);
        spinByY.setValue(mazeSolver.GRID_HEIGHT);
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
        roadButton.doClick();

        frame.setMinimumSize(frame.getSize());

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
            mazeSolver.settingValue = 1;
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
            mazeSolver.settingValue = 5;
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
            mazeSolver.settingValue = 9;
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
            mazeSolver.settingValue = Integer.MAX_VALUE;
        });

        spinByX.addChangeListener(e -> {
            mazeSolver.GRID_WIDTH = (int) spinByX.getValue();
        });

        spinByY.addChangeListener(e -> {
            mazeSolver.GRID_HEIGHT = (int) spinByY.getValue();
        });
    }
}