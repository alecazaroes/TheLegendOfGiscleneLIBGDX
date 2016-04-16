package com.leonardo.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.leonardo.game.GameManager;

//Classe que irá implementar os metodos de Screen
public class PlayScreen implements Screen{
    private GameManager game;
    //Cria variaveis que irão gerenciar a camera do jogo
    private OrthographicCamera gamecam;
    private Viewport gamePort;

    //Variaveis do TileMap
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //No construtor fazemos os valores serem atribuidos as variaveis
    //Dizemos que a texture será a badlogic.jpg
    public PlayScreen(){
        //Pega a referencia da instacia da classe
        this.game = GameManager.getInstance();
        //Cria camera que seguira o jogador o jogo todo
        gamecam = new OrthographicCamera();

        //Gerencia como ira funcionar o resize da tela
        gamePort = new StretchViewport(game.getWidth(), game.getHeight(), gamecam);

        //Tiled Map
        maploader = new TmxMapLoader();
        map = maploader.load("link_house.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
    }
    @Override
    public void show() {
    }
    //Metodo para pegar qualqeur input do player
    public void handleInput(float dt){
        //move a camera para a direita
        if(Gdx.input.isTouched())
            gamecam.position.x += 100 * dt;
    }

    //Metodo para atualizar informações da classe
    public void update(float dt){
        handleInput(dt);
        //Chama o Metodo Update da camera
        gamecam.update();
        //Diz que o render deve ser apenas o campo de visão da camera
        renderer.setView(gamecam);
    }
    @Override
    /*
        Primeiro Limpamos a tela com o ClearColor
        Depois usamos o BufferBit para normalizar a tela
        o batch.begin() indica que ali começa a renderização das imagens tudo que estiver entre begin, end sera desenhado;
        draw desenha a texture na posição 0, 0 da tela
     */
    public void render(float delta) {
        //Chama o metodo update
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Chama o metodo render do renderer
        renderer.render();

        //Seta qual projexão sera usada pelo Batch
        game.batch.setProjectionMatrix(gamecam.combined);
    }

    @Override
    public void resize(int width, int height) {
        //Atualiza as dimenções do gamePort
        game.setWidth(width);
        game.setHeight(height);
        gamePort.update(game.getWidth(), game.getHeight());
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

    }
}
