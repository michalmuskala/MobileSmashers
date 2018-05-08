package com.mobilesmashers.ShapeDrawing;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mobilesmashers.HelpClasses.Point;
import com.mobilesmashers.HelpClasses.Pointf;

public class RotRect extends Rect {

	protected Point transformOrigin;
	protected Pointf scale;
	protected float angle;

	public RotRect(Point beginPoint, Point size, Texture texture) {
		this(
				beginPoint,
				size,
				new Point(beginPoint.x + size.x / 2, beginPoint.y + size.y / 2),
				new Pointf(1.f, 1.f),
				0,
				texture
		);
	}
	public RotRect(Point beginPoint, Point size, Point transformOrigin, Pointf scale, float angle, Texture texture) {
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
				size.x,
				size.y,
				false,
				false
		);
	}

	public void incAngle(float n) {
		angle += n;
	}
}
