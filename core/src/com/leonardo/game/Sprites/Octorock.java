/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leonardo.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.leonardo.game.A_Star.Node;
import com.leonardo.game.GameManager;
import com.leonardo.game.Screens.GameScreen;

import java.util.ArrayList;

/**
 *
 * @author leonardo.fpinheiro1
 */
public class Octorock extends Sprite{
    public enum State {
        MOVE_UP, MOVE_DOWN, MOVE_LEFT, MOVE_RIGHT,
        ATTACK_UP, ATTACK_DOWN, ATTACK_LEFT, ATTACK_RIGHT
    };
    public State currentState;
    public State previousState;

    public enum DirectionEye{
        UP, DOWN, LEFT, RIGHT
    }
    public DirectionEye directionEye;
    public World world;
    public Body b2body;
    private float velX;
    private float velY;
    private float projVelX;
    private float projVelY;
    private float stateTimer;
    private GameScreen screen;
    private TextureAtlas atlas;
    //Animações
    private boolean inAnimation;
    private Animation octoMoveRight;
    private Animation octoMoveUp;
    private Animation octoMoveDown;
    private Animation octoAttackRight;
    private Animation octoAttackUp;
    private Animation octoAttackDown;
    private Animation projetil;

    //
    public Fixture fixture;
    //Ataques
    private boolean attack;

    public Octorock(World world, GameScreen screen){
        atlas = new TextureAtlas("Enemies\\OctoRock\\Octorock.pack");
        this.setRegion(getAtlas().findRegion("Attack_Down/attack_down_strip2"));

        currentState = State.MOVE_DOWN;
        previousState = State.MOVE_DOWN;
        directionEye    = DirectionEye.DOWN;

        stateTimer = 0;
        attack = false;
        inAnimation = false;

        octoMoveDown        = loadAnimation("Move_Down/move_down_strip2", 2, 0.5f, 0, 26, 22);
        octoMoveRight       = loadAnimation("Move_Right/move_right_strip2", 2, 0.5f, 0,26, 22);
        octoMoveUp          = loadAnimation("Move_Up/move_up_strip2", 2, 0.5f, 0, 26, 22);
        octoAttackDown      = loadAnimation("Attack_Down/attack_down_strip2", 2, 0.5f, 0, 26, 22);
        octoAttackRight     = loadAnimation("Attack_Right/attack_right_strip2", 2, 0.5f, 0, 26, 22);
        octoAttackUp        = loadAnimation("Attack_Up/attack_up_strip2", 2, 0.5f, 0, 26, 22);
        projetil            = loadAnimation("Projeteis/projeteis_strip3", 3, 0.5f, 0, 26, 22);

        this.world = world;
        defineOctorock();
    }

