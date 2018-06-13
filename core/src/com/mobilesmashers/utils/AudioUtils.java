package com.mobilesmashers.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Disposable;

import org.jetbrains.annotations.Contract;

import java.util.HashMap;

public final class AudioUtils {

	public static HashMap<String, AudioWrapper> audio = new HashMap<String, AudioWrapper>();

	private static double volume = Constants.DEFAULT_VOLUME;

	public static void load(String... paths) {
		for (String path : paths)
			audio.put(path, new MusicWrapper(path));
	}

	@Contract(pure = true)
	public static int getVolume() {
		return (int) volume;
	}

	public static void setVolume(int v) {
		v = Math.max(v, 0);
		v = Math.min(v, 100);
		volume = ((double) v) / 100.;
	}

	public static abstract class AudioWrapper implements Disposable {
		public double volume = 1.;

		public abstract void play();
	}

	public static class MusicWrapper extends AudioWrapper {
		private Music music;

		private MusicWrapper(String path) {
			music = Gdx.audio.newMusic(Gdx.files.internal(path));
		}

		@Override
		public void dispose() {
			music.dispose();
		}

		public void play() {
			music.play();
		}
	}

	public static void dispose() {
		for (AudioWrapper a : audio.values())
			a.dispose();
	}

	private AudioUtils() {
	}
}
