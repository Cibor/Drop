package org.rloop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Fixture;

public class Util {

    protected static int monitorResolutionY(int y){
        return Math.round(Gdx.graphics.getHeight() * 1f/1080 * y);
    }
    protected static int monitorResolutionX(int x){
        return Math.round(Gdx.graphics.getWidth() * 1f/1920 * x);
    }

    protected static boolean isOverlapingVec(Fixture a, Fixture b){
        if(Math.pow(a.getShape().getRadius() + b.getShape().getRadius(), 2) >= Math.pow(Math.abs(a.getBody().getLocalCenter().x - b.getBody().getLocalCenter().x),2) +  Math.pow(Math.abs(a.getBody().getLocalCenter().y - b.getBody().getLocalCenter().y),2)) {
            return true;
        }
        else{
            return false;
        }
    }


}
