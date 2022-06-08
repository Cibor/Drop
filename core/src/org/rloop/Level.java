package org.rloop;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.rloop.Tiles.*;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.abs;

public class Level {
    protected World world;
    protected rloop game;

    protected int HALF_ROOM_HEIGHT = 15;
    protected int HALF_ROOM_WIDTH = 11;
    protected ArrayList<Tile> wallTiles;
    protected ArrayList<Tile> floorTiles;
    protected ArrayList<Spikes> spikesTiles;
    protected Viewport viewport;
    protected Vector2 pos;
    protected ArrayList<Rectangle> template;

    public static boolean PointIsInRectangle(float x, float y, Rectangle r){
        if(x >= r.x && y >= r.y && x <= r.x + r.width && y <= r.y + r.height){
            return true;
        }else{
            return false;
        }
    }

    void chooseTile(int x, int y){
        boolean flagThis = false;
        boolean flagL = false;
        boolean flagR = false;
        boolean flagU = false;
        boolean flagD = false;
        boolean flagLD = false;
        boolean flagLU = false;
        boolean flagRD = false;
        boolean flagRU = false;
//        boolean flagDD = false;
        for(Rectangle rectangle: template) {
            if (PointIsInRectangle(x, y, rectangle)){
                flagThis = true;
            }
        }
//        for(Rectangle rectangle: template) {
//            if (PointIsInRectangle(x-4, y, rectangle)){
//                flagDD = true;
//            }
//        }
        for(Rectangle rectangle: template) {
            if (PointIsInRectangle(x-2, y-2, rectangle)){
                flagLD = true;
            }
        }
        for(Rectangle rectangle: template) {
            if (PointIsInRectangle(x-2, y+2, rectangle)){
                flagLU = true;
            }
        }
        for(Rectangle rectangle: template) {
            if (PointIsInRectangle(x+2, y+2, rectangle)){
                flagRU = true;
            }
        }
        for(Rectangle rectangle: template) {
            if (PointIsInRectangle(x+2, y-2, rectangle)){
                flagRD = true;
            }
        }
        for(Rectangle rectangle: template) {
            if (PointIsInRectangle(x-2, y, rectangle)){
                flagL = true;
            }
        }
        for(Rectangle rectangle: template) {
            if (PointIsInRectangle(x+2, y, rectangle)){
                flagR = true;
            }
        }
        for(Rectangle rectangle: template) {
            if (PointIsInRectangle(x, y-2, rectangle)){
                flagD = true;
            }
        }
        for(Rectangle rectangle: template) {
            if (PointIsInRectangle(x, y+2, rectangle)){
                flagU = true;
            }
        }
        if(flagThis){
            return;
        }
        if(flagL && flagR && flagD && flagU){
            wallTiles.add(new Wall(x-1, y-1, this, 0, 0));
            return;
        }
        if((flagU && flagD && flagL) || (flagU && flagD && flagR)){
            wallTiles.add(new Wall(x-1, y-1, this, 0, 0));
            return;
        }
        if(flagL && flagR && flagD){
            wallTiles.add(new Wall(x-1, y-1, this, 0, 0));
            return;
        }
        if(flagL && flagR && flagU){
            wallTiles.add(new Wall(x-1, y-1, this, 2, 1));
            return;
        }
//        if(flagU && flagR && (flagRD || flagLD || flagDD)){
//            wallTiles.add(new Wall(x-1, y-1, this, 1, 3));
//            return;
//        }
        if(flagU && flagD){
            wallTiles.add(new Wall(x-1, y-1, this, 0, 0));
            return;
        }
        if(flagL && flagD){
            wallTiles.add(new Wall(x-1, y-1, this, 0, 0));
            return;
        }
        if(flagU && flagLD){
            wallTiles.add(new Wall(x-1, y-1, this, 2, 0));
            return;
        }
        if(flagU && flagRD){
            wallTiles.add(new Wall(x-1, y-1, this, 1, 3));
            return;
        }
        if(flagR && flagU){
            wallTiles.add(new Wall(x-1, y-1, this, 1, 3));
            return;
        }
        if(flagL && flagU){
            wallTiles.add(new Wall(x-1, y-1, this, 2, 0));
            return;
        }
        if(flagU || flagD){
            wallTiles.add(new Wall(x-1, y-1, this, 0, 0));
            return;
        }
        if(((flagR && (flagLD || flagLU)) || (flagL && (flagRD || flagRU))) && !(flagU || flagD)){
            wallTiles.add(new Wall(x-1, y-1, this, 3, 0));
            return;
        }
        if(flagL && flagR){
            if(flagU) {
                wallTiles.add(new Wall(x - 1, y - 1, this, 2, 1));
            }else{
                wallTiles.add(new Wall(x - 1, y - 1, this, 3, 0));
            }
            return;
        }
        if(flagL){
            wallTiles.add(new Wall(x-1, y-1, this, 2, 3));
            return;
        }
        if(flagR){
            wallTiles.add(new Wall(x-1, y-1, this, 2, 2));
            return;
        }
        if((flagLU || flagLD) && (flagRU || flagRD)){
            if(flagU) {
                wallTiles.add(new Wall(x - 1, y - 1, this, 2, 1));
            }else{
                wallTiles.add(new Wall(x - 1, y - 1, this, 3, 0));
            }
            return;
        }
        if((flagLU || flagRU) && (flagLD || flagRD)){
            if(flagRU) {
                wallTiles.add(new Wall(x - 1, y - 1, this, 2,2));
            }else{
                wallTiles.add(new Wall(x - 1, y - 1, this, 2,3));
            }
            return;
        }
        if(flagRU){
            wallTiles.add(new Wall(x-1, y-1, this, 3, 1));
            return;
        }
        if(flagLD){
            wallTiles.add(new Wall(x-1, y-1, this, 2, 3));
            return;
        }
        if(flagRD){
            wallTiles.add(new Wall(x-1, y-1, this, 2, 2));
            return;
        }
        if(flagLU){
            wallTiles.add(new Wall(x-1, y-1, this, 3, 2));
            return;
        }
    }
    public void GenerateTiles(){
        wallTiles  = new ArrayList<>();
        floorTiles = new ArrayList<>();
        spikesTiles = new ArrayList<>();
        for(Rectangle r1: template){
            for(int i=0;i<r1.width;i+=2){
                for(int j=0;j<r1.height;j+=2){
                    floorTiles.add(new Floor((int)r1.x + i, (int)r1.y+j, this));
                    if(new Random().nextInt(13) == 0){
                        if(new Random().nextBoolean())
                        spikesTiles.add(new Spikes((int)r1.x + i, (int)r1.y+j, this));
                        else spikesTiles.add(new HiddenSpikes((int)r1.x + i, (int)r1.y+j, this));
                    }
                }
            }

            chooseTile((int) r1.x-1,(int)r1.y - 1);
            for(int i = 0; i <= r1.width; i += 2){
                for(int j = 0; j <= r1.height; j += 2){
                    float x = r1.x + i;
                    float y = r1.y + j;
                    chooseTile((int) x+1,(int)r1.y - 1);
                    chooseTile((int) x+1,(int)(r1.y + r1.height + 1));
                    chooseTile((int)r1.x-1,  (int) y+1);
                    chooseTile((int) r1.x + (int) r1.width + 1, (int) y+1);
                }
            }
        }
    }

