package com.leonardo.game.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.leonardo.game.GameManager;

import java.util.ArrayList;

public abstract class InteractiveTileObject {
    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;
    protected Fixture fixture;

    public InteractiveTileObject(World _world, TiledMap _map, Rectangle _bounds){
        this.world  = _world;
        this.map    = _map;
        this.bounds = _bounds;

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        //Seta a posição do objeto
        bdef.position.set((_bounds.getX() + _bounds.getWidth() / 2) / GameManager.getInstance().getPPM(), (_bounds.getY() + _bounds.getHeight() / 2) / GameManager.getInstance().getPPM());
        //getObjects().add(new Node_Grid(new Vector2(_bounds.getX(), _bounds.getY()), new Vector2(_bounds.getX() + _bounds.getWidth(),_bounds.getY()+ _bounds.getHeight()), false));
        body = world.createBody(bdef);

        shape.setAsBox(_bounds.getWidth() / 2 / GameManager.getInstance().getPPM(), _bounds.getHeight() / 2 / GameManager.getInstance().getPPM());
        fdef.shape = shape;
        fdef.isSensor = false;
        fixture = body.createFixture(fdef);
    }

    public abstract void onSwordHit();

    public void setCategoryFilter(short filterBit){
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }

    public ArrayList<TiledMapTileLayer.Cell> getCell(){
        ArrayList<TiledMapTileLayer.Cell> cells = new ArrayList<TiledMapTileLayer.Cell>();
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("objects");
        float fator = 8 / GameManager.getInstance().getPPM();
        cells.add(layer.getCell(
                (int)((body.getPosition().x - fator) * GameManager.getInstance().getPPM() / 8),
                (int)((body.getPosition().y) * GameManager.getInstance().getPPM() / 8)));
        cells.add(layer.getCell(
                (int)((body.getPosition().x) * GameManager.getInstance().getPPM() / 8),
                (int)((body.getPosition().y) * GameManager.getInstance().getPPM() / 8)));
        cells.add(layer.getCell(
                (int)((body.getPosition().x - fator) * GameManager.getInstance().getPPM() / 8),
                (int)((body.getPosition().y - fator) * GameManager.getInstance().getPPM() / 8)));
        cells.add(layer.getCell(
                (int)((body.getPosition().x) * GameManager.getInstance().getPPM() / 8),
                (int)((body.getPosition().y - fator) * GameManager.getInstance().getPPM() / 8)));

        System.out.println(cells.size());
        return cells;
    }
}
