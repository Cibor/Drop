package org.rloop.Tiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import org.rloop.Player;
import org.rloop.Room;

public class HiddenSpikes extends Spikes{
    public boolean isHidden = false;

    int hideCountDown = 150;


    static Texture hiddenOne = new Texture("pixil-frame-0.png");

    public HiddenSpikes(int x, int y, Room room) {
        super(x, y, room);
        isHiddenOne = true;
    }

    @Override
    public void render() {
        if (hideCountDown > 0) {
            hideCountDown--;
        }
        if (hideCountDown == 0) {
            isHidden = !isHidden;
            hideCountDown = 150;
        }
        if (isHidden) {
            room.getGame().getBatch().draw(hiddenOne, x-1, y-1, 2*WIDTH, 2*HEIGHT);
        } else {
            super.render();
        }
    }
}
