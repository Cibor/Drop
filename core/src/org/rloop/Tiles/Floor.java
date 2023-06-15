package org.rloop.Tiles;

import com.badlogic.gdx.graphics.Texture;
import org.rloop.Level;
import org.rloop.Resources;

public class Floor extends Tile{
    public Floor(int x, int y, Level level) {
        super(level.getGame().resources.stoneFloor, false, x, y, level);
    }
}
