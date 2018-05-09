package com.mobilesmashers.utils;

import com.badlogic.gdx.Gdx;

public class Constants {

	public static final int
		APP_WIDTH = Gdx.graphics.getWidth(),
		APP_HEIGHT = Gdx.graphics.getHeight();

	public static final float
		TIME_STEP = 1 / 300f,
		STAGE_BOUND_TOP = (float) APP_HEIGHT,
		STAGE_BOUND_RIGHT = (float) APP_WIDTH,
		STAGE_BOUND_BOTTOM = 0,
		STAGE_BOUND_LEFT = 0;

	public static final String GAME_NAME = "MobileSmashers";

	private Constants() {
	}
}
