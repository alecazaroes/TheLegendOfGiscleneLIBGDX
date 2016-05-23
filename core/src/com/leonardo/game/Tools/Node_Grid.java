package com.leonardo.game.Tools;

import com.badlogic.gdx.math.Vector2;

public class Node_Grid {
    private boolean walkable;
    private Vector2 startworldPosition;
    private Vector2 endsworldPosition;

    public Node_Grid(Vector2 _worldPosition, Vector2 _endsworldPosition, boolean _walkable) {
        this.startworldPosition = _worldPosition;
        this.walkable = _walkable;
        this.endsworldPosition = _endsworldPosition;
    }

    public boolean isWalkable() {
        return walkable;
    }

    public void setWalkable(boolean _walkable) {
        this.walkable = _walkable;
    }

    public Vector2 getStartworldPosition() {
        return startworldPosition;
    }

    public void setStartworldPosition(Vector2 startworldPosition) {
        this.startworldPosition = startworldPosition;
    }

    public Vector2 getEndsworldPosition() {
        return endsworldPosition;
    }

    public void setEndsworldPosition(Vector2 endsworldPosition) {
        this.endsworldPosition = endsworldPosition;
    }
}
