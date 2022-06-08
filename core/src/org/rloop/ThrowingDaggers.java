package org.rloop;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class ThrowingDaggers extends RangeWeaponProjectile{

    public ThrowingDaggers(float x, float y, Level level, Player player, Vector2 direction, Object spawner, float angle) {
        super(x, y, level, player, direction, spawner, angle);
        texture = player.level.game.resources.tripleShot;
    }

    @Override
    public void render(){
        this.setX(this.getBody().getPosition().x);
        this.setY(this.getBody().getPosition().y);
        this.level.getCamera().update();
        this.level.getViewport().apply();
        this.level.getGame().getBatch().setProjectionMatrix(level.getCamera().combined);
        this.level.getGame().getBatch().begin();

        level.getGame().getBatch().draw(new TextureRegion(texture), x - 1 , y - 1, 1f, 1f, 1.5f, 1.5f, 1, 1, angle);
        this.level.getGame().getBatch().end();

    }
}
