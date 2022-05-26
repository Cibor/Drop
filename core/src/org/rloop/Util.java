package org.rloop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Fixture;
import jdk.internal.net.http.common.Pair;

import java.awt.*;

public class Util {

    public static int rnd(int min, int max) {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }
    public static int monitorResolutionY(int y){
        return Math.round(Gdx.graphics.getHeight() * 1f/1080 * y);
    }
    public static int monitorResolutionX(int x){
        return Math.round(Gdx.graphics.getWidth() * 1f/1920 * x);
    }

    public static boolean isOverlapingVec(Fixture a, Fixture b){
        if(Math.pow(a.getShape().getRadius() + b.getShape().getRadius(), 2) >= Math.pow(Math.abs(a.getBody().getLocalCenter().x - b.getBody().getLocalCenter().x),2) +  Math.pow(Math.abs(a.getBody().getLocalCenter().y - b.getBody().getLocalCenter().y),2)) {
            return true;
        }
        else{
            return false;
        }
    }

    protected static double GetAngle(float MachineX, float MachineY, float DestinationX, float DestinationY)
    {
        return Math.atan2(DestinationY - MachineY, DestinationX - MachineX) * 180 / Math.PI;
    }
}
