package org.example;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class CommandRecordTest {

    @Test
    void storesRoverName() {
        assertEquals("Curiosity", new CommandRecord("Curiosity", "FF", true).getRoverName());
    }

    @Test
    void storesCommands() {
        assertEquals("FFRFF", new CommandRecord("R1", "FFRFF", true).getCommands());
    }

    @Test
    void storesSuccessResult() {
        assertTrue(new CommandRecord("R1", "F", true).succeeded());
    }

    @Test
    void storesFailureResult() {
        assertFalse(new CommandRecord("R1", "F", false).succeeded());
    }

    @Test
    void timestampIsSetAtCreation() {
        Instant before = Instant.now();
        CommandRecord record = new CommandRecord("R1", "F", true);
        Instant after = Instant.now();
        assertFalse(record.getTimestamp().isBefore(before));
        assertFalse(record.getTimestamp().isAfter(after));
    }

    @Test
    void toStringContainsAllFields() {
        CommandRecord record = new CommandRecord("R1", "FF", true);
        String s = record.toString();
        assertTrue(s.contains("R1"));
        assertTrue(s.contains("FF"));
        assertTrue(s.contains("OK"));
    }

    @Test
    void toStringShowsBlockedForFailure() {
        assertTrue(new CommandRecord("R1", "F", false).toString().contains("BLOCKED"));
    }
}
