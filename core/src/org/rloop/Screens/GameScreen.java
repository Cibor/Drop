package org.rloop.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.rloop.*;
import org.rloop.Stages.GameStage;
import org.rloop.Stages.*;

import java.util.ArrayList;
import java.util.HashSet;

import static org.rloop.Util.rnd;

public class GameScreen extends ScreenAdapter {
    final rloop game;
    Player player;
    public World world;
    Camera camera;
    Box2DDebugRenderer debugRenderer;
    public ExtendViewport viewport;
    Vector2 pos;
    public boolean paused = false;
    ShapeRenderer shapeRenderer;

    public boolean nextLvl = false;

    public Stage pauseStage;
    public Stage chestStage;
    GameStage gameScreenStage;

    public HashSet<Monster> monsters;
    public HashSet<Monster> monstersNotRender;
    public HashSet<Monster> monstersDied;
    public HashSet<Projectiles> projectiles;
    public HashSet<Projectiles> projectilesNotRender;
    public HashSet<Projectiles> projectilesDied;

    public ArrayList<Items> itemList;
    public ArrayList<Class> PossibleItems ;

    public boolean choosenWeapon;
    public LevelBuilder levelBuilder;
    public Portal portal = null;
    public Chest chest;
    public boolean chestMode = false;

    public int monsterCountLeft = 5;
    public int monsterCountRight = 8;


    public Level currentLevel;

    static Skin globalSkin = new Skin(Gdx.files.internal("pixthulhuui/pixthulhu-ui.json"));

    public GameScreen(rloop game, boolean choosenWeapon) {
        this.game = game;
        this.choosenWeapon = choosenWeapon;
        PossibleItems = new ArrayList<>();
        itemList = new ArrayList<>();


        PossibleItems.add(DamagePotion.class);
        PossibleItems.add(SpeedPotion.class);
        PossibleItems.add(HpPotion.class);
        PossibleItems.add(DefendingShoes.class);
        PossibleItems.add(TripleRangeWeapon.class);

        chestStage = new Stage(new ScreenViewport());
        //debugRenderer = new Box2DDebugRenderer();
        world = new World(new Vector2(0, 0), true);
        world.setContactListener(new GameContactListener(game));

        camera = new OrthographicCamera();
        viewport = new ExtendViewport(32,24,camera);


        levelBuilder = new LevelBuilder(world);


        currentLevel = new Level(world, game, viewport, levelBuilder.generateRoom());

        //adding player
        Vector2 playerPos = currentLevel.getPositionOfSomething();
        player = new Player(playerPos.x,playerPos.y, currentLevel, choosenWeapon, this, false);
        pos = this.player.getBody().getPosition();

        //adding monsters
        monsters = new HashSet<>();
        monstersNotRender = new HashSet<>();
        monstersDied = new HashSet<>();


        int numberOfMonsters = rnd(monsterCountLeft,monsterCountRight);
        for (int i = 0; i < numberOfMonsters; i++) {
            Vector2 monsterPos;
            do {
                monsterPos = currentLevel.getPositionOfSomething();
            } while(monsterPos.dst(playerPos) < 10);

            int decideType = rnd(0, 1);
            if (decideType == 0) {
                monsters.add(new ChasingMonster(monsterPos.x,monsterPos.y, currentLevel,player));
            } else if (decideType == 1) {
                monsters.add(new ShootingMonster(monsterPos.x, monsterPos.y, currentLevel, player));
            }
        }

        projectiles = new HashSet<>();
        projectilesNotRender = new HashSet<>();
        projectilesDied = new HashSet<>();

        pauseStage = new PauseGUI(this, globalSkin).getCurrentStage();
//        chestStage = new ChestGUI(this, globalSkin).getCurrentStage();

        gameScreenStage = new GameStage(this, globalSkin);

        shapeRenderer = new ShapeRenderer();

    }

    @Override
    public void render(float x){
        if(nextLvl){
            nextLvl = false;
            currentLevel.dispose();
            portal = null;
            chest = null;
            monsters.clear();
            monstersNotRender.clear();
            monstersDied.clear();
            projectiles.clear();
            projectilesNotRender.clear();
            projectilesDied.clear();
            Array<Body> bodys = new Array<>();
            world.getBodies(bodys);
            for(Body body : bodys){
                world.destroyBody(body);
            }
            createNextLvl();
        }

        if(paused) {
            justRender(x);
            renderPauseScreen();
        } else if(chestMode){
            justRender(x);
            renderChestScreen();
        }else{
            update(x);
            justRender(x);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            if(!chestMode) {
                paused = !paused;
            }else{
                chest.textureNumber = 0;
                chestMode = false;
                Gdx.input.setInputProcessor(gameScreenStage.getCurrentStage());
            }
        }
    }

