package com.mobilesmashers.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mobilesmashers.utils.Constants;

import static com.mobilesmashers.utils.World.met_to_pix;
import static com.mobilesmashers.utils.World.newBodyDef;
import static com.mobilesmashers.utils.World.newFixtureDef;

public class Hook extends GameActor {
	// TODO: consider fusing this with ball class

	static private BodyDef bodyDef;
	static private CircleShape shape;
	static private FixtureDef fixtureDef;

	static {
		bodyDef = newBodyDef(BodyDef.BodyType.DynamicBody);
		fixtureDef = newFixtureDef(
				Constants.HOOK_DENSITY,
				Constants.HOOK_FRICTION,
				Constants.HOOK_RESTITUTION
		);
	}

	public static void init_class() {
		shape = new CircleShape();
		fixtureDef.shape = shape;
	}

	public static void dispose_class() {
		shape.dispose();
	}

	private float
			radiusPx,
			radius;

	private Body body;

	public Hook(float radius, Texture texture) { // TODO: consider removing me
		super(texture);
		this.radius = radius;

		setVisible(false);
	}

	public Hook(World world, float x, float y, float radius, Texture texture) {
		super(texture);
		this.radius = radius;
		setBody(world, x, y);
	}

	@Override
	public void act(float delta) {
		if (body != null) {
			Vector2 position = body.getPosition();
			setPosition(met_to_pix(position.x), met_to_pix(position.y) - radiusPx);
		}
	}

	public void setBody(World world, float x, float y) {
		this.radiusPx = met_to_pix(radius);
		float diameterPx = 2 * radiusPx;
		setSize(diameterPx, diameterPx);
		setVisible(true);

		bodyDef.position.set(x, y);
		shape.setRadius(radius);

		body = world.createBody(bodyDef);
		body.createFixture(fixtureDef);
		body.setUserData(this);
	}

	public void setLinearVelocity(float vx, float vy) {
		if(body == null)
			throw new RuntimeException("Hook has no body!");
		body.setLinearVelocity(vx, vy);
	}

	public void applyLinearImpulse(float impulseX, float impulseY) {
		if(body == null)
			throw new RuntimeException("Hook has no body!");
		Vector2 pos = body.getPosition();
		body.applyLinearImpulse(impulseX, impulseY, pos.x, pos.y, true);
	}

	// TODO: class for exceptions
	// TODO: unify ball and hook
}
