package com.leonardo.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.leonardo.game.GameManager;

public class Vaso extends InteractiveTileObject {
    public Vaso(World _world, TiledMap _map, Rectangle _bounds){
        super(_world, _map, _bounds);
        fixture.setUserData(this);
        setCategoryFilter(GameManager.VASO_BIT);
    }

    @Override
    public void onSwordHit() {
        Gdx.app.log("Vaso", "Colisao");
        setCategoryFilter(GameManager.DESTROYED_BIT);
        for (TiledMapTileLayer.Cell cell: getCell()) {
            cell.setTile(null);
        }
    }
}
