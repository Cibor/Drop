package org.rloop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public abstract class Projectiles {
    public float projectileDamage;
    public float getProjectileSpeed;

    public Vector2 direction;
    public float angle;

    public TextureRegion texture;
    public Level level;
    public Player player;
    public float x; public float y;
    public Body body;
    public Fixture fixture;
    public Object spawner;

    public Projectiles(float x, float y, Level level,  Player player, Vector2 direction, Object spawner){
        this.player = player;
        this.level = level;
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.spawner = spawner;
    }

    public void definePhysics(float hx, float hy, Vector2 vec){
        BodyDef def = new BodyDef();
        def.fixedRotation = true;
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(vec.x, vec.y);
        body = level.getWorld().createBody(def);

        PolygonShape square = new PolygonShape();
        square.setAsBox(hx, hy, vec, 0);
        FixtureDef fixtureDef = new FixtureDef();

        fixtureDef.shape = square ;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.2f;
        fixtureDef.restitution = 0f;

        fixture = this.body.createFixture(fixtureDef);
        fixture.setUserData(this);
        square.dispose();
    }
    public void definePhysics(float hx, float hy){
        definePhysics(hx, hy, new Vector2(0,0));
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

    public void render(){}

    public void update(float x){}

}
