package com.mobilesmashers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class Sound {
    private static Music music;
    private static int volume = 0;

    private Sound() {
    }

    public void setVolume(int value) {
        this.volume = value;
    }

    public static int getVolume() {
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
        if (volume >= 1) {
            volume -= 1;
        }
    }

    public static void increaseVolume() {
        if (volume < 10) {
            volume += 1;
        }
    }
}
