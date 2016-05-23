package com.leonardo.game.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.leonardo.game.GameManager;

import java.util.ArrayList;

/**
 * Created by PC Casa on 01/05/2016.
 */
public class B2WorldCreator {
    private World world;
    private TiledMap map;
    private ArrayList<Node_Grid> objects;

    public B2WorldCreator(World world, TiledMap map){
        this.world = world;
        this.map = map;
        this.objects = new ArrayList<Node_Grid>();
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();

        Body body;
        createBody("blocked", false);
        //createBody("door", true);//door
    }

    private void createBody(String _layer, boolean _walkable){
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();

        Body body;
        //Desenha os objetos bloqueados
        for (MapObject object : map.getLayers().get(_layer).getObjects()) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();


            bdef.type = BodyDef.BodyType.StaticBody;
            //Seta a posição do objeto
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / GameManager.getInstance().getPPM(), (rect.getY() + rect.getHeight() / 2) / GameManager.getInstance().getPPM());
            //getObjects().add(new Node_Grid(new Vector2(rect.getX(), rect.getY()), new Vector2(rect.getWidth(), rect.getHeight()), _walkable));
            getObjects().add(new Node_Grid(new Vector2(rect.getX(), rect.getY()), new Vector2(rect.getX() + rect.getWidth(),rect.getY()+ rect.getHeight()), _walkable));
            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / GameManager.getInstance().getPPM(), rect.getHeight() / 2 / GameManager.getInstance().getPPM());
            fdef.shape = shape;
            fdef.isSensor = false;
            body.createFixture(fdef);
        }
    }
    private void createBody(int layer){
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();

        Body body;
        //Desenha as portas
        for (MapObject object : map.getLayers().get(layer).getObjects()) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            //Seta a posição do objeto
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / GameManager.getInstance().getPPM(), (rect.getY() + rect.getHeight() / 2) / GameManager.getInstance().getPPM());

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / GameManager.getInstance().getPPM(), rect.getHeight() / 2 / GameManager.getInstance().getPPM());
            fdef.shape = shape;
            fdef.isSensor = false;
            body.createFixture(fdef);
        }
    }

    public ArrayList<Node_Grid> getObjects() {
        return objects;
    }
}
