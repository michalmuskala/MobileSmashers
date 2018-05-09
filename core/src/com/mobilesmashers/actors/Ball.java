package com.mobilesmashers.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mobilesmashers.utils.Constants;

public class Ball extends GameActor {

	public Ball(Vector2 position, Vector2 size, Vector2 speed, Texture texture) {
		super(position, size, speed, texture);
	}

	@Override
	public void act(float delta) {
		if (Constants.STAGE_BOUND_LEFT > getX() || getX() + getWidth() > Constants.STAGE_BOUND_RIGHT)
			speed.x = -speed.x;
		if (Constants.STAGE_BOUND_BOTTOM > getY() || getY() + getHeight() > Constants.STAGE_BOUND_TOP)
			speed.y = -speed.y;
		super.act(delta);
	}
}
