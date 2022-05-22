package org.rloop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import java.util.ArrayList;

import static java.lang.Math.abs;

public class ChasingMonster extends Monster{

    double pastAngle = 1080;
    float speedMonst = 3;
    float damageMonst = (float) 0.05;

    public ChasingMonster(float x, float y, Room room, Player player){
        this.player = player;
        this.room = room;
        this.x = x;
        this.y = y;

        stateTime = 0;
        Texture texture = new Texture("player/player.png");  //TODO: change Texture
        TextureRegion[][] tmp = TextureRegion.split(texture,
                texture.getWidth() / 4,
                texture.getHeight() / 4);
        walkAnimation = new ArrayList<>();
        for(int i=0;i<4;i++){
            walkAnimation.add(new Animation<>(0.25f, tmp[i]));
        }

        definePhysics();
    }

    @Override
    public void definePhysics(){

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
    @Override
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

        float playerX = this.player.getBody().getPosition().x;
        float playerY = this.player.getBody().getPosition().y;
        float myX = this.getBody().getPosition().x;
        float myY = this.getBody().getPosition().y;

        Vector2 direction = new Vector2(playerX-myX, playerY-myY);

        double angle = Math.atan(direction.y * direction.y / direction.x * direction.x);

        angle *= 180;
        angle /= Math.PI;

        if(playerX < myX && playerY < myY){
            angle += 180;
        }
        else if(playerX < myX){
                angle += 90;
        }
        else if(playerY < myY){
            angle += 270;
        }
        if(abs(pastAngle - angle) > 5) {
            if ((angle >= 0 && angle <= 45) || (angle >= 315 && angle <= 360)) {
                this.setDirection(2);
            } else if (angle > 45 && angle <= 135) {
                this.setDirection(0);
            } else if (angle > 135 && angle <= 225) {
                this.setDirection(3);
            } else {
                this.setDirection(1);
            }
        }
        pastAngle = angle;

        float kaf = direction.x * direction.x + direction.y * direction.y;
        kaf = (speedMonst * speedMonst) / kaf;
        direction.x *= Math.sqrt(kaf);
        direction.y *= Math.sqrt(kaf);
        this.getBody().setLinearVelocity(direction);
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
