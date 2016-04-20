/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leonardo.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.leonardo.game.GameManager;
import com.leonardo.game.Screens.GameScreen;

/**
 *
 * @author leonardo.fpinheiro1
 */
public class Player extends Sprite{
    public World world;
    public Body b2body;
    public float velX;
    public float velY;
    private TextureRegion playerStand;
    
    public Player(World world, GameScreen screen){
        super(screen.getAtlas().findRegion("sprite_sheet_player"));
        this.world = world;
        definePlayer();
        playerStand = new TextureRegion(getTexture(), 0,0, 19,26);
        setBounds(0,0,19 / GameManager.getInstance().getPPM(), 26 / GameManager.getInstance().getPPM());
        setRegion(playerStand);
    }

    public void update(float deltattime){
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getWidth() / 2);
    }
    
    public void definePlayer(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / GameManager.getInstance().getPPM(),32 / GameManager.getInstance().getPPM());
        this.velX = 32 / GameManager.getInstance().getPPM();
        this.velY = 32 / GameManager.getInstance().getPPM();
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(6 / GameManager.getInstance().getPPM(), 6 / GameManager.getInstance().getPPM());
        
        fdef.shape = shape;
        b2body.createFixture(fdef);
    }
}
