package org.rloop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class TripleRangeWeapon extends RangeWeapon{

    public TripleRangeWeapon(float dam, float speed, float prSpeed, Player player){
        super(dam, speed, prSpeed, player);
    }

    @Override
    public void attack(Player player, float cursorX, float cursorY){

        float playerX = Gdx.graphics.getWidth() * 1f/2;
        float playerY = Gdx.graphics.getHeight() * 1f/2;

        Vector2 direction1 = new Vector2(cursorX - playerX, -1 * (cursorY - playerY));
        Vector2 direction2 = new Vector2((float) (direction1.x * Math.cos(Math.PI/6f) - direction1.y * Math.sin(Math.PI/6f)), (float) (direction1.x * Math.sin(Math.PI/6f) + direction1.y * Math.cos(Math.PI/6f)));
        Vector2 direction3 = new Vector2((float) (direction1.x * Math.cos(Math.PI * 2 - Math.PI/6f) - direction1.y * Math.sin(Math.PI * 2 -Math.PI/6f)), (float) (direction1.x * Math.sin(Math.PI * 2 - Math.PI/6f) + direction1.y * Math.cos(Math.PI * 2 - Math.PI/6f)));

        float angle = (float) Util.GetAngle(playerX, cursorY, cursorX, playerY); angle += 180;

        RangeWeaponProjectile proj1 = new RangeWeaponProjectile(player.x, player.y, player.level, player, direction1, this, angle);
        RangeWeaponProjectile proj2 = new RangeWeaponProjectile(player.x, player.y, player.level, player, direction2, this, (angle + 30) % 360);
        RangeWeaponProjectile proj3 = new RangeWeaponProjectile(player.x, player.y, player.level, player, direction3, this, (angle - 30 + 360) % 360);


        proj1.projectileDamage = this.weaponDamage;
        proj2.projectileDamage = this.weaponDamage;
        proj2.projectileDamage = this.weaponDamage;

        proj1.getProjectileSpeed = this.projectileSpeed;
        proj2.getProjectileSpeed = this.projectileSpeed;
        proj3.getProjectileSpeed = this.projectileSpeed;


        player.level.game.mainScreen.projectilesNotRender.add(proj1);
        player.level.game.mainScreen.projectilesNotRender.add(proj2);
        player.level.game.mainScreen.projectilesNotRender.add(proj3);
    }


    @Override
    public TextureRegion getTexture() {
        return player.level.game.resources.tripleShot;
    }

}
