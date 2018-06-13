package com.mobilesmashers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.mobilesmashers.screens.GameScreen;
import com.mobilesmashers.screens.MenuScreen;
import com.mobilesmashers.utils.AudioUtils;
import com.mobilesmashers.utils.Constants;
import com.mobilesmashers.utils.TextureUtils;

import static com.mobilesmashers.utils.Constants.PLAYER_SIZE;
import static com.mobilesmashers.utils.Constants.ROPE_THICKNESS_PX;
import static com.mobilesmashers.utils.TextureUtils.createDisk;
import static com.mobilesmashers.utils.TextureUtils.createRect;
import static com.mobilesmashers.utils.WorldUtils.met_to_pix;

public class MobileSmashers extends Game {

	private Screen menuScreen;
	private Screen infoScreen;
	private Screen gameScreen;

	@Override
	public void create() {
		AudioUtils.load(
				Constants.MUSIC_CATCH_KEY,
				Constants.MUSIC_EXPLO_KEY,
				Constants.MUSIC_LSHOT_KEY,
				Constants.MUSIC_SHOOT_KEY
		);

		TextureUtils.load(
				Constants.TEXTURE_BACK_KEY,
				Constants.TEXTURE_GOVR_KEY,
				Constants.TEXTURE_INFO_KEY,
				Constants.TEXTURE_LOGO_KEY,
				Constants.TEXTURE_MENU_KEY,
				Constants.TEXTURE_PLAY_KEY,
				Constants.TEXTURE_SNON_KEY,
				Constants.TEXTURE_SOFF_KEY
		);

		createGameAssets();

		menuScreen = new MenuScreen(this);
		setScreen(menuScreen);
	}

	@Override
	public void dispose() {
		super.dispose();

		menuScreen.dispose();
		if (gameScreen != null)
			gameScreen.dispose();

		AudioUtils.dispose();
		TextureUtils.dispose();
	}

	public void back() {
		if (screen == gameScreen || screen == infoScreen)
			setScreen(menuScreen);
		else
			Gdx.app.exit();
	}

	/*
	public void goInfo() {
		infoScreen = (infoScreen == null) ? (new InfoScreen(this)) : infoScreen;
		setScreen(infoScreen);
	}
	*/

	public void startGame() {
		gameScreen = new GameScreen(this);
		setScreen(gameScreen);
	}

	private void createGameAssets() {
		createDisk(Constants.TEXTURE_BALL_KEY, (int) met_to_pix(Constants.BALL_RADIUS), Color.BLUE);
		createDisk(Constants.TEXTURE_EXPL_KEY, (int) met_to_pix(Constants.EXPLOSION_RADIUS), Color.GOLD);
		createDisk(Constants.TEXTURE_HOOK_KEY, (int) met_to_pix(Constants.HOOK_RADIUS), Color.GREEN);
		createRect(Constants.TEXTURE_PLER_KEY, (int) met_to_pix(PLAYER_SIZE.x), (int) met_to_pix(PLAYER_SIZE.y), Color.RED);
		createRect(Constants.TEXTURE_ROPE_KEY, ROPE_THICKNESS_PX, ROPE_THICKNESS_PX, Color.GREEN);
		createRect(Constants.TEXTURE_WALL_KEY, 10, 10, Color.YELLOW);
	}
}
