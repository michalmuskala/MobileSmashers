package com.mobilesmashers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class WorldRenderer {
    private SpriteBatch batch;
    private World world;

    public WorldRenderer(SpriteBatch batch, World world) {
        this.batch = batch;
        this.world = world;
    }

    public void render() {
        batch.begin();

        renderPlayer();
        renderBalls();

        batch.end();
    }

    private void renderPlayer() {
        batch.draw(world.player.getImage(), world.player.getPosition().x, world.player.getPosition().y, Player.WIDTH, Player.HEIGHT);
    }

    private void renderBalls() {
        for (int i = 0; i < Board.LEVEL * 2; i++) {
            batch.draw(world.balls[i].getImage(), world.balls[i].getPosition().x, world.balls[i].getPosition().y, Ball.WIDTH, Ball.HEIGHT);
        }
    }
}
