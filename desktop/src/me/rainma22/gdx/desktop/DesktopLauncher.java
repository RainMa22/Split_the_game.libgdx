package me.rainma22.gdx.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import me.rainma22.gdx.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width=1600;
		config.height=900;
		config.fullscreen=true;
		config.title="Split!";
		new LwjglApplication(new Main(), config);
	}
}
