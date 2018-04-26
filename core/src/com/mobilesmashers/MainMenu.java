package com.mobilesmashers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.mobilesmashers.HelpClasses.MainMenuDimensions;

public class MainMenu extends ScreenAdapter implements InputProcessor {

    private MobileSmashers game;
    private final Texture background, logo, play, info, volumeLower, volumeHigher;

    public MainMenu(MobileSmashers game) {
        this.background = new Texture("pictures/background.png");
        this.logo = new Texture("pictures/logo.png");
        this.play = new Texture("pictures/play.png");
        this.info = new Texture("pictures/info.png");
        this.volumeLower = new Texture("pictures/sounds_off.png");
        this.volumeHigher = new Texture("pictures/sounds_on.png");

        this.game = game;
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        game.batch.begin();

        game.batch.draw(background, 0, 0, Board.WIDTH, Board.HEIGHT);
        game.batch.draw(logo, MainMenuDimensions.logoBegX, MainMenuDimensions.logoBegY, MainMenuDimensions.logoHeight, MainMenuDimensions.logoWidth);
        game.batch.draw(play, MainMenuDimensions.playBegX, MainMenuDimensions.playBegY, MainMenuDimensions.playHeight, MainMenuDimensions.playWidth);
        game.batch.draw(info, MainMenuDimensions.infoBegX, MainMenuDimensions.infoBegY, MainMenuDimensions.infoHeight, MainMenuDimensions.infoWidth);
        game.batch.draw(volumeLower, MainMenuDimensions.volumeLowerBegX, MainMenuDimensions.volumeLowerBegY, MainMenuDimensions.volumeLowerHeight, MainMenuDimensions.volumeLowerWidth);
        // in (5*Board.WIDTH /6, Board.HEIGHT / 20) -> get Sound.volume and show there
        game.batch.draw(volumeHigher, MainMenuDimensions.volumeHigherBegX, MainMenuDimensions.volumeHigherBegY, MainMenuDimensions.volumeHigherHeight, MainMenuDimensions.volumeHigherWidth);

        game.batch.end();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.BACK) {
            Gdx.app.exit();
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
        Rectangle playRectangle = new Rectangle(MainMenuDimensions.playBegX, MainMenuDimensions.playBegY, MainMenuDimensions.playHeight, MainMenuDimensions.playWidth);
        int touchX = (Board.HEIGHT - Gdx.input.getY());
        int touchY = Gdx.input.getX();

        //touched play button
        if (playRectangle.contains(touchY, touchX)) {
            Board.level = 1;
            game.setScreen(new GameScreen(game));
            Gdx.input.setInputProcessor(null);
        }
        //touched info button
        Rectangle infoRectangle = new Rectangle(MainMenuDimensions.infoBegX, MainMenuDimensions.infoBegY, MainMenuDimensions.infoHeight, MainMenuDimensions.infoWidth);
        if (infoRectangle.contains(touchY, touchX)) {
            //show info screen
        }

        //touched lowerVolume
        Rectangle lowerVolumeRectangle = new Rectangle(MainMenuDimensions.volumeLowerBegX, MainMenuDimensions.volumeLowerBegY, MainMenuDimensions.volumeLowerHeight, MainMenuDimensions.volumeLowerWidth);
        if (lowerVolumeRectangle.contains(touchY, touchX)) {
            com.mobilesmashers.Sound.lowerVolume();
            //refresh volume value on screen
        }
        //touched higherVolume
        Rectangle higherVolumeRectangle = new Rectangle(MainMenuDimensions.volumeHigherBegX, MainMenuDimensions.volumeHigherBegY, MainMenuDimensions.volumeHigherHeight, MainMenuDimensions.volumeHigherWidth);
        if (higherVolumeRectangle.contains(touchY, touchX)) {
            com.mobilesmashers.Sound.increaseVolume();
            //refresh volume value on screen
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

