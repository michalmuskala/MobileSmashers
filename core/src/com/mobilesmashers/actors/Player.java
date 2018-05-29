package com.mobilesmashers.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.mobilesmashers.utils.Constants;

import static com.mobilesmashers.utils.World.met_to_pix;
import static com.mobilesmashers.utils.World.newBodyDef;
import static com.mobilesmashers.utils.World.newFixtureDef;

public class Player extends GameActor {

	private static BodyDef bodyDef;
	private static PolygonShape shape;
	private static FixtureDef fixtureDef;

	static {
		bodyDef = newBodyDef(BodyDef.BodyType.DynamicBody);
		fixtureDef = newFixtureDef(
				Constants.PLAYER_DENSITY,
				Constants.PLAYER_FRICTION,
				Constants.PLAYER_RESTITUTION
		);
	}

	public static void init_class() {
		shape = new PolygonShape();
		fixtureDef.shape = shape;
	}

	public static void dispose_class() {
		shape.dispose();
	}

	public int ropeNumber;
	public Rope rope;
	private Body body;

	public Player(World world, float x, float y, float width, float height, Texture texture) {
		super(texture);

		setSize(met_to_pix(width), met_to_pix(height));

		ropeNumber = Constants.PLAYER_ROPE_CAPACITY;

		Vector2 playerCenter = new Vector2(width / 2f, height / 2f);
		bodyDef.position.set(x, y);
		shape.setAsBox(playerCenter.x, playerCenter.y, playerCenter, 0f);

		body = world.createBody(bodyDef);
		body.createFixture(fixtureDef);
		body.setUserData(this);
	}

	@Override
	public void act(float delta) {
		float
				vx = Gdx.input.getAccelerometerY(),
				vy = -Gdx.input.getAccelerometerX();
		Vector2
				pos = body.getPosition();

		setX(met_to_pix(pos.x));
		setY(met_to_pix(pos.y));

		body.setLinearVelocity(vx, vy);
	}

	public Vector2 getInWorldPosition() {
		return body.getPosition();
	}
}
