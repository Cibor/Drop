package org.rloop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.rloop.Tiles.Floor;
import org.rloop.Tiles.Tile;
import org.rloop.Tiles.Wall;

import java.util.ArrayList;

import static java.lang.Math.abs;

public class Room {
    protected World world;
    protected rloop game;

    protected static int HALF_ROOM_HEIGHT = 15;
    protected static int HALF_ROOM_WIDTH = 11;
    protected ArrayList<ArrayList<Tile>> tiles  = new ArrayList<>();
    protected Viewport viewport;

    public void GenerateTiles(){
//        for(int i=-HALF_ROOM_HEIGHT;i<=HALF_ROOM_HEIGHT; i +=  2) {
//            ArrayList<Tile> temp = new ArrayList<>();
//            for (int j = -HALF_ROOM_WIDTH; j <= HALF_ROOM_WIDTH; j += 2) {
//                if (abs(i) == HALF_ROOM_HEIGHT || abs(j) == HALF_ROOM_WIDTH) {
//                    temp.add(new Wall(i*Wall.WIDTH, j * Wall.HEIGHT, this));
//                } else {
//                    temp.add(new Floor(i*Wall.WIDTH, j * Wall.HEIGHT, this));
//                }
//            }
//            tiles.add(temp);
//        }
    }

    public void render(){
        this.getCamera().update();
        this.viewport.apply();
        this.getGame().getBatch().setProjectionMatrix(this.getCamera().combined);
        this.getGame().getBatch().begin();
        for(ArrayList<Tile> list: tiles){
            for(Tile tile: list){
                tile.render();
            }
        }
        this.getGame().getBatch().end();
    }

    //TODO: Randomly generated map
    public Room(World world, rloop game, Viewport viewport){
        this.viewport = viewport;
        this.game = game;
        this.world = world;
//        GenerateWalls();
//        GenerateDoors();
        GenerateTiles();
    }

    public void setTiles(ArrayList<ArrayList<Tile>> tiles){
        this.tiles = tiles;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public World getWorld(){
        return world;
    }
    public Camera getCamera(){
        return viewport.getCamera();
    }

    public rloop getGame(){
        return game;
    }

    public void render(float delta) {

    }
}
