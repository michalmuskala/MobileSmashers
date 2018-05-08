package com.mobilesmashers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;

public class GameScreen extends ScreenAdapter implements InputProcessor {

    private WorldRenderer worldRenderer;
    private MobileSmashers game;
    private World world;

    public GameScreen(MobileSmashers game) {
        this.game = game;
        world = new World(game);
        worldRenderer = new WorldRenderer(game.batch, world);
        Gdx.input.setInputProcessor(this);
    }

    private void draw() {
        worldRenderer.render();
    }

    private void update() {
        world.update();
    }

    @Override
    public void render(float delta) {
        Gdx.input.setInputProcessor(this);
        update();
        draw();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.BACK) {
            game.setScreen(new MainMenu(game));
            Gdx.input.setInputProcessor(null);
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
