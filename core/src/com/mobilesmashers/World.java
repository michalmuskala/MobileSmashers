package com.mobilesmashers;

import com.badlogic.gdx.math.Rectangle;
import com.mobilesmashers.HelpClasses.Point;

import java.util.Random;

public class World {
    public final Player player;
    public Ball balls[];

    public World() {
        this.player = new Player(new Point(Board.WIDTH / 2 - Player.WIDTH + 1, Board.HEIGHT / 2 - Player.HEIGHT + 1));
        this.balls = new Ball[Board.LEVEL * 2];
        for (int i = 0; i < Board.LEVEL * 2; i++) {
            this.balls[i] = new Ball(drawBallsPosition(new Point(0, 0)));
        }
    }

    public void update() {
        updatePlayer();
        updateBalls();

    }

    private void updatePlayer() {
        player.move();
    }

    private void updateBalls() {
        for (int i = 0; i < Board.LEVEL * 2; i++) {
            balls[i].move();
        }
    }

    private Point drawBallsPosition(Point position) {
        Random random = new Random();
        int x = random.nextInt(Board.WIDTH - Ball.WIDTH);
        int y = random.nextInt(Board.HEIGHT - Ball.HEIGHT);
        Rectangle ballRectangle = new Rectangle(x, y, Ball.WIDTH, Ball.HEIGHT);
        int distance = 200;
        Rectangle playerRectangle = new Rectangle(player.getPosition().x - distance, player.getPosition().y - distance, player.WIDTH + 2 * distance, player.HEIGHT + 2 * distance);
        if (ballRectangle.overlaps(playerRectangle)) {
            return drawBallsPosition(position);
        } else {
            position.x = x;
            position.y = y;
            return position;
        }
    }
}
