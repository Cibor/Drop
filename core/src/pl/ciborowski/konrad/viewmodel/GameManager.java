package pl.ciborowski.konrad.viewmodel;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import java.util.Collection;
import java.util.HashMap;
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
    
    public static final Texture heroImage  = new Texture(Gdx.files.internal("hero2.png"));
    private static final Texture enemyImage  = new Texture(Gdx.files.internal("enemy2.png"));

    public GameManager(Game game) {
        this.game = game;
        gameScreen = new GameScreen(this);
        game.setScreen(gameScreen);
        prepareNewLevel();
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
    
    public Collection<Shape> getShapesAfterMove() {
        characterMover.moveEnemies(gameScreen.getDeltaTime());
        changeHeroDirections();
        characterMover.moveHero(gameScreen.getDeltaTime());
        adjustShapesPositionToMatchCharacters();
        return shapes.values();
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
        hero.speed = 200;
        characters.add(hero);
        for (int i = 0; i < levelNumber; i++) {
            var enemy = new Character(ENEMY);
            enemy.x = 0;
            enemy.y = 0;
            enemy.speed = 200;
            characters.add(enemy);
        }
        level = new Level(levelNumber, characters);
        initializeCharactersMap();
        characterMover = new CharacterMover(characters);
    }

    private void initializeCharactersMap() {
        shapes.clear();
        addHeroShapeForCurrentLevel();
        addEnemyShapesForCurrentLevel();
    }

    private void addHeroShapeForCurrentLevel() {
        var rectangle = new Rectangle(hero.x, hero.y, HERO_WIDTH, HERO_HEIGHT);
        shapes.put(hero, new Shape(heroImage, rectangle));
    }

    private void addEnemyShapesForCurrentLevel() {
        level.characters.stream().filter(c -> c.role == ENEMY).forEach(enemy -> {
            shapes.put(enemy, new Shape(enemyImage, new Rectangle(enemy.x, enemy.y, HERO_WIDTH, HERO_HEIGHT)));
        });
    }
}
