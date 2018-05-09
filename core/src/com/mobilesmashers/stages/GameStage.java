package com.mobilesmashers.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.mobilesmashers.actors.Ball;
import com.mobilesmashers.actors.Hook;
import com.mobilesmashers.actors.Player;
import com.mobilesmashers.actors.Rope;
import com.mobilesmashers.utils.Constants;
import com.mobilesmashers.utils.Randomize;
import com.mobilesmashers.utils.ShapeDrawing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class GameStage extends Stage implements InputProcessor {

	private static final int
			VIEWPORT_WIDTH = Constants.APP_WIDTH,
			VIEWPORT_HEIGHT = Constants.APP_HEIGHT,
			BALL_NUMBER = 10,
			BALL_RADIUS = 120,
			HOOK_RADIUS = 60,
			ROPE_NUMBER = 6;
	private static final float
			SHOOT_FORCE = 80 / (float) Math.sqrt(VIEWPORT_WIDTH * VIEWPORT_WIDTH + VIEWPORT_HEIGHT * VIEWPORT_HEIGHT),
			ROPE_THICKNESS = 10;
	private static final Vector2
			PLAYER_SIZE = new Vector2(100, 100);
	private static final String
			PLAYER_KEY = "player",
			HOOK_KEY = "hook",
			ROPE_KEY = "rope",
			BALL_KEY = "ball";

	private HashMap<String, Texture> textures;

	private Player player;
	private List<Ball> balls;
	private List<Rope> ropes;
	private List<Hook> hooks;

	public GameStage() {

		super(new ScalingViewport(
				Scaling.stretch,
				VIEWPORT_WIDTH,
				VIEWPORT_HEIGHT,
				new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT)
		));

		Gdx.input.setInputProcessor(this);

		textures = new HashMap<String, Texture>();
		balls = new ArrayList<Ball>();
		ropes = new ArrayList<Rope>();
		hooks = new ArrayList<Hook>();

		createTextures();
		createActors();
		setUpActors();
		addActors();
	}

	@Override
	public void dispose() {
		for (Texture texture : textures.values())
			texture.dispose();
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
		screenX = VIEWPORT_WIDTH - screenX; // our origin is bottom, not top

		player.shoot(screenX, screenY, SHOOT_FORCE);

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

	private Vector2 getCenter(Vector2 elementSize) {
		return new Vector2((VIEWPORT_WIDTH - elementSize.x) / 2, (VIEWPORT_HEIGHT - elementSize.y) / 2);
	}

	private void createTextures() {
		Vector2 ropeSize = new Vector2(ROPE_THICKNESS, ROPE_THICKNESS);

		textures.put(PLAYER_KEY, ShapeDrawing.rect(PLAYER_SIZE, Color.RED));
		textures.put(HOOK_KEY, ShapeDrawing.disk(HOOK_RADIUS, Color.GREEN));
		textures.put(ROPE_KEY, ShapeDrawing.rect(ropeSize, Color.GREEN));
		textures.put(BALL_KEY, ShapeDrawing.disk(BALL_RADIUS, Color.BLUE));
	}

	private void createActors() {
		player = new Player(
				getCenter(PLAYER_SIZE),
				PLAYER_SIZE,
				textures.get(PLAYER_KEY)
		);
		for (int i = 0; i < BALL_NUMBER; ++i)
			balls.add(new Ball(
					new Vector2(Randomize.nextFloat(Constants.STAGE_BOUND_RIGHT - BALL_RADIUS), Randomize.nextFloat(Constants.STAGE_BOUND_TOP - BALL_RADIUS)),
					new Vector2(BALL_RADIUS, BALL_RADIUS),
					new Vector2(Randomize.nextFloat(10), Randomize.nextFloat(10)),
					textures.get(BALL_KEY)
			));
		for (int i = 0; i < ROPE_NUMBER; ++i)
			hooks.add(new Hook(
					new Vector2(HOOK_RADIUS, HOOK_RADIUS),
					player,
					textures.get(HOOK_KEY)
			));
		for (int i = 0, hook_number = 2 * ROPE_NUMBER; i < ROPE_NUMBER; i += 2)
			ropes.add(new Rope(
					hooks.get(i),
					hooks.get(i + 1),
					ROPE_THICKNESS,
					textures.get(ROPE_KEY)
			));
	}

	private void setUpActors() {
		for (Hook hook : hooks) {
			player.addToInventory(hook);
			hook.setVisible(false);
		}
	}

	private void addActors() {

		for (Ball ball : balls)
			addActor(ball);

		for (Rope rope : ropes)
			addActor(rope);

		addActor(player);

		for (Hook hook : hooks)
			addActor(hook);
	}
}
