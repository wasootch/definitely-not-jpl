package org.example;

import java.util.Arrays;

public class RoverMovedEvent extends RoverEvent {
    private final int[] from;
    private final int[] to;

    public RoverMovedEvent(String roverName, int[] from, int[] to) {
        super(roverName);
        this.from = Arrays.copyOf(from, from.length);
        this.to = Arrays.copyOf(to, to.length);
    }

    public int[] getFrom() {
        return Arrays.copyOf(from, from.length);
    }

    public int[] getTo() {
        return Arrays.copyOf(to, to.length);
    }

    @Override
    public String toString() {
        return String.format("[%s] %s moved %s -> %s", getTimestamp(), getRoverName(), Arrays.toString(from), Arrays.toString(to));
    }
}
