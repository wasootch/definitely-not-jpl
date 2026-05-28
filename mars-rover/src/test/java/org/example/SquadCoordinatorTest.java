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
        coordinator.commandRover("R1", "FF");
        assertArrayEquals(new int[]{0, 2}, rover.getPosition());
    }

    @Test
    void commandingUnknownRoverThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> coordinator.commandRover("Ghost", "F"));
    }

    @Test
    void multipleRoversCommandedIndependently() {
        Rover rover1 = new Rover("R1", new int[]{0, 0}, Direction.North);
        Rover rover2 = new Rover("R2", new int[]{3, 3}, Direction.East);
        coordinator.deployRover(rover1);
        coordinator.deployRover(rover2);

        coordinator.commandRover("R1", "FF");
        coordinator.commandRover("R2", "FF");

        assertArrayEquals(new int[]{0, 2}, rover1.getPosition());
        assertArrayEquals(new int[]{5, 3}, rover2.getPosition());
    }
}