    public void update(float x) {
        player.update();
        camera.position.x = player.getX();
        camera.position.y = player.getY();
        if(player.getCurrentHP() <= 0){
            game.setScreen(new DeathScreen(game));
            return;
        }

        currentLevel.update();
        //clearing monsters

        for(Monster monster: monsters){
            monster.update(x);
        }

        for(Monster monster: monstersDied){
            monster.body.getWorld().destroyBody(monster.body);
            monsters.remove(monster);
        }
        monstersDied.clear();

        for(Projectiles projectile: projectiles){
            projectile.update(x);
        }

        for(Projectiles projectile: projectilesDied){
            projectile.body.getWorld().destroyBody(projectile.body);
            projectiles.remove(projectile);
        }
        projectilesDied.clear();

        world.step(1 / 60f, 6, 2);
    }

    public void justRender(float x) {
        ScreenUtils.clear(0, 0, 0, 1);
        currentLevel.render();
        //rendering monsters
        for(Monster monster: monsters){
            monster.render();
        }
        for(Monster monster: monstersNotRender){
            monster.render();
            monsters.add(monster);
        }

        monstersNotRender.clear();

        if(monstersDied.size() == monsters.size()){
            if(portal == null) {
                Vector2 portalPos;
                do {
                    portalPos = currentLevel.getPortalPosition();
                } while (portalPos.dst(player.getPosition()) < 10);
                portal = new Portal(portalPos, currentLevel, this);
                Vector2 chestPos;
                do {
                    chestPos = currentLevel.getPositionOfSomething();
                } while (chestPos.dst(player.getPosition()) < 10 || chestPos.dst(portalPos) < 4);
                chest = new Chest(chestPos, currentLevel, this);
            }else{
                portal.render();
                chest.render();
            }
        }

        for(Projectiles projectile: projectiles){
            projectile.render();
        }
        for(Projectiles projectile: projectilesNotRender){
            projectile.render();
            projectiles.add(projectile);
        }

        this.player.render();

        projectilesNotRender.clear();

        gameScreenStage.update(this);
        gameScreenStage.getCurrentStage().act();
        gameScreenStage.getCurrentStage().draw();

        //debugRenderer.render(world, camera.combined);
    }

    public void renderChestScreen(){
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0,0,0,0.5f);
        shapeRenderer.rect(0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shapeRenderer.setColor(1,10,1,0.5f);
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 4; j++){
                shapeRenderer.rect(Util.monitorResolutionX(1000+110*i),Util.monitorResolutionY(600+110*j), Util.monitorResolutionX(100), Util.monitorResolutionY(100));
            }
        }
        shapeRenderer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);
        Gdx.input.setInputProcessor(chestStage);
        chestStage.act();
        chestStage.draw();
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

    public Player getPlayer() {return player;}
    public rloop getGame() {return game;}

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

    void createNextLvl(){
        world = new World(new Vector2(0, 0), true);
        world.setContactListener(new GameContactListener(game));

        levelBuilder = new LevelBuilder(world);

        currentLevel = new Level(world, game, viewport, levelBuilder.generateRoom());

        Vector2 playerPos = currentLevel.getPositionOfSomething();

        Player playercopy = new Player(playerPos.x,playerPos.y, currentLevel, false, this, true);

        playercopy.playerWeapon = player.playerWeapon;
        playercopy.playerDamage = player.playerDamage;
        playercopy.playerAttackSpeed = player.playerAttackSpeed;
        playercopy.statSpeed = player.statSpeed;
        playercopy.statCurrentHP = player.statCurrentHP;
        playercopy.statMaxHP = player.statMaxHP;

        player = playercopy;

        pos = this.player.getBody().getPosition();
        monsterCountLeft = monsterCountRight;
        monsterCountRight += 3;
        int numberOfMonsters = rnd(monsterCountLeft,monsterCountRight);
        for (int i = 0; i < numberOfMonsters; i++) {
            Vector2 monsterPos;
            do {
                monsterPos = currentLevel.getPositionOfSomething();
            } while(monsterPos.dst(playerPos) < 10);

            int decideType = rnd(0, 1);
            if (decideType == 0) {
                monsters.add(new ChasingMonster(monsterPos.x,monsterPos.y, currentLevel,getPlayer()));
            } else if (decideType == 1) {
                monsters.add(new ShootingMonster(monsterPos.x, monsterPos.y, currentLevel, getPlayer()));
            }
        }
    }
}