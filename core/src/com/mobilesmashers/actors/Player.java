package com.mobilesmashers.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mobilesmashers.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class Player extends GameActor {

	private List<Hook> inventory;

	public Player(Vector2 position, Vector2 size, Texture texture) {
		super(position, size, new Vector2(0, 0), texture);
		inventory = new ArrayList<Hook>();
	}

	@Override
	public void act(float delta) {
		speed.x = Gdx.input.getAccelerometerY();
		speed.y = -Gdx.input.getAccelerometerX();
		move();
	}

	public void addToInventory(Hook hook) {
		inventory.add(hook);
	}

	public void shoot(float dirX, float dirY, float force) {
		if (inventory.size() > 0) {
			Hook hook = inventory.get(0);
			inventory.remove(0);

			float
					speedX = (hook.getX() - dirX) * force,
					speedY = (hook.getY() - dirY) * force;

			hook.setVisible(true);
			hook.shoot(speedX, speedY);
		}
	}

	private void move() {
		float
				posX = getX(),
				posY = getY();

		//go left
		if (speed.x < -1 && posX > Constants.STAGE_BOUND_LEFT)
			moveBy(speed.x, 0);
		//go right
		if (speed.x > +1 && posX + getWidth() < Constants.STAGE_BOUND_RIGHT)
			moveBy(speed.x, 0);
		//go down
		if (speed.y < -1 && posY > Constants.STAGE_BOUND_BOTTOM)
			moveBy(0, speed.y);
		//go up
		if (speed.y > +1 && posY + getHeight() < Constants.STAGE_BOUND_TOP)
			moveBy(0, speed.y);
	}
}
