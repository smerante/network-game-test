package com.smerante.network.game.test.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.smerante.network.game.test.Source;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Source.screenW;
		config.height = Source.screenH;
		new LwjglApplication(new Source(), config);
	}
}
