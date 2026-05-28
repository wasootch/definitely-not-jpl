package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MovementExecutorTest {

    private SurfaceGrid grid;

    @BeforeEach
    void setUp() {
        grid = new SurfaceGrid();
    }

    private MovementExecutor executorFor(Rover rover) {
        grid.register(rover.getPosition());
        return new MovementExecutor(rover, grid);
    }

    @Test
    void forwardMovesNorthByOne() {
        Rover rover = new Rover("R1");
        assertTrue(executorFor(rover).executeCommands("F"));
        assertArrayEquals(new int[]{0, 1}, rover.getPosition());
    }

    @Test
    void backwardMovesSouthByOne() {
        Rover rover = new Rover("R1", new int[]{0, 3}, Direction.North);
        assertTrue(executorFor(rover).executeCommands("B"));
        assertArrayEquals(new int[]{0, 2}, rover.getPosition());
    }

    @Test
    void turnLeftChangesDirection() {
        Rover rover = new Rover("R1");
        assertTrue(executorFor(rover).executeCommands("L"));
        assertEquals(Direction.West, rover.getDirection());
    }

    @Test
    void turnRightChangesDirection() {
        Rover rover = new Rover("R1");
        assertTrue(executorFor(rover).executeCommands("R"));
        assertEquals(Direction.East, rover.getDirection());
    }

    @Test
    void ffrffEndsAtTwoTwoFacingEast() {
        Rover rover = new Rover("R1");
        assertTrue(executorFor(rover).executeCommands("FFRFF"));
        assertArrayEquals(new int[]{2, 2}, rover.getPosition());
        assertEquals(Direction.East, rover.getDirection());
    }

    @Test
    void movementBlockedAtNorthBoundary() {
        Rover rover = new Rover("R1", new int[]{0, 5}, Direction.North);
        assertFalse(executorFor(rover).executeCommands("F"));
        assertArrayEquals(new int[]{0, 5}, rover.getPosition());
    }

    @Test
    void movementBlockedAtSouthBoundary() {
        Rover rover = new Rover("R1", new int[]{0, 0}, Direction.South);
        assertFalse(executorFor(rover).executeCommands("F"));
        assertArrayEquals(new int[]{0, 0}, rover.getPosition());
    }

    @Test
    void movementBlockedByOccupiedCell() {
        Rover rover1 = new Rover("R1", new int[]{0, 1}, Direction.North);
        grid.register(rover1.getPosition());

        Rover rover2 = new Rover("R2", new int[]{0, 0}, Direction.North);
        MovementExecutor executor = new MovementExecutor(rover2, grid);
        grid.register(rover2.getPosition());

        assertFalse(executor.executeCommands("F"));
        assertArrayEquals(new int[]{0, 0}, rover2.getPosition());
    }

    @Test
    void invalidCommandThrows() {
        Rover rover = new Rover("R1");
        assertThrows(IllegalArgumentException.class,
                () -> executorFor(rover).executeCommands("FXF"));
    }
}
