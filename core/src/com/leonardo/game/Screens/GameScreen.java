package com.leonardo.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.leonardo.game.A_Star.Grid;
import com.leonardo.game.A_Star.Node;
import com.leonardo.game.GameManager;
import com.leonardo.game.Sprites.Octorock;
import com.leonardo.game.Sprites.Player;
import com.leonardo.game.Tools.B2WorldCreator;
import com.leonardo.game.Tools.WorldContactListener;

import java.util.ArrayList;

//Classe que irá implementar os metodos de Screen
public class GameScreen implements Screen{
    private enum State {
      UP_LEFT, UP_RIGHT, UP,
        DOWN_LEFT, DOWN_RIGHT, DOWN
    };
    public State currentStateCam;
    private Grid grid;
    //Cria variaveis que irão gerenciar a camera do jogo
    private OrthographicCamera gamecam;
    private Viewport gamePort;

    //Variaveis do TileMap
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private Rectangle screenDimensions;

    private World world;
    private Box2DDebugRenderer b2dr;
    
    private Player player;
    private Octorock enemie;

    //Variavel privada para carrega Textura
    private ArrayList<Node> path = new ArrayList<Node>();

    B2WorldCreator b2Creator;
    //No construtor fazemos os valores serem atribuidos as variaveis
    public GameScreen(){
        currentStateCam = State.DOWN;
        //Cria camera que seguira o jogador o jogo todo
        gamecam = new OrthographicCamera();

        //Gerencia como ira funcionar o resize da tela
        gamePort = new StretchViewport(GameManager.getInstance().getWidth() / GameManager.getInstance().getPPM(), GameManager.getInstance().getHeight() / GameManager.getInstance().getPPM(), gamecam);

        //Tiled Map
        maploader = new TmxMapLoader();
        //defineScreen("South_Hyrule_Field.tmx");
        //defineScreen("link_house.tmx");
        defineScreen("teste.tmx");
    }

    public void defineScreen(String level){
        map = maploader.load(level);
        renderer = new OrthogonalTiledMapRenderer(map, 1 / GameManager.getInstance().getPPM());
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0,0), true);
        b2dr = new Box2DDebugRenderer();
        b2Creator = new B2WorldCreator(world, map);

        player = new Player(world, this);
        enemie = new Octorock(world, this);

        world.setContactListener(new WorldContactListener(player));
        MapObject object = map.getLayers().get("Screen").getObjects().get(0);
        screenDimensions = ((RectangleMapObject) object).getRectangle();
        System.out.println(screenDimensions.getWidth());
        System.out.println(screenDimensions.getHeight());
        System.out.println(player.getBody().getPosition());
        grid = new Grid(world, map, b2Creator, 8, (int)(screenDimensions.getWidth()),(int)(screenDimensions.getHeight()));
    }

    public Vector2 getPosCam(Vector2 camPos, Vector2 playerPos){
        float fatorX = 120f / GameManager.getInstance().getPPM();
        float fatorY = 80f  / GameManager.getInstance().getPPM();
        Vector2 posFinal = new Vector2(playerPos);

        //X esquerda
        if(playerPos.x <= fatorX){
            posFinal.x = fatorX;
        }
        //X direita
        if(playerPos.x >= screenDimensions.getWidth() / GameManager.getInstance().getPPM() - fatorX){
            posFinal.x = screenDimensions.getWidth() / GameManager.getInstance().getPPM() - fatorX;
        }
        //Y inferior
        if(playerPos.y <= fatorY){
            posFinal.y = fatorY;
        }
        //Y superior
        if(playerPos.y >= screenDimensions.getHeight() / GameManager.getInstance().getPPM() - fatorY){
            posFinal.y = screenDimensions.getHeight() / GameManager.getInstance().getPPM() - fatorY;
        }

        return posFinal;
    }
    @Override
    public void show() {
    }
    //Metodo para pegar qualqeur input do player
    public void handleInput(float dt){
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            player.moveUp();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            player.moveDown();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            player.moveLeft();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            player.moveRight();
        }
        if(!Gdx.input.isKeyPressed(Input.Keys.UP) && !Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            player.stopedPlayerY();
        }
        if(!Gdx.input.isKeyPressed(Input.Keys.LEFT) && !Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            player.stopedPlayerX();
        }
        if(Gdx.input.isKeyJustPressed(62)){
            player.stopedPlayer();
            player.setAttackSword(true);
        }
        enemie.setAttack(true);
    }

    //Metodo para atualizar informações da classe
    public void update(float dt){
        Vector2 posCam;
        handleInput(dt);

        world.step(1 / 60f, 6, 2);

        player.update(dt);
        path = grid.Find(enemie.b2body.getPosition(), new Vector2(player.b2body.getPosition().x + (5/GameManager.getInstance().getPPM()),player.b2body.getPosition().y + (5/GameManager.getInstance().getPPM())));
        enemie.update(dt, path, player);
        posCam = getPosCam(new Vector2(gamecam.position.x, gamecam.position.y), new Vector2( player.getX() + (player.getWidth() / 2), player.getY()+ (player.getHeight() / 2 - 0.08f)));
        gamecam.position.set(posCam.x, posCam.y, 0);
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

        b2dr.render(world, gamecam.combined);
        //Seta qual projexão sera usada pelo Batch
        GameManager.getInstance().batch.setProjectionMatrix(gamecam.combined);
        GameManager.getInstance().batch.begin();
        enemie.draw(GameManager.getInstance().batch);
        player.draw(GameManager.getInstance().batch);
        GameManager.getInstance().batch.end();
    }

    @Override
    public void resize(int width, int height) {
        //Atualiza as dimenções do gamePort
        gamePort.update(width, height);
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
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
    }
}
