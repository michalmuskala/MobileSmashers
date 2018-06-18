package com.mobilesmashers.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Disposable;

import org.jetbrains.annotations.Contract;

import java.util.HashMap;

public final class AudioUtils {

	private static HashMap<String, AudioWrapper> audio = new HashMap<String, AudioWrapper>();

	private static int volume = Constants.DEFAULT_VOLUME;

	public static void load(String... paths) {
		for (String path : paths)
			audio.put(path, new MusicWrapper(path));
	}

	public static void play(String key) {
		audio.get(key).play();
	}

	@Contract(pure = true)
	public static int getVolume() {
		return volume;
	}

	public static void setVolume(int vol) {
		vol = Math.max(vol, 0);
		vol = Math.min(vol, 100);
		volume = vol;
	}

	public static void increaseVolume() {
		volume += 10;
		if (volume > 100)
			volume = 100;
	}

	public static void decreaseVolume() {
		volume -= 10;
		if(volume < 0)
			volume= 0;
	}

	public static abstract class AudioWrapper implements Disposable {
		public float volume = 1f;

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
			music.setVolume(AudioUtils.volume / 100f);
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
