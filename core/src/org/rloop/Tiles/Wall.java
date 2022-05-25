package org.rloop.Tiles;

import com.badlogic.gdx.graphics.Texture;
import org.rloop.Level;
import org.rloop.Resources;

public class Wall extends Tile {
    public Wall(int x, int y, Level level) {
        super(Resources.water, true, x, y, level);
    }
}
