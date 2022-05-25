package org.rloop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import static java.lang.Math.abs;

public class RangeWeaponProjectile extends ShootingMonsterProjectile{

    static Texture projTexture = new Texture("projectilePlayer.png");

    public RangeWeaponProjectile(float x, float y, Level level, Player player, Vector2 direction, float angle){
        super(x, y, level, player, direction, angle);
        texture = projTexture;
    }

}
