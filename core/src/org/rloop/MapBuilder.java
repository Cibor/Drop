package org.rloop;

import com.badlogic.gdx.Game;
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

import static java.lang.Math.max;


public class MapBuilder {
    int width;
    int height;
    ArrayList<Integer> roomWidth, roomHeight;
    ArrayList<Fixture> rooms;
    int n;
    World world;

    public static int rnd(int min, int max)
    {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }

    public MapBuilder(World world){
        this.world = world;
        this.width = Room.HALF_ROOM_HEIGHT;
        this.height = Room.HALF_ROOM_WIDTH;
    }

    public static boolean IsOverlapping(Fixture fA, Fixture fB) {
        ArrayList<Vector2> outputVertices = new ArrayList<>();
        PolygonShape polyA = (PolygonShape) fA.getShape();
        PolygonShape polyB = (PolygonShape) fB.getShape();

        for (int i = 0; i < polyA.getVertexCount(); i++) {
            Vector2 vertex = new Vector2();
            polyA.getVertex(i, vertex);
            vertex = fA.getBody().getWorldPoint(vertex);
            outputVertices.add(new Vector2(vertex));
        }

        // fill clip polygon from fixtureB polygon
        ArrayList<Vector2> clipPolygon = new ArrayList<>();
        for (int i = 0; i < polyB.getVertexCount(); i++) {
            Vector2 vertex = new Vector2();
            polyB.getVertex(i, vertex);
            vertex = fB.getBody().getWorldPoint(vertex);
            clipPolygon.add(new Vector2(vertex));
        }

        Vector2 cp1 = clipPolygon.get(clipPolygon.size() - 1);
        for (int j = 0; j < clipPolygon.size(); j++) {
            Vector2 cp2 = clipPolygon.get(j);
            if (outputVertices.isEmpty())
                return false;
            ArrayList<Vector2> inputList = new ArrayList<Vector2>(outputVertices);
            outputVertices.clear();
            Vector2 s = inputList.get(inputList.size() - 1);
            for (int i = 0; i < inputList.size(); i++) {
                Vector2 e = inputList.get(i);
                if (inside(cp1, cp2, e)) {
                    if (!inside(cp1, cp2, s)) {
                        outputVertices.add(intersection(cp1, cp2, s, e));
                    }
                    outputVertices.add(e);
                } else if (inside(cp1, cp2, s)) {
                    outputVertices.add(intersection(cp1, cp2, s, e));
                }
                s = e;
            }
            cp1 = cp2;
        }

        return !outputVertices.isEmpty();
    }

    public static Vector2 intersection(Vector2 cp1, Vector2 cp2, Vector2 s, Vector2 e) {
        Vector2 dc = new Vector2(cp1.x - cp2.x, cp1.y - cp2.y);
        Vector2 dp = new Vector2(s.x - e.x, s.y - e.y);
        float n1 = cp1.x * cp2.y - cp1.y * cp2.x;
        float n2 = s.x * e.y - s.y * e.x;
        float n3 = (dc.x * dp.y - dc.y * dp.x);
        if(n3 != 0){
            n3 = 1.0f / n3;
            return new Vector2((n1 * dp.x - n2 * dc.x) * n3, (n1 * dp.y - n2 * dc.y) * n3);
        }

        return null;
    }

    public static boolean inside(Vector2 cp1, Vector2 cp2, Vector2 p) {
        return (cp2.x - cp1.x) * (p.y - cp1.y) > (cp2.y - cp1.y) * (p.x - cp1.x);
    }

    public boolean IsAnyRoomOverlapped(){
        for(Fixture f1: rooms){
            for(Fixture f2: rooms){
                if(f1 != f2 && IsOverlapping(f1,f2)){
                    return true;
                }
            }
        }
        return false;
    }

    private void SeparateRooms()
    {
        do {
            for (int current = 0; current < n; current++)
            {
                for (int other = 0; other < n; other++)
                {
                    if (current == other || !IsOverlapping(rooms.get(current), rooms.get(other))){
                        continue;
                    }
                    float x1 = rooms.get(other).getBody().getPosition().x;
                    float y1 = rooms.get(other).getBody().getPosition().y;
                    float x2 = rooms.get(current).getBody().getPosition().x;
                    float y2 = rooms.get(current).getBody().getPosition().y;

                    if(x1 - x2 >= 0){
                        rooms.get(other).getBody().getPosition().x += 2;
                        rooms.get(current).getBody().getPosition().x -= 2;

                        if(y1 - y2 >= 0){
                            rooms.get(other).getBody().getPosition().y += 2;
                            rooms.get(current).getBody().getPosition().y -= 2;
                        }else{
                            rooms.get(other).getBody().getPosition().y -= 2;
                            rooms.get(current).getBody().getPosition().y -= 2;
                        }
                    }else{
                        rooms.get(other).getBody().getPosition().x -= 2;
                        rooms.get(current).getBody().getPosition().x += 2;
                        if(y1 - y2 >= 0){
                            rooms.get(other).getBody().getPosition().y += 2;
                            rooms.get(current).getBody().getPosition().y -= 2;
                        }else{
                            rooms.get(other).getBody().getPosition().y += 2;
                            rooms.get(current).getBody().getPosition().y -= 2;
                        }
                    }
                }
            }
        } while (IsAnyRoomOverlapped());
    }

    public void generateRoom(){
        n = rnd(1, 5);
        rooms = new ArrayList<>();
        for(int i = 0; i < n; i++){
            BodyDef barrierDef = new BodyDef();
            barrierDef.position.set(new Vector2(0,0));
            Body room = world.createBody(barrierDef);

            PolygonShape barrierBox = new PolygonShape();
            barrierBox.setAsBox(rnd(15, 25), rnd(15, 25));
            Fixture fixture = room.createFixture(barrierBox, 0.0f);
            barrierBox.dispose();
            rooms.add(fixture);
        }
        SeparateRooms();
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


    public void generateMap(){
//        ArrayList<ArrayList<Integer>> graph = generateGraph();
//        int n = graph.size();
//        ArrayList<Room> rooms = new ArrayList<>();
//        for(int i = 0; i < n; i++){
//            rooms.add(new Room(world, game, viewport));
//        }

    }

}
