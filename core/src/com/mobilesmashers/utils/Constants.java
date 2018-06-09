package com.mobilesmashers.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import static com.mobilesmashers.utils.WorldUtils.pix_to_met;
import static com.mobilesmashers.utils.WorldUtils.world_center;

public final class Constants {

	public static final boolean
			DO_SLEEP = true;

	public static final int
			APP_HEIGHT = Gdx.graphics.getHeight(),
			APP_WIDTH = Gdx.graphics.getWidth(),
			BALL_NUMBER = 6,
			PLAYER_ROPE_CAPACITY = BALL_NUMBER / 2 + 2,
			POS_ITERS = 2,
			ROPE_THICKNESS_PX = 7,
			VEL_ITERS = 6;

	public static final float
			VIEWPORT_HEIGHT = APP_HEIGHT,
			VIEWPORT_WIDTH = APP_WIDTH,
			PIX_PER_METER = 7f * VIEWPORT_WIDTH / 100f,
			WORLD_HEIGHT = pix_to_met(VIEWPORT_HEIGHT),
			WORLD_WIDTH = pix_to_met(VIEWPORT_WIDTH),
			BALL_DENSITY = 1f,
			BALL_FRICTION = 0f,
			BALL_MIN_DST_FROM_PLAYER = Math.min(WORLD_WIDTH, WORLD_HEIGHT) * 0.35f,
			BALL_RADIUS = .5f,
			BALL_RESTITUTION = 1f,
			EXPLOSION_IMPLOSION_FACTOR = 0.2f,
			EXPLOSION_RADIUS_DELTA = 0.25f,
			EXPLOSION_RADIUS = 3f,
			FLAT_WALLS_DIM = WORLD_WIDTH,
			HOOK_DENSITY = 1.5f,
			HOOK_FRICTION = 0.2f,
			HOOK_RADIUS = .1f,
			HOOK_RESTITUTION = 0.8f,
			MAX_TIME_DELTA = .25f,
			SHOOT_FORCE = 5f / (float) Math.sqrt(WORLD_WIDTH * WORLD_WIDTH + WORLD_HEIGHT * WORLD_HEIGHT),
			SIDE_WALLS_DIM = WORLD_HEIGHT,
			PLAYER_DENSITY = 1f,
			PLAYER_FRICTION = 0f,
			PLAYER_RESTITUTION = 0f,
			TIME_STEP = 1f / 30f,
			WALL_DENSITY = 0f,
			WALL_DIM = .05f;

	public static final Vector2
			BALL_MAX_INIT_SPEED = new Vector2(2f, 2f),
			GRAVITY = new Vector2(0f, 0f),
			PLAYER_SIZE = new Vector2(.3f, .5f),
			PLAYER_START_POS = world_center(PLAYER_SIZE.x, PLAYER_SIZE.y);

	public static final String
			APP_NAME = "MobileSmashers",
			BALL_TEXTURE_KEY = "ball",
			EXPL_TEXTURE_KEY = "explosion",
			HOOK_TEXTURE_KEY = "hook",
			PLER_TEXTURE_KEY = "player",
			ROPE_TEXTURE_KEY = "rope",
			WALL_TEXTURE_KEY = "wall";

	private Constants() {
	}
}
