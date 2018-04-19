package com.mobilesmashers.ShapeDrawing;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mobilesmashers.HelpClasses.Point;

public class Rect extends Shape {

	public static Texture newTexture(Point size, Color color) {
		Pixmap pixmap = new Pixmap(
				size.x,
				size.y,
				Pixmap.Format.RGBA8888
		);
		pixmap.setColor(color);
		pixmap.fillRectangle(0, 0, size.x, size.y);

		Texture texture = new Texture(pixmap);
		textures.add(texture);

		pixmap.dispose();

		return texture;
	}

	protected Point size;
	protected Texture texture;

	public Rect(Point beginPoint, Point size, Texture texture) {
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
