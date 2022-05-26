package org.rloop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.util.ArrayList;
import java.util.HashSet;

public class Player {
    protected Body body;
    protected Fixture fixture;
    protected Level level;
    protected float x;
    protected float y;
    protected ArrayList<Animation<TextureRegion>> walkAnimation;
    protected int direction;
    float MAX_VELOCITY = 5;
    float stateTime;

    Weapon playerWeapon;

    protected float statCurrentHP;
    protected float statMaxHP;
    protected float statSpeed;

    public float getCurrentHP() { return statCurrentHP; }

    public float getMaxHP() { return statMaxHP; }

    public Player(float x, float y, Level level, boolean choosenWeapon){
        this.level = level;
        this.x = x;
        this.y = y;

        stateTime = 0;
        Texture texture = new Texture("player/player.png");
        TextureRegion[][] tmp = TextureRegion.split(texture,
                texture.getWidth() / 4,
                texture.getHeight() / 4);
        walkAnimation = new ArrayList<>();
        for(int i=0;i<4;i++){
            walkAnimation.add(new Animation<>(0.25f, tmp[i]));
        }

        statSpeed = 1.0f;
        statCurrentHP = 1.0f;
        statMaxHP = 1.0f;

        if(choosenWeapon) {
            playerWeapon = new MeleeWeapon(0.15f, 1, 1.3f);
        }
        else{
            playerWeapon = new RangeWeapon(0.1f, 1f, 1.3f);
            attackTime = 200;
            //TODO: change attackSpeed normally
        }
        definePhysics();

    }

    void definePhysics(){
        BodyDef def = new BodyDef();
        def.fixedRotation = true;
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(x, y);
        body = level.getWorld().createBody(def);

        PolygonShape square = new PolygonShape();
        square.setAsBox(0.65f, 0.85f);
        FixtureDef fixtureDef = new FixtureDef();

        fixtureDef.shape = square ;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.2f;
        fixtureDef.restitution = 0f;

        fixture = this.body.createFixture(fixtureDef);
        fixture.setUserData(this);
        square.dispose();
    }

    public void update() {
        this.setX(this.getBody().getPosition().x);
        this.setY(this.getBody().getPosition().y);

        calculateDamage();

        stateTime += Gdx.graphics.getDeltaTime();

        Vector2 vel = this.getBody().getLinearVelocity();

        if (vel.x == 0 && vel.y == 0) {
            stateTime = 0;
        }
        if (vel.x != getBody().getLinearVelocity().x && vel.y != getBody().getLinearVelocity().y) {
            stateTime = 0;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            this.setDirection(3);
            vel.x = (-7.5f) * statSpeed;
        } else if (vel.x == (-7.5f) * statSpeed) {
            vel.x = 0;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            this.setDirection(2);
            vel.x = (7.5f) * statSpeed;
        } else if (vel.x == (7.5f) * statSpeed) {
            vel.x = 0;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W) && vel.y > -MAX_VELOCITY) {
            this.setDirection(0);
            vel.y = (7.5f) * statSpeed;
        } else if (vel.y == (7.5f) * statSpeed) {
            vel.y = 0;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S) && vel.y < MAX_VELOCITY) {
            this.setDirection(1);
            vel.y = (-7.5f) * statSpeed;
        } else if (vel.y == (-7.5f) * statSpeed) {
            vel.y = 0;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.U)){
            statSpeed += 0.02f;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.H)){
            statCurrentHP -= 0.02f;
        }

        this.getBody().setLinearVelocity(vel);

        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            if(this.canAttack()){
                lastAttack = System.currentTimeMillis();
                playerWeapon.attack(this, Gdx.input.getX(), Gdx.input.getY());
            }
        }
    }

    TextureRegion currentFrame;
    public void render(){
        currentFrame = walkAnimation.get(direction).getKeyFrame(stateTime, true);
        this.level.getCamera().update();
        this.level.getViewport().apply();
        this.level.getGame().getBatch().setProjectionMatrix(level.getCamera().combined);
        this.level.getGame().getBatch().begin();
        level.getGame().getBatch().draw(currentFrame, x - 1, y - 1, 2, 2);
        this.level.getGame().getBatch().end();
    }

    HashSet<DamageMakerPlayer> damageMakers = new HashSet<>();

    public void addDamageMaker(DamageMakerPlayer damageMaker) {
        damageMakers.add(damageMaker);
    }
    public void removeDamageMaker(DamageMakerPlayer damageMaker) {
        damageMakers.remove(damageMaker);
    }
    long immuneTime = 1000;
    long lastDamaged = System.currentTimeMillis();

    private boolean isImmune(){
        return (System.currentTimeMillis() - lastDamaged < immuneTime);
    }

    public void calculateDamage() {
        if (!isImmune() && !damageMakers.isEmpty()) {
            for (DamageMakerPlayer damageMaker: damageMakers)
                damageMaker.makeDamagePlayer(this);
            lastDamaged = System.currentTimeMillis();
        }
    }

    long attackTime = 500;
    long lastAttack = 0;

    public boolean canAttack(){
       return !(System.currentTimeMillis() - lastAttack < attackTime);
    }

    public void setX(float x){
        this.x = x;
    }

    public void setY(float y){
        this.y = y;
    }

    public void setDirection(int y){
        this.direction = y;
    }

    public Body getBody() {
        return body;
    }

    public void getHit(float hit){
        statCurrentHP -= hit;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}



