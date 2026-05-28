package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SquadCoordinatorTest {

    private SquadCoordinator coordinator;

    @BeforeEach
    void setUp() {
        coordinator = new SquadCoordinator();
    }

    @Test
    void deployRoverSucceeds() {
        assertDoesNotThrow(() -> coordinator.deployRover(new Rover("R1")));
    }

    @Test
    void deployingTwoRoversToSamePositionThrows() {
        coordinator.deployRover(new Rover("R1"));
        assertThrows(RuntimeException.class,
                () -> coordinator.deployRover(new Rover("R2")));
    }

    @Test
    void commandRoverMovesCorrectRover() {
        Rover rover = new Rover("R1");
        coordinator.deployRover(rover);
        assertTrue(coordinator.commandRover("R1", "FF"));
        assertArrayEquals(new int[]{0, 2}, rover.getPosition());
    }

    @Test
    void commandingUnknownRoverThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> coordinator.commandRover("Ghost", "F"));
    }

    @Test
    void commandRoverReturnsTrueWhenAllMovesSucceed() {
        coordinator.deployRover(new Rover("R1"));
        assertTrue(coordinator.commandRover("R1", "FF"));
    }

    @Test
    void commandRoverReturnsFalseWhenMoveIsBlocked() {
        coordinator.deployRover(new Rover("R1", new int[]{0, 4}, Direction.North));
        assertFalse(coordinator.commandRover("R1", "FF"));
    }

    @Test
    void getRoverStateContainsNameAndPosition() {
        coordinator.deployRover(new Rover("R1", new int[]{1, 2}, Direction.East));
        String state = coordinator.getRoverState("R1");
        assertTrue(state.contains("R1"));
        assertTrue(state.contains("East"));
    }

    @Test
    void getRoverStateThrowsForUnknownRover() {
        assertThrows(IllegalArgumentException.class,
                () -> coordinator.getRoverState("Ghost"));
    }

    @Test
    void customGridSizeIsRespected() {
        coordinator = new SquadCoordinator(new SurfaceGrid(0, 0, 2, 2));
        Rover rover = new Rover("R1");
        coordinator.deployRover(rover);
        assertFalse(coordinator.commandRover("R1", "FFF"));
        assertArrayEquals(new int[]{0, 2}, rover.getPosition());
    }

    @Test
    void multipleRoversCommandedIndependently() {
        Rover rover1 = new Rover("R1", new int[]{0, 0}, Direction.North);
        Rover rover2 = new Rover("R2", new int[]{3, 3}, Direction.East);
        coordinator.deployRover(rover1);
        coordinator.deployRover(rover2);

        assertTrue(coordinator.commandRover("R1", "FF"));
        assertTrue(coordinator.commandRover("R2", "FF"));

        assertArrayEquals(new int[]{0, 2}, rover1.getPosition());
        assertArrayEquals(new int[]{5, 3}, rover2.getPosition());
    }
}
