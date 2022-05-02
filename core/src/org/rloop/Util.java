package org.rloop;

import com.badlogic.gdx.Gdx;

public class Util {

    protected static int monitorResolutionY(int y){
        return Math.round(Gdx.graphics.getHeight() * 1f/1080 * y);
    }
    protected static int monitorResolutionX(int x){
        return Math.round(Gdx.graphics.getWidth() * 1f/1920 * x);
    }

}
