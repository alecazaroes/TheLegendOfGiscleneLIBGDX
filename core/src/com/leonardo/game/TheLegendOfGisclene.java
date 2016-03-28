package com.leonardo.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.leonardo.game.Screens.PlayScreen;

//Classe irá extender de Game
public class TheLegendOfGisclene extends Game {
	//Variavel public que terá todo o conjunto de sprites que sera desenhado
	public SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		//Seta a Screen atual para ser PlayScreen e passa como parametro ele mesmo
		this.setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		//Chama o metodo Render de Game
		super.render();
	}
}
