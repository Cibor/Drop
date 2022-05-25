package org.rloop.Tiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import org.rloop.Level;
import org.rloop.Player;

public class Spikes extends Tile{

    public boolean isHiddenOne = false;

    public Spikes(int x, int y, Level level) {
        super(new Texture("Columned Spikes.png"), false, x, y, level);
    }

    @Override
    public void render(){
        level.getGame().getBatch().draw(texture, x-1, y-1, 2*WIDTH, 2*HEIGHT);
        if(level.getPlayerCurrentPosition().x >= x - 1 && level.getPlayerCurrentPosition().x <= x + 1 && level.getPlayerCurrentPosition().y >= y - 1 && level.getPlayerCurrentPosition().y <= y + 1){
            Player curPlayer = level.getPlayer();
            if(!curPlayer.isImmune()){
                curPlayer.getHit(0.05f);
                Gdx.audio.newSound(Gdx.files.internal("music/DamageSound.mp3")).play(level.getGame().GlobalAudioSound);
                curPlayer.makeImmune();
            }
        }
    }

}
