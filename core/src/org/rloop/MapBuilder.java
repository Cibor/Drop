package org.rloop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.Viewport;
import jdk.internal.net.http.common.Pair;
import org.rloop.Tiles.Floor;
import org.rloop.Tiles.Tile;
import org.rloop.Tiles.Wall;

import javax.swing.text.Position;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.badlogic.gdx.math.Intersector.overlaps;
import static java.lang.Math.max;


public class MapBuilder {
    ArrayList<Rectangle> rooms;
    int n;
    World world;

    public static int rnd(int min, int max) {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }

    public MapBuilder(World world) {
        this.world = world;
    }

    public boolean IsAnyRoomOverlapped() {
        for (Rectangle r1 : rooms) {
            for (Rectangle r2 : rooms) {
                if (r1 != r2 && overlaps(r1, r2)) {
                    return true;
                }
            }
        }

        return false;
    }

    private void SeparateRooms() {
        do {
            for (int current = 0; current < n; current++) {
                for (int other = current + 1; other < n; other++) {
                    if (!overlaps(rooms.get(current), rooms.get(other))) {
                        continue;
                    }
                    Vector2 vec1 = new Vector2();
                    rooms.get(other).getCenter(vec1);
                    float x1 = vec1.x;
                    float y1 = vec1.y;

                    Vector2 vec2 = new Vector2();
                    rooms.get(current).getCenter(vec2);
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

                    rooms.get(other).setCenter(x1, y1);
                    rooms.get(current).setCenter(x2, y2);
                }
            }
        } while (IsAnyRoomOverlapped());
    }

    public ArrayList<Rectangle> generateRoom() {
        n = rnd(4,6);
        rooms = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Rectangle rec = new Rectangle(0, 0, 2*rnd(4,7), 2*rnd(4, 7)); // Parity is important!!
            rooms.add(rec);
        }
        SeparateRooms();
//        for (Rectangle r1 : rooms) {
//            for (Rectangle r2 : rooms) {
//                System.out.print(r1);
//                System.out.print(" ");
//                System.out.print(r2);
//                System.out.println(overlaps(r1, r2));
//            }
//        }
        for(Rectangle r: rooms){
            r.width+=6;
            r.height+=6;
        }
        return rooms;
    }

//    public ArrayList<ArrayList<Tile>> generateRoomTiles(Room room){
//        generateTemplate();
//        ArrayList<ArrayList<Tile>> tiles = new ArrayList<>();
//
//        for(int i=-height; i<=height; i +=  2) {
//            ArrayList<Tile> row = new ArrayList<>();
//            for(int j = -width; j < templateLeft.get(i+height); j+=2){
//                row.add(new Wall(i*Wall.WIDTH, j * Wall.HEIGHT, room));
//            }
//            for(int j = templateLeft.get(i+height); j <= templateRight.get(i+height); j+=2){
//                row.add(new Floor(i*Wall.WIDTH, j * Wall.HEIGHT, room)); //TODO: Add other tiles
//            }
//            for(int j = templateRight.get(i+height)+1; j < width; j+=2){
//                row.add(new Wall(i*Wall.WIDTH, j * Wall.HEIGHT, room));
//            }
//            tiles.add(row);
//        }
//
//        return tiles;
//    }

//    public ArrayList<ArrayList<Integer>> generateGraph(){
//        Random rand = new Random();
//        int n = rand.nextInt(10)+1;  //number of rooms
//
//        //TODO: Use some planar graph(not complete). May be done to the third presentation.
//        //TODO: PROBLEM WITH DEGREE(it must be at most 4)
//        //TODO: Make graph not-oriented
//
//        ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
//        for(int i=0;i<n;i++){
//            ArrayList<Integer> oneVertex = new ArrayList<>();
//            for(int j=0;j<n;j++){
//                int isInGraph = rand.nextInt(2);
//                if(isInGraph == 1){
//                    oneVertex.add(j);
//                }
//            }
//            graph.add(oneVertex);
//        }
//        ArrayList<Boolean> areInGraph = new ArrayList<>();
//        for(int i=0;i<n;i++){
//            areInGraph.add(false);
//        }
//
//        ArrayList<Integer> queue = new ArrayList<>();
//
//        areInGraph.set(0, true);
//        queue.add(0);
//
//        while(queue.size()>0)
//        {
//            int s = queue.get(queue.size()-1);
//            queue.remove(queue.size()-1);
//
//            for (Integer adj: graph.get(s))
//            {
//                if (!areInGraph.get(adj))
//                {
//                    areInGraph.set(adj, true);
//                    queue.add(0, adj);
//                }
//            }
//        }
//        Map<Integer, Integer> order = new HashMap<>();
//        int k = 0;
//        for(int i=0;i<n;i++){
//            if(areInGraph.get(i)) {
//                order.put(i, k);
//                k++;
//            }
//        }
//        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
//        for(int i = 0; i < n; i++){
//            if(areInGraph.get(i)){
//                ArrayList<Integer> row = new ArrayList<>();
//                for(int j = 0; j < graph.get(i).size(); j++){
//                    if(areInGraph.get(graph.get(i).get(j))){
//                        row.add(order.get(graph.get(i).get(j)));
//                    }
//                }
//                result.add(row);
//            }
//        }
//        return result;
//    }


//    public void generateMap() {
//        ArrayList<ArrayList<Integer>> graph = generateGraph();
//        int n = graph.size();
//        ArrayList<Room> rooms = new ArrayList<>();
//        for(int i = 0; i < n; i++){
//            rooms.add(new Room(world, game, viewport));
//        }

//    }

}
