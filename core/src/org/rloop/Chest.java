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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import org.rloop.Screens.GameScreen;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.rloop.Util.monitorResolutionY;
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
    ArrayList<Actor> actors;

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
        actors = new ArrayList<>();
        this.gameScreen = gameScreen;
        this.texture = level.getGame().resources.portal;
        this.x = (int) pos.x;
        this.y = (int) pos.y;
        this.level = level;

        int itemCount = rnd(1, 10);
        if (itemCount == 10) itemCount = 2;
        else itemCount = 1;
        for(int i = 0; i < itemCount; i++){
            int ranItem = rnd(0, gameScreen.PossibleItems.size() - 1);
            TextureRegionDrawable ret = new TextureRegionDrawable();
            if(gameScreen.PossibleItems.get(ranItem) == DamagePotion.class) {
                drop.add(new DamagePotion(rnd(20, 50) * 1f / 100f, level));
                ret = new TextureRegionDrawable(gameScreen.getGame().resources.damagePotion);

            }else if(gameScreen.PossibleItems.get(ranItem) == SpeedPotion.class){
                drop.add(new SpeedPotion(rnd(5, 20) * 1f/100f, level));
                ret = new TextureRegionDrawable(gameScreen.getGame().resources.speedPotion);
            }
            else if(gameScreen.PossibleItems.get(ranItem) == HpPotion.class){
                drop.add(new HpPotion(rnd(5, 10) * 1f/100f, level));
                ret = new TextureRegionDrawable(gameScreen.getGame().resources.hpPotion);
            }
            else if(gameScreen.PossibleItems.get(ranItem) == DefendingShoes.class){
                drop.add(new DefendingShoes(level));
                ret = new TextureRegionDrawable(gameScreen.getGame().resources.boots);
            }
            else if(gameScreen.PossibleItems.get(ranItem) == TripleRangeWeapon.class){
                drop.add(new TripleRangeWeapon(0.1f * level.getPlayer().playerDamage, 1f * level.getPlayer().playerAttackSpeed, 1.3f, level.getPlayer()));
                ret = new TextureRegionDrawable(gameScreen.getGame().resources.tripleShot);
            }

            final int idx = i;
            ret.setMinSize(Util.monitorResolutionX(90), Util.monitorResolutionY(90));
            ImageButton item = new ImageButton(ret);
            item.setBounds(Util.monitorResolutionX(1005 + 110 * (i / 4)), monitorResolutionY(606+110*(i%4)), Util.monitorResolutionX(90), Util.monitorResolutionY(90));
            item.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    actor.addAction(Actions.removeActor());
                    actors.set(idx, null);
                    drop.get(idx).pickUp(level.getPlayer());
                    drop.set(idx, null);
                }
            });
            gameScreen.chestStage.addActor(item);
            actors.add(item);
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
