package com.mobilesmashers.screens;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.mobilesmashers.MobileSmashers;
import com.mobilesmashers.actors.Text;
import com.mobilesmashers.utils.Constants;
import com.mobilesmashers.utils.Dimensions;
import com.mobilesmashers.utils.Eng_lang;
import com.mobilesmashers.utils.TextureUtils;

import static com.mobilesmashers.utils.Constants.TEXTURE_ADDITION_KEY;
import static com.mobilesmashers.utils.Constants.TEXTURE_BACK_KEY;
import static com.mobilesmashers.utils.Constants.TEXTURE_INFO_KEY;
import static com.mobilesmashers.utils.Constants.TEXTURE_PARITY_KEY;

public class InfoMenuScreen extends MenuScreen {

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
				Eng_lang.INFO_GOAL_KEY,
				Constants.TEXT_COLOR,
				3);

		GlyphLayout layout = new GlyphLayout();
		layout.setText(foobar.getBitmapFont(), Eng_lang.INFO_PLAYERS_MOVE_KEY);
		int width = (int) layout.width;

		texts.add(new Text(
				Dimensions.movingPlayerInfoBegX + 40 - width / 2,
				Dimensions.movingPlayerInfoBegY,
				Eng_lang.INFO_PLAYERS_MOVE_KEY,
				Constants.TEXT_COLOR,
				3f
		));

		texts.add(new Text(
				Dimensions.movingPlayerInfoBegX + 40 - width / 2,
				Dimensions.movingPlayerInfoBegY - 50,
				Eng_lang.INFO_GOAL_KEY,
				Constants.TEXT_COLOR,
				3f
		));

		texts.add(new Text(
				Dimensions.movingPlayerInfoBegX + 40 - width / 2,
				Dimensions.movingPlayerInfoBegY - 100,
				Eng_lang.INFO_CATCH_KEY,
				Constants.TEXT_COLOR,
				3f
		));
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Input.Keys.BACK)
			game.back();
		return true;
	}
}
