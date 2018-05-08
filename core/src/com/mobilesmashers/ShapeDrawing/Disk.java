package com.mobilesmashers.ShapeDrawing;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mobilesmashers.HelpClasses.Point;

public class Disk extends Shape {

	public static Texture newTexture(int radius, Color color) {
		int dradius = 2 * radius;
		Pixmap pixmap = new Pixmap(
				dradius,
				dradius,
				Pixmap.Format.RGBA8888
		);
		pixmap.setColor(color);
		pixmap.fillCircle(radius, radius, radius);

		Texture texture = new Texture(pixmap);
		textures.add(texture);

		pixmap.dispose();

		return texture;
	}

	protected int radius;
	protected Texture texture;

	public Disk(Point position, int radius, Texture texture) {
		super(position);
		this.radius = radius;
		this.texture = texture;
	}

	public void draw(Batch batch) {
		batch.draw(
				texture,
				position.x,
				position.y,
				radius,
				radius
		);
	}
}
