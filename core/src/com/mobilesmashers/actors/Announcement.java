package com.mobilesmashers.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mobilesmashers.utils.Constants;

public class Announcement extends Actor {
	/* Constants */
	Vector2 POSITION_PX = new Vector2(Constants.VIEWPORT_WIDTH / 2f - 150f, Constants.VIEWPORT_HEIGHT / 1.15f);

	/* Fields */
	private float
			time,
			time_length;
	private Text text;

	public Announcement() {
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		if(text.isVisible())
			text.draw(batch, parentAlpha);
	}

	@Override
	public void act(float delta) {
		if(time < time_length)
			time += delta;
		else
			text.setVisible(false);
	}

	public void make(float time_length, String text) {
		this.text = new Text(POSITION_PX.x, POSITION_PX.y, text, Constants.TEXT_COLOR, 10f);
		this.time_length = time_length;

		time = 0f;
	}

	public Text getText() {
		return text;
	}
}
