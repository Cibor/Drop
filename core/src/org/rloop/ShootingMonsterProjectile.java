package org.rloop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import static java.lang.Math.abs;

public class ShootingMonsterProjectile extends Projectiles implements DamageMakerPlayer{

    TextureRegion texture;
    int stateTime;

    public ShootingMonsterProjectile(float x, float y, Level level, Player player, Vector2 direction,Object spawner, float angle){
        super(x, y, level, player, direction, spawner);

        this.getProjectileSpeed = 7;
        this.projectileDamage = (float) 0.1;
        this.angle = angle;


        float kaf = direction.x * direction.x + direction.y * direction.y;
        kaf = (getProjectileSpeed * getProjectileSpeed) / kaf;
        direction.x *= Math.sqrt(kaf);
        direction.y *= Math.sqrt(kaf);

        stateTime = 0;
        texture = new TextureRegion(level.game.resources.prTexture);
        definePhysics(0.3f, 0.3f , null);
    }
    @Override
    public void render(){
        this.setX(this.getBody().getPosition().x);
        this.setY(this.getBody().getPosition().y);
        this.level.getCamera().update();
        this.level.getViewport().apply();
        this.level.getGame().getBatch().setProjectionMatrix(level.getCamera().combined);
        this.level.getGame().getBatch().begin();

        level.getGame().getBatch().draw(new TextureRegion(texture), x - 1, y - 1, 1, 1, 2, 2, 1, 1, angle);
        this.level.getGame().getBatch().end();

    }

    public void update(float x){
        this.setX(this.getBody().getPosition().x);
        this.setY(this.getBody().getPosition().y);
        stateTime += x;
        this.getBody().setLinearVelocity(direction);
    }

    @Override
    public void makeDamagePlayer(Player player) {
        player.getHit(projectileDamage);
        level.getGame().getOurMusic().dmgSound.play(level.getGame().getOurMusic().getSoundVolume());
    }
}