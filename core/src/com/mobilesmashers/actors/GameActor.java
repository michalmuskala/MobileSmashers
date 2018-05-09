package com.mobilesmashers.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class GameActor extends Actor {

	protected Vector2 speed;
	protected Texture texture;

	public GameActor(Vector2 position, Vector2 size, Vector2 speed, Texture texture) {
		setPosition(position.x, position.y);
		setSize(size.x, size.y);
		this.speed = speed;
		this.texture = texture;
	}

	@Override
	public void act(float delta) {
		moveBy(speed.x, speed.y);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(
				texture,
				getX(), getY(),
				getWidth(), getHeight()
		);
	}

	public Vector2 getSpeed() {
		return speed;
	}

	public Vector2 getMiddlePosition() {
		return new Vector2(getX() + getWidth() / 2, getY() + getHeight() / 2);
	}
}
