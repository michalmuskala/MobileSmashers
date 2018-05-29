package com.mobilesmashers.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mobilesmashers.utils.Constants;

import static com.mobilesmashers.utils.World.newBodyDef;

public class Wall extends GameActor {

	private static BodyDef bodyDef = newBodyDef(BodyDef.BodyType.StaticBody);
	private static PolygonShape shape;

	public static void init_class() {
		shape = new PolygonShape();
	}

	public static void dispose_class() {
		shape.dispose();
	}

	private Body body;

	public Wall(World world, float x, float y, float width, float height, float angle, Texture texture) {
		super(x, y, width, height, texture);

		Vector2 wallCenter = new Vector2(width / 2f, height / 2f);
		bodyDef.position.set(x, y);
		shape.setAsBox(wallCenter.x, wallCenter.y, wallCenter, angle);

		body = world.createBody(bodyDef);
		body.createFixture(shape, Constants.WALL_DENSITY);
		body.setUserData(this);
	}
}
