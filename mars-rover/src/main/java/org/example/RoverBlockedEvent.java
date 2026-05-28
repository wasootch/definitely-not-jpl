package org.example;

import java.util.Arrays;

public class RoverBlockedEvent extends RoverEvent {
    private final int[] currentPosition;
    private final int[] attemptedPosition;

    public RoverBlockedEvent(String roverName, int[] currentPosition, int[] attemptedPosition) {
        super(roverName);
        this.currentPosition = Arrays.copyOf(currentPosition, currentPosition.length);
        this.attemptedPosition = Arrays.copyOf(attemptedPosition, attemptedPosition.length);
    }

    public int[] getCurrentPosition() {
        return Arrays.copyOf(currentPosition, currentPosition.length);
    }

    public int[] getAttemptedPosition() {
        return Arrays.copyOf(attemptedPosition, attemptedPosition.length);
    }

    @Override
    public String toString() {
        return String.format("[%s] %s blocked at %s attempting %s", getTimestamp(), getRoverName(),
                Arrays.toString(currentPosition), Arrays.toString(attemptedPosition));
    }
}
