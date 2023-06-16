package pl.ciborowski.konrad.viewmodel;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import pl.ciborowski.konrad.model.Character;
import static pl.ciborowski.konrad.model.Role.*;
import static pl.ciborowski.konrad.view.GameScreen.*;

public class CollisionsDetector {

    public List<Character> detectHitsAndReturnBulletsToRemove(Set<Character> characters) {
        List<Character> bulletsToRemove = new LinkedList<>();
        var hero = characters.stream().filter(c -> c.role == HERO).findFirst().get();
        characters.stream().filter(c -> c.role == ENEMY_BULLET).forEach(bullet -> {
            var correction = BULLET_RECTANGLE_SIZE / 2;
            var withinX = bullet.x + correction >= hero.x && bullet.x + correction <= hero.x + HERO_WIDTH;
            var withinY = bullet.y + correction >= hero.y && bullet.y + correction <= hero.y + HERO_HEIGHT;
            if (withinX && withinY) {
                hero.healthPoints -= 10;
                bulletsToRemove.add(bullet);
            }
        });
        characters.stream().filter(c -> c.role == HERO_BULLET).forEach(bullet -> {
            characters.stream().filter(c -> c.role == ENEMY).forEach(enemy -> {
                var correction = BULLET_RECTANGLE_SIZE / 2;
                var withinX = bullet.x + correction >= enemy.x && bullet.x + correction <= enemy.x + ENEMY_WIDTH;
                var withinY = bullet.y + correction >= enemy.y && bullet.y + correction <= enemy.y + ENEMY_WIDTH;
                if (withinX && withinY) {
                    enemy.healthPoints -= 10;
                    bulletsToRemove.add(bullet);
                }
            });
        });
        return bulletsToRemove;
    }

}