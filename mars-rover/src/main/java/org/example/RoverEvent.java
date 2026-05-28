package org.example;

import java.time.Instant;

public abstract class RoverEvent {
    private final String roverName;
    private final Instant timestamp;

    protected RoverEvent(String roverName) {
        this.roverName = roverName;
        this.timestamp = Instant.now();
    }

    public String getRoverName() {
        return roverName;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}
