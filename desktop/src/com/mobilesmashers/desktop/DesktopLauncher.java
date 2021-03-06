package com.mobilesmashers.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mobilesmashers.MobileSmashers;
import com.mobilesmashers.utils.Constants;

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = Constants.APP_NAME;
		config.height = 800;
		config.width = 1024;
		new LwjglApplication(new MobileSmashers(), config);
	}
}
