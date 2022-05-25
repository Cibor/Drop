package org.rloop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.util.ArrayList;

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

    int damageImmune;

    Weapon playerWeapon = null;

    protected float statCurrentHP;
    protected float statMaxHP;
    protected float statSpeed;

    int attackCoolDown = 0;

    public Player(float x, float y, Level level){
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

        playerWeapon = new RangeWeapon(0.1f, 1, 2);

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
    public void render(){
        this.setX(this.getBody().getPosition().x);
        this.setY(this.getBody().getPosition().y);
        TextureRegion currentFrame = walkAnimation.get(direction).getKeyFrame(stateTime, true);
        this.level.getCamera().update();
        this.level.getViewport().apply();
        this.level.getGame().getBatch().setProjectionMatrix(level.getCamera().combined);
        this.level.getGame().getBatch().begin();
        level.getGame().getBatch().draw(currentFrame, x - 1, y - 1, 2, 2);
        this.level.getGame().getBatch().end();

        stateTime += Gdx.graphics.getDeltaTime();

        float velx = getBody().getLinearVelocity().x;
        float vely = getBody().getLinearVelocity().y;
        Vector2 vel = this.getBody().getLinearVelocity();

        if (velx == 0 && vely == 0) {
            stateTime = 0;
        }

        if(attackCoolDown > 0){
            attackCoolDown --;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            this.setDirection(3);
            velx = (-7.5f) * statSpeed;
        } else if (velx == (-7.5f) * statSpeed) {
            velx = 0;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            this.setDirection(2);
            velx = (7.5f) * statSpeed;
        } else if (velx == (7.5f) * statSpeed) {
            velx = 0;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W) && vel.y > -MAX_VELOCITY) {
            this.setDirection(0);
            vely = (7.5f) * statSpeed;
        } else if (vely == (7.5f) * statSpeed) {
            vely = 0;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S) && vel.y < MAX_VELOCITY) {
            this.setDirection(1);
            vely = (-7.5f) * statSpeed;
        } else if (vely == (-7.5f) * statSpeed) {
            vely = 0;
        }

        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            if(attackCoolDown == 0){
                attackCoolDown = Math.round(playerWeapon.getWeaponAttackSpeed() * 1);
                playerWeapon.attack(this, Gdx.input.getX(), Gdx.input.getY());
            }
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.U)){
            statSpeed += 0.02f;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.H)){
            statCurrentHP -= 0.02f;
        }

        if (velx != getBody().getLinearVelocity().x && vely != getBody().getLinearVelocity().y) {
            stateTime = 0;
        }

        this.getBody().setLinearVelocity(velx, vely);
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

    public boolean isImmune(){
        return damageImmune > 0;
    }

    public void makeImmune(){
        damageImmune = 120;
    }

    void renderPaused(){
        this.setX(this.getBody().getPosition().x);
        this.setY(this.getBody().getPosition().y);
        float velx = getBody().getLinearVelocity().x;
        float vely = getBody().getLinearVelocity().y;
        if (velx == 0 && vely == 0) {
            stateTime = 0;
        }
        TextureRegion currentFrame = walkAnimation.get(direction).getKeyFrame(stateTime, true);
        this.level.getCamera().update();
        this.level.getViewport().apply();
        this.level.getGame().getBatch().setProjectionMatrix(level.getCamera().combined);
        this.level.getGame().getBatch().begin();
        level.getGame().getBatch().draw(currentFrame, x - 1, y - 1, 2, 2);
        this.level.getGame().getBatch().end();
    }

}



