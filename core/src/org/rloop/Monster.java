package org.rloop;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;

import java.util.ArrayList;

public abstract class Monster {
    public Body body;
    protected Fixture fixture;
    protected Level level;
    Player player;
    protected float x;
    protected float y;
    protected ArrayList<Animation<TextureRegion>> walkAnimation;
    protected int direction;

    float MAX_VELOCITY = 5;
    float stateTime;

    float damageMonst;
    float speedMonst;
    float hpMonst;

    public Monster(float x, float y, Level level, Player player){
        this.player = player;
        this.level = level;
        this.x = x;
        this.y = y;

        stateTime = 0;
    }

    public void definePhysics() {

        BodyDef def = new BodyDef();
        def.fixedRotation = true;
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(x, y);
        body = level.getWorld().createBody(def);

        PolygonShape square = new PolygonShape();
        square.setAsBox(0.65f, 0.85f);
        FixtureDef fixtureDef = new FixtureDef();

        fixtureDef.shape = square;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.2f;
        fixtureDef.restitution = 0f;

        fixture = this.body.createFixture(fixtureDef);
        fixture.setUserData(this);
        square.dispose();
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setDirection(int y) {
        this.direction = y;
    }

    public Body getBody() {
        return body;
    }

    public void getHit(float hit) {
        hpMonst -= hit;
        if (hpMonst <= 0) {
            level.game.mainScreen.monstersDied.add(this);
        }
        lastDamaged = System.currentTimeMillis();
    }

    long immuneTime = 100;
    long lastDamaged = System.currentTimeMillis();

    public boolean isImmune(){
        return (System.currentTimeMillis() - lastDamaged < immuneTime);
    }


    public void update(float x) {}

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
    }


}