    public Animation loadAnimation(String region, int amountFrames, float timePerFrame, int startY, int width, int height){
        //Carrega animações do player
        Animation animationLoad;
        this.setRegion(getAtlas().findRegion(region));
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 0; i < amountFrames; i++){
            frames.add(new TextureRegion(getTexture(), i * width, startY, width, height));
        }
        animationLoad = new Animation(timePerFrame, frames);
        frames.clear();
        return animationLoad;
    }

    public TextureRegion loadTexture(String region, int starX, int startY, int width, int height){
        TextureRegion textureLoad;
        this.setRegion(getAtlas().findRegion(region));
        textureLoad = new TextureRegion(getTexture(), starX, startY, width, height);
        return textureLoad;
    }

    public void update(float deltatime, ArrayList<Node> path, Player player){
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getWidth() / 2f);
        TextureRegion region = getFrame(deltatime);
        setRegion(region);
        if(path.size() >= 1) {
            Node caminho = path.get(0);
            if (b2body.getPosition().x < caminho.worldPosition.x) {
                this.moveRight();
            }
            if (b2body.getPosition().x > caminho.worldPosition.x) {
                this.moveLeft();
            }
            if (b2body.getPosition().y > caminho.worldPosition.y) {
                this.moveDown();
            }
            if (b2body.getPosition().y < caminho.worldPosition.y) {
                this.moveUp();
            }

            if (b2body.getPosition().y == caminho.worldPosition.x && b2body.getPosition().y == caminho.worldPosition.y) {
                path.remove(0);
            }
        }
        /*
        setBounds(float x, float y, float width, float height)
        Sets the position and size of the sprite when drawn, before scaling and rotation are applied.
        */
        setBounds(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getWidth() / 2.5f
                , region.getRegionWidth() / GameManager.getInstance().getPPM(), region.getRegionHeight() / GameManager.getInstance().getPPM());
        this.getBody().setLinearVelocity(this.velX, this.velY);
    }

    public TextureRegion getFrame(float deltatime){
        previousState = currentState;
        currentState = getState();
        TextureRegion region;
        switch (currentState) {
            //Movimentar para baixo
            case MOVE_DOWN:
                region = octoMoveDown.getKeyFrame(stateTimer, true);
                break;
            //Movimentar para cima
            case MOVE_UP:
                region = octoMoveUp.getKeyFrame(stateTimer, true);
                break;
            //Movimentar para direita
            case MOVE_LEFT:
                if (!octoMoveRight.getKeyFrame(stateTimer, true).isFlipX()) {
                    octoMoveRight.getKeyFrame(stateTimer, true).flip(true, false);
                }
                region = octoMoveRight.getKeyFrame(stateTimer, true);
                break;
            //Movimentar para a esquerda
            case MOVE_RIGHT:
                if (octoMoveRight.getKeyFrame(stateTimer, true).isFlipX()) {
                    octoMoveRight.getKeyFrame(stateTimer, true).flip(true, false);
                }
                region = octoMoveRight.getKeyFrame(stateTimer, true);
                break;
            //Atacar para baixo
            case ATTACK_DOWN:
                region = octoAttackDown.getKeyFrame(stateTimer, true);
                break;
            //Atacar para cima
            case ATTACK_UP:
                region = octoAttackUp.getKeyFrame(stateTimer, true);
                break;
            //Atacar para direita
            case ATTACK_LEFT:
                if (!octoAttackRight.getKeyFrame(stateTimer, true).isFlipX()) {
                    octoAttackRight.getKeyFrame(stateTimer, true).flip(true, false);
                }
                region = octoAttackRight.getKeyFrame(stateTimer, true);
                break;
            //Atacar para a esquerda
            case ATTACK_RIGHT:
                if (octoAttackRight.getKeyFrame(stateTimer, true).isFlipX()) {
                    octoAttackRight.getKeyFrame(stateTimer, true).flip(true, false);
                }
                region = octoAttackRight.getKeyFrame(stateTimer, true);
                break;
            default:
                region = octoMoveDown.getKeyFrame(stateTimer, true);
                break;
        }
        stateTimer = currentState == previousState ? stateTimer + deltatime : 0;
        if(octoAttackDown.isAnimationFinished(stateTimer) && octoAttackUp.isAnimationFinished(stateTimer) && octoAttackRight.isAnimationFinished(stateTimer)){
            this.attack = false;
            inAnimation = false;
        }
        return region;
    }

    public State getState(){
        State state;
        state = previousState;
        switch (directionEye){
            case UP:
                if(isAttack()){
                    state = State.ATTACK_UP;
                }else if(this.velY == 1){
                    state = State.MOVE_UP;
                }
                break;
            case DOWN:
                if(isAttack()){
                    state = State.ATTACK_DOWN;
                }else if(this.velY == -1){
                    state = State.MOVE_DOWN;
                }
                break;
            case LEFT:
                if(isAttack()){
                    state = State.ATTACK_LEFT;
                }else if(this.velX == -1){
                    state = State.MOVE_LEFT;
                }
                break;
            case RIGHT:
                if(isAttack()){
                    state = State.ATTACK_RIGHT;
                }else if(this.velX == 1){
                    state = State.MOVE_RIGHT;
                }
                break;
        }
        return state;
    }

    public void defineOctorock(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(40 / GameManager.getInstance().getPPM(),40 / GameManager.getInstance().getPPM());
        velX = 0f;
        velY = 0f;
        projVelX = 0;
        projVelY = 0;
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(4f / GameManager.getInstance().getPPM(), 4 / GameManager.getInstance().getPPM());
        
        fdef.shape = shape;
        fixture = b2body.createFixture(fdef);
        fixture.setUserData(this);

    }
    public void attack(){

    }
    public Body getBody() {
        return b2body;
    }

    public boolean isAttack() {
        return attack;
    }

    public void setAttack(boolean attack) {
        this.attack = attack;
        attack();
    }

    public void moveUp(){
        if(!inAnimation) {
            this.directionEye = DirectionEye.UP;
            this.velY = 0.5f;
        }
    }
    public void moveDown(){
        if(!inAnimation) {
            this.directionEye = DirectionEye.DOWN;
            this.velY = -0.5f;
        }
    }
    public void moveLeft(){
        if (!inAnimation){
            this.directionEye = DirectionEye.LEFT;
            this.velX = -0.5f;
        }
    }
    public void moveRight(){
        if (!inAnimation) {
            this.directionEye = DirectionEye.RIGHT;
            this.velX = 0.5f;
        }
    }
    public void stopedPlayer(){
        this.velX = 0;
        this.velY = 0;
    }
    public void stopedPlayerX(){
        this.velX = 0;
    }
    public void stopedPlayerY(){
        this.velY = 0;
    }
    public TextureAtlas getAtlas(){
        return atlas;
    }

    public void onHit(){
        Gdx.app.log("Octorock", "Colision");
    }
}
