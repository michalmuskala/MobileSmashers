package com.mobilesmashers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.mobilesmashers.HelpClasses.Dimensions;
import com.mobilesmashers.HelpClasses.Point;
import com.mobilesmashers.HelpClasses.Strings;
import com.mobilesmashers.ShapeDrawing.Text;

import static com.badlogic.gdx.graphics.Color.ORANGE;

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
    }

    @Override
    public void render(float delta) {
        Gdx.input.setInputProcessor(this);
        game.batch.begin();

        game.batch.draw(background, 0, 0, Board.WIDTH, Board.HEIGHT);
        game.batch.draw(logo, Dimensions.logoBegX, Dimensions.logoBegY, Dimensions.logoHeight, Dimensions.logoWidth);
        game.batch.draw(play, Dimensions.playBegX, Dimensions.playBegY, Dimensions.playHeight, Dimensions.playWidth);
        game.batch.draw(info, Dimensions.infoBegX, Dimensions.infoBegY, Dimensions.infoHeight, Dimensions.infoWidth);
        game.batch.draw(volumeLower, Dimensions.volumeLowerBegX, Dimensions.volumeLowerBegY, Dimensions.volumeLowerHeight, Dimensions.volumeLowerWidth);
        Text volumeInfo = new Text(new Point((int)(4.6*Board.WIDTH /6), (Board.HEIGHT)/ 6), String.valueOf(Sound.getVolume() * 10) + "%", ORANGE, 3);
        volumeInfo.draw(game.batch);
        game.batch.draw(volumeHigher, Dimensions.volumeHigherBegX, Dimensions.volumeHigherBegY, Dimensions.volumeHigherHeight, Dimensions.volumeHigherWidth);

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
        Rectangle playRectangle = new Rectangle(Dimensions.playBegX, Dimensions.playBegY, Dimensions.playHeight, Dimensions.playWidth);
        int touchX = (Board.HEIGHT - Gdx.input.getY());
        int touchY = Gdx.input.getX();

        //touched play button
        if (playRectangle.contains(touchY, touchX)) {
            Board.level = 1;
            game.setScreen(new GameScreen(game));
        }
        //touched info button
        Rectangle infoRectangle = new Rectangle(Dimensions.infoBegX, Dimensions.infoBegY, Dimensions.infoHeight, Dimensions.infoWidth);
        if (infoRectangle.contains(touchY, touchX)) {
            game.setScreen(new InfoScreen(game));
        }

        //touched lowerVolume
        Rectangle lowerVolumeRectangle = new Rectangle(Dimensions.volumeLowerBegX, Dimensions.volumeLowerBegY, Dimensions.volumeLowerHeight, Dimensions.volumeLowerWidth);
        if (lowerVolumeRectangle.contains(touchY, touchX)) {
            com.mobilesmashers.Sound.lowerVolume();
        }
        //touched higherVolume
        Rectangle higherVolumeRectangle = new Rectangle(Dimensions.volumeHigherBegX, Dimensions.volumeHigherBegY, Dimensions.volumeHigherHeight, Dimensions.volumeHigherWidth);
        if (higherVolumeRectangle.contains(touchY, touchX)) {
            com.mobilesmashers.Sound.increaseVolume();
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

