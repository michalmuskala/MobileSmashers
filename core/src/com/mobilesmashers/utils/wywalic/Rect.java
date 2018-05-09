package com.mobilesmashers.utils.wywalic;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

public class Rect extends Shape {

	public static Texture newTexture(Vector2 size, Color color) {
		Pixmap pixmap = new Pixmap(
			(int) size.x,
			(int) size.y,
			Pixmap.Format.RGBA8888
		);
		pixmap.setColor(color);
		pixmap.fillRectangle(0, 0, (int) size.x, (int) size.y);

		Texture texture = new Texture(pixmap);

		pixmap.dispose();

		return texture;
	}

	protected Vector2 size;
	protected Texture texture;

	public Rect(Vector2 beginPoint, Vector2 size, Texture texture) {
		super(beginPoint);
		this.size = size;
		this.texture = texture;
	}

	public void draw(Batch batch) {
		batch.draw(
			texture,
			position.x,
			position.y,
			size.x,
			size.y
		);
	}
}
