package org.rloop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import org.rloop.Screens.GameScreen;
import org.rloop.*;

import java.util.ArrayList;

public class Portal {
    static float WIDTH = 0.25F;
    static float HEIGHT = 0.25F;
    Texture texture;
    int x;
    int y;
    Level level;
    GameScreen gameScreen;
    float stateTime;
    Animation<TextureRegion> animation;
    TextureRegion currentFrame;



    public Portal(Vector2 pos, Level level, GameScreen gameScreen){
        stateTime = 0;
        this.texture = level.getGame().resources.portal;
        TextureRegion[][] tmp = TextureRegion.split(this.texture,
                texture.getWidth(),
                texture.getHeight() / 5);
        TextureRegion[] temp = new TextureRegion[5];
        for(int i=0;i<5;i++){
            temp[i] = tmp[i][0];
        }
        animation = new Animation<>(0.25f, temp);
        this.gameScreen = gameScreen;
        this.texture = level.getGame().resources.portal;
        this.x = (int) pos.x;
        this.y = (int) pos.y;
        this.level = level;
        definePhysics();
    }

    void definePhysics(){
        BodyDef barrierDef = new BodyDef();
        barrierDef.position.set(new Vector2((float)x+(float)0.5, (float)y+(float)0.5));
        Body barrier = level.getWorld().createBody(barrierDef);

        PolygonShape barrierBox = new PolygonShape();
        barrierBox.setAsBox(WIDTH, HEIGHT);
        barrier.createFixture(barrierBox, 0.0f).setUserData(this);
        barrierBox.dispose();
    }

    public void render() {
        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = animation.getKeyFrame(stateTime, true);
        this.level.getCamera().update();
        this.level.getViewport().apply();
        this.level.getGame().getBatch().setProjectionMatrix(level.getCamera().combined);
        this.level.getGame().getBatch().begin();
        this.level.getGame().getBatch().draw(currentFrame, x-1, y-1, 3, 3);
        this.level.getGame().getBatch().end();
    }

    public Texture getTexture() {
        return texture;
    }

    public float getWidth() {
        return WIDTH;
    }

    public float getHeight() {
        return HEIGHT;
    }
}
