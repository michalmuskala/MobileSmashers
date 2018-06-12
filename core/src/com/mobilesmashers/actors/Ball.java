package com.mobilesmashers.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mobilesmashers.tasks.Task;
import com.mobilesmashers.utils.Constants;

import static com.mobilesmashers.utils.WorldUtils.newFixtureDef;
import static com.mobilesmashers.utils.WorldUtils.vectorAngle;

public class Ball extends CircleDynamicBody {

	private static FixtureDef fixtureDef;

	static {
		fixtureDef = newFixtureDef(
				Constants.BALL_DENSITY,
				Constants.BALL_FRICTION,
				Constants.BALL_RESTITUTION
		);
	}

	private Fixture fixture;
	private Rope rope;

	public Task task;
	public Text label;

	public Ball(World world, float x, float y, float radius, Task task, Texture texture) {
		super(world, fixtureDef, x, y, radius, texture);
		body.setUserData(this);
		fixture = body.getFixtureList().first();
		setTask(task);
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		label.setX(getX() + getWidth() / 2f);
		label.setY(getY() + getHeight() / 2f);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		label.draw(batch, parentAlpha);
	}

	public void fuse(Ball ball) {
		fixture.setSensor(true);

		if (ball != null)
			ball.fuse(null);
		else
			return;

		float
				angle = vectorAngle(body.getPosition(), ball.getBodyPosition()),
				vx = MathUtils.cos(angle) * Constants.BALL_FUSION_FORCE,
				vy = MathUtils.sin(angle) * Constants.BALL_FUSION_FORCE;

		setLinearVelocity(vx, vy);
		ball.setLinearVelocity(-vx, -vy);
	}

	public Rope getRope() {
		return rope;
	}

	public boolean match(Ball ball) {
		return task.match(ball.task);
	}

	public boolean isSensor() {
		return fixture.isSensor();
	}

	public void setRope(Rope rope) {
		this.rope = rope;
	}

	public void setTask(Task task) {
		this.task = task;
		label = new Text(
				task.getValue(),
				Constants.BALL_LABEL_COLOR,
				Constants.BALL_LABEL_SCALE
		);
	}
}
