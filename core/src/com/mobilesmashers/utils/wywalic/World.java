package com.mobilesmashers.utils.wywalic;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mobilesmashers.utils.wywalic.oldActors.Ball;
import com.mobilesmashers.utils.wywalic.oldActors.Hook;
import com.mobilesmashers.utils.wywalic.oldActors.Player;
import com.mobilesmashers.utils.wywalic.oldActors.Wall;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by kuser on 5/16/18.
 * Temporary class to test World and Body classes
 * Gonna be removed
 */

public class World {

	public Body player;
	public List<Body> bawlz = new ArrayList<Body>();
	public List<Body> hooks = new ArrayList<Body>();
	public HashMap<String, Body> walls = new HashMap<String, Body>();

	public com.badlogic.gdx.physics.box2d.World world;

	public World(float wsizex, float wsizey, List<Ball> balls, List<Hook> hooks, HashMap<String, Wall> walls, Player player) {
		world = new com.badlogic.gdx.physics.box2d.World(new Vector2(0, 0), true);
		makeBawlz(balls);
		makeWalls(wsizex, wsizex, walls);
	}

	public void makeBawlz(List<Ball> bawlz) {

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;

		CircleShape shape = new CircleShape();
		shape.setRadius(120); // GameStage.BALL_RADIUS

		for(Ball bawl : bawlz) {
			bodyDef.position.set(bawl.getX(), bawl.getY());

			Body body = world.createBody(bodyDef);
			body.setUserData(bawl);
			body.createFixture(shape, 1);
			body.resetMassData();

			body.setLinearVelocity(bawl.getSpeed());
			this.bawlz.add(body);
		}

		shape.dispose();
	}

	public void makeWalls(float wsizex, float wsizey, HashMap<String, Wall> walls) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;

		PolygonShape shape = new PolygonShape();

		Wall wall;
		Body body;

		wall = walls.get("up");
		shape.setAsBox(wsizex/2, 2.5f, new Vector2(wsizex/2f, 2.5f), 0);
		wall.setX(0);
		wall.setY(wsizey-5);
		wall.setSize(wsizex, 5);
		bodyDef.position.set(wall.getX(), wall.getY());
		body = world.createBody(bodyDef);
		body.setUserData(wall);
		body.createFixture(shape, 0);

		wall = walls.get("down");
		shape.setAsBox(0, 2.5f, new Vector2(wsizex/2f, 2.5f), 0);
		wall.setX(0);
		wall.setY(wsizey-5);
		wall.setSize(wsizex, 5);
		bodyDef.position.set(wall.getX(), wall.getY());
		body = world.createBody(bodyDef);
		body.setUserData(wall);
		body.createFixture(shape, 0);

		shape.dispose();
	}
}
