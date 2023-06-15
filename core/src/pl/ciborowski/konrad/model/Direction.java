package pl.ciborowski.konrad.model;

import static com.badlogic.gdx.Input.Keys.*;
import java.util.Set;

public enum Direction {

    NORTH(Set.of(W, UP)), SOUTH(Set.of(S, DOWN)), WEST(Set.of(A, LEFT)),
    EAST(Set.of(D, RIGHT));

    public Set<Integer> keyCodes;

    private Direction(Set<Integer> keyCodes) {
        this.keyCodes = keyCodes;
    }

    public void moveCharacter(Character character) {
        switch (this) {
            case NORTH -> {
                character.y += character.speed;
            }
            case SOUTH -> {
                character.y -= character.speed;
            }
            case EAST -> {
                character.x += character.speed;
            }
            case WEST -> {
                character.x -= character.speed;
            }
            default -> throw new AssertionError();
        }
    }

}
