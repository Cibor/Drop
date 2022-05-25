package org.rloop.Tiles;

import com.badlogic.gdx.graphics.Texture;
import org.rloop.Level;

public class Floor extends Tile{

    static Texture floorTexture = new Texture("StoneFloorTexture.png");

    public Floor(int x, int y, Level level) {
        super(floorTexture, false, x, y, level);
    }
}
