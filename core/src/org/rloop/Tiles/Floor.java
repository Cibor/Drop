package org.rloop.Tiles;

import com.badlogic.gdx.graphics.Texture;
import org.rloop.Room;

public class Floor extends Tile{

    static Texture floorTexture = new Texture("StoneFloorTexture.png");

    public Floor(int x, int y, Room room) {
        super(floorTexture, false, x, y, room);
    }
}
