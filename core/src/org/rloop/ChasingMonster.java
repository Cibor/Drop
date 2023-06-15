package org.rloop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import org.rloop.Tiles.HiddenSpikes;
import org.rloop.Tiles.Spikes;

import java.util.ArrayList;

public class ChasingMonster extends Monster implements DamageMakerPlayer {

    public ChasingMonster(float x, float y, Level level, Player player) {
        super(x, y, level, player);

        this.speedMonst = 3;
        this.damageMonst = (float) 0.05;
        this.hpMonst = 1;

        Texture texture = level.getGame().resources.goblin;
        TextureRegion[][] tmp = TextureRegion.split(texture,
                16,
                17);
        walkAnimation = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            walkAnimation.add(new Animation<>(0.25f, tmp[i]));
        }

        definePhysics();
    }

    @Override
    public void update(float x) {

        this.setX(this.getBody().getPosition().x);
        this.setY(this.getBody().getPosition().y);

        stateTime += x;

        float playerX = this.player.getBody().getPosition().x;
        float playerY = this.player.getBody().getPosition().y;
        float myX = this.getBody().getPosition().x;
        float myY = this.getBody().getPosition().y;

        Vector2 direction = new Vector2(playerX - myX, playerY - myY);

        for (Spikes spike : level.spikesTiles) {
            if (spike.isHiddenOne) {
                HiddenSpikes curSpike = (HiddenSpikes) spike;
                if (curSpike.isHidden) {
                    continue;
                }
            }
            if (this.x >= spike.getX() - 1 && this.x <= spike.getX() + 1 && this.y >= spike.getY() - 1 && this.y <= spike.getY() + 1) {
                if (!this.isImmune()) {
                    this.getHit(0.02f);
                    //Gdx.audio.newSound(Gdx.files.internal("music/DamageSound.mp3")).play(room.getGame().GlobalAudioSound);
                }
            }
        }

        double angle = Util.GetAngle(myX, myY, playerX, playerY);

        angle += 180;

        if ((angle >= 0 && angle <= 45) || (angle >= 315 && angle <= 360)) {
            this.setDirection(3);
        } else if (angle > 45 && angle <= 135) {
            this.setDirection(0);
        } else if (angle > 135 && angle <= 225) {
            this.setDirection(2);
        } else {
            this.setDirection(1);
        }

        float kaf = direction.x * direction.x + direction.y * direction.y;
        kaf = (speedMonst * speedMonst) / kaf;
        direction.x *= Math.sqrt(kaf);
        direction.y *= Math.sqrt(kaf);
        this.getBody().setLinearVelocity(direction);
    }

    @Override
    public void makeDamagePlayer(Player player) {
        player.getHit(damageMonst);
        level.getGame().getOurMusic().dmgSound.play(level.getGame().getOurMusic().getSoundVolume());
    }
}
