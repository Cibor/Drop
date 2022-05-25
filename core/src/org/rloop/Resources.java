package org.rloop;

import com.badlogic.gdx.graphics.Texture;

public class Resources {
    public Texture hiddenSpikes;
    public Texture spikes;
    public Texture stoneFloor;
    public Texture water;

    Resources() {
        hiddenSpikes = new Texture("HiddenSpikes.png");
        spikes = new Texture("Spikes.png");
        stoneFloor = new Texture("StoneFloor.png");
        water = new Texture("water.png");
    }

    void dispose() {
        hiddenSpikes.dispose();
        spikes.dispose();
        stoneFloor.dispose();
        water.dispose();
    }
}
