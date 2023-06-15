package pl.ciborowski.konrad.viewmodel;

import java.util.List;
import static java.util.stream.Collectors.toList;
import pl.ciborowski.konrad.model.Character;
import pl.ciborowski.konrad.model.Direction;
import static pl.ciborowski.konrad.model.Role.*;

public class CharacterMover {

    public List<Character> enemies;
    public Character hero;

    public CharacterMover(List<Character> characters) {
        enemies = characters.stream().filter(c -> c.role == ENEMY).collect(toList());
        hero = characters.stream().filter(c -> c.role == HERO).findFirst().get();
    }

    public void moveEnemies() {
        for (var enemy : enemies) {
            for (var direction : Direction.values()) {
                direction.moveCharacter(enemy);
            }
        }
    }

    public void moveHero() {
        for (var direction : Direction.values()) {
            direction.moveCharacter(hero);
        }
    }
}
