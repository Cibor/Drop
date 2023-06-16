package pl.ciborowski.konrad.viewmodel;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import static com.badlogic.gdx.math.MathUtils.random;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import static java.lang.System.currentTimeMillis;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import pl.ciborowski.konrad.model.Character;
import pl.ciborowski.konrad.model.Direction;
import pl.ciborowski.konrad.model.Level;
import static pl.ciborowski.konrad.model.Role.*;
import pl.ciborowski.konrad.view.GameScreen;
import static pl.ciborowski.konrad.view.GameScreen.*;
import pl.ciborowski.konrad.view.Shape;

public class GameManager {

    public Game game;
    private Level level;
    private Map<Character, Shape> shapes = new HashMap<>();
    private CharacterMover characterMover;
    private final GameScreen gameScreen;
    private Character hero;
    private boolean bulletFired;

    public static final Texture heroImage = new Texture(Gdx.files.internal("hero2.png"));
    private static final Texture enemyImage = new Texture(Gdx.files.internal("enemy2.png"));
    private static final Texture bulletImage = new Texture(Gdx.files.internal("bullet-png-free-image-download-11.png"));

    public GameManager(Game game) {
        this.game = game;
        gameScreen = new GameScreen(this);
        game.setScreen(gameScreen);
        prepareNewLevel();
    }

    public Collection<Shape> getShapesAfterMove() {
        characterMover.moveEnemies(gameScreen.getDeltaTime());
        var newEnemyBullets = fireByEnemiesAndReturnNewBullets();
        for (var bullet : newEnemyBullets) {
            shapes.put(bullet, new Shape(bulletImage, new Rectangle(bullet.x, bullet.y, 20, 20)));
        }
        changeHeroDirections();
        if (bulletFired) {
            addBulletShotByHero();
        }
        characterMover.moveHero(gameScreen.getDeltaTime());
        var bulletOutOfBounds = characterMover.moveBulletsAndReturnBulletsOutOfBounds(gameScreen.getDeltaTime());
        bulletOutOfBounds.forEach(bullet -> shapes.remove(bullet));
        adjustShapesPositionToMatchCharacters();
        return shapes.values();
    }

    public void changeHeroDirections() {
        hero.directions.clear();
        for (var direction : Direction.values()) {
            for (var keyCode : direction.keyCodes) {
                if (gameScreen.isKeyPressed(keyCode)) {
                    hero.directions.add(direction);
                }
            }
        }
    }

    public void fireByHero() {
        long currentTime = currentTimeMillis();
        if (currentTime - hero.timeOfLastShotInMillis > hero.coolDownPeriodInMillis) {
            bulletFired = true;
            hero.timeOfLastShotInMillis = currentTime;
        }
    }

    private List<Character> fireByEnemiesAndReturnNewBullets() {
        long currentTime = currentTimeMillis();
        List<Character> bullets = new LinkedList<>();
        shapes.keySet().stream().filter(c -> c.role == ENEMY).forEach(enemy -> {
            if (currentTime - enemy.timeOfLastShotInMillis > enemy.coolDownPeriodInMillis) {
                enemy.timeOfLastShotInMillis = currentTime;
                var bullet = new Character(ENEMY_BULLET);
                bullet.x = enemy.x;
                bullet.y = enemy.y;
                bullet.speed = 2 * enemy.speed;
                bullet.speedVector = new Vector2((hero.x - enemy.x), hero.y - enemy.y);
                bullets.add(bullet);
            }
        });
        return bullets;
    }

    private void addBulletShotByHero() {
        bulletFired = false;
        if (hero.directions.isEmpty()) {
            return;
        }
        var bullet = new Character(HERO_BULLET);
        bullet.x = hero.x;
        bullet.y = hero.y;
        bullet.speed = 2 * hero.speed;
        bullet.directions = new HashSet<>(hero.directions);
        shapes.put(bullet, new Shape(bulletImage, new Rectangle(hero.x, hero.y, 20, 20)));
    }

    private void adjustShapesPositionToMatchCharacters() {
        for (var entry : shapes.entrySet()) {
            var shape = entry.getValue();
            var character = entry.getKey();
            shape.rectangle.x = character.x;
            shape.rectangle.y = character.y;
        }
    }

    private void prepareNewLevel() {
        var levelNumber = level == null ? 1 : level.number + 1;
        hero = new Character(HERO);
        List<Character> characters = new LinkedList<>();
        hero.x = (CAMERA_WIDTH - HERO_WIDTH) / 2;
        hero.y = (CAMERA_HEIGHT - HERO_HEIGHT) / 2;
        hero.coolDownPeriodInMillis = 1000;
        hero.speed = 200;
        characters.add(hero);
        for (int i = 0; i < levelNumber; i++) {
            var enemy = new Character(ENEMY);
            enemy.x = random(0, CAMERA_WIDTH - ENEMY_WIDTH);
            enemy.y = random(0, CAMERA_HEIGHT - ENEMY_HEIGHT);
            enemy.coolDownPeriodInMillis = 1000;
            enemy.speed = 10 * levelNumber;
            characters.add(enemy);
        }
        level = new Level(levelNumber, characters);
        initializeShapesMap();
    }

    private void initializeShapesMap() {
        shapes.clear();
        addHeroShapeForCurrentLevel();
        addEnemyShapesForCurrentLevel();
        characterMover = new CharacterMover(shapes);
    }

    private void addHeroShapeForCurrentLevel() {
        var rectangle = new Rectangle(hero.x, hero.y, HERO_WIDTH, HERO_HEIGHT);
        shapes.put(hero, new Shape(heroImage, rectangle));
    }

    private void addEnemyShapesForCurrentLevel() {
        level.characters.stream().filter(c -> c.role == ENEMY).forEach(enemy -> {
            shapes.put(enemy, new Shape(enemyImage, new Rectangle(enemy.x, enemy.y, ENEMY_WIDTH, ENEMY_HEIGHT)));
        });
    }
}