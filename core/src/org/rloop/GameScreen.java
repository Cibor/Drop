package org.rloop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

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
    boolean paused = false;
    ShapeRenderer shapeRenderer;

    Stage pauseStage;

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

        pauseStage = new pauseGUI(this, new Skin(Gdx.files.internal("pixthulhuui/pixthulhu-ui.json"))).currentStage;

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
        this.player.setX(this.player.getBody().getPosition().x);
        this.player.setY(this.player.getBody().getPosition().y);
        map.render();
        float velx = player.getBody().getLinearVelocity().x;
        float vely = player.getBody().getLinearVelocity().y;
        if (velx == 0 && vely == 0) {
            stateTime = 0;
        }
        player.render(stateTime);


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
        stateTime += Gdx.graphics.getDeltaTime();
        ScreenUtils.clear(0, 0, 0, 1);
        this.player.setX(this.player.getBody().getPosition().x);
        this.player.setY(this.player.getBody().getPosition().y);

        map.render();
        float velx = player.getBody().getLinearVelocity().x;
        float vely = player.getBody().getLinearVelocity().y;
        if (velx == 0 && vely == 0) {
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
        if (velx != player.getBody().getLinearVelocity().x && vely != player.getBody().getLinearVelocity().y) {
            stateTime = 0;
        }
        this.player.getBody().setLinearVelocity(velx, vely);

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            System.out.println(1);
            this.paused = true;
        }

        //debugRenderer.render(world, camera.combined); еще надо будет
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
