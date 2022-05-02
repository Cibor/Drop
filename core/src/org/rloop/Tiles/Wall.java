package org.rloop.Tiles;

import com.badlogic.gdx.graphics.Texture;
import org.rloop.Room;

public class Wall extends Tile {

    public Wall(int x, int y, Room room) {
        super(new Texture("water 3.png"), true, x, y, room);
    }
}
