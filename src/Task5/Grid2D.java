package Task5;

public class Grid2D {
    private Integer[][] grid2D;

    public Integer[][] initGrid2D(int x, int y) {
        this.grid2D = new Integer[x][y];
        return grid2D;
    }

    public Integer[][] getGrid2D() {
        return grid2D;
    }

    public void setCell(int x, int y, int value) {
        grid2D[x][y] = value;
    }

    public void setRoad(int x, int y) {
        setCell(x, y, 1);
    }

    public void setGround(int x, int y) {
        setCell(x, y, 5);
    }

    public void setSand(int x, int y) {
        setCell(x, y, 9);
    }

    public void setObstacle(int x, int y) {
        setCell(x, y, Integer.MAX_VALUE);
    }

    public int getCell(int x, int y) {
        return grid2D[x][y];
    }
}
