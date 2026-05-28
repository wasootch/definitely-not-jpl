package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DirectionTest {

    @Test
    void turnRightCyclesClockwise() {
        assertEquals(Direction.East,  Direction.North.turnRight());
        assertEquals(Direction.South, Direction.East.turnRight());
        assertEquals(Direction.West,  Direction.South.turnRight());
        assertEquals(Direction.North, Direction.West.turnRight());
    }

    @Test
    void turnLeftCyclesCounterClockwise() {
        assertEquals(Direction.West,  Direction.North.turnLeft());
        assertEquals(Direction.North, Direction.East.turnLeft());
        assertEquals(Direction.East,  Direction.South.turnLeft());
        assertEquals(Direction.South, Direction.West.turnLeft());
    }

    @Test
    void fourRightTurnsReturnToStart() {
        Direction d = Direction.North;
        for (int i = 0; i < 4; i++) d = d.turnRight();
        assertEquals(Direction.North, d);
    }

    @Test
    void fourLeftTurnsReturnToStart() {
        Direction d = Direction.South;
        for (int i = 0; i < 4; i++) d = d.turnLeft();
        assertEquals(Direction.South, d);
    }
}
