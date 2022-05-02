package org.rloop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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

    public Player(float x, float y, Room room){
        this.room = room;
        this.x = x;
        this.y = y;

        Texture texture = new Texture("player/player.png");
        TextureRegion[][] tmp = TextureRegion.split(texture,
                texture.getWidth() / 4,
                texture.getHeight() / 4);
        walkAnimation = new ArrayList<>();
        for(int i=0;i<4;i++){
            walkAnimation.add(new Animation<>(0.25f, tmp[i]));
        }

        BodyDef def = new BodyDef();
        def.fixedRotation = true;
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(x, y);
        body = room.getWorld().createBody(def);

        PolygonShape square = new PolygonShape();
        square.setAsBox(1, 1);
        FixtureDef fixtureDef = new FixtureDef();

        fixtureDef.shape = square ;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.2f;
        fixtureDef.restitution = 0f;

        fixture = this.body.createFixture(fixtureDef);
        fixture.setUserData(this);
        square.dispose();
    }
    public void render(float stateTime){
        TextureRegion currentFrame = walkAnimation.get(direction).getKeyFrame(stateTime, true);
        this.room.getCamera().update();
        this.room.getViewport().apply();
        this.room.getGame().getBatch().setProjectionMatrix(room.getCamera().combined);
        this.room.getGame().getBatch().begin();
        room.getGame().getBatch().draw(currentFrame, x-1, y-1,2,2);
        this.room.getGame().getBatch().end();

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

}

