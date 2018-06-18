package com.mobilesmashers.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mobilesmashers.MobileSmashers;
import com.mobilesmashers.actors.Text;
import com.mobilesmashers.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public abstract class MenuScreen extends ScreenAdapter implements InputProcessor {

	protected final MobileSmashers game;
	protected List<Drawable> drawables;
	protected List<Button> buttons;
	protected List<Text> texts;

	protected Batch batch;

	public MenuScreen(final MobileSmashers game) {
		Gdx.input.setInputProcessor(this);
		this.game = game;
		batch = new SpriteBatch();
		drawables = new ArrayList<Drawable>();
		buttons = new ArrayList<Button>();
		texts = new ArrayList<Text>();
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

		for (Text text : texts)
			text.draw(batch, 1f);

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
