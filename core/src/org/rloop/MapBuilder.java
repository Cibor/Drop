package org.rloop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.Viewport;
import jdk.internal.net.http.common.Pair;
import org.rloop.Tiles.Floor;
import org.rloop.Tiles.Tile;
import org.rloop.Tiles.Wall;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static java.lang.Math.max;


public class MapBuilder {
    int width;
    int height;
    ArrayList<Integer> templateLeft, templateRight;

    public static int rnd(int min, int max)
    {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }

    public MapBuilder(){
        this.width = Room.HALF_ROOM_HEIGHT;
        this.height = Room.HALF_ROOM_WIDTH;
    }

    public void generateTemplate(){
        templateLeft = new ArrayList<>();
        templateRight = new ArrayList<>();
        Random rand;
        rand = new Random();
        int left = 0, right = 0;
        for(int i = 0; i < 2*height + 1; i++){
            int tempLeft, tempRight;
            if(i!=0) {
                tempLeft = rnd(-width, right);
                tempRight = rnd(max(left, tempLeft+1), width);
            }else{
                tempLeft = rnd(-width, 0);
                tempRight = rnd(tempLeft+1, width);
            }
            left = tempLeft;
            right = tempRight;
            templateLeft.add(left);
            templateRight.add(right);
            //TODO: add some boundaries
        }
    }


    public ArrayList<ArrayList<Tile>> generateRoomTiles(Room room){
        generateTemplate();
        ArrayList<ArrayList<Tile>> tiles = new ArrayList<>();

        for(int i=-height; i<=height; i +=  2) {
            ArrayList<Tile> row = new ArrayList<>();
            for(int j = -width; j < templateLeft.get(i+height); j+=2){
                row.add(new Wall(i*Wall.WIDTH, j * Wall.HEIGHT, room));
            }
            for(int j = templateLeft.get(i+height); j <= templateRight.get(i+height); j+=2){
                row.add(new Floor(i*Wall.WIDTH, j * Wall.HEIGHT, room)); //TODO: Add other tiles
            }
            for(int j = templateRight.get(i+height)+1; j < width; j+=2){
                row.add(new Wall(i*Wall.WIDTH, j * Wall.HEIGHT, room));
            }
            tiles.add(row);
        }

        return tiles;
    }

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


    public void generateMap(){
//        ArrayList<ArrayList<Integer>> graph = generateGraph();
//        int n = graph.size();
//        ArrayList<Room> rooms = new ArrayList<>();
//        for(int i = 0; i < n; i++){
//            rooms.add(new Room(world, game, viewport));
//        }

    }

}
