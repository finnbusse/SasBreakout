import sas.*;

public class Game {

    View window;
    Circle ball;
    Rectangle player;
    Rectangle[] borders;

    int score;

    boolean gameOver;
    double speed, direction;

    public static void main(String[] args) {
        new Game();
    }

    public Game () {

        window = new View(1000,700, "Breakout");

        player = new Rectangle(375, 650, 250, 25);
        ball = new Circle(485, 500, 15);

        borders = new Rectangle[] {
                new Rectangle(0, 0, 5, 700),
                new Rectangle(0, 0, 1000, 5),
                new Rectangle(995, 0, 5, 700)
        };

        gameOver = false;
        direction = 180;
        speed = 1;
        score = 0;

        ball.setDirection(direction);

        startGame();
    }

    public void startGame() {

        while (!gameOver) {

            window.wait(1);
            ball.move(speed);

            if (ball.intersects(player)) {
                speed = -speed;
            }

            if (window.keyPressed('a')) {
                player.move(-1, 0);
            }

            if (window.keyPressed('d')) {
                player.move(1, 0);
            }



        }
    }
}