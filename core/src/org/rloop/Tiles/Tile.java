package org.rloop.Tiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import org.rloop.Room;

public abstract class Tile {
    final protected Texture texture;
    final protected boolean isBarrier;

    final public static int WIDTH = 1;
    final public static int HEIGHT = 1;

    final protected int x;
    final protected int y;

    final protected Room room;

    public Tile(Texture texture, boolean isBarrier, int x, int y, Room room){
        this.texture = texture;
        this.isBarrier = isBarrier;

        this.x = x;
        this.y = y;

        this.room = room;

        if (this.isBarrier)
            createBarrier();
    }

    protected void createBarrier() {
        BodyDef barrierDef = new BodyDef();
        barrierDef.position.set(new Vector2(x, y));
        Body barrier = room.getWorld().createBody(barrierDef);

        PolygonShape barrierBox = new PolygonShape();
        barrierBox.setAsBox(WIDTH, HEIGHT);
        barrier.createFixture(barrierBox, 0.0f);
        barrierBox.dispose();
    }

    public void render() {
        room.getGame().getBatch().draw(texture, x-1, y-1, 2*WIDTH, 2*HEIGHT);
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
