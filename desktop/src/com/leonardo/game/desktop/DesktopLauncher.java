package com.leonardo.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.leonardo.game.TheLegendOfGisclene;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new TheLegendOfGisclene(), config);
        //Configura tamanhos iniciais da tela
		config.width = TheLegendOfGisclene.V_WIDTH;
		config.height = TheLegendOfGisclene.V_HEIGHT;
	}
}
