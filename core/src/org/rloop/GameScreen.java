package org.rloop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.util.ArrayList;
import java.util.List;

public class GameScreen extends ScreenAdapter {
    final rloop game;
    Player player;
    World world;

    Camera camera;
    Box2DDebugRenderer debugRenderer;
    ExtendViewport viewport;

    Vector2 pos;
    boolean paused = false;
    ShapeRenderer shapeRenderer;

    Stage pauseStage;

    ArrayList<Monster> monsters;

    Room currentRoom;

    public GameScreen(rloop game) {
        this.game = game;

        debugRenderer = new Box2DDebugRenderer();
        world = new World(new Vector2(0, 0), true);
        world.setContactListener(new GameContactListener(game));

        camera = new OrthographicCamera();
        viewport = new ExtendViewport(32,24,camera);

        MapBuilder mapBuilder = new MapBuilder(world);
        mapBuilder.generateRoom();
        //generating map:
//        currentRoom = new Room(world, game, viewport);

//        player = new Player(0,0, currentRoom);
//        pos = this.player.getBody().getPosition();
//        ArrayList<ArrayList<Integer>> graph = mapBuilder.generateGraph();
//        int n = graph.size();
//
//        ArrayList<Room> rooms = new ArrayList<>();
//        for(int i = 0; i < n; i++){
//            Room room = new Room(world, game, viewport);
//            room.setTiles(mapBuilder.generateRoomTiles(room));
//            rooms.add(room);
//        }
//
//        for(int i = 0; i < n; i++){
//            for(int j = 0; j < graph.get(i).size(); j++){
//                Portal portal = new Portal(2,4, rooms.get(i), rooms.get(graph.get(i).get(j)));
//                //TODO: Add Contact listener
//            }
//        }

        //adding player
//        player = new Player(0,0, currentRoom);
//        pos = this.player.getBody().getPosition();

        //adding monsters
//        monsters = new ArrayList<>();
//        monsters.add(new ChasingMonster(-5,-5,currentRoom,player));

        pauseStage = new PauseGUI(this, new Skin(Gdx.files.internal("pixthulhuui/pixthulhu-ui.json"))).currentStage;

        shapeRenderer = new ShapeRenderer();

    }

    @Override
    public void render(float x){
        if(!paused) {
            renderUnpaused();
        }
        else{
            renderPaused();
        }
    }

    void renderPaused(){
//        currentRoom.render();

//        this.player.renderPaused();
//        for(Monster monster: monsters){
//            monster.renderPaused();
//        }

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0,0,0,0.5f);
        shapeRenderer.rect(0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
        Gdx.input.setInputProcessor(pauseStage);

        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            paused = false;
        }

        pauseStage.act();

        pauseStage.draw();
    }

    void renderUnpaused(){
        ScreenUtils.clear(0, 0, 0, 1);

//        currentRoom.render();
//        player.render();
//        for(Monster monster: monsters){
//            monster.render();
//        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            this.paused = true;
        }

        debugRenderer.render(world, camera.combined);
        world.step(1 / 60f, 6, 2);
    }

    @Override
    public void dispose(){
    }

    @Override
    public void resize(int width, int height){
        viewport.update(width,height);
    }

    @Override
    public void pause(){
        paused = true;
    }

    @Override
    public void resume(){
        paused = false;
    }
}
