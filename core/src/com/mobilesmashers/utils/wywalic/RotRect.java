package com.mobilesmashers.utils.wywalic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

public class RotRect extends Rect {

	protected Vector2 transformOrigin;
	protected Vector2 scale;
	protected float angle;

	public RotRect(Vector2 beginPoint, Vector2 size, Texture texture) {
		this(
			beginPoint,
			size,
			new Vector2(beginPoint.x + size.x / 2, beginPoint.y + size.y / 2),
			new Vector2(1.f, 1.f),
			0,
			texture
		);
	}

	public RotRect(Vector2 beginPoint, Vector2 size, Vector2 transformOrigin, Vector2 scale, float angle, Texture texture) {
		super(beginPoint, size, texture);

		this.transformOrigin = transformOrigin;
		this.scale = scale;
		this.angle = angle;
	}

	@Override
	public void draw(Batch batch) {
		batch.draw(
			texture,
			position.x,
			position.y,
			transformOrigin.x,
			transformOrigin.y,
			size.x,
			size.y,
			scale.x,
			scale.y,
			angle,
			0,
			0,
			(int) size.x,
			(int) size.y,
			false,
			false
		);
	}

	public void incAngle(float n) {
		angle += n;
	}
}
