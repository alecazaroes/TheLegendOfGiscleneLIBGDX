/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leonardo.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.leonardo.game.GameManager;
import com.leonardo.game.Screens.GameScreen;

/**
 *
 * @author leonardo.fpinheiro1
 */
public class Player extends Sprite{
    private boolean drawRegion;
    private enum State {
        MOVE_UP, MOVE_DOWN, MOVE_LEFT, MOVE_RIGHT,
        STAND_UP, STAND_DOWN, STAND_LEFT, STAND_RIGHT,
        ATTACKSWORD_UP, ATTACKSWORD_DOWN, ATTACKSWORD_LEFT, ATTACKSWORD_RIGHT,
        ATTACKBOOMERANGUE_UP, ATTACKBOOMERANGUE_DOWN, ATTACKBOOMERANGUE_LEFT, ATTACKBOOMERANGUE_RIGHT
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
    private float velY = 0;
    private float stateTimer;
    private GameScreen screen;

    //Animações
    private boolean inAnimation;
    private TextureRegion playerStandDown;
    private TextureRegion playerStandUp;
    private TextureRegion playerStandRight;
    private TextureRegion directionStand;
    private Animation playerMoveRight;
    private Animation playerMoveUp;
    private Animation playerMoveDown;
    private Animation playerAttackSwordRight;
    private Animation playerAttackSwordUp;
    private Animation playerAttackSwordDown;
    private Animation playerAttackBoomerangueRight;
    private Animation playerAttackBoomerangueUp;
    private Animation playerAttackBoomerangueDown;
    private TextureAtlas atlas;
    //Ataques
    private boolean attackSword;
    private boolean attackBoomerangue;

    public Player(World world, GameScreen screen){
        atlas = new TextureAtlas("AnimationsPlayer\\Player.pack");
        this.setRegion(getAtlas().findRegion("Stands/stand_strip3"));
        this.screen     = screen;
        currentState    = State.STAND_DOWN;
        previousState   = State.STAND_DOWN;
        directionEye    = DirectionEye.DOWN;
        drawRegion      = true;

        stateTimer      = 0;
        attackSword     = false;
        inAnimation     = false;

        playerMoveDown          = loadAnimation("Move_Down/move_down_strip10", 10, 0.07f, 0,49, 45);
        playerMoveRight         = loadAnimation("Move_Right/move_right_strip10", 10, 0.07f, 0,49, 45);
        playerMoveUp            = loadAnimation("Move_Up/move_up_strip10", 10, 0.07f, 0,49, 45);

        playerAttackSwordDown   = loadAnimation("Attack_Down/attack_down_strip8", 8, 0.05f, 0,49, 45);
        playerAttackSwordRight  = loadAnimation("Attack_Right/attack_right_strip8", 8, 0.05f, 0, 49, 45);
        playerAttackSwordUp     = loadAnimation("Attack_Up/attack_up_strip8", 8, 0.05f, 0, 49, 45);

        playerStandDown         = loadTexture("Stands/stand_strip3", 0, 0, 49, 45);
        playerStandRight        = loadTexture("Stands/stand_strip3", 49, 0, 49, 45);
        playerStandUp           = loadTexture("Stands/stand_strip3", 98, 0, 49, 45);

        this.world = world;
        definePlayer();
        directionStand          = playerStandDown;
        if(drawRegion) {
            setRegion(playerStandDown);
        }
    }

