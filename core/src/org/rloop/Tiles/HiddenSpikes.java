package org.rloop.Tiles;

import com.badlogic.gdx.graphics.Texture;
import org.rloop.Level;

public class HiddenSpikes extends Spikes{
    public boolean isHidden = false;

    int hideCountDown = 150;


    static Texture hiddenOne = new Texture("pixil-frame-0.png");

    public HiddenSpikes(int x, int y, Level level) {
        super(x, y, level);
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
            level.getGame().getBatch().draw(hiddenOne, x-1, y-1, 2*WIDTH, 2*HEIGHT);
        } else {
            super.render();
        }
    }
}
