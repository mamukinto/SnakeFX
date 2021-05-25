import javafx.application.Application;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class Main extends Application {
    private static final Random rand = new Random();

    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    private static final int CELL_SIZE = 40;
    private static Snake snake = new Snake();
    private static Cell food = new Cell();

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        canvas.setFocusTraversable(true);
        spawnSnake();
        spawnFood();
        addControls(canvas);
        gameRefresher(canvas.getGraphicsContext2D());

        root.setCenter(canvas);
        stage.setScene(scene);


        stage.show();
    }

    private void addControls(Canvas canvas) {
        canvas.setOnKeyPressed(keyPress -> {
            switch (keyPress.getCode()) {
                case UP:
                    snake.setDirection(0);
                    break;
                case DOWN:
                    snake.setDirection(1);
                    break;
                case RIGHT:
                    snake.setDirection(2);
                    break;
                case LEFT:
                    snake.setDirection(3);
                    break;
                case K:
                    snake.addCell(CELL_SIZE);
                    break;
            }
        });
    }

    private void calibrateSnakeDirection() {
        for (int i = snake.getBody().size() - 1; i >= 0; i--) {
            if (i != 0) {
                snake.getBody().get(i).setDirection(snake.getBody().get(i - 1).getDirection());
            }
        }
    }

    private void spawnSnake() {
        int x = rand.nextInt(WIDTH);
        int y = rand.nextInt(HEIGHT);
        int direction = rand.nextInt(4);
        Cell cell = new Cell(x, y, direction);
        snake = new Snake(cell);
        snake.addCell(CELL_SIZE);
    }

    private void spawnFood() {
        int x = rand.nextInt(WIDTH);
        int y = rand.nextInt(HEIGHT);
        food = new Cell(x,y,0);
    }

    private void drawFood(GraphicsContext g) {
        g.setFill(Color.web("blue"));
        g.fillRect(food.getX(),food.getY(),CELL_SIZE,CELL_SIZE);
        g.setFill(Color.web("black"));
    }

    private void moveSnake() {
        snake.getBody().forEach(cell -> {
            cell.move(CELL_SIZE);
        });
    }

    private void clearMap(GraphicsContext g) {
        g.setFill(Color.web("black"));
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.setFill(Color.web("white"));
    }

    private void drawSnake(GraphicsContext g) {
        g.setFill(Color.web("white"));
        for (int i = 0; i < snake.getBody().size(); i++) {
            if (i != 0) {
                g.fillRect(snake.getBody().get(i).getX(), snake.getBody().get(i).getY(), CELL_SIZE - 5, CELL_SIZE - 5);
            }
        }
//
//
//        snake.getBody().forEach(cell -> {
//            if (snake.getBody().indexOf(cell) != 0) {
//                g.fillRect(cell.getX(), cell.getY(), CELL_SIZE + 10, CELL_SIZE + 10);
//                g.setFill(Color.web("white"));
////            g.setFont(new Font(18));
////            g.fillText(cell.getDirection() + "",cell.getX() + CELL_SIZE/2, cell.getY() + CELL_SIZE/2);
//                g.setFill(Color.web("black"));
//            }
//        });
    }

    private void gameRefresher(GraphicsContext g) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                clearMap(g);
                drawScore(g);
                calibrateSnakeDirection();
                moveSnake();
                drawSnake(g);
                drawFood(g);

                if (checkForCollisionSnakeWithFood()) {
                    snake.addCell(CELL_SIZE);
                    spawnFood();
                    System.out.println("intersect");
                }
            }
        }, 0, 75);
    }

    boolean checkForCollisionSnakeWithFood() {

        if (food.getX() < snake.getBody().get(1).getX() + CELL_SIZE &&
                food.getX() + CELL_SIZE > snake.getBody().get(1).getX() &&
                food.getY() < snake.getBody().get(1).getY() + CELL_SIZE &&
                food.getY() + CELL_SIZE > snake.getBody().get(1).getY()) {
            return true;
        }
        return false;
    }


    private void drawScore(GraphicsContext g) {
        g.setFont(new Font(50));
        g.setTextAlign(TextAlignment.CENTER);
        g.setTextBaseline(VPos.CENTER);
        g.fillText("Score: " + (snake.getBody().size() - 1),Math.round(WIDTH/2), Math.round(HEIGHT/2));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
