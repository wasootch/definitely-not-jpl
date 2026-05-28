package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MarsRoverIntegrationTest {

    private SquadCoordinator coordinator;

    @BeforeEach
    void setUp() {
        coordinator = new SquadCoordinator();
    }

    @Test
    void roverExecutesMultiStepMission() {
        Rover rover = new Rover("Curiosity");
        coordinator.deployRover(rover);
        coordinator.commandRover("Curiosity", "FFRFF");
        assertArrayEquals(new int[]{2, 2}, rover.getPosition());
        assertEquals(Direction.East, rover.getDirection());

        List<CommandRecord> history = coordinator.getHistory("Curiosity");
        assertEquals(1, history.size());
        assertEquals("FFRFF", history.get(0).getCommands());
        assertTrue(history.get(0).succeeded());
    }

    @Test
    void roverIsBlockedByGridBoundary() {
        Rover rover = new Rover("Spirit", new int[]{0, 4}, Direction.North);
        coordinator.deployRover(rover);
        coordinator.commandRover("Spirit", "FF");
        assertArrayEquals(new int[]{0, 5}, rover.getPosition());

        List<CommandRecord> history = coordinator.getHistory("Spirit");
        assertEquals(1, history.size());
        assertEquals("FF", history.get(0).getCommands());
        assertFalse(history.get(0).succeeded());
    }

    @Test
    void roverIsBlockedByOccupiedCell() {
        Rover blocker = new Rover("Blocker", new int[]{0, 1}, Direction.North);
        Rover mover   = new Rover("Mover",   new int[]{0, 0}, Direction.North);
        coordinator.deployRover(blocker);
        coordinator.deployRover(mover);

        coordinator.commandRover("Mover", "F");

        assertArrayEquals(new int[]{0, 0}, mover.getPosition());
    }

    @Test
    void twoRoversNavigateIndependentlyWithoutCollision() {
        Rover rover1 = new Rover("R1", new int[]{0, 0}, Direction.North);
        Rover rover2 = new Rover("R2", new int[]{3, 0}, Direction.East);
        coordinator.deployRover(rover1);
        coordinator.deployRover(rover2);

        coordinator.commandRover("R1", "FFRF");
        coordinator.commandRover("R2", "FFF");

        assertArrayEquals(new int[]{1, 2}, rover1.getPosition());
        assertEquals(Direction.East, rover1.getDirection());
        assertArrayEquals(new int[]{5, 0}, rover2.getPosition()); // blocked at boundary on 3rd move
    }

    @Test
    void roverCanNavigateAroundAnother() {
        Rover blocker = new Rover("Blocker", new int[]{0, 2}, Direction.North);
        Rover mover   = new Rover("Mover",   new int[]{0, 0}, Direction.North);
        coordinator.deployRover(blocker);
        coordinator.deployRover(mover);

        // go around: right, forward, left, forward twice
        coordinator.commandRover("Mover", "RFLFF");

        assertArrayEquals(new int[]{1, 2}, mover.getPosition());
        assertEquals(Direction.North, mover.getDirection());
    }

    @Test
    void roverCanReverseThroughMultipleSteps() {
        Rover rover = new Rover("Rover", new int[]{0, 5}, Direction.North);
        coordinator.deployRover(rover);
        coordinator.commandRover("Rover", "BBBBB");
        assertArrayEquals(new int[]{0, 0}, rover.getPosition());
    }

    @Test
    void roverMakesFullTurnAndReturnsToStart() {
        Rover rover = new Rover("Rover");
        coordinator.deployRover(rover);
        coordinator.commandRover("Rover", "FRRFF");
        assertArrayEquals(new int[]{0, 0}, rover.getPosition());
        assertEquals(Direction.South, rover.getDirection());
    }

    @Test
    void deployingRoverToOccupiedPositionThrows() {
        coordinator.deployRover(new Rover("R1", new int[]{2, 2}, Direction.North));
        assertThrows(RuntimeException.class,
                () -> coordinator.deployRover(new Rover("R2", new int[]{2, 2}, Direction.North)));
    }

    @Test
    void commandingRoverAfterBoundaryBlockContinuesNormally() {
        Rover rover = new Rover("Rover", new int[]{0, 4}, Direction.North);
        coordinator.deployRover(rover);
        coordinator.commandRover("Rover", "FF");   // one move succeeds, one is blocked
        coordinator.commandRover("Rover", "RF");   // turn east, move
        assertArrayEquals(new int[]{1, 5}, rover.getPosition());
        assertEquals(Direction.East, rover.getDirection());

        List<CommandRecord> history = coordinator.getHistory("Rover");
        assertEquals(2, history.size());
        assertFalse(history.get(0).succeeded());
        assertTrue(history.get(1).succeeded());
    }
}
