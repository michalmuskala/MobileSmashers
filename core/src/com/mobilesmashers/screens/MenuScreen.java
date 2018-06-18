package com.mobilesmashers.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mobilesmashers.MobileSmashers;
import com.mobilesmashers.actors.Text;
import com.mobilesmashers.utils.AudioUtils;
import com.mobilesmashers.utils.Constants;
import com.mobilesmashers.utils.Dimensions;
import com.mobilesmashers.utils.TextureUtils;

import java.util.ArrayList;
import java.util.List;

import static com.mobilesmashers.utils.Constants.TEXTURE_BACK_KEY;
import static com.mobilesmashers.utils.Constants.TEXTURE_INFO_KEY;
import static com.mobilesmashers.utils.Constants.TEXTURE_LOGO_KEY;
import static com.mobilesmashers.utils.Constants.TEXTURE_PLAY_KEY;
import static com.mobilesmashers.utils.Constants.TEXTURE_SNON_KEY;
import static com.mobilesmashers.utils.Constants.TEXTURE_SOFF_KEY;

public class MenuScreen extends ScreenAdapter implements InputProcessor {

	private final MobileSmashers game;
	private List<Drawable> drawables;
	private List<Button> buttons;
	private Text volumeLabel;

	private Batch batch;

	public MenuScreen(final MobileSmashers game) {
		Gdx.input.setInputProcessor(this);
		this.game = game;
		batch = new SpriteBatch();

		createDrawables();
		createButtons();
		drawables.addAll(buttons);

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

		volumeLabel = new Text(
				4.6f * Constants.APP_WIDTH / 6f,
				Constants.APP_HEIGHT / 6f,
				null,
				Color.ORANGE,
				3f
		);
		updateVolumeLabel();
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	@Override
	public void render(float delta) {
		batch.begin();

		for (Drawable drawable : drawables)
			drawable.draw(batch);
		volumeLabel.draw(batch, 1f);

		batch.end();
	}

	/* INPUT HANDLING */

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		int screenY_transformed = Constants.APP_HEIGHT - screenY;

		for (Button b : buttons)
			if (b.action != null && b.ifClicked(screenX, screenY_transformed)) {
				b.action.click();
				return true;
			}
		return false;
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Input.Keys.BACK)
			Gdx.app.exit();
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

	private void createDrawables() {
		drawables = new ArrayList<Drawable>();
		drawables.add(new Drawable(TextureUtils.get(TEXTURE_BACK_KEY),
				0, 0, Constants.APP_WIDTH, Constants.APP_HEIGHT));
		drawables.add(new Drawable(TextureUtils.get(TEXTURE_LOGO_KEY),
				Dimensions.logoBegX, Dimensions.logoBegY,
				Dimensions.logoHeight, Dimensions.logoWidth));
	}

	private void createButtons() {
		buttons = new ArrayList<Button>();
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
	}

	private void updateVolumeLabel() {
		volumeLabel.setText(String.format("%1$s%%", AudioUtils.getVolume()));
	}

	protected interface ButtonAction {
		void click();
	}

	protected static class Drawable {
		public Texture t;
		public float posX, posY, height, width;

		public Drawable(Texture t, float posX, float posY, float width, float height) {
			this.t = t;
			this.posX = posX;
			this.posY = posY;
			this.width = width;
			this.height = height;
		}

		public void draw(Batch batch) {
			batch.draw(t, posX, posY, width, height);
		}
	}

	protected static class Button extends Drawable {
		public ButtonAction action;

		private Rectangle rectangle;

		public Button(Texture t, float posX, float posY, float width, float height) {
			super(t, posX, posY, width, height);
			rectangle = new Rectangle(posX, posY, width, height);
		}

		public Boolean ifClicked(float screenX, float screenY) {
			return rectangle.contains(screenX, screenY);
		}
	}
}
