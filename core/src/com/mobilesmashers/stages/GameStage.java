package com.mobilesmashers.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.mobilesmashers.actors.Ball;
import com.mobilesmashers.actors.CircleDynamicBody;
import com.mobilesmashers.actors.Explosion;
import com.mobilesmashers.actors.GameBody;
import com.mobilesmashers.actors.Hook;
import com.mobilesmashers.actors.Player;
import com.mobilesmashers.actors.Rope;
import com.mobilesmashers.actors.Wall;
import com.mobilesmashers.tasks.Parity;
import com.mobilesmashers.tasks.Task;
import com.mobilesmashers.utils.Constants;
import com.mobilesmashers.utils.ShapeDrawing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.mobilesmashers.utils.Constants.BALL_MAX_INIT_SPEED;
import static com.mobilesmashers.utils.Constants.BALL_MIN_DST_FROM_PLAYER;
import static com.mobilesmashers.utils.Constants.BALL_RADIUS;
import static com.mobilesmashers.utils.Constants.BALL_TEXTURE_KEY;
import static com.mobilesmashers.utils.Constants.EXPLOSION_RADIUS;
import static com.mobilesmashers.utils.Constants.EXPL_TEXTURE_KEY;
import static com.mobilesmashers.utils.Constants.FLAT_WALLS_DIM;
import static com.mobilesmashers.utils.Constants.HOOK_RADIUS;
import static com.mobilesmashers.utils.Constants.HOOK_SPAWN_DISTANCE;
import static com.mobilesmashers.utils.Constants.HOOK_TEXTURE_KEY;
import static com.mobilesmashers.utils.Constants.PLAYER_SIZE;
import static com.mobilesmashers.utils.Constants.PLAYER_START_POS;
import static com.mobilesmashers.utils.Constants.PLER_TEXTURE_KEY;
import static com.mobilesmashers.utils.Constants.ROPE_TEXTURE_KEY;
import static com.mobilesmashers.utils.Constants.ROPE_THICKNESS_PX;
import static com.mobilesmashers.utils.Constants.SIDE_WALLS_DIM;
import static com.mobilesmashers.utils.Constants.TIME_STEP;
import static com.mobilesmashers.utils.Constants.VIEWPORT_HEIGHT;
import static com.mobilesmashers.utils.Constants.VIEWPORT_WIDTH;
import static com.mobilesmashers.utils.Constants.WALL_DIM;
import static com.mobilesmashers.utils.Constants.WALL_TEXTURE_KEY;
import static com.mobilesmashers.utils.Constants.WORLD_HEIGHT;
import static com.mobilesmashers.utils.Constants.WORLD_WIDTH;
import static com.mobilesmashers.utils.Randomize.nextFloat;
import static com.mobilesmashers.utils.WorldUtils.hookSpeed;
import static com.mobilesmashers.utils.WorldUtils.met_to_pix;
import static com.mobilesmashers.utils.WorldUtils.pix_to_met;
import static com.mobilesmashers.utils.WorldUtils.vectorAngle;

public class GameStage extends Stage implements InputProcessor, ContactListener {

	private boolean gameOn;
	private float accumulator;
	private World world;
	private Explosion explosion;
	private Player player;
	private List<Actor> tiedActors;
	private List<GameBody> toRemove;
	private List<Rope> toFuse; // FIXME: consider using single List<Rope> instead of tiedActors and toFuse arrays
	private HashMap<String, Texture> textures;

	public GameStage() {
		super(new ScalingViewport(
				Scaling.stretch,
				VIEWPORT_WIDTH,
				VIEWPORT_HEIGHT,
				new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT)
		));

		Gdx.input.setInputProcessor(this);

		World.setVelocityThreshold(0.8f);

