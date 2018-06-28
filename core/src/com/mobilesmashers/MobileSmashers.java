package com.mobilesmashers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.mobilesmashers.screens.AuthorsMenuScreen;
import com.mobilesmashers.screens.GameScreen;
import com.mobilesmashers.screens.InfoMenuScreen;
import com.mobilesmashers.screens.MainMenuScreen;
import com.mobilesmashers.utils.AudioUtils;
import com.mobilesmashers.utils.Constants;
import com.mobilesmashers.utils.TextureUtils;

import static com.mobilesmashers.utils.Constants.PLAYER_SIZE;
import static com.mobilesmashers.utils.Constants.ROPE_THICKNESS_PX;
import static com.mobilesmashers.utils.TextureUtils.createDisk;
import static com.mobilesmashers.utils.TextureUtils.createRect;
import static com.mobilesmashers.utils.WorldUtils.met_to_pix;

public class MobileSmashers extends Game {

	@Override
	public void create() {
		AudioUtils.load(Constants.AUDIO_PATHS);
		TextureUtils.load(Constants.TEXTURE_PATHS);

		createGameAssets();

		setScreen(new MainMenuScreen(this));
	}

	@Override
	public void dispose() {
		super.dispose();
		screen.dispose();

		AudioUtils.dispose();
		TextureUtils.dispose();
	}

	public void start() {
		screen.dispose();
		setScreen(new GameScreen(this));
	}

	public void info() {
		screen.dispose();
		setScreen(new AuthorsMenuScreen(this));
	}

	public void back() {
		if (screen.getClass() == MainMenuScreen.class) {
			Gdx.app.exit();
		} else {
			screen.dispose();
			setScreen(new MainMenuScreen(this));
		}
	}

	private void createGameAssets() {
		createDisk(Constants.TEXTURE_BALL_KEY, (int) met_to_pix(Constants.BALL_RADIUS), Constants.BALL_COLOR);
		createDisk(Constants.TEXTURE_EXPL_KEY, (int) met_to_pix(Constants.EXPLOSION_RADIUS), Constants.EXPLOSION_COLOR);
		createDisk(Constants.TEXTURE_HOOK_KEY, (int) met_to_pix(Constants.HOOK_RADIUS), Constants.HOOK_COLOR);
		createRect(Constants.TEXTURE_PLER_KEY, (int) met_to_pix(PLAYER_SIZE.x), (int) met_to_pix(PLAYER_SIZE.y), Constants.PLAYER_COLOR);
		createRect(Constants.TEXTURE_ROPE_KEY, ROPE_THICKNESS_PX, ROPE_THICKNESS_PX, Constants.ROPE_COLOR);
		createRect(Constants.TEXTURE_WALL_KEY, 10, 10, Constants.WALL_COLOR);
	}
}
