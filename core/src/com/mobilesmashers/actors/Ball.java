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

public class Ball extends GameActor {

	private static BodyDef bodyDef;
	private static CircleShape shape;
	private static FixtureDef fixtureDef;

	static {
		bodyDef = newBodyDef(BodyDef.BodyType.DynamicBody);
		fixtureDef = newFixtureDef(
				Constants.BALL_DENSITY,
				Constants.BALL_FRICTION,
				Constants.BALL_RESTITUTION
		);
	}

	public static void init_class() {
		shape = new CircleShape();
		fixtureDef.shape = shape;
	}

	public static void dispose_class() {
		shape.dispose();
	}

	private Body body;
	private float radiusPx;

	public Ball(World world, float x, float y, float radius, Texture texture) {
		super(texture);

		this.radiusPx = met_to_pix(radius);
		float diameterPx = 2 * radiusPx;
		setSize(diameterPx, diameterPx);

		bodyDef.position.set(x, y);
		shape.setRadius(radius);

		body = world.createBody(bodyDef);
		body.createFixture(fixtureDef);
		body.setUserData(this);
	}

	@Override
	public void act(float delta) {
		Vector2 position = body.getPosition();
		setPosition(met_to_pix(position.x) - radiusPx, met_to_pix(position.y) - radiusPx);

		//Gdx.app.log("ball_speed", body.getLinearVelocity().x + ", "+body.getLinearVelocity().y);
		//Gdx.app.log("awake", String.valueOf(body.isAwake()));
	}

	public void setLinearVelocity(float vx, float vy) {
		body.setLinearVelocity(vx, vy);
	}

	public void applyLinearImpulse(float impulseX, float impulseY) {
		Vector2 pos = body.getPosition();
		body.applyLinearImpulse(impulseX, impulseY, pos.x, pos.y, true);
	}
}