		initFields();
		initClasses();
		createTextures();
		createWorld();
		createTasks();
		createPlayer();
		createWalls();
	}

	@Override
	public void act(float delta) {
		float frameTime = Math.min(delta, Constants.MAX_TIME_DELTA);

		accumulator += frameTime;
		while (accumulator >= TIME_STEP) {
			if (gameOn) {
				world.step(TIME_STEP, Constants.VEL_ITERS, Constants.POS_ITERS);
			} else if (explosion != null && explosion.getWidth() < 0) {
				explosion.remove();
				explosion = null;
			}
			super.act(frameTime);
			accumulator -= TIME_STEP;

			while (toRemove.size() > 0) {
				GameBody gb = toRemove.get(0);
				gb.remove();
				world.destroyBody(gb.getBody());
				toRemove.remove(0);
			}

			while (toFuse.size() > 0) {
				Rope rope = toFuse.get(0);
				rope.fuse();
				toFuse.remove(0);
			}
		}
	}

	@Override
	public void dispose() {

		CircleDynamicBody.dispose_class();
		Wall.dispose_class();

		for (Texture texture : textures.values())
			texture.dispose();

		world.dispose();

		super.dispose();
	}

	/* INPUT HANDLING */

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Input.Keys.BACK) {
			// TODO: make it go to main menu...
		}
		return super.keyDown(keycode);
	}

	@Override
	public boolean keyUp(int keycode) {
		return super.keyUp(keycode);
	}

	@Override
	public boolean keyTyped(char character) {
		return super.keyTyped(character);
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		int screenY_transformed = (int) VIEWPORT_HEIGHT - screenY; // we need origin at bottom
		shoot(pix_to_met(screenX), pix_to_met(screenY_transformed));
		return super.touchDown(screenX, screenY, pointer, button);
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return super.touchUp(screenX, screenY, pointer, button);
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return super.touchDragged(screenX, screenY, pointer);
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return super.mouseMoved(screenX, screenY);
	}

	@Override
	public boolean scrolled(int amount) {
		return super.scrolled(amount);
	}

	/* CONTACT LISTENER */

	@Override
	public void beginContact(Contact contact) {
		Object
				a = contact.getFixtureA().getBody().getUserData(),
				b = contact.getFixtureB().getBody().getUserData();
		Class
				aClass = a.getClass(),
				bClass = b.getClass();

		// TODO: add player hook catching
		if (aClass == Hook.class || bClass == Hook.class) {
			if ((aClass == Ball.class || bClass == Ball.class))
				tie(a, b);
		} else if (aClass == Ball.class && bClass == Ball.class) {
			Ball
					ballA = ((Ball) a),
					ballB = ((Ball) b);
			if (ballA.isSensor() && ballB.isSensor()) {
				toRemove.add(ballA);
				toRemove.add(ballB);
				ballA.getRope().remove();

				if(!ballA.match(ballB)) {
					gameOn = false;
					explode(a, b);
				}
			}
		} else if (a == player || b == player) {
			if (aClass == Ball.class
					|| bClass == Ball.class) {
				gameOn = false;
				explode(a, b);
			}
		}
	}

	@Override
	public void endContact(Contact contact) {
	}

	@Override
	public void preSolve(Contact contact, Manifold manifold) {
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse contactImpulse) {
	}

	/* HELPER FUNCTIONS */

	private void tie(Object a, Object b) {

		if (a.getClass() == Ball.class) {
			Object temp = a;
			a = b;
			b = temp;
		}

		Ball ball = (Ball) b;
		Hook hook = (Hook) a;
		Rope rope = hook.getRope();

		for (Actor actor : tiedActors)
			if (actor == ball)
				return;

		rope.replaceEnd(hook, ball);
		tiedActors.add(ball);
		toRemove.add(hook);

		startFusion(rope);
	}

	private void shoot(float dirX, float dirY) {
		Vector2
				pos = player.getBodyPosition(),
				vel = player.getLinearVelocity();
		float
				speed = hookSpeed(Vector2.dst(pos.x, pos.y, dirX, dirY)),
				angle = vectorAngle(pos.x, pos.y, dirX, dirY),
				cosAngle = MathUtils.cos(angle),
				sinAngle = MathUtils.sin(angle),
				posX = pos.x + PLAYER_SIZE.x / 2f + cosAngle * HOOK_SPAWN_DISTANCE,
				posY = pos.y + PLAYER_SIZE.y / 2f + sinAngle * HOOK_SPAWN_DISTANCE,
				vx = speed * cosAngle + vel.x,
				vy = speed * sinAngle + vel.y;

		if (player.rope != null) {
			Hook hook = spawnHook(posX, posY, vx, vy);
			hook.setRope(player.rope);
			player.rope.setTail(hook);
			player.rope = null;
			tiedActors.remove(player);
		} else if (player.ropeNumber > 0) {
			player.ropeNumber -= 1;
			Hook hook = spawnHook(posX, posY, vx, vy);
			Rope rope = new Rope(
					Constants.ROPE_THICKNESS_PX,
					hook,
					player,
					textures.get(ROPE_TEXTURE_KEY)
			);
			hook.setRope(rope);
			player.rope = rope;
			addActor(rope);
			tiedActors.add(player);
		}
		// FIXME: make hook bodies spawn outside of player body
	}

	private Hook spawnHook(float x, float y, float vx, float vy) {
		Hook hook = new Hook(world, x, y, Constants.HOOK_RADIUS, textures.get(HOOK_TEXTURE_KEY));
		hook.setLinearVelocity(vx, vy);
		addActor(hook);
		return hook;
	}

	private void startFusion(Rope rope) {
		// FIXME: consider doing rope.fuse check here, instead in the Rope class (to avoid method call overhead)
		toFuse.add(rope);
	}

	private void explode(Object a, Object b) {
		explosion = new Explosion(
				Constants.EXPLOSION_RADIUS,
				Constants.EXPLOSION_RADIUS_DELTA,
				Constants.EXPLOSION_IMPLOSION_FACTOR,
				a.getClass() == Ball.class ? (Ball) a : (Ball) b,
				player,
				textures.get(EXPL_TEXTURE_KEY)
		);
		addActor(explosion);
	}

	/* INITIALIZATION */

	private void createTextures() {
		textures = new HashMap<String, Texture>();
		textures.put(BALL_TEXTURE_KEY, ShapeDrawing.disk((int) met_to_pix(BALL_RADIUS), Color.BLUE));
		textures.put(EXPL_TEXTURE_KEY, ShapeDrawing.disk((int) met_to_pix(EXPLOSION_RADIUS), Color.GOLD));
		textures.put(HOOK_TEXTURE_KEY, ShapeDrawing.disk((int) met_to_pix(HOOK_RADIUS), Color.GREEN));
		textures.put(PLER_TEXTURE_KEY, ShapeDrawing.rect((int) met_to_pix(PLAYER_SIZE.x), (int) met_to_pix(PLAYER_SIZE.y), Color.RED));
		textures.put(ROPE_TEXTURE_KEY, ShapeDrawing.rect(ROPE_THICKNESS_PX, ROPE_THICKNESS_PX, Color.GREEN));
		textures.put(WALL_TEXTURE_KEY, ShapeDrawing.rect(10, 10, Color.YELLOW));
	}

	private void initFields() {
		gameOn = true;
		accumulator = 0;
		tiedActors = new ArrayList<Actor>();
		toRemove = new ArrayList<GameBody>();
		toFuse = new ArrayList<Rope>();
	}

	private void initClasses() {
		CircleDynamicBody.init_class();
		Wall.init_class();
	}

	private void createWorld() {
		world = new World(Constants.GRAVITY, Constants.DO_SLEEP);
		world.setContactListener(this);
	}

	/*
	private void createBalls() {
		for (int i = 0; i < Constants.BALL_NUMBER; ++i) {
			Vector2 pos = new Vector2(
					PLAYER_START_POS.x,
					PLAYER_START_POS.y
			);
			while (pos.dst(PLAYER_START_POS) < BALL_MIN_DST_FROM_PLAYER) {
				pos.x = nextFloat(WORLD_WIDTH);
				pos.y = nextFloat(WORLD_HEIGHT);
			}
			Ball ball = new Ball(
					world,
					pos.x,
					pos.y,
					BALL_RADIUS,
					textures.get(BALL_TEXTURE_KEY)
			);
			ball.setLinearVelocity(
					nextFloat(BALL_MAX_INIT_SPEED.x),
					nextFloat(BALL_MAX_INIT_SPEED.y)
			);
			addActor(ball);
		}
	}
	*/

	private void createTasks() {
		for (int i = 0; i < Constants.TASK_NUMBER; ++i) {
			Vector2
					posA = new Vector2(PLAYER_START_POS.x, PLAYER_START_POS.y),
					posB = new Vector2(PLAYER_START_POS.x, PLAYER_START_POS.y);

			while (posA.dst(PLAYER_START_POS) < BALL_MIN_DST_FROM_PLAYER) {
				posA.x = nextFloat(WORLD_WIDTH);
				posA.y = nextFloat(WORLD_HEIGHT);
			}
			while (posB.dst(PLAYER_START_POS) < BALL_MIN_DST_FROM_PLAYER) {
				posB.x = nextFloat(WORLD_WIDTH);
				posB.y = nextFloat(WORLD_HEIGHT);
			}

			Task[] tasks = Parity.createTask(2);
			Ball
					ballA = new Ball(world, posA.x, posA.y, BALL_RADIUS, tasks[0],
							textures.get(BALL_TEXTURE_KEY)),
					ballB = new Ball( world, posB.x, posB.y, BALL_RADIUS, tasks[1],
							textures.get(BALL_TEXTURE_KEY));
			ballA.setLinearVelocity(
					nextFloat(BALL_MAX_INIT_SPEED.x),
					nextFloat(BALL_MAX_INIT_SPEED.y)
			);
			ballB.setLinearVelocity(
					nextFloat(BALL_MAX_INIT_SPEED.x),
					nextFloat(BALL_MAX_INIT_SPEED.y)
			);
			addActor(ballA);
			addActor(ballB);
		}

	}

	private void createPlayer() {
		player = new Player(
				world,
				PLAYER_START_POS.x,
				PLAYER_START_POS.y,
				PLAYER_SIZE.x,
				PLAYER_SIZE.y,
				textures.get(PLER_TEXTURE_KEY)
		);
		addActor(player);
	}

	private void createWalls() {
		addActor(new Wall(
				world,
				0f,
				0f,
				FLAT_WALLS_DIM,
				WALL_DIM,
				0,
				textures.get(WALL_TEXTURE_KEY)
		));
		addActor(new Wall(
				world,
				0f,
				WORLD_HEIGHT - WALL_DIM,
				FLAT_WALLS_DIM,
				WALL_DIM,
				0,
				textures.get(WALL_TEXTURE_KEY)
		));
		addActor(new Wall(
				world,
				0f,
				0f,
				WALL_DIM,
				SIDE_WALLS_DIM,
				0,
				textures.get(WALL_TEXTURE_KEY)
		));
		addActor(new Wall(
				world,
				WORLD_WIDTH - WALL_DIM,
				0f,
				WALL_DIM,
				SIDE_WALLS_DIM,
				0,
				textures.get(WALL_TEXTURE_KEY)
		));
	}
}
