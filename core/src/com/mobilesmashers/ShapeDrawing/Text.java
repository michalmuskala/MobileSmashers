package com.mobilesmashers.ShapeDrawing;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.mobilesmashers.HelpClasses.Point;

import static com.badlogic.gdx.graphics.Texture.TextureFilter.Linear;

public class Text extends Shape {

	protected BitmapFont bitmapFont;
	protected String text;

	public Text(Point position, String text, Color color, float scale) {
		super(position);

		this.text = text;

		bitmapFont = new BitmapFont();
		bitmapFont.setColor(color);
		bitmapFont.getRegion().getTexture().setFilter(Linear, Linear);
		bitmapFont.getData().setScale(scale);
	}

	public void draw(Batch batch) {
		bitmapFont.draw(batch, text, position.x, position.y);
	}
}
