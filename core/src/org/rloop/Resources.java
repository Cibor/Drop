package org.rloop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Resources {
    public Texture hiddenSpikes;
    public Texture spikes;
    public Texture stoneFloor;
    public Texture water;
    public Texture skeletonMage;
    public Texture goblin;

    Resources() {
        hiddenSpikes = new Texture("HiddenSpikes.png");
        spikes = new Texture("Spikes.png");
        stoneFloor = new Texture("StoneFloor.png");
        water = new Texture("water.png");

        skeletonMage = new Texture("player/skeletonMage.png");
        goblin = new Texture("player/goblin.png");
    }

    void dispose() {
        hiddenSpikes.dispose();
        spikes.dispose();
        stoneFloor.dispose();
        water.dispose();
    }
}
