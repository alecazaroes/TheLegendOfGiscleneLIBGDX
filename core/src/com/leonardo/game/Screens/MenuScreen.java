package com.leonardo.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.leonardo.game.GameManager;
import com.leonardo.game.States.State;

public class MenuScreen extends State implements Screen {
    //Variavel privada para referenciar a classe GameManager
    private GameManager game;

    //Variavel privada para carrega Textura
    private Texture backgrouund;

    //Cria variaveis que ir„o gerenciar a camera do jogo
    private OrthographicCamera gamecam;
    private Viewport gamePort;

    public MenuScreen() {
        //background recebe imagem menu
        backgrouund = new Texture("menu.jpg");
        this.game = GameManager.getInstance();

        //Cria camera que seguira o jogador o jogo todo
        gamecam = new OrthographicCamera();

        //Gerencia como ira funcionar o resize da tela
        gamePort = new StretchViewport(game.getWidth(), game.getHeight(), gamecam);
    }

    @Override
    public void show() {

    }

    //Metodo para pegar qualqeur input do player
    public void handleInput(float dt){
        //se apertar ESPA«O abre a proxima Screen
        if(Gdx.input.isKeyJustPressed(62)) {
            game.setScreen(new GameScreen());
            this.hide();
        }
    }

    //Metodo para atualizar informa√ß√µes da classe
    public void update(float dt){
        handleInput(dt);
        //Chama o Metodo Update da camera
        gamecam.update();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(backgrouund, 0, 0, game.getWidth(), game.getHeight());
        game.batch.end();
        update(delta);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        this.dispose();
    }
}
