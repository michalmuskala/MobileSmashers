package com.mobilesmashers.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class ShapeDrawing {

	public static Texture disk(int radius, Color color) {
		int dRadius = 2 * radius;

		Pixmap pixmap = new Pixmap(
				dRadius,
				dRadius,
				Pixmap.Format.RGBA8888
		);
		pixmap.setColor(color);
		pixmap.fillCircle(radius, radius, radius);

		Texture texture = new Texture(pixmap);

		pixmap.dispose();

		return texture;
	}

	public static Texture rect(Vector2 size, Color color) {
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

	private ShapeDrawing() {}
}
