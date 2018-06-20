package com.mobilesmashers.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
import com.mobilesmashers.MobileSmashers;
import com.mobilesmashers.actors.CircleDynamicBody;
import com.mobilesmashers.actors.Explosion;
import com.mobilesmashers.actors.GameActor;
import com.mobilesmashers.actors.GameBody;
import com.mobilesmashers.actors.Hook;
import com.mobilesmashers.actors.Player;
import com.mobilesmashers.actors.Rope;
import com.mobilesmashers.actors.TaskBall;
import com.mobilesmashers.actors.Text;
import com.mobilesmashers.actors.Wall;
import com.mobilesmashers.tasks.Addition;
import com.mobilesmashers.tasks.Parity;
import com.mobilesmashers.tasks.Task;
import com.mobilesmashers.utils.AudioUtils;
import com.mobilesmashers.utils.Constants;
import com.mobilesmashers.utils.TextureUtils;
import com.mobilesmashers.utils.WorldUtils;

import java.util.ArrayList;
import java.util.List;

import static com.mobilesmashers.utils.Constants.BALL_MAX_INIT_SPEED;
import static com.mobilesmashers.utils.Constants.BALL_MIN_DST_FROM_PLAYER;
import static com.mobilesmashers.utils.Constants.BALL_RADIUS;
import static com.mobilesmashers.utils.Constants.FLAT_WALLS_DIM;
import static com.mobilesmashers.utils.Constants.HOOK_SPAWN_DISTANCE;
import static com.mobilesmashers.utils.Constants.MUSIC_SUCCE_KEY;
import static com.mobilesmashers.utils.Constants.PLAYER_SIZE;
import static com.mobilesmashers.utils.Constants.PLAYER_START_POS;
import static com.mobilesmashers.utils.Constants.SIDE_WALLS_DIM;
import static com.mobilesmashers.utils.Constants.TEXTURE_BALL_KEY;
import static com.mobilesmashers.utils.Constants.TEXTURE_EXPL_KEY;
import static com.mobilesmashers.utils.Constants.TEXTURE_HOOK_KEY;
import static com.mobilesmashers.utils.Constants.TEXTURE_ROPE_KEY;
import static com.mobilesmashers.utils.Constants.TEXTURE_WALL_KEY;
import static com.mobilesmashers.utils.Constants.TIME_STEP;
import static com.mobilesmashers.utils.Constants.VIEWPORT_HEIGHT;
import static com.mobilesmashers.utils.Constants.VIEWPORT_WIDTH;
import static com.mobilesmashers.utils.Constants.WALL_DIM;
import static com.mobilesmashers.utils.Constants.WORLD_HEIGHT;
import static com.mobilesmashers.utils.Constants.WORLD_WIDTH;
import static com.mobilesmashers.utils.RandomUtils.nextFloat;
import static com.mobilesmashers.utils.WorldUtils.hookSpeed;
import static com.mobilesmashers.utils.WorldUtils.pix_to_met;
import static com.mobilesmashers.utils.WorldUtils.vectorAngle;

public class GameStage extends Stage implements ContactListener {

	private gameState state;
	private int level;
	private float accumulator;
	private MobileSmashers game;
	private World world;
	private Explosion explosion;
	private Player player;
	private List<TaskBall> taskBalls;
	private List<Actor> tiedActors;
	private List<GameBody> toRemove;
	private List<Rope> toFuse; // FIXME: consider using single List<Rope> instead of tiedActors and toFuse arrays

	public GameStage(MobileSmashers game) {
		super(new ScalingViewport(
				Scaling.stretch,
				VIEWPORT_WIDTH,
				VIEWPORT_HEIGHT,
				new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT)
		));

		this.game = game;

