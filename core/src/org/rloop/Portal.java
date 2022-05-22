package org.rloop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Portal {
    static int WIDTH = 2;
    static int HEIGHT = 2;
    Texture texture;
    int x;
    int y;
    Room start, end;
    Body body;
    Fixture fixture;


    public Portal(int x, int y, Room start, Room end){
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.start = start;
        this.end = end;
        definePhysics();
    }

    void definePhysics(){

        BodyDef def = new BodyDef();
        def.fixedRotation = true;
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(x, y);
        body = start.getWorld().createBody(def);

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

    public void render() {
        start.getGame().getBatch().draw(texture, x-1, y-1, 2*WIDTH, 2*HEIGHT);
    }

    public Texture getTexture() {
        return texture;
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }
}
