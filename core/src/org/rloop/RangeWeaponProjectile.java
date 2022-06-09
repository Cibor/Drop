package org.rloop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import static java.lang.Math.abs;

public class RangeWeaponProjectile extends ShootingMonsterProjectile{

    static TextureRegion projTexture = new TextureRegion(new Texture("projectilePlayer.png"));

    public RangeWeaponProjectile(float x, float y, Level level, Player player, Vector2 direction,Object spawner, float angle){
        super(x, y, level, player, direction,spawner, angle);
        texture = projTexture;
    }

    @Override
    public void makeDamagePlayer(Player player){

    }
}
