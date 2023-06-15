package pl.ciborowski.konrad.viewmodel;

import static java.lang.Float.max;
import static java.lang.Float.min;
import static java.lang.Math.signum;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import static java.util.stream.Collectors.toList;
import pl.ciborowski.konrad.model.Character;
import pl.ciborowski.konrad.model.Direction;
import static pl.ciborowski.konrad.model.Direction.*;
import static pl.ciborowski.konrad.model.Role.*;
import static pl.ciborowski.konrad.view.GameScreen.*;
import pl.ciborowski.konrad.view.Shape;

public class CharacterMover {


    private Map<Character, Shape> shapes;
    
    public CharacterMover(Map<Character, Shape> shapes) {
        this.shapes = shapes;
    }

    public void moveEnemies(float deltaTime) {
        var hero = shapes.keySet().stream().filter(c -> c.role == HERO).findFirst().get();
        var enemies = shapes.keySet().stream().filter(c -> c.role == ENEMY).collect(toList());
        for (var enemy : enemies) {
            var xDistanceToHero = hero.x - enemy.x;
            var yDistanceToHero = hero.y - enemy.y;
            enemy.x += enemy.speed * deltaTime * signum(xDistanceToHero);
            enemy.y += enemy.speed * deltaTime * signum(yDistanceToHero);
        }
    }

    public List<Character> moveBulletsAndReturnBulletsOutOfBounds(float deltaTime) {
        List<Character> bulletsOutOfBounds = new LinkedList<>();
        var bullets = shapes.keySet().stream().filter(c -> c.role == BULLET).collect(toList());
        for (var bullet : bullets) {
            for (var direction : bullet.directions) {
                moveCharacter(bullet, direction, deltaTime);
                if (bullet.x < 0 || bullet.x > CAMERA_WIDTH 
                        || bullet.y < 0 || bullet.y > CAMERA_HEIGHT) {
                    bulletsOutOfBounds.add(bullet);
                } 
            }
        }
        return bulletsOutOfBounds;
    }

    public void moveHero(float deltaTime) {
        var hero = shapes.keySet().stream().filter(c -> c.role == HERO).findFirst().get();
        for (var direction : hero.directions) {
            moveCharacter(hero, direction, deltaTime);
            fixCharacterChoordinatesToStayInbounds(hero);
        }
    }

    private void fixCharacterChoordinatesToStayInbounds(Character character) {
        var characterWidth = character.role == ENEMY ? ENEMY_WIDTH : HERO_WIDTH;
        var characterHeight = character.role == ENEMY ? ENEMY_HEIGHT : HERO_HEIGHT;
        character.x = max(character.x, 0);
        character.x = min(character.x, CAMERA_WIDTH - characterWidth);
        character.y = max(character.y, 0);
        character.y = min(character.y, CAMERA_HEIGHT - characterHeight);
    }

    public void moveCharacter(Character character, Direction direction, float deltaTime) {
        switch (direction) {
            case NORTH -> {
                character.y += character.speed * deltaTime;
            }
            case SOUTH -> {
                character.y -= character.speed * deltaTime;
            }
            case EAST -> {
                character.x += character.speed * deltaTime;
            }
            case WEST -> {
                character.x -= character.speed * deltaTime;
            }
            default ->
                throw new AssertionError();
        }
    }
}
