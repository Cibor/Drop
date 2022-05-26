package org.rloop.Tiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import org.rloop.*;

public class Spikes extends Tile implements DamageMakerPlayer, DamageMakerMonster {

    public boolean isHiddenOne = false;

    boolean wasJustOnSpikes = false;

    public Spikes(int x, int y, Level level) {
        super(level.getGame().resources.spikes, false, x, y, level);
    }

    @Override
    public void render(){
        super.render();
    }

    public void update(){
        Player curPlayer = level.getPlayer();
        if(level.getPlayerCurrentPosition().x >= x - 1 && level.getPlayerCurrentPosition().x <= x + 1 && level.getPlayerCurrentPosition().y >= y - 1 && level.getPlayerCurrentPosition().y <= y + 1){
            curPlayer.addDamageMaker(this);
            wasJustOnSpikes = true;
        }
        else{
            if(wasJustOnSpikes){
                wasJustOnSpikes = false;
                curPlayer.removeDamageMaker(this);
            }
        }
    }

    @Override
    public void makeDamagePlayer(Player player) {
        player.getHit(0.05f);
        level.getGame().getOurMusic().dmgSound.play(level.getGame().getOurMusic().getSoundVolume());
    }

    @Override
    public  void makeDamageMonster(Monster monster){

    }
}
