package org.rloop.Tiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import org.rloop.Player;
import org.rloop.Room;

public class Spikes extends Tile{

    public boolean isHiddenOne = false;

    public Spikes(int x, int y, Room room) {
        super(new Texture("Columned Spikes.png"), false, x, y, room);
    }

    @Override
    public void render(){
        room.getGame().getBatch().draw(texture, x-1, y-1, 2*WIDTH, 2*HEIGHT);
        if(room.getPlayerCurrentPosition().x >= x - 1 && room.getPlayerCurrentPosition().x <= x + 1 && room.getPlayerCurrentPosition().y >= y - 1 && room.getPlayerCurrentPosition().y <= y + 1){
            Player curPlayer = room.getPlayer();
            if(!curPlayer.isImmune()){
                curPlayer.getHit(0.05f);
                Gdx.audio.newSound(Gdx.files.internal("music/DamageSound.mp3")).play(room.getGame().GlobalAudioSound);
                curPlayer.makeImmune();
            }
        }
    }

}
