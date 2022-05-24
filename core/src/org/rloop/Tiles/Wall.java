package org.rloop.Tiles;

import com.badlogic.gdx.graphics.Texture;
import org.rloop.Room;

public class Wall extends Tile {

    static Texture wallTexture = new Texture("water 3.png");

    public Wall(int x, int y, Room room) {
        super(wallTexture, true, x, y, room);
    }
}
