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
    float MAX_VELOCITY = 5;
    Camera camera;
    Box2DDebugRenderer debugRenderer;
    ExtendViewport viewport;
    Vector2 vel;
    Vector2 pos;
    Level map;
    float stateTime;
    boolean paused = false;
    ShapeRenderer shapeRenderer;

    Stage pauseStage;
    GameStage gameScreenStage;

    HashSet<Monster> monsters;
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
        Vector2 p = currentLevel.getPlayerPosition();
        player = new Player(p.x,p.y, currentLevel);
        pos = this.player.getBody().getPosition();

        //adding player
//        player = new Player(0,0, currentRoom);
//        pos = this.player.getBody().getPosition();

        //adding monsters
        monsters = new HashSet<>();
        monstersNotRender = new HashSet<>();
        monstersDied = new HashSet<>();
        monsters.add(new ChasingMonster(-1,-1, currentLevel,player));
       // monsters.add(new ShootingMonster(-3, -3, currentRoom, player));

        monsters.add(new ShootingMonsterProjectile(-2, -2, currentLevel, this.player, new Vector2(1,1), 180));
        //monsters.add(new ShootingMonsterProjectile(-2, -2, currentRoom, this.player, new Vector2(1,1), 243));

        pauseStage = new PauseGUI(this, globalSkin).currentStage;

        gameScreenStage = new GameStage(this, globalSkin);

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
        currentLevel.render();
        this.player.renderPaused();
        for(Monster monster: monsters){
            monster.renderPaused();
        }

        for(Monster monster: monstersNotRender){
            monster.renderPaused();
            monsters.add(monster);
        }

        for(Monster monster: monstersDied){
            monster.body.getWorld().destroyBody(monster.body);
            monsters.remove(monster);
        }
        monstersDied.clear();

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
        System.out.println(2);
        stateTime += Gdx.graphics.getDeltaTime();
        ScreenUtils.clear(0, 0, 0, 1);

        currentLevel.render();
        player.render();

        for(Monster monster: monstersDied){
            monster.body.getWorld().destroyBody(monster.body);
            if(monsters.contains(monster))
                monsters.remove(monster);
        }

        for(Monster monster: monsters){
           monster.render();
        }
        for(Monster monster: monstersNotRender){
            monster.render();
            monsters.add(monster);
        }

        monstersDied.clear();

        monstersNotRender.clear();

        camera.position.x = player.x;
        camera.position.y = player.y;
        for(Contact curCon : world.getContactList()){
            Fixture fa = curCon.getFixtureA();
            Fixture fb = curCon.getFixtureB();
            if(fa == null || fb == null){
                continue;
            }
            if(fa.getUserData() == null || fb.getUserData() == null){
               continue;
            }
            if((fa.getUserData().getClass() == Player.class && fb.getUserData().getClass() == ChasingMonster.class) || (fb.getUserData().getClass() == Player.class && fa.getUserData().getClass() == ChasingMonster.class)) {
                Player curPlayer;
                ChasingMonster curMonster;
                if (fb.getUserData().getClass() == Player.class) {
                    curPlayer = (Player) fb.getUserData();
                    curMonster = (ChasingMonster) fa.getUserData();
                } else {
                    curPlayer = (Player) fa.getUserData();
                    curMonster = (ChasingMonster) fb.getUserData();
                }
                if (!curPlayer.isImmune()) {
                    curPlayer.getHit(curMonster.damageMonst);
                    curPlayer.makeImmune();
                    Gdx.audio.newSound(Gdx.files.internal("music/DamageSound.mp3")).play(game.GlobalAudioSound);
                }
            }
            else
            if((fa.getUserData().getClass() == Player.class && fb.getUserData().getClass() == ShootingMonsterProjectile.class) || (fb.getUserData().getClass() == Player.class && fa.getUserData().getClass() == ShootingMonsterProjectile.class)) {
                Player curPlayer;
                ShootingMonsterProjectile curMonster;
                if (fb.getUserData().getClass() == Player.class) {
                    curPlayer = (Player) fb.getUserData();
                    curMonster = (ShootingMonsterProjectile) fa.getUserData();
                } else {
                    curPlayer = (Player) fa.getUserData();
                    curMonster = (ShootingMonsterProjectile) fb.getUserData();
                }

                if (!curPlayer.isImmune()){
                    curPlayer.getHit(curMonster.damageMonst);
                    curPlayer.makeImmune();
                    Gdx.audio.newSound(Gdx.files.internal("music/DamageSound.mp3")).play(game.GlobalAudioSound);
                }

                curMonster.getBody().getWorld().destroyBody(curMonster.getBody());
                monsters.remove(curMonster);
            }
            else if ((fa.getUserData().getClass() == Wall.class && fb.getUserData().getClass() == ShootingMonsterProjectile.class) || (fb.getUserData().getClass() == Wall.class && fa.getUserData().getClass() == ShootingMonsterProjectile.class)){
                Wall curWall;
                ShootingMonsterProjectile curMonster;
                if(fb.getUserData().getClass() == Wall.class) {
                    curWall = (Wall) fb.getUserData();
                    curMonster = (ShootingMonsterProjectile) fa.getUserData();
                }
                else{
                    curWall = (Wall) fa.getUserData();
                    curMonster = (ShootingMonsterProjectile) fb.getUserData();
                }

                curMonster.getBody().getWorld().destroyBody(curMonster.getBody());
                monsters.remove(curMonster);
            }
        }
        if(player.isImmune())
        player.damageImmune--;


        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            this.paused = true;
        }
        gameScreenStage.update(this);
        gameScreenStage.currentStage.act();
        gameScreenStage.currentStage.draw();

        debugRenderer.render(world, camera.combined);
        System.out.println(1);
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
