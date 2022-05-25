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
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;

import static java.lang.Math.abs;

public class RangeWeaponProjectile extends ShootingMonsterProjectile{

    static Texture projTexture = new Texture("projectilePlayer.png");

    public RangeWeaponProjectile(float x, float y, Room room, Player player, Vector2 direction, float angle){
        super(x, y, room, player, direction, angle);
        texture = projTexture;
    }

}
