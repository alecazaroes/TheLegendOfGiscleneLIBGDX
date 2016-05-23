package com.leonardo.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.leonardo.game.GameManager;

public class Chest extends InteractiveTileObject {
    public Chest(World _world, TiledMap _map, Rectangle _bounds){
        super(_world, _map, _bounds);
        fixture.setUserData(this);
        setCategoryFilter(GameManager.CHEST_BIT);
    }

    @Override
    public void onSwordHit() {
        Gdx.app.log("Chest", "Colisao");
        setCategoryFilter(GameManager.OPENED);
        for (TiledMapTileLayer.Cell cell: getCell()) {
            cell.setTile(null);
        }
    }
}
