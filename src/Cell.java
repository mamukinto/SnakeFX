public class Cell {
    private int x;
    private int y;
    private int direction;


    public Cell() {
    }

    public Cell(int x, int y, int direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void move(int cellSize) {
        switch(direction) {
            case 0: // up
                y -= cellSize;
                break;
            case 1: // down
                y += cellSize;
                break;
            case 2: // right
                x += cellSize;
                break;
            case 3: // left
                x -= cellSize;
                break;
        }
    }

    @Override
    public String toString() {
        return "[x: " + x + "y: " + y +  "]" + "  direction: " + direction;
    }
}
