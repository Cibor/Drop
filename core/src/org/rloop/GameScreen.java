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
import org.rloop.Tiles.Wall;

import java.util.HashSet;

public class GameScreen extends ScreenAdapter {
    final rloop game;
    Player player;
    World world;
    Camera camera;
    Box2DDebugRenderer debugRenderer;
    ExtendViewport viewport;
    Vector2 pos;
    float stateTime;
    boolean paused = false;
    ShapeRenderer shapeRenderer;

    Stage pauseStage;
    GameStage gameScreenStage;

    public HashSet<Monster> monsters;
    HashSet<Monster> monstersNotRender;
    HashSet<Monster> monstersDied;

    Level currentLevel;

    static Skin globalSkin = new Skin(Gdx.files.internal("pixthulhuui/pixthulhu-ui.json"));

    public GameScreen(rloop game) {
        this.game = game;

        debugRenderer = new Box2DDebugRenderer();
        world = new World(new Vector2(0, 0), true);
        world.setContactListener(new GameContactListener(game));

        camera = new OrthographicCamera();
        viewport = new ExtendViewport(32,24,camera);

        LevelBuilder levelBuilder = new LevelBuilder(world);
        currentLevel = new Level(world, game, viewport, levelBuilder.generateRoom());

        //adding player
        Vector2 p = currentLevel.getPlayerPosition();
        player = new Player(p.x,p.y, currentLevel);
        pos = this.player.getBody().getPosition();

        //adding monsters
        monsters = new HashSet<>();
        monstersNotRender = new HashSet<>();
        monstersDied = new HashSet<>();
        monsters.add(new ChasingMonster(-1,-1, currentLevel,player));
        monsters.add(new ShootingMonster(-3, -3, currentLevel, player));

        monsters.add(new ShootingMonsterProjectile(-2, -2, currentLevel, this.player, new Vector2(1,1), 180));
        //monsters.add(new ShootingMonsterProjectile(-2, -2, currentRoom, this.player, new Vector2(1,1), 243));

        pauseStage = new PauseGUI(this, globalSkin).currentStage;

        gameScreenStage = new GameStage(this, globalSkin);

        shapeRenderer = new ShapeRenderer();

    }

    @Override
    public void render(float x){
        if(!paused) {
            update(x);
            justRender(x);
        } else{
            justRender(x);
            renderPauseScreen();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            paused = !paused;
        }
    }

    public void update(float x) {
        player.update();
        camera.position.x = player.x;
        camera.position.y = player.y;

        currentLevel.update();
        //clearing monsters
        for(Monster monster: monstersDied){
            monster.body.getWorld().destroyBody(monster.body);
            monsters.remove(monster);
        }
        monstersDied.clear();

        world.step(1 / 60f, 6, 2);
    }

    public void justRender(float x) {
        ScreenUtils.clear(0, 0, 0, 1);
        currentLevel.render();
        this.player.render();

        //rendering monsters
        for(Monster monster: monsters){
            monster.render();
        }
        for(Monster monster: monstersNotRender){
            monster.render();
            monsters.add(monster);
        }

        monstersNotRender.clear();

        gameScreenStage.update(this);
        gameScreenStage.currentStage.act();
        gameScreenStage.currentStage.draw();

        debugRenderer.render(world, camera.combined);
    }

    public void renderPauseScreen() {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0,0,0,0.5f);
        shapeRenderer.rect(0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shapeRenderer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);
        Gdx.input.setInputProcessor(pauseStage);

        pauseStage.act();
        pauseStage.draw();
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
