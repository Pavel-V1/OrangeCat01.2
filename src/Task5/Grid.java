package Task5;

import javax.swing.*;

public class Grid {
    private JSpinner sizeByX;
    private JSpinner sizeByY;
    private JButton findPathButton;
    private JRadioButton roadRadioButton;
    private JRadioButton groundRadioButton;
    private JRadioButton sandRadioButton;
    private JRadioButton obstacleRadioButton;

    public int getSizeByXValue() {
        return sizeByX.getX();
    }

    public int getSizeByYValue() {
        return sizeByY.getX();
    }

    public boolean findPathButtonSelected() {
        return findPathButton.isSelected();
    }

    public boolean roadRadioButtonSelected() {
        return roadRadioButton.isSelected();
    }

    public boolean groundRadioButtonSelected() {
        return groundRadioButton.isSelected();
    }

    public boolean sandRadioButtonSelected() {
        return sandRadioButton.isSelected();
    }

    public boolean obstacleRadioButtonSelected() {
        return obstacleRadioButton.isSelected();
    }
}
