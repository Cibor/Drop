package org.rloop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import java.awt.*;

public class RangeWeapon extends Weapon{
        float projectileSpeed;

        public RangeWeapon(float dam, float speed, float prSpeed){
            super(dam, speed);
            projectileSpeed = prSpeed;
        }

        @Override
        public void attack(Player player, float cursorX, float cursorY){

            System.out.println(cursorX + " " + cursorY);
            float playerX = Gdx.graphics.getWidth() * 1f/2;
            float playerY = Gdx.graphics.getHeight() * 1f/2;

            Vector2 direction = new Vector2(cursorX - playerX, -1 * (cursorY - playerY));

            float angle = (float) Util.GetAngle(playerX, cursorY, cursorX, playerY); angle += 180;

            RangeWeaponProjectile proj = new RangeWeaponProjectile(player.x, player.y, player.room, player, direction, angle);


            proj.damageMonst = this.weaponDamage;

            proj.speedMonst = this.projectileSpeed;


            player.room.game.mainScreen.monstersNotRender.add(proj);
        }
}
