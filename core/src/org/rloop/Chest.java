package org.rloop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import org.rloop.Screens.GameScreen;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.rloop.Util.rnd;

public class Chest {
    static float WIDTH = 1;
    static float HEIGHT = 1;
    Texture texture;
    int x;
    int y;
    Level level;
    GameScreen gameScreen;
    Body barrier;
    float stateTime;
    Animation<TextureRegion> animation;
    TextureRegion currentFrame;
    public int textureNumber = 0;
    TextureRegion[] textureRegion;

    ArrayList<Items> drop;

    public boolean PointIsInChest(float x, float y){
        if(x >= barrier.getPosition().x-1 && y >= barrier.getPosition().y-1 && x <= barrier.getPosition().x+1 && y <= barrier.getPosition().y+1){
            return true;
        }else{
            return false;
        }
    }

    public Chest(Vector2 pos, Level level, GameScreen gameScreen){
        stateTime = 0;
        this.texture = level.getGame().resources.chest;
        TextureRegion[][] tmp = TextureRegion.split(this.texture,
                texture.getWidth()/2,
                texture.getHeight());
        textureRegion = tmp[0];
        drop = new ArrayList<>();
        this.gameScreen = gameScreen;
        this.texture = level.getGame().resources.portal;
        this.x = (int) pos.x;
        this.y = (int) pos.y;
        this.level = level;

        int itemCount = rnd(1, 2);

        for(int i = 0; i < itemCount; i++){
            int ranItem = rnd(0, gameScreen.PossibleItems.size() - 1);
            if(gameScreen.PossibleItems.get(ranItem) == DamagePotion.class){
                drop.add(new DamagePotion(rnd(20, 50) * 1f/100f, level));
            }
        }

        definePhysics();
    }

    void definePhysics(){
        BodyDef barrierDef = new BodyDef();
        barrierDef.position.set(new Vector2((float)x, (float)y));
        barrier = level.getWorld().createBody(barrierDef);

        PolygonShape barrierBox = new PolygonShape();
        barrierBox.setAsBox(WIDTH, HEIGHT);
        barrier.createFixture(barrierBox, 0.0f).setUserData(this);
        barrierBox.dispose();
    }

    public void render() {
        this.level.getCamera().update();
        this.level.getViewport().apply();
        this.level.getGame().getBatch().setProjectionMatrix(level.getCamera().combined);
        this.level.getGame().getBatch().begin();
        this.level.getGame().getBatch().draw(textureRegion[textureNumber], x-1, y-1, 2, 2);
        this.level.getGame().getBatch().end();
        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            Vector3 vec = new Vector3(Gdx.input.getX(), Gdx.input.getY(),0);
            vec = level.getCamera().unproject(vec);
            if(gameScreen.getPlayer().getPosition().dst(new Vector2(gameScreen.chest.x, gameScreen.chest.y)) < 5 && this.PointIsInChest(vec.x, vec.y)){
                textureNumber = 1;
                gameScreen.chestMode = true;
            }
        }
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
