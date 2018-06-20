package com.mobilesmashers.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;

public final class TextureUtils {

	private static HashMap<String, Texture> textures = new HashMap<String, Texture>();

	public static void load(String... keys) {
		for (String key : keys)
			textures.put(key, new Texture(key));
	}

	public static Texture get(String key) {
		return textures.get(key);
	}

	public static Texture createDisk(String key, int radius, Color color) {
		int dRadius = 2 * radius;

		Pixmap pixmap = new Pixmap(dRadius, dRadius, Pixmap.Format.RGBA8888);
		pixmap.setColor(color);
		pixmap.fillCircle(radius, radius, radius);

		Texture texture = new Texture(pixmap);

		pixmap.dispose();

		textures.put(key, texture);

		return texture;
	}

	public static Texture createRect(String key, int width, int height, Color color) {
		Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
		pixmap.setColor(color);
		pixmap.fillRectangle(0, 0, width, height);

		Texture texture = new Texture(pixmap);

		pixmap.dispose();

		textures.put(key, texture);

		return texture;
	}

	public static void dispose() {
		for (Texture texture : textures.values())
			texture.dispose();
	}

	private TextureUtils() {
	}
}
