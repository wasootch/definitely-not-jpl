package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CommandHistoryTest {

    private SquadCoordinator coordinator;

    @BeforeEach
    void setUp() {
        coordinator = new SquadCoordinator();
        coordinator.deployRover(new Rover("R1"));
    }

    @Test
    void historyIsEmptyAfterDeploy() {
        assertTrue(coordinator.getHistory("R1").isEmpty());
    }

    @Test
    void successfulCommandIsRecorded() {
        coordinator.commandRover("R1", "FF");
        List<CommandRecord> history = coordinator.getHistory("R1");
        assertEquals(1, history.size());
        assertEquals("FF", history.get(0).getCommands());
        assertEquals("R1", history.get(0).getRoverName());
        assertTrue(history.get(0).succeeded());
        assertNotNull(history.get(0).getTimestamp());
    }

    @Test
    void blockedCommandIsRecordedAsFailed() {
        coordinator.deployRover(new Rover("R1b", new int[]{0, 5}, Direction.North));
        coordinator.commandRover("R1b", "F");
        CommandRecord record = coordinator.getHistory("R1b").get(0);
        assertFalse(record.succeeded());
    }

    @Test
    void multipleCommandsAreRecordedInOrder() {
        coordinator.commandRover("R1", "F");
        coordinator.commandRover("R1", "R");
        coordinator.commandRover("R1", "F");
        List<CommandRecord> history = coordinator.getHistory("R1");
        assertEquals(3, history.size());
        assertEquals("F", history.get(0).getCommands());
        assertEquals("R", history.get(1).getCommands());
        assertEquals("F", history.get(2).getCommands());
    }

    @Test
    void historyIsCappedAtTenEntries() {
        for (int i = 0; i < 12; i++) {
            coordinator.commandRover("R1", "F");
        }
        assertEquals(10, coordinator.getHistory("R1").size());
    }

    @Test
    void oldestEntryIsDroppedWhenCapReached() {
        coordinator.commandRover("R1", "FF");
        for (int i = 0; i < 10; i++) {
            coordinator.commandRover("R1", "L");
        }
        List<CommandRecord> history = coordinator.getHistory("R1");
        assertEquals(10, history.size());
        assertEquals("L", history.get(0).getCommands());
    }

    @Test
    void historyIsUnmodifiable() {
        coordinator.commandRover("R1", "F");
        assertThrows(UnsupportedOperationException.class,
                () -> coordinator.getHistory("R1").clear());
    }

    @Test
    void getHistoryThrowsForUnknownRover() {
        assertThrows(IllegalArgumentException.class,
                () -> coordinator.getHistory("Ghost"));
    }

    @Test
    void toStringContainsKeyInfo() {
        coordinator.commandRover("R1", "FF");
        String record = coordinator.getHistory("R1").get(0).toString();
        assertTrue(record.contains("R1"));
        assertTrue(record.contains("FF"));
        assertTrue(record.contains("OK"));
    }
}
