package com.mobilesmashers.utils.wywalic;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

public abstract class Shape {

	protected Vector2 position;

	protected Shape(Vector2 position) {
		this.position = position;
	}

	public abstract void draw(Batch batch);
}
