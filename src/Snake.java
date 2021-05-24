import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class Snake {
    private List<Cell> body;

    Snake() {

    }

    Snake(Cell head) {
        body = new ArrayList<>();
        body.add(head);
    }

    public void addCell(int cellSize) {
        int lastDirection = body.get(body.size() - 1).getDirection();
        int lastX = body.get(body.size() - 1).getX();
        int lastY = body.get(body.size() - 1).getY();
        switch (lastDirection) {
            case 0: // up
                body.add(new Cell(lastX,lastY + cellSize,lastDirection));
                break;
            case 1: // down
                body.add(new Cell(lastX,lastY - cellSize,lastDirection));
                break;
            case 2: // right
                body.add(new Cell(lastX - cellSize,lastY,lastDirection));
                break;
            case 3: // left
                body.add(new Cell(lastX + cellSize,lastY,lastDirection));
                break;
        }
    }

    public void setDirection(int direction) {
        body.get(0).setDirection(direction);
    }

    public Snake(List<Cell> body) {
        this.body = body;
    }

    public List<Cell> getBody() {
        return body;
    }

    public void setBody(List<Cell> body) {
        this.body = body;
    }
}

