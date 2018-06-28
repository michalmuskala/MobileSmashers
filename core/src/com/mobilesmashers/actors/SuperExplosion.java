package com.mobilesmashers.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mobilesmashers.utils.Constants;

public class SuperExplosion extends Explosion {

	public SuperExplosion(float delta_radius, Actor exploding, Texture texture) {
		super(
				Math.max(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT),
				delta_radius,
				0,
				exploding,
				null,
				texture
		);
	}

	@Override
	public void act(float delta) {
		float
				diameter = getWidth(),
				radius = diameter / 2f;

		if(expanding) {
			if (radius >= max_radiusPx)
				expanding = false;

			diameter += delta_diameterPx;

			setX(exploding.getX() + exploding.getWidth() / 2f - radius);
			setY(exploding.getY() + exploding.getHeight() / 2f - radius);
		} else
			diameter = -1;

		setSize(diameter, diameter);
	}
}
