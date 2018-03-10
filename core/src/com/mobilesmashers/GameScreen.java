package com.mobilesmashers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;

public class GameScreen extends ScreenAdapter {

    private WorldRenderer worldRenderer;
    private MobileSmashers game;
    private World world;


    public GameScreen(MobileSmashers game) {
        this.game = game;
        world = new World();
        worldRenderer = new WorldRenderer(game.batch, world);
    }

    private void draw() {
        worldRenderer.render();
    }

    private void update() {
        world.update();
    }

    @Override
    public void render(float delta) {
        update();
        draw();
    }
}