    public Player getPlayer(){
        return this;
    }
    public Animation loadAnimation(String region, int amountFrames, float timePerFrame, int startY, int width, int height){
        //Carrega animações do player
        Animation animationLoad;
        this.setRegion(this.getAtlas().findRegion(region));
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

    public void update(float deltatime){
        TextureRegion region = getFrame(deltatime);
        if(drawRegion) {
            setRegion(region);
        }
        setPosition(b2body.getPosition().x, b2body.getPosition().y);
        /*
        setBounds(float x, float y, float width, float height)
        Sets the position and size of the sprite when drawn, before scaling and rotation are applied.
        */
        //setBounds(b2body.getPosition().x-(getWidth()/2), b2body.getPosition().y-(getHeight()/2-0.08f), 49 / GameManager.getInstance().getPPM(), 45 / GameManager.getInstance().getPPM());
        setBounds(b2body.getPosition().x-(getWidth()/2), b2body.getPosition().y-(getHeight()/2-0.08f), 49 / GameManager.getInstance().getPPM(), 45 / GameManager.getInstance().getPPM());
        this.getBody().setLinearVelocity(this.velX, this.velY);

    }

    public TextureRegion getFrame(float deltatime){
        previousState = currentState;
        currentState = getState();
        TextureRegion region;
        switch (currentState) {
            //Movimentar para baixo
            case MOVE_DOWN:
                region = playerMoveDown.getKeyFrame(stateTimer, true);
                break;
            //Movimentar para cima
            case MOVE_UP:
                region = playerMoveUp.getKeyFrame(stateTimer, true);
                break;
            //Movimentar para direita
            case MOVE_LEFT:
                if (!playerMoveRight.getKeyFrame(stateTimer, true).isFlipX()) {
                    playerMoveRight.getKeyFrame(stateTimer, true).flip(true, false);
                }
                region = playerMoveRight.getKeyFrame(stateTimer, true);
                break;
            //Movimentar para a esquerda
            case MOVE_RIGHT:
                if (playerMoveRight.getKeyFrame(stateTimer, true).isFlipX()) {
                    playerMoveRight.getKeyFrame(stateTimer, true).flip(true, false);
                }
                region = playerMoveRight.getKeyFrame(stateTimer, true);
                break;
            //Ataque de espada para baixo
            case ATTACKSWORD_DOWN:
                inAnimation = true;
                region = playerAttackSwordDown.getKeyFrame(stateTimer, true);
                break;
            //Ataque de espada para cima
            case ATTACKSWORD_UP:
                inAnimation = true;
                region = playerAttackSwordUp.getKeyFrame(stateTimer, true);
                break;
            //Ataque de espada para direita
            case ATTACKSWORD_LEFT:
                if (!playerAttackSwordRight.getKeyFrame(stateTimer, true).isFlipX()) {
                    playerAttackSwordRight.getKeyFrame(stateTimer, true).flip(true, false);
                }
                inAnimation = true;
                region = playerAttackSwordRight.getKeyFrame(stateTimer, true);
                break;
            //Ataque de espada para esquerda
            case ATTACKSWORD_RIGHT:
                if (playerAttackSwordRight.getKeyFrame(stateTimer, true).isFlipX()) {
                    playerAttackSwordRight.getKeyFrame(stateTimer, true).flip(true, false);
                }
                inAnimation = true;
                region = playerAttackSwordRight.getKeyFrame(stateTimer, true);
                break;
            case STAND_DOWN:
                region = playerStandDown;
                break;
            case STAND_UP:
                region = playerStandUp;
                break;
            case STAND_LEFT:
                if (!playerStandRight.isFlipX()) {
                    playerStandRight.flip(true, false);
                }
                region = playerStandRight;
                break;
            case STAND_RIGHT:
                if (playerStandRight.isFlipX()) {
                    playerStandRight.flip(true, false);
                }
                region = playerStandRight;
                break;
            default:
                region = directionStand;
                break;
        }
        stateTimer = currentState == previousState ? stateTimer + deltatime : 0;
        if(playerAttackSwordDown.isAnimationFinished(stateTimer) && playerAttackSwordUp.isAnimationFinished(stateTimer) && playerAttackSwordRight.isAnimationFinished(stateTimer)){
            this.attackSword = false;
            inAnimation = false;
        }
        return region;
    }

    public State getState(){
        State state;
        state = previousState;
        switch (directionEye){
            case UP:
                if(isAttackSword()){
                    state = State.ATTACKSWORD_UP;
                }else if(this.velY == 1){
                    state = State.MOVE_UP;
                }else {
                    state = State.STAND_UP;
                }
                break;
            case DOWN:
                if(isAttackSword()){
                    state = State.ATTACKSWORD_DOWN;
                }else if(this.velY == -1){
                    state = State.MOVE_DOWN;
                }else {
                    state = State.STAND_DOWN;
                }
                break;
            case LEFT:
                if(isAttackSword()){
                    state = State.ATTACKSWORD_LEFT;
                }else if(this.velX == -1){
                    state = State.MOVE_LEFT;
                }else {
                    state = State.STAND_LEFT;
                }
                break;
            case RIGHT:
                if(isAttackSword()){
                    state = State.ATTACKSWORD_RIGHT;
                }else if(this.velX == 1){
                    state = State.MOVE_RIGHT;
                }else {
                    state = State.STAND_RIGHT;
                }
                break;
        }
        return state;
    }

    public void definePlayer(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(40 / GameManager.getInstance().getPPM(),40 / GameManager.getInstance().getPPM());
        b2body = world.createBody(bdef);
        this.velX = 1 / GameManager.getInstance().getPPM();
        this.velY = 1 / GameManager.getInstance().getPPM();
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(4f / GameManager.getInstance().getPPM(), 4 / GameManager.getInstance().getPPM());
        fdef.filter.categoryBits = GameManager.getInstance().getPlayerBit();
        fdef.filter.maskBits =
                GameManager.DEFAULT_BIT|
                GameManager.CHEST_BIT |
                GameManager.VASO_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData("bodyPlayer");

        //Area de attack
        CircleShape areaOfAttack = new CircleShape();
        areaOfAttack.setRadius(20f / GameManager.getInstance().getPPM());
        areaOfAttack.setPosition(new Vector2(areaOfAttack.getPosition().x, areaOfAttack.getPosition().y + (6 / GameManager.getInstance().getPPM())));
        fdef.shape = areaOfAttack;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData("areaOfAttack");
    }

    public Body getBody() {
        return b2body;
    }

    public boolean isAttackSword() {
        return attackSword;
    }

    public void setAttackSword(boolean attackSword) {
        this.attackSword = attackSword;
    }

    public boolean isAttackBoomerangue() {
        return attackBoomerangue;
    }

    public void setAttackBoomerangue(boolean attackBoomerangue) {
        this.attackBoomerangue = attackBoomerangue;
    }

    public void moveUp(){
        if(!inAnimation) {
            this.directionEye = DirectionEye.UP;
            this.velY = 1;
        }
    }
    public void moveDown(){
        if(!inAnimation) {
            this.directionEye = DirectionEye.DOWN;
            this.velY = -1;
        }
    }
    public void moveLeft(){
        if (!inAnimation){
            this.directionEye = DirectionEye.LEFT;
            this.velX = -1;
        }
    }
    public void moveRight(){
        if (!inAnimation) {
            this.directionEye = DirectionEye.RIGHT;
            this.velX = 1;
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
}
