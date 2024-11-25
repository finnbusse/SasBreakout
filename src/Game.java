import org.w3c.dom.css.Rect;
import sas.*;
import java.awt.Color;
import java.util.ArrayList;

public class Game {

    View window;
    Circle ball;
    Rectangle player, block;
    Text scoreText, gameOverText;
    Rectangle[] borders;
    Rectangle borderUp;

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
                new Rectangle(995, 0, 5, 730)
        };

        borderUp = new Rectangle(0, 0, 1000, 5);

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
                    direction = 360 - direction;
                    ball.setDirection(direction);

                    while (ball.intersects(border)) {
                        ball.move(speed * 2);
                    }
                }
            }

            if (ball.intersects(borderUp)) {
                direction = direction + 270;
                ball.setDirection(direction);

                ball.move(speed * 2);
            }

            for (int i = 0; i < blocks.size(); i++) {
                Block block = blocks.get(i);
                if (ball.intersects(block.getBlock())) {
                    block.getBlock().setHidden(true);
                    blocks.remove(i);
                    speed = -speed;
                    direction = 360 - direction;
                    ball.setDirection(direction);
                    score++;
                    scoreText.setText("Punktestand: " + score);
                }
            }

            if (window.keyPressed('a') || window.keyLeftPressed()) {
                player.move(-1.2, 0);
            }

            if (window.keyPressed('d') || window.keyRightPressed()) {
                player.move(1.2, 0);
            }

            if (ball.getCenterY() > 710) {
                gameOverText = new Text(450, 340, "Game Over!", Color.RED);
                gameOver = true;
            }

            if (blocks.size() < 1) {
                gameOverText = new Text(450, 340, "Fertig!");
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