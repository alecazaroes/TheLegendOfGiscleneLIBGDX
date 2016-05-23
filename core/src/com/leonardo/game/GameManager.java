package com.leonardo.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.leonardo.game.Screens.MenuScreen;

//Classe irá extender de Game
public class GameManager extends Game {
	//Na criação da classe ela ja se auto referencia fazendo com que ela nunca seja criada uma segunda vez (Singleton)
	private static GameManager gameManager = new GameManager();

	//Batch é a variavel que armazenara todas as imagens a serem desenhadas no jogo e irá organiza-las de modo a deixar mais otimizado a renderização
	public SpriteBatch batch;
	//Largura e altura da tela
	private int WIDTH = 240;
	private int HEIGHT = 160;
	private float PPM = 100f;

    public static final short DEFAULT_BIT = 1;
    public static final short PLAYER_BIT = 2;
    public static final short VASO_BIT = 4;
    public static final short CHEST_BIT = 8;
    public static final short DESTROYED_BIT = 16;
    public static final short OPENED = 32;

	//Construtor
	public GameManager() {
	}
	//Esse metodo faz parte do Singleton, nele você consegue pegar a instancia do primeiro e unico objeto criado
	public static GameManager getInstance(){
		return gameManager;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		//Seta a Screen atual para ser MenuScreen
		this.setScreen(new MenuScreen());
	}

	@Override
	public void render () {
		//Chama o metodo Render de Game
		super.render();
	}

	//Pega a largura da tela
	public int getWidth() {
		return gameManager.WIDTH;
	}
	//Pega a altura da tela
	public int getHeight() {
		return gameManager.HEIGHT;
	}

    public float getPPM(){
        return gameManager.PPM;
    }

    public void setPPM(float PPM) {
        gameManager.PPM = PPM;
    }

    //Seta a largura da tela
	public void setWidth(int width) {
		gameManager.WIDTH = width;
	}

	//Seta a altura da tela
	public void setHeight(int height) {
		gameManager.HEIGHT = height;
	}

    public short getDefaultBit() {
        return DEFAULT_BIT;
    }

    public short getPlayerBit() {
        return PLAYER_BIT;
    }

    public short getVasoBit() {
        return VASO_BIT;
    }

    public short getDestroyedBit() {
        return DESTROYED_BIT;
    }

    public short getChestBit() {
        return CHEST_BIT;
    }

    public short getOPENED() {
        return OPENED;
    }
}
