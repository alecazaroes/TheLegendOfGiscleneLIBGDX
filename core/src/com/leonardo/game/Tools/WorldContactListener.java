package com.leonardo.game.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.leonardo.game.Sprites.InteractiveTileObject;
import com.leonardo.game.Sprites.Player;

public class WorldContactListener implements ContactListener {
    private Player player;

    public WorldContactListener(Player player){
        this.player = player;
    }
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        if(fixA.getUserData() == "bodyPlayer" || fixB.getUserData() == "bodyPlayer"){
            Fixture bodyPlayer = fixA.getUserData() == "bodyPlayer" ? fixA : fixB;
            Fixture object = bodyPlayer == fixA ? fixB : fixA;

            if(object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())){
                ((InteractiveTileObject) object.getUserData()).onSwordHit();
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
    /**
     * Fixture fixA = contact.getFixtureA();
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
     */
}
