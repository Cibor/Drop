package org.rloop.Tiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import org.rloop.DamageMaker;
import org.rloop.Level;
import org.rloop.Player;
import org.rloop.Resources;

public class Spikes extends Tile implements DamageMaker {

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
    public void makeDamage(Player player) {
        player.getHit(0.05f);
        level.getGame().getOurMusic().dmgSound.play(level.getGame().getOurMusic().getSoundVolume());
    }
}
