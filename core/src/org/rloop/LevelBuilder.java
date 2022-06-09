package org.rloop;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

import static com.badlogic.gdx.math.Intersector.overlaps;
import static org.rloop.Util.rnd;


public class LevelBuilder {
    ArrayList<Rectangle> roomShapes;
    int numOfRooms;
    World world;

    public LevelBuilder(World world) {
        this.world = world;
    }

//    public static int rnd(int min, int max) {
//        max -= min;
//        return (int) (Math.random() * ++max) + min;
//    }

    public ArrayList<Rectangle> generateRoom() {
        numOfRooms = rnd(20, 30);
        roomShapes = new ArrayList<>();
        for (int i = 0; i < numOfRooms; i++) {
            Rectangle rec = new Rectangle(0, 0, 2 * rnd(4, 7), 2 * rnd(4, 7)); // Parity is important!!
            roomShapes.add(rec);
        }
        SeparateRooms();

        for (Rectangle r : roomShapes) {
            r.width += 6;
            r.height += 6;
        }
        return roomShapes;
    }

    private void SeparateRooms() {
        Vector2 vec1 = new Vector2();
        Vector2 vec2 = new Vector2();

        while (IsAnyRoomOverlapped()) {
            for (int current = 0; current < numOfRooms; current++) {
                for (int other = current + 1; other < numOfRooms; other++) {
                    if (!overlaps(roomShapes.get(current), roomShapes.get(other)))
                        continue;

                    roomShapes.get(current).getCenter(vec1);
                    float x1 = vec1.x;
                    float y1 = vec1.y;

                    roomShapes.get(other).getCenter(vec2);
                    float x2 = vec2.x;
                    float y2 = vec2.y;

                    if (x1 - x2 >= 0) {
                        x1 += 2;
                        x2 -= 2;
                    } else {
                        x1 -= 2;
                        x2 += 2;
                    }

                    if (y1 - y2 >= 0) {
                        y1 += 2;
                        y2 -= 2;
                    } else {
                        y1 -= 2;
                        y2 += 2;
                    }

                    roomShapes.get(current).setCenter(x1, y1);
                    roomShapes.get(other).setCenter(x2, y2);
                }
            }
        }
    }

    public boolean IsAnyRoomOverlapped() {
        for (int current = 0; current < numOfRooms; current++) {
            for (int other = current + 1; other < numOfRooms; other++) {
                if (overlaps(roomShapes.get(current), roomShapes.get(other))) {
                    return true;
                }
            }
        }

        return false;
    }
}
