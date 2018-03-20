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
        renderBall();

        batch.end();
    }

    private void renderPlayer() {
        batch.draw(world.player.getImage(), world.player.getPosition().x, world.player.getPosition().y, Player.WIDTH, Player.HEIGHT);
    }

    private void renderBall() {
        batch.draw(world.ball.getImage(), world.ball.getPosition().x, world.ball.getPosition().y, Ball.WIDTH, Ball.HEIGHT);
    }
}
