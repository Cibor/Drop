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

public class ShootingMonsterProjectile extends Monster{

    Vector2 direction;

    float angle;

    Texture texture;

    public ShootingMonsterProjectile(float x, float y, Room room, Player player, Vector2 direction, float angle){
        this.player = player;
        this.room = room;
        this.x = x;
        this.y = y;

        this.direction = direction;
        this.speedMonst = 7;
        this.damageMonst = (float) 0.1;
        this.angle = angle;


        float kaf = direction.x * direction.x + direction.y * direction.y;
        kaf = (speedMonst * speedMonst) / kaf;
        direction.x *= Math.sqrt(kaf);
        direction.y *= Math.sqrt(kaf);

        stateTime = 0;
        texture = new Texture("ProjectileTexture.png");  //TODO: change Texture
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
        square.setAsBox(0.3f, 0.3f);
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
        this.room.getCamera().update();
        this.room.getViewport().apply();
        this.room.getGame().getBatch().setProjectionMatrix(room.getCamera().combined);
        this.room.getGame().getBatch().begin();
        room.getGame().getBatch().draw(new TextureRegion(texture), x - 1, y - 1, 1, 1, 2, 2, 1, 1, angle);
        this.room.getGame().getBatch().end();

        stateTime += Gdx.graphics.getDeltaTime();

        this.getBody().setLinearVelocity(direction);

    }
    public void setX(float x){
        this.x = x;
    }

    public void setY(float y){
        this.y = y;
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
        this.room.getCamera().update();
        this.room.getViewport().apply();
        this.room.getGame().getBatch().setProjectionMatrix(room.getCamera().combined);
        this.room.getGame().getBatch().begin();
        room.getGame().getBatch().draw(new TextureRegion(texture), x - 1, y - 1, 1, 1, 2, 2, 1, 1, angle);
        this.room.getGame().getBatch().end();
    }

}
