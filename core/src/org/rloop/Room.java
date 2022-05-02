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
    protected ArrayList<Door> doors;
    protected rloop game;

    protected int HALF_ROOM_HEIGHT = 15;
    protected int HALF_ROOM_WIDTH = 11;
    protected ArrayList<ArrayList<Tile>> tiles  = new ArrayList<>();
    protected Viewport viewport;

    private void GenerateWalls(){

        BodyDef groundBodyDefR = new BodyDef();
        groundBodyDefR.position.set(new Vector2(16, 0));
        Body groundBodyR = world.createBody(groundBodyDefR);
        PolygonShape groundBoxR = new PolygonShape();
        groundBoxR.setAsBox(viewport.getCamera().viewportWidth, 12.0f);
        groundBodyR.createFixture(groundBoxR, 0.0f);
        groundBoxR.dispose();

        BodyDef groundBodyDefL = new BodyDef();
        groundBodyDefL.position.set(new Vector2(-16, 0));
        Body groundBodyL = world.createBody(groundBodyDefL);
        PolygonShape groundBoxL = new PolygonShape();
        groundBoxL.setAsBox(viewport.getCamera().viewportWidth, 12.0f);
        groundBodyL.createFixture(groundBoxL, 0.0f);
        groundBoxL.dispose();

        BodyDef groundBodyDefU = new BodyDef();
        groundBodyDefU.position.set(new Vector2(0, 12));
        Body groundBodyU = world.createBody(groundBodyDefU);
        PolygonShape groundBoxU = new PolygonShape();
        groundBoxU.setAsBox(16.0f, viewport.getCamera().viewportHeight);
        groundBodyU.createFixture(groundBoxU, 0.0f);
        groundBoxU.dispose();

        BodyDef groundBodyDefD = new BodyDef();
        groundBodyDefD.position.set(new Vector2(0, -12));
        Body groundBodyD = world.createBody(groundBodyDefD);
        PolygonShape groundBoxD = new PolygonShape();
        groundBoxD.setAsBox(16.0f, viewport.getCamera().viewportHeight);
        groundBodyD.createFixture(groundBoxD, 0.0f);
        groundBoxD.dispose();
    }
    public void GenerateDoors(){
//        Random rand = new Random();
//        if(rand.nextInt(2) == 0){
//            doors.add(new Door(new Random().nextInt(22) - 12, -10, this));
//        }
//        if(rand.nextInt(2) == 0){
//            doors.add(new Door(new Random().nextInt(22) -12, 10, this));
//        }
//        if(rand.nextInt(2) == 0){
//            doors.add(new Door(-14, new Random().nextInt(32) - 16, this));
//        }
//        if(rand.nextInt(2) == 0) {
//            doors.add(new Door(14, new Random().nextInt(32) - 16, this));
//        }
    }
    public void GenerateTiles(){
        for(int i=-HALF_ROOM_HEIGHT;i<=HALF_ROOM_HEIGHT; i +=  2) {
            ArrayList<Tile> temp = new ArrayList<>();
            for (int j = -HALF_ROOM_WIDTH; j <= HALF_ROOM_WIDTH; j += 2) {
                if (abs(i) == HALF_ROOM_HEIGHT || abs(j) == HALF_ROOM_WIDTH) {
                    temp.add(new Wall(i*Wall.WIDTH, j * Wall.HEIGHT, this));
                } else {
                    temp.add(new Floor(i*Wall.WIDTH, j * Wall.HEIGHT, this));
                }
            }
            tiles.add(temp);
        }
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
        doors = new ArrayList<>();
//        GenerateDoors();
        GenerateTiles();
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
