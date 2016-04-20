package com.leonardo.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.leonardo.game.Screens.Menu;
import com.leonardo.game.Screens.ScreenGame;

//Classe irá extender de Game
public class GameManager extends Game {
	//Batch é a variavel que armazenara todas as imagens a serem desenhadas no jogo e irá organiza-las de modo a deixar mais otimizado a renderização
	public SpriteBatch batch;
	//Largura e altura da tela
	public static final int V_WIDTH = 240;
	public static final int V_HEIGHT = 160;
        public static final float PPM = 100;

	@Override
	public void create () {
		batch = new SpriteBatch();
		//Seta a Screen atual para ser ScreenGame e passa como parametro ele mesmo
		this.setScreen(new Menu(this));
	}

	@Override
	public void render () {
		//Chama o metodo Render de Game
		super.render();
	}
}
