package org.rloop.Tiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import org.rloop.Level;

public abstract class Tile {
    protected Texture texture;
    final protected boolean isBarrier;

    final public static int WIDTH = 1;
    final public static int HEIGHT = 1;

    final protected int x;
    final protected int y;

    final protected Level level;

    public Tile(Texture texture, boolean isBarrier, int x, int y, Level level){
        this.texture = texture;
        this.isBarrier = isBarrier;

        this.x = x;
        this.y = y;

        this.level = level;

        if (this.isBarrier)
            createBarrier();
    }

    protected void createBarrier() {
        BodyDef barrierDef = new BodyDef();
        barrierDef.position.set(new Vector2(x, y));
        Body barrier = level.getWorld().createBody(barrierDef);

        PolygonShape barrierBox = new PolygonShape();
        barrierBox.setAsBox(WIDTH, HEIGHT);
        barrier.createFixture(barrierBox, 0.0f).setUserData(this);
        barrierBox.dispose();
    }

    public void render() {
        level.getGame().getBatch().draw(texture, x-1, y-1, 2*WIDTH, 2*HEIGHT);
    }

    public void update(){}

    public float getX(){
        return x;
    }
    public float getY(){
        return y;
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
