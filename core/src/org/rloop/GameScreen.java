package org.rloop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class GameScreen extends ScreenAdapter {
    final rloop game;
    Player player;
    World world;
    float MAX_VELOCITY = 5;
    Camera camera;
    Box2DDebugRenderer debugRenderer;
    ExtendViewport viewport;
    Vector2 vel;
    Vector2 pos;
    Room map;
    float stateTime;

    public GameScreen(rloop game) {
        this.game = game;

        debugRenderer = new Box2DDebugRenderer();
        world = new World(new Vector2(0, 0), true);
        world.setContactListener(new DoorsContactListener(game));

        camera = new OrthographicCamera();
        viewport = new ExtendViewport(32,24,camera);

        map = new Room(world, game, viewport);

        player = new Player(0,0, map);
        vel = this.player.getBody().getLinearVelocity();
        pos = this.player.getBody().getPosition();

        stateTime = 0;
    }

    @Override
    public void render(float x){
        stateTime +=  Gdx.graphics.getDeltaTime();
        ScreenUtils.clear(0, 0, 0, 1);
        this.player.setX(this.player.getBody().getPosition().x);
        this.player.setY(this.player.getBody().getPosition().y);
//        camera.update();
//        viewport.apply();
//        game.getBatch().setProjectionMatrix(camera.combined);
//        game.getBatch().begin();
//        game.getBatch().draw(new Texture("WallSet.png"),10, 2, 1,1);
////		batchForPlayer.draw(player,  800 / 2 - 64 / 2,400);
//        game.getBatch().end();
        map.render();
        float velx = player.getBody().getLinearVelocity().x;
        float vely = player.getBody().getLinearVelocity().y;
        if(velx == 0 && vely == 0){
            stateTime = 0;
        }
        player.render(stateTime);

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            this.player.setDirection(3);
            velx = -10f;
        } else if (velx == -10f) {
            velx = 0;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            this.player.setDirection(2);
            velx = 10f;
        } else if (velx == 10f) {
            velx = 0;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W) && vel.y > -MAX_VELOCITY) {
            this.player.setDirection(0);
            vely = 10f;
        } else if (vely == 10f) {
            vely = 0;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S) && vel.y < MAX_VELOCITY) {
            this.player.setDirection(1);
            vely = -10f;
        } else if (vely == -10f) {
            vely = 0;
        }
        if(velx != player.getBody().getLinearVelocity().x && vely != player.getBody().getLinearVelocity().y){
            stateTime = 0;
        }
        this.player.getBody().setLinearVelocity(velx, vely);

//        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //debugRenderer.render(world, camera.combined); еще надо будет
        world.step(1/60f, 6, 2);
    }


    @Override
    public void dispose(){
    }

    @Override
    public void resize(int width, int height){
        viewport.update(width,height);
    }

}
