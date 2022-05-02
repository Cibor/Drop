package org.rloop.Tiles;

import com.badlogic.gdx.graphics.Texture;
import org.rloop.Room;

public class Floor extends Tile{
    public Floor(int x, int y, Room room) {
        super(new Texture("StoneFloorTexture.png"), false, x, y, room);
    }
}
