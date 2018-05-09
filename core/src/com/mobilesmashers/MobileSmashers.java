package com.mobilesmashers;

import com.badlogic.gdx.Game;
import com.mobilesmashers.screens.GameScreen;

public class MobileSmashers extends Game {

	@Override
	public void create() {
		setScreen(new GameScreen());
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		super.dispose();
	}
}
