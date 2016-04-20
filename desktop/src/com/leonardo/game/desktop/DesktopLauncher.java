package com.leonardo.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.leonardo.game.GameManager;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new GameManager(), config);
        //Configura tamanhos iniciais da tela
		config.width = GameManager.V_WIDTH;
		config.height = GameManager.V_HEIGHT;
		config.fullscreen = false;
	}
}
