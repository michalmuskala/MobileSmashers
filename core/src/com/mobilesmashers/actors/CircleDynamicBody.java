package com.mobilesmashers.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import static com.mobilesmashers.utils.WorldUtils.met_to_pix;
import static com.mobilesmashers.utils.WorldUtils.newBodyDef;

public class CircleDynamicBody extends GameBody {

	static private BodyDef bodyDef;
	static private CircleShape shape;

	static {
		bodyDef = newBodyDef(BodyDef.BodyType.DynamicBody);
	}

	public static void init_class() {
		shape = new CircleShape();
	}

	public static void dispose_class() {
		shape.dispose();
	}

	private float radiusPx;

	public CircleDynamicBody(World world, FixtureDef fixtureDef, float x, float y, float radius, Texture texture) {
		super(createBody(world, fixtureDef, x, y, radius), texture);

		this.radiusPx = met_to_pix(radius);
		float diameterPx = 2 * radiusPx;
		setSize(diameterPx, diameterPx);
	}

	public static Body createBody(World world, FixtureDef fixtureDef, float x, float y, float radius) {
		bodyDef.position.set(x, y);
		shape.setRadius(radius);
		fixtureDef.shape = shape;

		Body body = world.createBody(bodyDef);
		body.createFixture(fixtureDef);

		return body;
	}

	@Override
	public void act(float delta) {
		Vector2 position = body.getPosition();
		setPosition(met_to_pix(position.x) - radiusPx, met_to_pix(position.y) - radiusPx);
	}
}
