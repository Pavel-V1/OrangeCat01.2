package Task5;

public class Grid2D {
    private Integer[][] grid2D;

    public void initGrid2D(int x, int y) {
        this.grid2D = new Integer[x][y];
    }

    public void setCell(int x, int y, int value) {
        grid2D[x][y] = value;
    }

    public int getCell(int x, int y) {
        return grid2D[x][y];
    }
}
