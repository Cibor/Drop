package org.rloop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Flamethrower implements DamageMakerPlayer, DamageMakerMonster{
    static float WIDTH = 6;
    static float HEIGHT = 2;
    Texture texture;
    int x;
    int y;
    Level level;
    float stateTime;
    float angle;
    protected ArrayList<Animation<TextureRegion>> walkAnimation;
    boolean wasJustOnFlame = false;
    int direction;

    public Flamethrower(Vector2 pos, Level level, int direction){
        this.direction = direction;
        stateTime = 0;
        this.texture = level.getGame().resources.flamethrower;
        this.x = (int) pos.x;
        this.y = (int) pos.y;
        if(direction == 0) {
            y+=6;
            angle = 270;
        }else if(direction == 1) {
            x += 6;
            y += 2;
            angle = 180;
        }else if(direction == 2) {
            y -= 4;
            x += 2;
            angle = 90;
        } else if(direction == 3){
            x-=4;
            angle = 0;
        }

        TextureRegion[][] tmp = TextureRegion.split(this.texture,
                texture.getWidth()/12,
                texture.getHeight());
        walkAnimation = new ArrayList<>();
        walkAnimation.add(new Animation<>(0.25f, tmp[0]));
        this.texture = level.getGame().resources.portal;
        this.level = level;
    }

    boolean flameContainsPlayer(Vector2 playerPos){
        if(direction == 0 ){
            if(playerPos.x >= x - 1 && playerPos.x <= x + 1 &&
                    playerPos.y >= y - 7 && playerPos.y <= y - 1){
                return true;
            }
        }
        if(direction == 2 ){
            if(playerPos.x >= x - 3 && playerPos.x <= x - 1 &&
                    playerPos.y >= y - 1 && playerPos.y <= y + 5){
                return true;
            }
        }
        if(direction == 1 ){
            if(playerPos.x >= x - 7 && playerPos.x <= x - 1 &&
                    playerPos.y >= y - 3 && playerPos.y <= y - 1){
                return true;
            }
        }
        if(direction == 3 ){
            if(playerPos.x >= x - 1 && playerPos.x <= x + 7 &&
                    playerPos.y >= y - 1 && playerPos.y <= y + 1){
                return true;
            }
        }
        return false;
    }

    public void update(){
        Player curPlayer = level.getPlayer();
        stateTime += Gdx.graphics.getDeltaTime();
        if(flameContainsPlayer(level.getPlayerCurrentPosition())){
            curPlayer.addDamageMaker(this);
            wasJustOnFlame = true;
        } else{
            if(wasJustOnFlame){
                wasJustOnFlame = false;
                curPlayer.removeDamageMaker(this);
            }
        }
    }
    @Override
    public void makeDamagePlayer(Player player) {
        player.getHit(0.05f);
        level.getGame().getOurMusic().dmgSound.play(level.getGame().getOurMusic().getSoundVolume());
    }

    public void render() {
        this.level.getGame().getBatch().draw(walkAnimation.get(0).getKeyFrame(stateTime, true), x-1,y-1,0, 0, WIDTH, HEIGHT,1,1, angle,false);
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

    @Override
    public void makeDamageMonster(Monster monster) {

    }
}
