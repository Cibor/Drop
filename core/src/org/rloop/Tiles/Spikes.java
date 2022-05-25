package org.rloop.Tiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import org.rloop.Level;
import org.rloop.Player;
import org.rloop.Resources;

public class Spikes extends Tile{

    public boolean isHiddenOne = false;

    public Spikes(int x, int y, Level level) {
        super(level.getGame().resources.spikes, false, x, y, level);
    }

    @Override
    public void render(){
        super.render();

        if(level.getPlayerCurrentPosition().x >= x - 1 && level.getPlayerCurrentPosition().x <= x + 1 && level.getPlayerCurrentPosition().y >= y - 1 && level.getPlayerCurrentPosition().y <= y + 1){
            Player curPlayer = level.getPlayer();
            if(!curPlayer.isImmune()){
                curPlayer.getHit(0.05f);
                level.getGame().getOurMusic().dmgSound.play(level.getGame().getOurMusic().getSoundVolume());
                curPlayer.makeImmune();
            }
        }
    }

}
