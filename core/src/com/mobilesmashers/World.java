package com.mobilesmashers;

import com.mobilesmashers.HelpClasses.Point;

public class World {
    public final Player player;

    public final Ball ball;

    public World() {
        this.player = new Player(new Point(Board.WIDTH / 2 - Player.WIDTH + 1, Board.HEIGHT / 2 - Player.HEIGHT + 1));
        this.ball = new Ball(new Point(1, 1));
    }

    public void update() {
        updatePlayer();
        updateBall();
    }

    private void updatePlayer() {
        player.move();
    }

    private void updateBall() {
        ball.move();
    }
}
