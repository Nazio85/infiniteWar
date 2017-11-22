package com.geek.rpg.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import pro.x_way.infinities_war.windows.RpgGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = RpgGame.SCREEN_WIDTH;
		config.height = RpgGame.SCREEN_HEIGHT;
		new LwjglApplication(new RpgGame(), config);
	}
}