    public void render(){
        this.getCamera().update();
        this.viewport.apply();
        this.getGame().getBatch().setProjectionMatrix(this.getCamera().combined);
        this.getGame().getBatch().begin();
        for(Tile tile: wallTiles){
            tile.render();
        }
        for(Tile tile: floorTiles){
            tile.render();
        }
        for(Tile tile: spikesTiles){
            tile.render();
        }
        this.getGame().getBatch().end();
    }

    public void update(){
        for(Tile tile: wallTiles){
            tile.update();
        }
        for(Tile tile: floorTiles){
            tile.update();
        }
        for(Tile tile: spikesTiles){
            tile.update();
        }
    }

    public Vector2 getPositionOfSomething(){
        int k = new Random().nextInt(floorTiles.size());
        return new Vector2(floorTiles.get(k).getX(), floorTiles.get(k).getY());
    }

    public Vector2 getPlayerCurrentPosition(){
        return new Vector2(game.mainScreen.getPlayer().x, game.mainScreen.getPlayer().y);
    }

    public Vector2 getPortalPosition(){
        int k = new Random().nextInt(template.size());
        return new Vector2(template.get(k).x+2, template.get(k).y+2);
    }

    public Level(World world, rloop game, Viewport viewport, ArrayList<Rectangle> r){
        this.template = r;
        pos = new Vector2();
        this.viewport = viewport;
        this.game = game;
        this.world = world;
        GenerateTiles();
    }

    public Player getPlayer(){
        return game.mainScreen.getPlayer();
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

    public void dispose(){
        wallTiles.clear();
        floorTiles.clear();
        spikesTiles.clear();
    }
}
