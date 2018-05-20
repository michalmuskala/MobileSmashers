package com.mobilesmashers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.mobilesmashers.HelpClasses.Dimensions;
import com.mobilesmashers.HelpClasses.Point;
import com.mobilesmashers.HelpClasses.Strings;
import com.mobilesmashers.ShapeDrawing.Disk;
import com.mobilesmashers.ShapeDrawing.Text;

import static com.badlogic.gdx.graphics.Color.BLUE;
import static com.badlogic.gdx.graphics.Color.ORANGE;

public class InfoScreen extends ScreenAdapter implements InputProcessor {
    private final Texture background, info, tiltPhone;
    private final Texture prime, even, tenth;
    private MobileSmashers game;
    private int page;

    public InfoScreen(MobileSmashers game) {
        background = new Texture("pictures/background.png");
        info = new Texture("pictures/info.png");
        tiltPhone = new Texture("pictures/info/tiltPhone.png");
        prime = new Texture("pictures/info/prime.png");
        even = new Texture("pictures/info/even.png");
        tenth = new Texture("pictures/info/10th.png");

        this.game = game;
        page = 0;
    }

    @Override
    public void render(float delta) {
        Gdx.input.setInputProcessor(this);
        game.batch.begin();
        game.batch.draw(background, 0, 0, Board.WIDTH, Board.HEIGHT);
        GlyphLayout layout = new GlyphLayout();
        layout.setText(Text.getBitmapFont(), Strings.MOVING_PLAYER_INFO);
        int width = (int) layout.width;
        int height = (int) layout.height;
        switch (page) {
            case 0:
                game.batch.draw(info, Dimensions.infoBegX, Dimensions.logoBegY + 100, Dimensions.infoHeight, Dimensions.infoWidth);

                Text movingPlayerInfo = new Text(new Point(Dimensions.movingPlayerInfoBegX - width / 2, Dimensions.movingPlayerInfoBegY), Strings.MOVING_PLAYER_INFO, ORANGE, 3);
                movingPlayerInfo.draw(game.batch);
                game.batch.draw(tiltPhone, width, Dimensions.logoBegY - 60, 120, 120);
                Text goalInfo = new Text(new Point(Dimensions.movingPlayerInfoBegX - width / 2, Dimensions.movingPlayerInfoBegY - height - 40), Strings.GOAL_INFO, ORANGE, 3);
                goalInfo.draw(game.batch);
                Text catchInfo = new Text(new Point(Dimensions.movingPlayerInfoBegX - width / 2, Dimensions.movingPlayerInfoBegY - height - 100), Strings.CATCH_INFO, ORANGE, 3);
                catchInfo.draw(game.batch);

                game.batch.draw(prime, Dimensions.primeBegX, Dimensions.primeBegY, Dimensions.primeWidth, Dimensions.primeHeight);
                game.batch.draw(even, Dimensions.evenBegX, Dimensions.evenBegY, Dimensions.evenWidth, Dimensions.evenHeight);
                game.batch.draw(tenth, Dimensions.tenthBegX, Dimensions.tenthBegY, Dimensions.tenthWidth, Dimensions.tenthHeight);
                break;
            case 1:
                game.batch.draw(prime, Dimensions.infoBegX, Dimensions.logoBegY + 100, Dimensions.infoHeight, Dimensions.infoWidth);
                createInfos(width, height, Strings.PRIME_INFO_ONE, Strings.PRIME_INFO_TWO);
                createBalls("3", "57", "97");
                break;
            case 2:
                game.batch.draw(even, Dimensions.infoBegX, Dimensions.logoBegY + 100, Dimensions.infoHeight, Dimensions.infoWidth);
                createInfos(width, height, Strings.EVEN_INFO_ONE, Strings.EVEN_INFO_TWO);
                createBalls("0", "4", "8");
                break;
            case 3:
                game.batch.draw(tenth, Dimensions.infoBegX, Dimensions.logoBegY + 100, Dimensions.infoHeight, Dimensions.infoWidth);
                createInfos(width, height, Strings.TENTH_INFO_ONE, Strings.TENTH_INFO_TWO);
                createBalls("10", "40", "90");
                break;
            default:
                break;
        }
        game.batch.end();
    }

    private void createInfos(int widthText, int heightText, String firstInfo, String secondInfo) {
        Text firstInfoText = new Text(new Point(Dimensions.movingPlayerInfoBegX - widthText / 2, Dimensions.movingPlayerInfoBegY), firstInfo, ORANGE, 3);
        firstInfoText.draw(game.batch);
        Text secondInfoText = new Text(new Point(Dimensions.movingPlayerInfoBegX - widthText / 2, Dimensions.movingPlayerInfoBegY - heightText - 40), secondInfo, ORANGE, 3);
        secondInfoText.draw(game.batch);
    }

    private void createBalls(String first, String second, String third) {
        Disk firstDisk = new Disk(new Point(Board.WIDTH / 4, Board.HEIGHT / 4), 100, Disk.newTexture(100, ORANGE));
        firstDisk.draw(game.batch);
        Text firstDiskNumber = new Text(new Point(Board.WIDTH / 4 - 15, Board.HEIGHT / 4 + 15), first, BLUE, 3);
        firstDiskNumber.draw(game.batch);

        Disk secondDisk = new Disk(new Point(2 * Board.WIDTH / 4, Board.HEIGHT / 4), 100, Disk.newTexture(100, ORANGE));
        secondDisk.draw(game.batch);
        Text secondDiskNumber = new Text(new Point(2 * Board.WIDTH / 4 - 15, Board.HEIGHT / 4 + 15), second, BLUE, 3);
        secondDiskNumber.draw(game.batch);

        Disk thirdDisk = new Disk(new Point(3 * Board.WIDTH / 4, Board.HEIGHT / 4), 100, Disk.newTexture(100, ORANGE));
        thirdDisk.draw(game.batch);
        Text thirdDiskNumber = new Text(new Point(3 * Board.WIDTH / 4 - 15, Board.HEIGHT / 4 + 15), third, BLUE, 3);
        thirdDiskNumber.draw(game.batch);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.BACK) {
            if (page > 0) {
                page = 0;
            } else {
                game.setMainMenuScreen();
            }
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
        Rectangle primeRectangle = new Rectangle(Dimensions.primeBegX, Dimensions.primeBegY, Dimensions.primeWidth, Dimensions.primeHeight);
        Rectangle evenRectangle = new Rectangle(Dimensions.evenBegX, Dimensions.evenBegY, Dimensions.evenWidth, Dimensions.evenHeight);
        Rectangle tenthRectangle = new Rectangle(Dimensions.tenthBegX, Dimensions.tenthBegY, Dimensions.tenthWidth, Dimensions.tenthHeight);

        int touchX = (Board.HEIGHT - Gdx.input.getY());
        int touchY = Gdx.input.getX();

        //touched prime button
        if (primeRectangle.contains(touchY, touchX)) {
            page = 1;
        }
        //touched even
        if (evenRectangle.contains(touchY, touchX)) {
            page = 2;
        }
        //touched tenth
        if (tenthRectangle.contains(touchY, touchX)) {
            page = 3;
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
