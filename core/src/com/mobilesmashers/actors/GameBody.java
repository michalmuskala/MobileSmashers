package com.mobilesmashers.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;

import static com.mobilesmashers.utils.WorldUtils.met_to_pix;

public class GameBody extends GameActor {

	protected Body body;

	public GameBody(Body body, Texture texture) {
		super(texture);
		this.body = body;
	}

	@Override
	public void act(float delta) {
		Vector2 pos = body.getPosition();
		setX(met_to_pix(pos.x));
		setY(met_to_pix(pos.y));
	}

	public Vector2 getBodyPosition() {
		return body.getPosition();
	}

	public Vector2 getLinearVelocity() {
		return body.getLinearVelocity();
	}

	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}

	public void setLinearVelocity(float vx, float vy) {
		body.setLinearVelocity(vx, vy);
	}

	public void applyLinearImpulse(float impulseX, float impulseY) {
		Vector2 pos = body.getPosition();
		body.applyLinearImpulse(impulseX, impulseY, pos.x, pos.y, true);
	}

	public void applyForceAtCenter(float fx, float fy) {
		body.applyForceToCenter(fx, fy, true);
	}
}