		createWorld();
		initClasses();
		initFields();
		createPlayer();
		createParityTasks();
		//createAdditionTasks();
		createWalls();
		playIntroMusic();
	}

	/* STAGE */

	@Override
	public void act(float delta) {
		float frameTime = Math.min(delta, Constants.MAX_TIME_DELTA);

		accumulator += frameTime;
		while (accumulator >= TIME_STEP) {
			if (state != gameState.OVER) {
				world.step(TIME_STEP, Constants.VEL_ITERS, Constants.POS_ITERS);
			} else if (explosion != null && explosion.getWidth() < 0) {
				explosion.remove();
				explosion = null;

				float
						gotW = WORLD_WIDTH,
						gotH = WORLD_HEIGHT / 2f;
				Vector2 gotPos = WorldUtils.world_center(gotW, gotH);
				addActor(new GameActor(
						gotPos.x, gotPos.y, gotW, gotH,
						TextureUtils.get(Constants.TEXTURE_GOVR_KEY)
				));
			}

			if (state == gameState.LEVEL_UP) {
				createParityTasks();
				state = gameState.RUNNING;
				player.setRopeNumber(level + 1);
				AudioUtils.play(MUSIC_SUCCE_KEY);
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

	/* DISPOSABLE */

	@Override
	public void dispose() {

		CircleDynamicBody.dispose_class();
		Wall.dispose_class();

		world.dispose();

		super.dispose();
	}

	/* INPUT PROCESSOR */

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		int screenY_transformed = (int) VIEWPORT_HEIGHT - screenY; // we need origin at bottom
		shoot(pix_to_met(screenX), pix_to_met(screenY_transformed));
		return false;
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Input.Keys.BACK) {
			game.back();
			// TODO: add pause while state
			return true;
		}
		return false;
	}

	// overrides below block useless super calls

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
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
			if ((aClass == TaskBall.class || bClass == TaskBall.class))
				tie(a, b);
		} else if (aClass == TaskBall.class && bClass == TaskBall.class) {
			TaskBall
					taskBallA = ((TaskBall) a),
					taskBallB = ((TaskBall) b);
			if (taskBallA.isSensor() && taskBallB.isSensor()) { // FIXME: this will work badly if two non-bounded sensor balls touch
				toRemove.add(taskBallA);
				toRemove.add(taskBallB);
				taskBallA.getRope().remove();

				taskBalls.remove(taskBallA);
				taskBalls.remove(taskBallB);

				if (taskBallA.match(taskBallB) == Task.match.BAD_MATCH) {
					state = gameState.OVER;
					explode(a, b);
				} else if (taskBallA.match(taskBallB) == Task.match.NO_MATCH) { // TODO: when linking multiple balls will be supported
					state = gameState.OVER;
					explode(a, b);
				} else if (taskBalls.size() == 0) {
					level += 1;
					state = gameState.LEVEL_UP;
				}
			}
		} else if (a == player || b == player) {
			if (aClass == TaskBall.class
					|| bClass == TaskBall.class) {
				state = gameState.OVER;
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

	/* HELPER METHODS */

	private void tie(Object a, Object b) {

		if (a.getClass() == TaskBall.class) {
			Object temp = a;
			a = b;
			b = temp;
		}

		TaskBall taskBall = (TaskBall) b;
		Hook hook = (Hook) a;
		Rope rope = hook.getRope();

		for (Actor actor : tiedActors)
			if (actor == taskBall)
				return;

		rope.replaceEnd(hook, taskBall);
		tiedActors.add(taskBall);
		toRemove.add(hook);

		AudioUtils.play(Constants.MUSIC_CATCH_KEY);

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
					TextureUtils.get(TEXTURE_ROPE_KEY)
			);
			hook.setRope(rope);
			player.rope = rope;
			addActor(rope);
			tiedActors.add(player);
		} else {
			return;
		}

		AudioUtils.play(Constants.MUSIC_SHOOT_KEY);
		// FIXME: make hook bodies spawn outside of player body
	}

	private Hook spawnHook(float x, float y, float vx, float vy) {
		Hook hook = new Hook(world, x, y, Constants.HOOK_RADIUS, TextureUtils.get(TEXTURE_HOOK_KEY));
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
				a.getClass() == TaskBall.class ? (TaskBall) a : (TaskBall) b,
				player,
				TextureUtils.get(TEXTURE_EXPL_KEY)
		);
		addActor(explosion);
		AudioUtils.play(Constants.MUSIC_EXPLO_KEY);
	}

	// init

	private void createWorld() {
		world = new World(Constants.GRAVITY, Constants.DO_SLEEP);
		world.setContactListener(this);
	}

	private void initClasses() {

		CircleDynamicBody.init_class();
		Wall.init_class();

		Gdx.input.setInputProcessor(this);

		World.setVelocityThreshold(0.8f);
	}

	private void initFields() {
		state = gameState.RUNNING;
		level = 1;
		accumulator = 0;
		taskBalls = new ArrayList<TaskBall>();
		tiedActors = new ArrayList<Actor>();
		toRemove = new ArrayList<GameBody>();
		toFuse = new ArrayList<Rope>();
	}

	private void createPlayer() {
		player = new Player(
				world,
				PLAYER_START_POS.x,
				PLAYER_START_POS.y,
				PLAYER_SIZE.x,
				PLAYER_SIZE.y,
				TextureUtils.get(Constants.TEXTURE_PLER_KEY)
		);
		player.setRopeNumber(level + 1);
		addActor(player);
	}

	private void createParityTasks() {
		for (int i = 0; i < level; ++i) {
			Task[] tasks = Parity.createTask();
			for (int j = 0; j < 2; ++j) {

				float dst, posX, posY;
				do {
					posX = nextFloat(WORLD_WIDTH);
					posY = nextFloat(WORLD_HEIGHT);
					dst = PLAYER_START_POS.dst(posX, posY);
				} while (dst < BALL_MIN_DST_FROM_PLAYER);

				TaskBall ball = new TaskBall(world, posX, posY, BALL_RADIUS, tasks[j],
						TextureUtils.get(TEXTURE_BALL_KEY));
				ball.setLinearVelocity(
						nextFloat(BALL_MAX_INIT_SPEED.x),
						nextFloat(BALL_MAX_INIT_SPEED.y)
				);
				taskBalls.add(ball);
				addActor(ball);
			}
		}
	}

	private void createAdditionTasks() {
		Integer target = Addition.resetTargetValue();
		Text targetText = new Text(0, 0, target.toString(), Constants.TEXT_COLOR, 5f);
		addActor(targetText);
		for (int i = 0; i < level; ++i) {
			int ballNumber = 2;
			Task[] tasks = Addition.createTask(ballNumber);
			for (int j = 0; j < ballNumber; ++j) {

				float dst, posX, posY;
				do {
					posX = nextFloat(WORLD_WIDTH);
					posY = nextFloat(WORLD_HEIGHT);
					dst = PLAYER_START_POS.dst(posX, posY);
				} while (dst < BALL_MIN_DST_FROM_PLAYER);

				TaskBall ball = new TaskBall(world, posX, posY, BALL_RADIUS, tasks[j],
						TextureUtils.get(TEXTURE_BALL_KEY));
				ball.setLinearVelocity(
						nextFloat(BALL_MAX_INIT_SPEED.x),
						nextFloat(BALL_MAX_INIT_SPEED.y)
				);
				taskBalls.add(ball);
				addActor(ball);
			}
		}
	}

	private void createWalls() {
		addActor(new Wall(
				world,
				0f,
				0f,
				FLAT_WALLS_DIM,
				WALL_DIM,
				0,
				TextureUtils.get(TEXTURE_WALL_KEY)
		));
		addActor(new Wall(
				world,
				0f,
				WORLD_HEIGHT - WALL_DIM,
				FLAT_WALLS_DIM,
				WALL_DIM,
				0,
				TextureUtils.get(TEXTURE_WALL_KEY)
		));
		addActor(new Wall(
				world,
				0f,
				0f,
				WALL_DIM,
				SIDE_WALLS_DIM,
				0,
				TextureUtils.get(TEXTURE_WALL_KEY)
		));
		addActor(new Wall(
				world,
				WORLD_WIDTH - WALL_DIM,
				0f,
				WALL_DIM,
				SIDE_WALLS_DIM,
				0,
				TextureUtils.get(TEXTURE_WALL_KEY)
		));
	}

	private void playIntroMusic() {
		AudioUtils.play(Constants.MUSIC_INTRO_KEY);
	}

	// classes

	enum gameState {
		RUNNING,
		LEVEL_UP,
		PAUSE,
		OVER
	}
}
