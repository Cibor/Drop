package org.rloop.Tiles;

import com.badlogic.gdx.graphics.Texture;
import org.rloop.Level;
import org.rloop.Resources;

public class HiddenSpikes extends Spikes{
    public boolean isHidden = false;

    int hideCountDown = 150;


    static Texture hiddenOne = new Texture("HiddenSpikes.png");

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
            if (isHidden)
                texture = level.getGame().resources.spikes;
            else
                texture = level.getGame().resources.hiddenSpikes;
            hideCountDown = 150;
        }

        super.render();
    }
}
