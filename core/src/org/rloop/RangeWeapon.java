package org.rloop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class RangeWeapon extends Weapon{
        float projectileSpeed;

        public RangeWeapon(float dam, float speed, float prSpeed, Player player){
            super(dam, speed);
            this.player = player;
            projectileSpeed = prSpeed;
        }

        @Override
        public void attack(Player player, float cursorX, float cursorY){

            float playerX = Gdx.graphics.getWidth() * 1f/2;
            float playerY = Gdx.graphics.getHeight() * 1f/2;

            Vector2 direction = new Vector2(cursorX - playerX, -1 * (cursorY - playerY));

            float angle = (float) Util.GetAngle(playerX, cursorY, cursorX, playerY); angle += 180;

            RangeWeaponProjectile proj = new RangeWeaponProjectile(player.x, player.y, player.level, player, direction, this, angle);


            proj.projectileDamage = this.weaponDamage;

            proj.getProjectileSpeed = this.projectileSpeed;


            player.level.game.mainScreen.projectilesNotRender.add(proj);
        }

    @Override
    public TextureRegion getTexture() {
        return player.level.game.resources.magickBook;
    }
}
