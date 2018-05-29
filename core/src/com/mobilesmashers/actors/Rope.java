package com.mobilesmashers.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.mobilesmashers.utils.Geo.vectorAngle;

public class Rope extends GameActor {

	private float thickness;

	private Actor
			head,
			tail;

	public Rope(float thickness, Texture texture) {
		this(thickness, null, null, texture);
	}

	public Rope(float thickness, Actor head, Actor tail, Texture texture) {
		super(texture);

		this.thickness = thickness;
		this.head = head;
		this.tail = tail;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (head == null || tail == null)
			return;

		float
				headX = head.getX() + head.getWidth() / 2f,
				headY = head.getY() + head.getHeight() / 2f,
				tailX = tail.getX() + tail.getWidth() / 2f,
				tailY = tail.getY() + tail.getHeight() / 2f,
				len = Vector2.dst(headX, headY, tailX, tailY);

		batch.draw(
				texture,
				headX, headY,
				0, 0,// transform origin x, y (rel to origin)
				len, thickness,      // size x, y
				1, 1,  // scale x, y
				vectorAngle(headX, headY, tailX, tailY),
				0, 0,
				(int) len, (int) thickness,
				false, false
		);
	}

	public void setHead(Actor head) {
		this.head = head;
	}

	public void setTail(Actor tail) {
		this.tail = tail;
	}
}
