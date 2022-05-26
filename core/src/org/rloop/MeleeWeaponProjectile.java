package org.rloop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.sun.java.swing.plaf.motif.MotifTreeCellRenderer;

public class MeleeWeaponProjectile extends Projectiles implements DamageMakerMonster{

    float alpha = (float) (Math.PI/3f);

    MeleeWeapon myWeapon;

    public MeleeWeaponProjectile(float x, float y, Level level, Player player, Vector2 direction, Object spawner) {

        super(x, y, level, player, direction, spawner);
        texture = level.game.resources.mainSword;

        myWeapon = (MeleeWeapon) spawner;

        projectileDamage = myWeapon.weaponDamage;
        getProjectileSpeed = myWeapon.weaponAttackSpeed;

        float kaf = direction.x * direction.x + direction.y * direction.y;
        kaf = (myWeapon.length * myWeapon.length) / kaf;
        direction.x *= Math.sqrt(kaf);
        direction.y *= Math.sqrt(kaf);

        Vector2 rtp = new Vector2();
        if(direction.x > 0){
            rtp.add(new Vector2(0, -1 * myWeapon.length));
        }
        else{
            rtp.add(new Vector2(0, myWeapon.length));
        }

        definePhysics(myWeapon.width, myWeapon.length, rtp);
    }


    Vector2 A = new Vector2();
    Vector2 B = new Vector2();
    Vector2 C = new Vector2();
    float beta;

    public void update(float delta){
        beta = (float) (Math.atan(direction.y / direction.x) + Math.PI/2 + alpha);
        A.x = (float) -(Math.cos(beta) * myWeapon.width / 2);
        A.y = (float) -(Math.sin(beta) * myWeapon.width / 2);
        B.x = (float) Math.cos(Math.atan(direction.y / direction.x) + alpha);
        B.y = (float) Math.sin(Math.atan(direction.y / direction.x) + alpha);
        B.add(A);

        this.body.setTransform(player.x, player.y, beta);

        alpha -= delta/0.5 * Math.PI * 2 / 3;
        if (alpha < Math.PI / -3){
            level.game.mainScreen.projectilesDied.add(this);
        }
    }

    public void render() {
        this.level.getCamera().update();
        this.level.getViewport().apply();
        this.level.getGame().getBatch().setProjectionMatrix(level.getCamera().combined);
        this.level.getGame().getBatch().begin();
        if(direction.x < 0) {
            level.getGame().getBatch().draw(texture, player.x - 1, player.y - 0.1f, 1, 0, 1, 1.2f, 1.2f, 1.4f, (float) ((float) 180 / Math.PI * (fixture.getBody().getAngle()) - 30));
        }
        else{
            level.getGame().getBatch().draw(texture, player.x - 1, player.y - 0.1f, 1, 0, 1, 1.2f, 1.2f, 1.2f, (float) ((float) 180 / Math.PI * (fixture.getBody().getAngle())) + 150);
        }
        this.level.getGame().getBatch().end();
    }



    @Override
    public void makeDamageMonster(Monster monster) {

    }
}

