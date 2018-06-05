package com.mobilesmashers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.mobilesmashers.HelpClasses.Dimensions;
import com.mobilesmashers.HelpClasses.Point;
import com.mobilesmashers.ShapeDrawing.Text;

import static com.badlogic.gdx.graphics.Color.ORANGE;

public class GameOverScreen extends ScreenAdapter implements InputProcessor {

    private MobileSmashers game;
    private Texture background, gameOver, menu;

    public GameOverScreen(MobileSmashers game)
    {
        this.game = game;
        background = new Texture("pictures/background.png");
        gameOver = new Texture("pictures/gameOver.png");
        menu = new Texture("pictures/menu.png");
    }

    @Override
    public void render(float delta) {
        Gdx.input.setInputProcessor(this);
        game.batch.begin();
        game.batch.draw(background, 0, 0, Board.WIDTH, Board.HEIGHT);
        game.batch.draw(gameOver, Dimensions.gameOverBegX, Dimensions.gameOverBegY, Dimensions.gameOverHeight, Dimensions.gameOverWidth);
        Text firstInfoText = new Text(new Point(Board.BOTTOM_BOUND/2 - 80, Dimensions.movingPlayerInfoBegY), "Level:" + Board.level, ORANGE, 3);
        firstInfoText.draw(game.batch);
        game.batch.draw(menu, Dimensions.menuBegX, Dimensions.menuBegY, Dimensions.menuHeight, Dimensions.menuWidth);
        game.batch.end();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.BACK) {
            game.setMainMenuScreen();
        }
        return false;
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
        Rectangle menuRectangle = new Rectangle(Dimensions.menuBegX, Dimensions.menuBegY, Dimensions.menuHeight, Dimensions.menuWidth);

        int touchX = (Board.HEIGHT - Gdx.input.getY());
        int touchY = Gdx.input.getX();

        if (menuRectangle.contains(touchY, touchX)) {
            game.setMainMenuScreen();
        }

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
