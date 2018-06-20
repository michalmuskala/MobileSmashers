package com.mobilesmashers.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.mobilesmashers.utils.WorldUtils.met_to_pix;

public class GameActor extends Actor {

	protected Texture texture;

	public GameActor(Texture texture) {
		this(0, 0, 0, 0, texture);
	}

	public GameActor(float width, float height, Texture texture) {
		this(0, 0, width, height, texture);
	}

	public GameActor(float x, float y, float width, float height, Texture texture) {
		this.texture = texture;
		setName(toString());
		setPosition(met_to_pix(x), met_to_pix(y));
		setSize(met_to_pix(width), met_to_pix(height));
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(texture, getX(), getY(), getWidth(), getHeight());
	}
}
