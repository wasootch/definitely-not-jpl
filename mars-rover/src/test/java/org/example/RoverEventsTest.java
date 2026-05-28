package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoverEventsTest {

    @Test
    void deployedEventStoresNameAndPosition() {
        RoverDeployedEvent event = new RoverDeployedEvent("Curiosity", new int[]{1, 2});
        assertEquals("Curiosity", event.getRoverName());
        assertArrayEquals(new int[]{1, 2}, event.getPosition());
        assertNotNull(event.getTimestamp());
    }

    @Test
    void deployedEventPositionIsCopied() {
        int[] pos = new int[]{1, 2};
        RoverDeployedEvent event = new RoverDeployedEvent("R1", pos);
        pos[0] = 99;
        assertArrayEquals(new int[]{1, 2}, event.getPosition());
    }

    @Test
    void movedEventStoresFromAndTo() {
        RoverMovedEvent event = new RoverMovedEvent("R1", new int[]{0, 0}, new int[]{0, 1});
        assertArrayEquals(new int[]{0, 0}, event.getFrom());
        assertArrayEquals(new int[]{0, 1}, event.getTo());
    }

    @Test
    void blockedEventStoresCurrentAndAttempted() {
        RoverBlockedEvent event = new RoverBlockedEvent("R1", new int[]{0, 5}, new int[]{0, 6});
        assertArrayEquals(new int[]{0, 5}, event.getCurrentPosition());
        assertArrayEquals(new int[]{0, 6}, event.getAttemptedPosition());
    }

    @Test
    void deployedEventToStringContainsKeyInfo() {
        String s = new RoverDeployedEvent("R1", new int[]{1, 2}).toString();
        assertTrue(s.contains("R1"));
        assertTrue(s.contains("1"));
        assertTrue(s.contains("2"));
    }

    @Test
    void movedEventToStringContainsKeyInfo() {
        String s = new RoverMovedEvent("R1", new int[]{0, 0}, new int[]{0, 1}).toString();
        assertTrue(s.contains("R1"));
        assertTrue(s.contains("moved"));
    }

    @Test
    void blockedEventToStringContainsKeyInfo() {
        String s = new RoverBlockedEvent("R1", new int[]{0, 5}, new int[]{0, 6}).toString();
        assertTrue(s.contains("R1"));
        assertTrue(s.contains("blocked"));
    }
}
