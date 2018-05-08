package com.mobilesmashers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class Sound {
    private static Music music;
    private static float volume = 0f;

    private Sound() {
    }

    public void setVolume(float value) {
        this.volume = value;
    }

    public static float getVolume() {
        return volume;
    }

    public static void playShootLine() {
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/shootLine.mp3"));
        music.setVolume(volume);
        music.play();
    }

    public static void playLineBack() {
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/lineBack.mp3"));
        music.setVolume(volume);
        music.play();
    }

    public static void playBallsCatched() {
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/catchedBalls.mp3"));
        music.setVolume(volume);
        music.play();
    }

    public static void playPlayerDestroyed() {
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/explosion.wav"));
        music.setVolume(volume);
        music.play();
    }

    public static void lowerVolume() {
        if (volume > 0.1f) {
            volume -= 0.1f;
        }
    }

    public static void increaseVolume() {
        if (volume < 1f) {
            volume += 0.1f;
        }
    }
}
