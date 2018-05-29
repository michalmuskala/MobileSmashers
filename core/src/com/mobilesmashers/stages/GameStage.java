package com.mobilesmashers.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.mobilesmashers.actors.Ball;
import com.mobilesmashers.actors.Explosion;
import com.mobilesmashers.actors.Hook;
import com.mobilesmashers.actors.Player;
import com.mobilesmashers.actors.Rope;
import com.mobilesmashers.actors.Wall;
import com.mobilesmashers.utils.Constants;
import com.mobilesmashers.utils.ShapeDrawing;

import java.util.HashMap;

import static com.mobilesmashers.utils.Constants.BALL_MAX_INIT_SPEED;
import static com.mobilesmashers.utils.Constants.BALL_MIN_DST_FROM_PLAYER;
import static com.mobilesmashers.utils.Constants.BALL_RADIUS;
import static com.mobilesmashers.utils.Constants.BALL_TEXTURE_KEY;
import static com.mobilesmashers.utils.Constants.EXPLOSION_RADIUS;
import static com.mobilesmashers.utils.Constants.EXPL_TEXTURE_KEY;
import static com.mobilesmashers.utils.Constants.FLAT_WALLS_DIM;
import static com.mobilesmashers.utils.Constants.HOOK_RADIUS;
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
import static com.mobilesmashers.utils.World.met_to_pix;
import static com.mobilesmashers.utils.World.pix_to_met;

public class GameStage extends Stage implements InputProcessor, ContactListener {

	private boolean gameOn;
	private float accumulator;
	private World world;
	private Explosion explosion;
	private Player player;

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

		gameOn = true;
		accumulator = 0;

		initClasses();
		createTextures();
		createWorld();
		createBalls();
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
		}
	}

	@Override
	public void dispose() {

		Ball.dispose_class();
		Hook.dispose_class();
		Player.dispose_class();
		Wall.dispose_class();

		for (Texture texture : textures.values())
			texture.dispose();

		world.dispose();

		super.dispose();
	}

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
		screenX = (int) VIEWPORT_WIDTH - screenX; // our origin is bottom, not top
		shoot(pix_to_met(screenX), pix_to_met(screenY));
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

	@Override
	public void preSolve(Contact contact, Manifold manifold) {
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse contactImpulse) {
	}

	@Override
	public void beginContact(Contact contact) {
		Object a = contact.getFixtureA().getBody().getUserData();
		Object b = contact.getFixtureB().getBody().getUserData();
		if (a == player || b == player) {
			if (a.getClass() == Ball.class
					|| b.getClass() == Ball.class) {
				gameOn = false;
				explode(a, b);
			}
		} else if (a.getClass() == Hook.class
				|| b.getClass() == Hook.class) {
			if (a.getClass() == Ball.class
					|| b.getClass() == Ball.class) {
				// TODO: continue in tie method
			}
		}
	}

	@Override
	public void endContact(Contact contact) {
	}

	private void tie(Object a, Object b) {
		// TODO: continue here. Consider making TieAble interface
	}
	private void shoot(float dirX, float dirY) {
		float vx = dirX, vy = dirY;
		Vector2 pos = player.getInWorldPosition();

		if (player.rope != null) {
			player.rope.setTail(setNewHook(pos.x, pos.y, vx, vy));
			player.rope = null;
		} else if (player.ropeNumber > 0) {
			player.ropeNumber -= 1;
			Rope rope = new Rope(
					Constants.ROPE_THICKNESS_PX,
					setNewHook(pos.x, pos.y, vx, vy),
					player,
					textures.get(ROPE_TEXTURE_KEY)
			);
			player.rope = rope;
			addActor(rope);
		}
		// FIXME: make hook bodies spawn outside of player body
	}

	private Hook setNewHook(float x, float y, float vx, float vy) {
		Hook hook = new Hook(world, x, y, Constants.HOOK_RADIUS, textures.get(HOOK_TEXTURE_KEY));
		hook.setLinearVelocity(vx, vy);
		addActor(hook);
		return hook;
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

	private void createTextures() {
		textures = new HashMap<String, Texture>();
		textures.put(BALL_TEXTURE_KEY, ShapeDrawing.disk((int) met_to_pix(BALL_RADIUS), Color.BLUE));
		textures.put(EXPL_TEXTURE_KEY, ShapeDrawing.disk((int) met_to_pix(EXPLOSION_RADIUS), Color.GOLD));
		textures.put(HOOK_TEXTURE_KEY, ShapeDrawing.disk((int) met_to_pix(HOOK_RADIUS), Color.GREEN));
		textures.put(PLER_TEXTURE_KEY, ShapeDrawing.rect((int) met_to_pix(PLAYER_SIZE.x), (int) met_to_pix(PLAYER_SIZE.y), Color.RED));
		textures.put(ROPE_TEXTURE_KEY, ShapeDrawing.rect(ROPE_THICKNESS_PX, ROPE_THICKNESS_PX, Color.GREEN));
		textures.put(WALL_TEXTURE_KEY, ShapeDrawing.rect(10, 10, Color.YELLOW));
	}

	private void initClasses() {
		Ball.init_class();
		Hook.init_class();
		Player.init_class();
		Wall.init_class();
	}

	private void createWorld() {
		world = new World(Constants.GRAVITY, Constants.DO_SLEEP);
		world.setContactListener(this);
	}

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
