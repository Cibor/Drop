package org.rloop.Tiles;

import com.badlogic.gdx.graphics.Texture;
import org.rloop.Level;
import org.rloop.Player;
import org.rloop.Resources;

public class HiddenSpikes extends Spikes {
    public boolean isHidden = false;

    int hideCountDown = 150;

    public HiddenSpikes(int x, int y, Level level) {
        super(x, y, level);
        isHiddenOne = true;
    }

    @Override
    public void render() {
        super.render();
    }

    public void update(){
        if(!isHidden){
            super.update();
        }

        if (hideCountDown > 0) {
            hideCountDown--;
        }
        if (hideCountDown == 0) {
            isHidden = !isHidden;
            if (isHidden)
                texture = level.getGame().resources.hiddenSpikes;
            else
                texture = level.getGame().resources.spikes;
            hideCountDown = 150;
        }
    }
    @Override
    public void makeDamagePlayer(Player player) {
        if (!isHidden) {
            player.getHit(0.05f);
            level.getGame().getOurMusic().dmgSound.play(level.getGame().getOurMusic().getSoundVolume());
        }
    }
}
