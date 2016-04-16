package com.leonardo.game.desktop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.leonardo.game.GameManager;

public class DesktopLauncher {
	public static void main (String[] arg) {
        GameManager gameManager = GameManager.getInstance();
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(gameManager, config);
        //Configura tamanhos iniciais da tela
		config.width = gameManager.getWidth();
		config.height = gameManager.getHeight();
	}
}
