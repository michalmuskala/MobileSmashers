package com.mobilesmashers.utils;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class WorldUtils {

	@Contract(pure = true)
	public static float met_to_pix(float met) {
		return met * Constants.PIX_PER_METER;
	}

	@Contract(pure = true)
	public static float pix_to_met(float pix) {
		return pix / Constants.PIX_PER_METER;
	}

	@Contract(pure = true)
	public static float hookSpeed(float v) {
		return Math.abs(v) < Constants.HOOK_MAX_SPEED ? v : Constants.HOOK_MAX_SPEED;
	}

	@NotNull
	public static Vector2 world_center() {
		return new Vector2(Constants.WORLD_WIDTH / 2, Constants.WORLD_HEIGHT / 2);
	}

	@NotNull
	public static Vector2 world_center(float el_width, float el_height) {
		return new Vector2(
				(Constants.WORLD_WIDTH - el_width) / 2,
				(Constants.WORLD_HEIGHT - el_height) / 2
		);
	}

	public static float vectorAngle(Vector2 beg, Vector2 end) {
		return vectorAngle(beg.x, beg.y, end.x, end.y);
	}

	public static float vectorAngle(float begX, float begY, float endX, float endY) {
		// no idea why this werks
		return MathUtils.atan2(endY - begY, endX - begX);
	}

	@NotNull
	public static BodyDef newBodyDef(BodyDef.BodyType type) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = type;
		return bodyDef;
	}

	@NotNull
	public static FixtureDef newFixtureDef(float density, float friction, float restitution) {
		return newFixtureDef(density, friction, restitution, null);
	}

	@NotNull
	public static FixtureDef newFixtureDef(float density, float friction, float restitution, Shape shape) {
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = density;
		fixtureDef.friction = friction;
		fixtureDef.restitution = restitution;
		fixtureDef.shape = shape;
		return fixtureDef;
	}

	private WorldUtils() {
	}
}
