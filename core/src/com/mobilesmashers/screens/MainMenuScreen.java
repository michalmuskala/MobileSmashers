package com.mobilesmashers.screens;

import com.mobilesmashers.MobileSmashers;
import com.mobilesmashers.actors.Text;
import com.mobilesmashers.utils.AudioUtils;
import com.mobilesmashers.utils.Constants;
import com.mobilesmashers.utils.Dimensions;
import com.mobilesmashers.utils.TextureUtils;

import static com.mobilesmashers.utils.Constants.TEXTURE_BACKGROUND_KEY;
import static com.mobilesmashers.utils.Constants.TEXTURE_BACK_KEY;
import static com.mobilesmashers.utils.Constants.TEXTURE_INFO_KEY;
import static com.mobilesmashers.utils.Constants.TEXTURE_LOGO_KEY;
import static com.mobilesmashers.utils.Constants.TEXTURE_PLAY_KEY;
import static com.mobilesmashers.utils.Constants.TEXTURE_SNON_KEY;
import static com.mobilesmashers.utils.Constants.TEXTURE_SOFF_KEY;

public class MainMenuScreen extends MenuScreen {

	protected Text volumeLabel;

	public MainMenuScreen(final MobileSmashers game) {
		super(game);

		createDrawables();
		createButtons();
		setUpButtonActions();
		createTexts();
	}

	private void createDrawables() {
		//drawables.add(new Drawable(TextureUtils.get(TEXTURE_BACK_KEY),
		//		0, 0, Constants.APP_WIDTH, Constants.APP_HEIGHT));
		drawables.add(new Drawable(TextureUtils.get(TEXTURE_BACKGROUND_KEY),
						0, 0, Constants.APP_WIDTH, Constants.APP_HEIGHT));
		drawables.add(new Drawable(TextureUtils.get(TEXTURE_LOGO_KEY),
				Dimensions.logoBegX, Dimensions.logoBegY,
				Dimensions.logoHeight, Dimensions.logoWidth));
	}

	private void createButtons() {
		buttons.add(new Button(
				TextureUtils.get(TEXTURE_PLAY_KEY),
				Dimensions.playBegX, Dimensions.playBegY,
				Dimensions.playHeight, Dimensions.playWidth));
		buttons.add(new Button(
				TextureUtils.get(TEXTURE_INFO_KEY),
				Dimensions.infoBegX, Dimensions.infoBegY,
				Dimensions.infoHeight, Dimensions.infoWidth));
		buttons.add(new Button(
				TextureUtils.get(TEXTURE_SOFF_KEY),
				Dimensions.volumeLowerBegX, Dimensions.volumeLowerBegY,
				Dimensions.volumeLowerHeight, Dimensions.volumeLowerWidth));
		buttons.add(new Button(
				TextureUtils.get(TEXTURE_SNON_KEY),
				Dimensions.volumeHigherBegX, Dimensions.volumeHigherBegY,
				Dimensions.volumeHigherHeight, Dimensions.volumeHigherWidth));

		drawables.addAll(buttons);
	}

	private void createTexts() {
		volumeLabel = new Text(
				4.6f * Constants.APP_WIDTH / 6f,
				Constants.APP_HEIGHT / 6f,
				null,
				Constants.TEXT_COLOR,
				3f
		);
		texts.add(volumeLabel);
		updateVolumeLabel();
	}

	private void setUpButtonActions() {

		buttons.get(0).action = new ButtonAction() {
			@Override
			public void click() {
				game.start();
			}
		};
		buttons.get(1).action = new ButtonAction() {
			@Override
			public void click() {
				game.info();
			}
		};
		buttons.get(2).action = new ButtonAction() {
			@Override
			public void click() {
				AudioUtils.decreaseVolume();
				updateVolumeLabel();
			}
		};
		buttons.get(3).action = new ButtonAction() {
			@Override
			public void click() {
				AudioUtils.increaseVolume();
				updateVolumeLabel();
			}
		};
	}

	private void updateVolumeLabel() {
		volumeLabel.setText(String.format("%1$s%%", AudioUtils.getVolume()));
	}
}
