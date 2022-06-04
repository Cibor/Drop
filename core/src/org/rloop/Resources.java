package org.rloop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;

public class Resources {
    public Texture hiddenSpikes;
    public Texture spikes;
    public TextureRegion stoneFloor;
    public TextureRegion[][] walls;
    public Texture skeletonMage;
    public Texture goblin;
    public Texture allThings;
    public TextureRegion mainSword;
    public TextureRegion magickBook;
    public Texture portal;
    public Texture chest;

    Resources() {
        hiddenSpikes = new Texture("HiddenSpikes.png");
        spikes = new Texture("Spikes.png");
        Texture texture = new Texture("WallSet.png");
        walls = TextureRegion.split(texture,
                texture.getWidth() / 4,
                texture.getHeight() / 4);
        stoneFloor = walls[3][3];
        portal = new Texture("teleport.png");
        chest = new Texture("chest.png");
        skeletonMage = new Texture("player/skeletonMage.png");
        goblin = new Texture("player/goblin.png");
        allThings = new Texture("all-assets-preview.png");
        mainSword = new TextureRegion(allThings, 512, 64, 15, 15);
        magickBook = new TextureRegion(allThings, 673, 176, 15, 15);
    }

    void dispose() {
        hiddenSpikes.dispose();
        spikes.dispose();
        allThings.dispose();
    }
}
