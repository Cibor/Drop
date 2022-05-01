package org.rloop;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public class Player {
    private int x;
    private int y;
    Body player;
    public Player(int x, int y, World world){
        this.x = x;
        this.y = y;
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(x, y);

        player = world.createBody(def);
    }

}

