package org.rloop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.FrictionJointDef;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class GameScreen extends ScreenAdapter {
    final Game game;
    Body player;
    World world;
    float MAX_VELOCITY = 5;
    Camera camera;
    Box2DDebugRenderer debugRenderer;
    BodyDef groundBodyDef;
    Body groundBody;
    ExtendViewport viewport;
    Vector2 vel;
    PolygonShape groundBox;
    Vector2 pos;

    public GameScreen(Game game) {
        this.game = game;

        world = new World(new Vector2(0, 0), true);
        debugRenderer = new Box2DDebugRenderer();
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(0, 0);

        player = world.createBody(def);

        PolygonShape circle = new PolygonShape();
        circle.setAsBox(1, 1);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.2f;
        fixtureDef.restitution = 0f;

//        Fixture fixture = player.createFixture(fixtureDef);
        circle.dispose();
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(32,24,camera);
        vel = this.player.getLinearVelocity();
        pos = this.player.getPosition();

        BodyDef groundBodyDefR = new BodyDef();
        groundBodyDefR.position.set(new Vector2(16, 0));
        Body groundBodyR = world.createBody(groundBodyDefR);
        PolygonShape groundBoxR = new PolygonShape();
        groundBoxR.setAsBox(camera.viewportWidth, 12.0f);
        groundBodyR.createFixture(groundBoxR, 0.0f);
        groundBoxR.dispose();

        BodyDef groundBodyDefL = new BodyDef();
        groundBodyDefL.position.set(new Vector2(-16, 0));
        Body groundBodyL = world.createBody(groundBodyDefL);
        PolygonShape groundBoxL = new PolygonShape();
        groundBoxL.setAsBox(camera.viewportWidth, 12.0f);
        groundBodyL.createFixture(groundBoxL, 0.0f);
        groundBoxL.dispose();

        BodyDef groundBodyDefU = new BodyDef();
        groundBodyDefU.position.set(new Vector2(0, 12));
        Body groundBodyU = world.createBody(groundBodyDefU);
        PolygonShape groundBoxU = new PolygonShape();
        groundBoxU.setAsBox(16.0f, camera.viewportHeight);
        groundBodyU.createFixture(groundBoxU, 0.0f);
        groundBoxU.dispose();

        BodyDef groundBodyDefD = new BodyDef();
        groundBodyDefD.position.set(new Vector2(0, -12));
        Body groundBodyD = world.createBody(groundBodyDefD);
        PolygonShape groundBoxD = new PolygonShape();
        groundBoxD.setAsBox(16.0f, camera.viewportHeight);
        groundBodyD.createFixture(groundBoxD, 0.0f);
        groundBoxD.dispose();
//
//        FrictionJointDef jointDef = new FrictionJointDef ();
//        jointDef.maxForce = 150f;
//        jointDef.maxTorque = 1f;
//        jointDef.initialize(player, groundBodyD, new Vector2(0, 0));
//        Joint frJ = world.createJoint(jointDef);
    }


    float velx;
    float vely;
    @Override
    public void render(float x){
        super.render(x);
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
//            this.player.setLinearVelocity(new Vector2(-10,0));//applyLinearImpulse(-0.80f, 0, pos.x, pos.y, true);
            velx = -10f;
        } else if (velx == -10f) {
            velx = 0;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            velx = 10f;
        } else if (velx == 10f) {
            velx = 0;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W) && vel.y > -MAX_VELOCITY) {
            vely = 10f;
        } else if (vely == 10f) {
            vely = 0;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S) && vel.y < MAX_VELOCITY) {
            vely = -10f;
        } else if (vely == -10f) {
            vely = 0;
        }

        this.player.setLinearVelocity(velx, vely);

        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        debugRenderer.render(world, camera.combined);
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
