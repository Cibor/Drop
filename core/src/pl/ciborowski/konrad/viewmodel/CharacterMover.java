package pl.ciborowski.konrad.viewmodel;

import static java.lang.Float.max;
import static java.lang.Float.min;
import java.util.List;
import static java.util.stream.Collectors.toList;
import pl.ciborowski.konrad.model.Character;
import pl.ciborowski.konrad.model.Direction;
import static pl.ciborowski.konrad.model.Direction.*;
import static pl.ciborowski.konrad.model.Role.*;
import static pl.ciborowski.konrad.view.GameScreen.*;

public class CharacterMover {

    public List<Character> enemies;
    public Character hero;

    public CharacterMover(List<Character> characters) {
        enemies = characters.stream().filter(c -> c.role == ENEMY).collect(toList());
        hero = characters.stream().filter(c -> c.role == HERO).findFirst().get();
        
    }

    public void moveEnemies(float deltaTime) {
        for (var enemy : enemies) {
            for (var direction : enemy.directions) {
                moveCharacter(enemy, direction, deltaTime);
                fixCharacterChoordinatesToStayInbounds(enemy);
            }
        }
    }

    public void moveHero(float deltaTime) {
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
            default -> throw new AssertionError();
        }
    }
}
