package com.mobilesmashers.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mobilesmashers.utils.Constants;

import static com.mobilesmashers.utils.WorldUtils.newFixtureDef;

public class Ball extends CircleDynamicBody {

	private static FixtureDef fixtureDef;

	static {
		fixtureDef = newFixtureDef(
				Constants.BALL_DENSITY,
				Constants.BALL_FRICTION,
				Constants.BALL_RESTITUTION
		);
	}

	public Ball(World world, float x, float y, float radius, Texture texture) {
		super(world, fixtureDef, x, y, radius, texture);
		body.setUserData(this);
	}
}
