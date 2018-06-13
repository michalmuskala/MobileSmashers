package com.mobilesmashers.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.mobilesmashers.MobileSmashers;
import com.mobilesmashers.stages.GameStage;

public class GameScreen extends ScreenAdapter {

	private GameStage stage;

	public GameScreen(MobileSmashers game) {
		stage = new GameStage(game);
	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act();
		stage.draw();
	}

	@Override
	public void dispose() {
		stage.dispose();
	}
}
