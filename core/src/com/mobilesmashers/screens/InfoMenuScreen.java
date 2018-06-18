package com.mobilesmashers.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mobilesmashers.MobileSmashers;
import com.mobilesmashers.utils.Constants;
import com.mobilesmashers.utils.Dimensions;
import com.mobilesmashers.utils.TextureUtils;

import static com.mobilesmashers.utils.Constants.TEXTURE_ADDITION_KEY;
import static com.mobilesmashers.utils.Constants.TEXTURE_BACK_KEY;
import static com.mobilesmashers.utils.Constants.TEXTURE_INFO_KEY;
import static com.mobilesmashers.utils.Constants.TEXTURE_PARITY_KEY;
import static com.mobilesmashers.utils.Constants.TEXTURE_TILT_KEY;

public class InfoMenuScreen extends MenuScreen {

	MobileSmashers game;

	public InfoMenuScreen(MobileSmashers game) {
		super(game);
		createDrawables();
	}

	private void createDrawables() {
		drawables.add(new Drawable(TextureUtils.get(TEXTURE_BACK_KEY),
				0, 0, Constants.APP_WIDTH, Constants.APP_HEIGHT));

		drawables.add(new Drawable(TextureUtils.get(TEXTURE_INFO_KEY),
				Dimensions.infoBegX, Dimensions.logoBegY + 100,
				Dimensions.infoHeight, Dimensions.infoWidth));

		//drawables.add(new Drawable(TextureUtils.get(TEXTURE_ADDITION_KEY),

		drawables.add(new Drawable("pictures/addition.png",
				Dimensions.additionBegX, Dimensions.additionBegY,
				Dimensions.additionHeight, Dimensions.additionWidth));

		//drawables.add(new Drawable(TextureUtils.get(TEXTURE_PARITY_KEY),
		//		Dimensions.parityBegX, Dimensions.parityBegY,
		//		Dimensions.parityHeight, Dimensions.parityWidth));

	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Input.Keys.BACK)
			super.game.setScreen(new MainMenuScreen(super.game));
		return false;
	}
}
