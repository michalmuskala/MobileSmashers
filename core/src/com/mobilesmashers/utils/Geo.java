package com.mobilesmashers.utils;

import com.badlogic.gdx.math.Vector2;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import static com.badlogic.gdx.math.MathUtils.PI;
import static com.badlogic.gdx.math.MathUtils.atan2;

public final class Geo {

	@NotNull
	public static float vectorAngle(Vector2 beg, Vector2 end) {
		return vectorAngle(beg.x, beg.y, end.x, end.y);
	}

	public static float vectorAngle(float begX, float begY, float endX, float endY) {
		// no idea why this werks
		return atan2(endY - begY, endX - begX);
	}

	private Geo() {
	}
}
