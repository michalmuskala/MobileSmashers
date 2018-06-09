package com.mobilesmashers.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mobilesmashers.utils.Constants;

import static com.mobilesmashers.utils.WorldUtils.newFixtureDef;

public class Hook extends CircleDynamicBody {

	private static FixtureDef fixtureDef;

	private Rope rope;

	static {
		fixtureDef = newFixtureDef(
				Constants.HOOK_DENSITY,
				Constants.HOOK_FRICTION,
				Constants.HOOK_RESTITUTION
		);
	}

	public Hook(World world, float x, float y, float radius, Texture texture) {
		super(world, fixtureDef, x, y, radius, texture);
		body.setUserData(this);
	}

	public Rope getRope() {
		return rope;
	}

	public void setRope(Rope rope) {
		this.rope = rope;
	}
}
