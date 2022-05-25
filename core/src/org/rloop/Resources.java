package org.rloop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Resources {
    public Texture hiddenSpikes;
    public Texture spikes;
    public Texture stoneFloor;
    public Texture water;
    public Texture allThings;
    public TextureRegion mainSword;

    Resources() {
        hiddenSpikes = new Texture("HiddenSpikes.png");
        spikes = new Texture("Spikes.png");
        stoneFloor = new Texture("StoneFloor.png");
        water = new Texture("water.png");
        allThings = new Texture("all-assets-preview.png");
        mainSword = new TextureRegion(allThings, 512, 64, 15, 15);
    }

    void dispose() {
        hiddenSpikes.dispose();
        spikes.dispose();
        stoneFloor.dispose();
        water.dispose();
        allThings.dispose();
    }
}
