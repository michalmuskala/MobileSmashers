package com.mobilesmashers.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mobilesmashers.utils.Constants;

import static com.mobilesmashers.utils.WorldUtils.met_to_pix;
import static com.mobilesmashers.utils.WorldUtils.newBodyDef;
import static com.mobilesmashers.utils.WorldUtils.newFixtureDef;

public class Player extends GameBody {

	private static Body createBody(World world, float x, float y, float width, float height) {

		BodyDef bodyDef = newBodyDef(BodyDef.BodyType.DynamicBody);
		bodyDef.position.set(x, y);

		Vector2 playerCenter = new Vector2(width / 2f, height / 2f);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(playerCenter.x, playerCenter.y, playerCenter, 0f);

		FixtureDef fixtureDef = newFixtureDef(
				Constants.PLAYER_DENSITY,
				Constants.PLAYER_FRICTION,
				Constants.PLAYER_RESTITUTION,
				shape
		);

		Body body = world.createBody(bodyDef);
		body.createFixture(fixtureDef);

		shape.dispose();

		return body;
	}

	public int ropeNumber;
	public Rope rope;

	public Player(World world, float x, float y, float width, float height, Texture texture) {
		super(createBody(world, x, y, width, height), texture);
		setSize(met_to_pix(width), met_to_pix(height));
		body.setUserData(this);
		ropeNumber = Constants.PLAYER_ROPE_CAPACITY;
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		float
				vx = Gdx.input.getAccelerometerY(),
				vy = -Gdx.input.getAccelerometerX();


		body.setLinearVelocity(vx, vy);
	}
}
