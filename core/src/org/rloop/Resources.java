package org.rloop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;

public class Resources {
    public Texture hiddenSpikes;
    public Texture spikes;
    public TextureRegion stoneFloor;
    public Texture water;
    public TextureRegion[][] walls;

    Resources() {
        hiddenSpikes = new Texture("HiddenSpikes.png");
        spikes = new Texture("Spikes.png");
        water = new Texture("water.png");
        Texture texture = new Texture("WallSet.png");
        walls = TextureRegion.split(texture,
                texture.getWidth() / 4,
                texture.getHeight() / 4);;
        stoneFloor = walls[3][3];
    }

    void dispose() {
        hiddenSpikes.dispose();
        spikes.dispose();
        water.dispose();
    }
}
