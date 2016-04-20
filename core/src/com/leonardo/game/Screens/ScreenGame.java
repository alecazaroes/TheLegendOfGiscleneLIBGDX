package com.leonardo.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.leonardo.game.GameManager;
import com.leonardo.game.Sprites.Player;


/**
 * Created by PC Casa on 28/03/2016.
 */
//Classe que irá implementar os metodos de Screen
public class ScreenGame implements Screen{
    //Variavel privada para referenciar a classe GameManager
    private GameManager game;
    private TextureAtlas atlas;

    //Cria variaveis que irão gerenciar a camera do jogo
    private OrthographicCamera gamecam;
    private Viewport gamePort;

    //Variaveis do TileMap
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private World world;
    private Box2DDebugRenderer b2dr;
    
    private Player player;
    //No construtor fazemos os valores serem atribuidos as variaveis
    public ScreenGame(GameManager game){
        atlas = new TextureAtlas("Player_animation.pack");

        this.game = game;
        //Cria camera que seguira o jogador o jogo todo
        gamecam = new OrthographicCamera();

        //Gerencia como ira funcionar o resize da tela
        gamePort = new StretchViewport(GameManager.V_WIDTH / GameManager.PPM, GameManager.V_HEIGHT / GameManager.PPM, gamecam);

        //Tiled Map
        maploader = new TmxMapLoader();
        map = maploader.load("link_house.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / GameManager.PPM);
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
        
        world = new World(new Vector2(0,0), true);
        b2dr = new Box2DDebugRenderer();
        
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;
        
        player = new Player(world, this);
        
        //Desenha os objetos bloqueados
        for (MapObject object : map.getLayers().get("blocked").getObjects()) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            
            bdef.type = BodyDef.BodyType.StaticBody;
            //Seta a posição do objeto
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / GameManager.PPM, (rect.getY() + rect.getHeight() / 2) / GameManager.PPM);
            
            body = world.createBody(bdef);
            
            shape.setAsBox(rect.getWidth() / 2 / GameManager.PPM, rect.getHeight() / 2 / GameManager.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
        
        //Desenha as portas
        for (MapObject object : map.getLayers().get(3).getObjects()) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            
            bdef.type = BodyDef.BodyType.StaticBody;
            //Seta a posição do objeto
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / GameManager.PPM, (rect.getY() + rect.getHeight() / 2) / GameManager.PPM);
            
            body = world.createBody(bdef);
            
            shape.setAsBox(rect.getWidth() / 2 / GameManager.PPM, rect.getHeight() / 2 / GameManager.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
    }

    public TextureAtlas getAtlas(){
        return atlas;
    }
    
    @Override
    public void show() {
    }
    //Metodo para pegar qualqeur input do player
    public void handleInput(float dt){
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            player.velY = 1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            player.velY = -1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            player.velX = -1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            player.velX = 1;
        }
        if(!Gdx.input.isKeyPressed(Input.Keys.UP) && !Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            player.velY = 0;
        }
        if(!Gdx.input.isKeyPressed(Input.Keys.LEFT) && !Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            player.velX = 0;
        }
        player.b2body.setLinearVelocity(player.velX, player.velY);
    }

    //Metodo para atualizar informações da classe
    public void update(float dt){
        handleInput(dt);
        
        world.step(1/60f, 6, 2);

        player.update(dt);
        
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

        b2dr.render(world, gamecam.combined);
        game.batch.setProjectionMatrix(gamecam.combined);
        //Seta qual projexão sera usada pelo Batch
        //game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);
        game.batch.end();
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
        
    }
}
