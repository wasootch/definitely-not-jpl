package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RoverTest {

    @Test
    void defaultConstructorStartsAtOriginFacingNorth() {
        Rover rover = new Rover("R1");
        assertArrayEquals(new int[]{0, 0}, rover.getPosition());
        assertEquals(Direction.North, rover.getDirection());
    }

    @Test
    void customConstructorSetsPositionAndDirection() {
        Rover rover = new Rover("R1", new int[]{3, 4}, Direction.East);
        assertArrayEquals(new int[]{3, 4}, rover.getPosition());
        assertEquals(Direction.East, rover.getDirection());
    }

    @Test
    void getNameReturnsName() {
        assertEquals("Curiosity", new Rover("Curiosity").getName());
    }

    @Test
    void turnLeftUpdatesDirection() {
        Rover rover = new Rover("R1");
        rover.turnLeft();
        assertEquals(Direction.West, rover.getDirection());
    }

    @Test
    void turnRightUpdatesDirection() {
        Rover rover = new Rover("R1");
        rover.turnRight();
        assertEquals(Direction.East, rover.getDirection());
    }

    @Test
    void setPositionUpdatesCoordinates() {
        Rover rover = new Rover("R1");
        rover.setPosition(2, 3);
        assertArrayEquals(new int[]{2, 3}, rover.getPosition());
    }

    @Test
    void getPositionReturnsCopy() {
        Rover rover = new Rover("R1");
        int[] pos = rover.getPosition();
        pos[0] = 99;
        assertArrayEquals(new int[]{0, 0}, rover.getPosition());
    }

    @Test
    void getStateContainsNamePositionAndDirection() {
        Rover rover = new Rover("R1", new int[]{1, 2}, Direction.South);
        String state = rover.getState();
        assertTrue(state.contains("R1"));
        assertTrue(state.contains("1"));
        assertTrue(state.contains("2"));
        assertTrue(state.contains("South"));
    }
}
