package Task5;

public class Grid2D {
    private int[][] grid2D;

    public Grid2D(int x, int y) {
        this.grid2D = new int[x][y];
    }

    public int[][] getGrid2D() {
        return grid2D.clone();
    }

    public int[][] setGrid2D(int x, int y) {
        this.grid2D = new int[x][y];
        return grid2D;
    }

    public void setCell(int x, int y, int value) {
        grid2D[x][y] = value;
    }

    public int getCell(int x, int y) {
        return grid2D[x][y];
    }
}
