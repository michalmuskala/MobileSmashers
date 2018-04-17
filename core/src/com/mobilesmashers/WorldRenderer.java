package com.mobilesmashers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mobilesmashers.HelpClasses.Point;


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

        if(world.line!= null) {
            shootLine();
        }
    }

    private void renderPlayer() {
        batch.draw(world.player.getImage(), world.player.getPosition().x, world.player.getPosition().y, Player.WIDTH, Player.HEIGHT);
    }

    private void shootLine()
    {
        renderLine(world.line.getInitialPosition(), world.line.getTarget());
    }

    private void renderLine(Point initialPosition, Point target) {
        ShapeRenderer debugRenderer = new ShapeRenderer();
        debugRenderer.begin(ShapeRenderer.ShapeType.Line);
        debugRenderer.setColor(Color.WHITE);
        Vector2 firstPosition = new Vector2(initialPosition.x, initialPosition.y);
        Vector2 secondPosition = new Vector2(target.x, target.y);
        debugRenderer.line(firstPosition, secondPosition);
        debugRenderer.end();
        Gdx.gl.glLineWidth(5);
    }

    private void renderBalls() {
        for (int i = 0; i < world.balls.size(); i++) {
            batch.draw(world.balls.get(i).getImage(), world.balls.get(i).getPosition().x, world.balls.get(i).getPosition().y, Ball.WIDTH, Ball.HEIGHT);
        }
    }
}
