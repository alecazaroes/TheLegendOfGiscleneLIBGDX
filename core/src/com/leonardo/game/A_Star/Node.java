package com.leonardo.game.A_Star;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Node {
    public boolean walkable;
    public Vector2 worldPosition;
    public int gridX;
    public int gridY;

    public int gCost;
    public int hCost;
    public Node parent;

    private Texture nodeText;

    public Node(boolean _walkable, Vector2 _worldPos, int _gridX, int _gridY){
        this.walkable = _walkable;
        this.worldPosition = _worldPos;
        this.gridX = _gridX;
        this.gridY = _gridY;
        nodeText = new Texture("node.png");
    }

    public int fCost(){
        return gCost + hCost;
    }
}
