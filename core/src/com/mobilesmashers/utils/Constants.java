package com.mobilesmashers.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import static com.mobilesmashers.utils.WorldUtils.pix_to_met;
import static com.mobilesmashers.utils.WorldUtils.world_center;

public final class Constants {

	public static final boolean
			DO_SLEEP = true;

	public static final int
			APP_HEIGHT = Gdx.graphics.getHeight(),
			APP_WIDTH = Gdx.graphics.getWidth(),
			TASK_NUMBER = 2,
			PLAYER_ROPE_CAPACITY = TASK_NUMBER / 2 + 2,
			POS_ITERS = 2,
			ROPE_THICKNESS_PX = 7,
			TASK_PARITY_MAX = 200,
			VEL_ITERS = 6;

	public static final float
			VIEWPORT_HEIGHT = APP_HEIGHT,
			VIEWPORT_WIDTH = APP_WIDTH,
			PIX_PER_METER = 7f * VIEWPORT_WIDTH / 100f,
			WORLD_HEIGHT = pix_to_met(VIEWPORT_HEIGHT),
			WORLD_WIDTH = pix_to_met(VIEWPORT_WIDTH),
			BALL_DENSITY = 1f,
			BALL_FRICTION = 0f,
			BALL_FUSION_FORCE = 1f,
			BALL_LABEL_SCALE = 4f,
			BALL_MIN_DST_FROM_PLAYER = Math.min(WORLD_WIDTH, WORLD_HEIGHT) * .35f,
			BALL_RADIUS = .5f,
			BALL_RESTITUTION = 1f,
			EXPLOSION_IMPLOSION_FACTOR = .2f,
			EXPLOSION_RADIUS_DELTA = .25f,
			EXPLOSION_RADIUS = 3f,
			FLAT_WALLS_DIM = WORLD_WIDTH,
			HOOK_DENSITY = 1.5f,
			HOOK_FRICTION = .2f,
			HOOK_RADIUS = .1f,
			HOOK_RESTITUTION = .8f,
			MAX_TIME_DELTA = .25f,
			HOOK_MAX_SPEED = (float) Math.sqrt(WORLD_WIDTH * WORLD_WIDTH + WORLD_HEIGHT * WORLD_HEIGHT) / 2,
			SIDE_WALLS_DIM = WORLD_HEIGHT,
			PLAYER_DENSITY = 1f,
			PLAYER_FRICTION = 0f,
			PLAYER_RESTITUTION = 0f,
			TIME_STEP = 1f / 30f,
			WALL_DENSITY = 0f,
			WALL_DIM = .05f;

	public static final double DEFAULT_VOLUME = 1.;

	public static Color
			BALL_LABEL_COLOR = Color.WHITE,
			PLAYER_COLOR = Color.RED;

	public static final Vector2
			BALL_MAX_INIT_SPEED = new Vector2(2f, 2f),
			GRAVITY = new Vector2(0f, 0f),
			PLAYER_SIZE = new Vector2(.3f, .5f),
			PLAYER_START_POS = world_center(PLAYER_SIZE.x, PLAYER_SIZE.y);

	public static final String
			APP_NAME = "MobileSmashers",
			MUSIC_CATCH_KEY = "sounds/ballCatch.mp3",
			MUSIC_EXPLO_KEY = "sounds/explosion.wav",
			MUSIC_LSHOT_KEY = "sounds/lineBack.mp3",
			MUSIC_SHOOT_KEY = "sounds/lineShoot.mp3",
			TEXTURE_BACK_KEY = "pictures/background.png",
			TEXTURE_BALL_KEY = "ball",
			TEXTURE_EXPL_KEY = "explosion",
			TEXTURE_GOVR_KEY = "pictures/gameOver.png",
			TEXTURE_HOOK_KEY = "hook",
			TEXTURE_INFO_KEY = "pictures/info.png",
			TEXTURE_LOGO_KEY = "pictures/logo.png",
			TEXTURE_MENU_KEY = "pictures/menu.png",
			TEXTURE_PLAY_KEY = "pictures/play.png",
			TEXTURE_PLER_KEY = "player",
			TEXTURE_ROPE_KEY = "rope",
			TEXTURE_SNON_KEY = "pictures/sound_on.png",
			TEXTURE_SOFF_KEY = "pictures/sound_off.png",
			TEXTURE_WALL_KEY = "wall";

	public static final float
			PLAYER_EXCIRCLE_RADIUS = (float) Math.sqrt(PLAYER_SIZE.x * PLAYER_SIZE.x + PLAYER_SIZE.y * PLAYER_SIZE.y) / 2f,
			HOOK_SPAWN_DISTANCE = PLAYER_EXCIRCLE_RADIUS + HOOK_RADIUS + .0001f;

	private Constants() {
	}
}
