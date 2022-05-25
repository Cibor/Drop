package org.rloop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class MeleeWeapon extends Weapon{

    float length;
    float width;

    public MeleeWeapon(float dam, float speed, float length){
        super(dam, speed);
        this.length = length;
        this.width = length / 10;
    }

    @Override
    public void attack(Player player, float cursorX, float cursorY){

        Vector3 vec = new Vector3(cursorX, cursorY,0);
        vec = player.level.getCamera().unproject(vec);

        Vector2 direction = new Vector2(vec.x - player.getX(), vec.y - player.getY());

        MeleeWeaponProjectile proj = new MeleeWeaponProjectile(player.x, player.y, player.level, player, direction, this);

        player.level.game.mainScreen.projectilesNotRender.add(proj);
    }

    @Override
    public void specialAttack(Player player, float cursorX, float cursorY){}
}
