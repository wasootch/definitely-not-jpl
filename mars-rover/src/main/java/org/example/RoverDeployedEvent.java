package org.example;

import java.util.Arrays;

public class RoverDeployedEvent extends RoverEvent {
    private final int[] position;

    public RoverDeployedEvent(String roverName, int[] position) {
        super(roverName);
        this.position = Arrays.copyOf(position, position.length);
    }

    public int[] getPosition() {
        return Arrays.copyOf(position, position.length);
    }

    @Override
    public String toString() {
        return String.format("[%s] %s deployed at %s", getTimestamp(), getRoverName(), Arrays.toString(position));
    }
}
