package com.mobilesmashers.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.badlogic.gdx.graphics.Texture.TextureFilter.Linear;

public class Text extends Actor {

	protected BitmapFont bitmapFont;
	protected String text;

	public Text(float x, float y, String text, Color color, float scale) {
		this(text, color, scale);
		setX(x);
		setY(y);
	}

	public Text(String text, Color color, float scale) {
		this.text = text;

		bitmapFont = new BitmapFont();
		bitmapFont.setColor(color);
		bitmapFont.getRegion().getTexture().setFilter(Linear, Linear);
		bitmapFont.getData().setScale(scale);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		bitmapFont.draw(batch, text, getX(), getY());
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
