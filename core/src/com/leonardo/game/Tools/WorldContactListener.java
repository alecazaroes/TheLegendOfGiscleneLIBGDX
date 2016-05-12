package com.leonardo.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.leonardo.game.Sprites.Octorock;
import com.leonardo.game.Sprites.Player;

/**
 * Created by PC Casa on 01/05/2016.
 */
public class WorldContactListener implements ContactListener {
    private Player player;

    public WorldContactListener(Player player){
        this.player = player;
    }
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        if(fixA.getUserData() == "areaOfAttack" || fixB.getUserData() == "areaOfAttack"){
            Fixture areaOfAttack = fixA.getUserData() == "areaOfAttack" ? fixA : fixB;
            Fixture object = areaOfAttack == fixA ? fixB : fixA;

            if(object.getUserData() != null && Octorock.class.isAssignableFrom(object.getUserData().getClass())){
                if(player.isAttackSword()){
                    //Gdx.app.log("Player Attack", "OctoRock");
                }else{
                    //Gdx.app.log("Octorock Attack", "Player");
                }
                ((Octorock) object.getUserData()).onHit();
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        //Gdx.app.log("End Contact", "");
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
