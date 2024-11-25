import org.w3c.dom.css.Rect;
import sas.*;
import java.awt.Color;
import java.util.ArrayList;

public class Game {

    View window;
    Circle ball;
    Rectangle player, block;
    Text scoreText;
    Rectangle[] borders;

    ArrayList<Block> blocks;

    int score;

    boolean gameOver;
    double speed, direction, directionRandomizer;

    public static void main(String[] args) {
        new Game();
    }

    public Game () {

        window = new View(1000,730, "Breakout");

        player = new Rectangle(370, 650, 250, 25);
        ball = new Circle(480, 500, 10);

        borders = new Rectangle[] {
                new Rectangle(0, 0, 5, 730),
                new Rectangle(0, 0, 1000, 5),
                new Rectangle(995, 0, 5, 730)
        };

        scoreText = new Text(450, 700, "Punktestand: " + score);

        blocks = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            blocks.add(new Block(i * 105 + 30, 15));
            blocks.add(new Block(i * 105 + 30, 70));
            blocks.add(new Block(i * 105 + 30, 125));
            blocks.add(new Block(i * 105 + 30, 180));
            blocks.add(new Block(i * 105 + 30, 235));
            blocks.add(new Block(i * 105 + 30, 290));
        }

        gameOver = false;

        directionRandomizer = Tools.randomNumber(-15, 15);
        direction = 135 + directionRandomizer;

        speed = 1;
        score = 0;

        ball.setDirection(direction);

        startGame();
    }

    public void startGame() {

        while (!gameOver) {

            window.wait(2);
            ball.move(speed);

            if (ball.intersects(player)) {
                direction = 360 - direction;
                ball.setDirection(direction);
                speed = -speed;
            }

            for (Rectangle border : borders) {
                if (ball.intersects(border)) {
                    speed = -speed;
                    direction = direction - 90;
                    ball.move(-1);
                    ball.setDirection(direction);
                }
            }

            for (int i = 0; i < blocks.size(); i++) {
                Block block = blocks.get(i);
                if (ball.intersects(block.getBlock())) {
                    block.getBlock().setHidden(true);
                    speed = -speed;
                    direction = 360 - direction;
                    ball.setDirection(direction);
                    score++;
                    scoreText.setText("Punktestand: " + score);
                }
            }

            if (window.keyPressed('a')) {
                player.move(-1.2, 0);
            }

            if (window.keyPressed('d')) {
                player.move(1.2, 0);
            }

            if (ball.getCenterY() > 710) {
                gameOver = true;
            }
        }
    }
}

class Block {

    Rectangle newBlock;

    public Block (int x, int y) {
        newBlock = new Rectangle(x, y, 100, 50);
    }

    public Rectangle getBlock() {
        return newBlock;
    }
}