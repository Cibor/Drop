package org.rloop;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;

import java.util.ArrayList;

public abstract class Monster {
    protected Body body;
    protected Fixture fixture;
    protected Room room;
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

    void render(){}
    void definePhysics(){}
    void renderPaused(){}
}
