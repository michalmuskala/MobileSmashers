package com.mobilesmashers.screens;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.mobilesmashers.MobileSmashers;
import com.mobilesmashers.actors.Text;
import com.mobilesmashers.utils.Constants;
import com.mobilesmashers.utils.Dimensions;
import com.mobilesmashers.utils.TextureUtils;

import static com.mobilesmashers.utils.Constants.TEXTURE_ADDITION_KEY;
import static com.mobilesmashers.utils.Constants.TEXTURE_BACK_KEY;
import static com.mobilesmashers.utils.Constants.TEXTURE_INFO_KEY;
import static com.mobilesmashers.utils.Constants.TEXTURE_PARITY_KEY;

public class InfoMenuScreen extends MenuScreen {

    private Text playersMove,
            goalInfo,
            catchInfo;

    public InfoMenuScreen(MobileSmashers game) {
        super(game);
        createDrawables();
        createTexts();
    }

    private void createDrawables() {
        drawables.add(new Drawable(TextureUtils.get(TEXTURE_BACK_KEY),
                0, 0, Constants.APP_WIDTH, Constants.APP_HEIGHT));

        drawables.add(new Drawable(TextureUtils.get(TEXTURE_INFO_KEY),
                Dimensions.infoBegX, Dimensions.logoBegY + 100,
                Dimensions.infoHeight, Dimensions.infoWidth));

        drawables.add(new Drawable(TextureUtils.get(TEXTURE_ADDITION_KEY),
                Dimensions.additionBegX, Dimensions.additionBegY,
                Dimensions.additionHeight, Dimensions.additionWidth));

        drawables.add(new Drawable(TextureUtils.get(TEXTURE_PARITY_KEY),
                Dimensions.parityBegX, Dimensions.parityBegY,
                Dimensions.parityHeight, Dimensions.parityWidth));

    }

    private void createTexts() {
        Text foobar = new Text(Dimensions.movingPlayerInfoBegX,
                Dimensions.movingPlayerInfoBegY,
                Constants.INFO_GOAL_KEY,
                Color.ORANGE,
                3);

        GlyphLayout layout = new GlyphLayout();
        layout.setText(foobar.getBitmapFont(), Constants.INFO_PLAYERS_MOVE_KEY);
        int width = (int) layout.width;

        playersMove = new Text(
                Dimensions.movingPlayerInfoBegX + 40 - width / 2,
                Dimensions.movingPlayerInfoBegY,
                Constants.INFO_PLAYERS_MOVE_KEY,
                Color.ORANGE,
                3f
        );
        texts.add(playersMove);

        goalInfo = new Text(
                Dimensions.movingPlayerInfoBegX + 40 - width / 2,
                Dimensions.movingPlayerInfoBegY - 50,
                Constants.INFO_GOAL_KEY,
                Color.ORANGE,
                3f
        );
        texts.add(goalInfo);

        catchInfo = new Text(
                Dimensions.movingPlayerInfoBegX + 40 - width / 2,
                Dimensions.movingPlayerInfoBegY - 100,
                Constants.INFO_CATCH_KEY,
                Color.ORANGE,
                3f
        );
        texts.add(catchInfo);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.BACK)
            super.game.setScreen(new MainMenuScreen(super.game));
        return false;
    }
}
