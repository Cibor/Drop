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
    protected Room room;
    protected float x;
    protected float y;
    protected ArrayList<Animation<TextureRegion>> walkAnimation;
    protected int direction;
    float MAX_VELOCITY = 5;
    float stateTime;

    public Player(float x, float y, Room room){
        this.room = room;
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

        definePhysics();
    }

    void definePhysics(){

        BodyDef def = new BodyDef();
        def.fixedRotation = true;
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(x, y);
        body = room.getWorld().createBody(def);

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
        this.room.getCamera().update();
        this.room.getViewport().apply();
        this.room.getGame().getBatch().setProjectionMatrix(room.getCamera().combined);
        this.room.getGame().getBatch().begin();
        room.getGame().getBatch().draw(currentFrame, x - 1, y - 1, 2, 2);
        this.room.getGame().getBatch().end();

        stateTime += Gdx.graphics.getDeltaTime();

        float velx = getBody().getLinearVelocity().x;
        float vely = getBody().getLinearVelocity().y;
        Vector2 vel = this.getBody().getLinearVelocity();

        if (velx == 0 && vely == 0) {
            stateTime = 0;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            this.setDirection(3);
            velx = -10f;
        } else if (velx == -10f) {
            velx = 0;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            this.setDirection(2);
            velx = 10f;
        } else if (velx == 10f) {
            velx = 0;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W) && vel.y > -MAX_VELOCITY) {
            this.setDirection(0);
            vely = 10f;
        } else if (vely == 10f) {
            vely = 0;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S) && vel.y < MAX_VELOCITY) {
            this.setDirection(1);
            vely = -10f;
        } else if (vely == -10f) {
            vely = 0;
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

    void renderPaused(){
        this.setX(this.getBody().getPosition().x);
        this.setY(this.getBody().getPosition().y);
        float velx = getBody().getLinearVelocity().x;
        float vely = getBody().getLinearVelocity().y;
        if (velx == 0 && vely == 0) {
            stateTime = 0;
        }
        TextureRegion currentFrame = walkAnimation.get(direction).getKeyFrame(stateTime, true);
        this.room.getCamera().update();
        this.room.getViewport().apply();
        this.room.getGame().getBatch().setProjectionMatrix(room.getCamera().combined);
        this.room.getGame().getBatch().begin();
        room.getGame().getBatch().draw(currentFrame, x - 1, y - 1, 2, 2);
        this.room.getGame().getBatch().end();
    }

}



