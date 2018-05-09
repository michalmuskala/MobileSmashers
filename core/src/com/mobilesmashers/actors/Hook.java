package com.mobilesmashers.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mobilesmashers.utils.Constants;

public class Hook extends GameActor {

	private GameActor owner;

	public Hook(Vector2 position, Vector2 size, Vector2 speed, Texture texture) {
		super(position, size, speed, texture);
	}

	public Hook(Vector2 size, GameActor owner, Texture texture) {
		super(owner.getMiddlePosition(), size, new Vector2(0, 0), texture);
		this.owner = owner;
	}

	@Override
	public void act(float delta) {
		if (owner == null) {
			if (Constants.STAGE_BOUND_LEFT > getX() || getX() + getWidth() > Constants.STAGE_BOUND_RIGHT)
				speed.x = -speed.x;
			if (Constants.STAGE_BOUND_BOTTOM > getY() || getY() + getHeight() > Constants.STAGE_BOUND_TOP)
				speed.y = -speed.y;
		} else
			setPosition(owner.getX(), owner.getY());

		super.act(delta);
	}

	public void shoot(float speedX, float speedY) {

		if(owner == null)
			return;

		owner = null;
		speed.x = speedX;
		speed.y = speedY;
	}
}
