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

    int damageImmune = 0;

    public ChasingMonster(float x, float y, Level level, Player player) {
        this.player = player;
        this.level = level;
        this.x = x;
        this.y = y;

        this.speedMonst = 3;
        this.damageMonst = (float) 0.05;
        this.hpMonst = 2;

        stateTime = 0;
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
    public void definePhysics() {

        BodyDef def = new BodyDef();
        def.fixedRotation = true;
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(x, y);
        body = level.getWorld().createBody(def);

        PolygonShape square = new PolygonShape();
        square.setAsBox(0.65f, 0.85f);
        FixtureDef fixtureDef = new FixtureDef();

        fixtureDef.shape = square;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.2f;
        fixtureDef.restitution = 0f;

        fixture = this.body.createFixture(fixtureDef);
        fixture.setUserData(this);
        square.dispose();
    }

    @Override
    public void render() {
        this.setX(this.getBody().getPosition().x);
        this.setY(this.getBody().getPosition().y);
        TextureRegion currentFrame = walkAnimation.get(direction).getKeyFrame(stateTime, true);
        this.level.getCamera().update();
        this.level.getViewport().apply();
        this.level.getGame().getBatch().setProjectionMatrix(level.getCamera().combined);
        this.level.getGame().getBatch().begin();
        level.getGame().getBatch().draw(currentFrame, x - 1, y - 1, 2, 2);
        this.level.getGame().getBatch().end();

        stateTime += Gdx.graphics.getDeltaTime();

        if (this.isImmune()) {
            damageImmune--;
        }

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
                    this.getHit(0.1f);
                    //Gdx.audio.newSound(Gdx.files.internal("music/DamageSound.mp3")).play(room.getGame().GlobalAudioSound);
                    this.makeImmune();
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

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setDirection(int y) {
        this.direction = y;
    }

    public Body getBody() {
        return body;
    }

    public void getHit(float hit) {
        hpMonst -= hit;
        if (hpMonst <= 0) {
            level.game.mainScreen.monstersDied.add(this);
        }
    }

    public boolean isImmune() {
        return damageImmune > 0;
    }

    public void makeImmune() {
        damageImmune = 60;
    }


    void renderPaused() {
        this.setX(this.getBody().getPosition().x);
        this.setY(this.getBody().getPosition().y);
        float velx = getBody().getLinearVelocity().x;
        float vely = getBody().getLinearVelocity().y;
        if (velx == 0 && vely == 0) {
            stateTime = 0;
        }
        TextureRegion currentFrame = walkAnimation.get(direction).getKeyFrame(stateTime, true);
        this.level.getCamera().update();
        this.level.getViewport().apply();
        this.level.getGame().getBatch().setProjectionMatrix(level.getCamera().combined);
        this.level.getGame().getBatch().begin();
        level.getGame().getBatch().draw(currentFrame, x - 1, y - 1, 2, 2);
        this.level.getGame().getBatch().end();
    }

    @Override
    public void makeDamagePlayer(Player player) {
        player.getHit(damageMonst);
        level.getGame().getOurMusic().dmgSound.play(level.getGame().getOurMusic().getSoundVolume());
    }
}
