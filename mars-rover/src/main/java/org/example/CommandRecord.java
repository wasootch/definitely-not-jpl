package org.example;

import java.time.Instant;

public class CommandRecord {
    private final String roverName;
    private final String commands;
    private final boolean succeeded;
    private final Instant timestamp;

    public CommandRecord(String roverName, String commands, boolean succeeded) {
        this.roverName = roverName;
        this.commands = commands;
        this.succeeded = succeeded;
        this.timestamp = Instant.now();
    }

    public String getRoverName() { return roverName; }
    public String getCommands()  { return commands; }
    public boolean succeeded()   { return succeeded; }
    public Instant getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        return String.format("[%s] %s \"%s\" -> %s", timestamp, roverName, commands, succeeded ? "OK" : "BLOCKED");
    }
}
