package com.leonardo.game.A_Star;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.leonardo.game.GameManager;
import com.leonardo.game.Tools.B2WorldCreator;
import com.leonardo.game.Tools.Node_Grid;

import java.util.ArrayList;

public class Grid {
    private TiledMap map;
    private World world;
    public Vector2 gridWorldSize;
    public float nodeRadius;
    public Node[][] grid;
    public ArrayList<Node> path;

    int gridSizeX, gridSizeY;

    public Pathfinding pathfinding;

    public B2WorldCreator b2WorldCreator;
    public Grid(World _world, TiledMap _map, B2WorldCreator _b2WorldCreator, float _nodeRadius, int largura, int altura) {
        this.world = _world;
        this.gridWorldSize = new Vector2(largura, altura);
        this.nodeRadius = _nodeRadius;
        this.map = _map;
        this.b2WorldCreator = _b2WorldCreator;
        gridSizeX = (int) (gridWorldSize.x / nodeRadius);
        gridSizeY = (int) (gridWorldSize.y / nodeRadius);
        System.out.printf("X: "+gridSizeX+" Y: "+gridSizeY);
        CreateGrid();
        pathfinding = new Pathfinding(this);
    }

    public void CreateGrid() {
        grid = new Node[gridSizeX][gridSizeY];

        for (int x = 0; x < gridSizeX; x++) {
            for (int y = 0; y < gridSizeY; y++) {
                Vector2 worldPoint = new Vector2((x * nodeRadius) / GameManager.getInstance().getPPM(), (y * nodeRadius) / GameManager.getInstance().getPPM());
                grid[x][y] = new Node(foundNodeNotWalkable(x,y), worldPoint, x, y);
            }
        }
        /*
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();

        Body body;
        //Desenha os objetos
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                if(!grid[x][y].walkable) {
                    Rectangle rect = new Rectangle(getNode(x, y).worldPosition.x, getNode(x, y).worldPosition.y, nodeRadius, nodeRadius);
                    bdef.type = BodyDef.BodyType.KinematicBody;
                    //Seta a posição do objeto
                    bdef.position.set((rect.getX() + rect.getWidth() / 2), (rect.getY() + rect.getHeight() / 2));

                    body = world.createBody(bdef);

                    shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
                    fdef.shape = shape;
                    fdef.isSensor = true;
                    body.createFixture(fdef);
                }
            }
        }
         */
    }

    public ArrayList<Node> GetNeighbours(Node node) {
        ArrayList<Node> neighbours = new ArrayList<Node>();

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (x == 0 && y == 0)
                    continue;

                int checkX = node.gridX + x;
                int checkY = node.gridY + y;

                if (checkX >= 0 && checkX < gridSizeX && checkY >= 0 && checkY < gridSizeY) {
                    neighbours.add(grid[checkX][checkY]);
                }
            }
        }
        return neighbours;
    }

    public Node NodeFromWorldPoint(Vector2 worldPosition) {
        int x = (int) ((worldPosition.x * GameManager.getInstance().getPPM()) / nodeRadius);
        int y = (int) ((worldPosition.y * GameManager.getInstance().getPPM()) / nodeRadius);
        return getNode(x, y);
    }

    public ArrayList<Node> Find(Vector2 seekPos, Vector2 targetPos) {
        pathfinding.FindPath(seekPos, targetPos);
        return path;
    }

    public Node getNode(int x, int y) {
        return grid[x][y];
    }

    public boolean foundNodeNotWalkable(int x, int y) {
        for (Node_Grid node: this.b2WorldCreator.getObjects()) {
            if(x >= (int)(node.getStartworldPosition().x / nodeRadius)
                    && x <= (int)(node.getEndsworldPosition().x / nodeRadius)
                    && y >= (int)(node.getStartworldPosition().y / nodeRadius)
                    && y <= (int)(node.getEndsworldPosition().y / nodeRadius)){
                return node.isWalkable();
            }
        }
        return true;
    }

}

