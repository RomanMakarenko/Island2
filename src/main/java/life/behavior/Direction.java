package life.behavior;

import java.util.Random;

public enum Direction {
    UP,
    RIGHT,
    DOWN,
    LEFT;

    public static Direction getRandomDirection() {
        Direction[] values = Direction.values();
        int index = new Random().nextInt(values.length);
        return values[index];
    }
}
