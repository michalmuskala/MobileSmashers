package com.mobilesmashers.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mobilesmashers.utils.Constants;

import static com.mobilesmashers.utils.WorldUtils.met_to_pix;
import static com.mobilesmashers.utils.WorldUtils.newBodyDef;

public class Wall extends GameBody {

	private static BodyDef bodyDef = newBodyDef(BodyDef.BodyType.StaticBody);
	private static PolygonShape shape;

	private static Body createBody(World world, float x, float y, float width, float height, float angle) {
		Vector2 wallCenter = new Vector2(width / 2f, height / 2f);
		bodyDef.position.set(x, y);
		shape.setAsBox(wallCenter.x, wallCenter.y, wallCenter, angle);

		Body body = world.createBody(bodyDef);
		body.createFixture(shape, Constants.WALL_DENSITY);

		return body;
	}

	public static void init_class() {
		shape = new PolygonShape();
	}

	public static void dispose_class() {
		shape.dispose();
	}

	public Wall(World world, float x, float y, float width, float height, float angle, Texture texture) {
		super(createBody(world, x, y, width, height, angle), texture);
		setPosition(met_to_pix(x), met_to_pix(y));
		setSize(met_to_pix(width), met_to_pix(height));
		body.setUserData(this);
	}

	@Override
	public void act(float delta) {
	}
}
