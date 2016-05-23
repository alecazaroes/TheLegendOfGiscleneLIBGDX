package com.leonardo.game.Sprites;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;

/**
 * Created by elerson.gmarabini1 on 17/05/2016.
 */
public class Door {
    private Vector2 initPosition;
    private String stageNext;
    public Fixture fixture;

    public Door(Vector2 initPosition, String stageNext, Fixture _fixture) {
        this.initPosition = initPosition;
        this.stageNext = stageNext;
        this.fixture = fixture;
        this.fixture.setUserData(this);
    }

    public Vector2 getInitPosition() {
        return initPosition;
    }

    public void setInitPosition(Vector2 initPosition) {
        this.initPosition = initPosition;
    }

    public String getStageNext() {
        return stageNext;
    }

    public void setStageNext(String stageNext) {
        this.stageNext = stageNext;
    }
}
