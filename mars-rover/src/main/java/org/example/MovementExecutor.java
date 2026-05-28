package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class MovementExecutor {
    private static final Logger logger = LoggerFactory.getLogger(MovementExecutor.class);
    private static final int MAX_DELTA = 1;

    private final Rover rover;
    private final SurfaceGrid surfaceGrid;
    private final RoverEventPublisher publisher;

    public MovementExecutor(Rover rover, SurfaceGrid surfaceGrid) {
        this(rover, surfaceGrid, new RoverEventPublisher());
    }

    public MovementExecutor(Rover rover, SurfaceGrid surfaceGrid, RoverEventPublisher publisher) {
        this.rover = rover;
        this.surfaceGrid = surfaceGrid;
        this.publisher = publisher;
    }

    public boolean executeCommands(String commands) {
        CommandValidator.validateCommand(commands);

        boolean allSucceeded = true;
        for (char cmd : commands.toCharArray()) {
            switch (cmd) {
                case 'L':
                    rover.turnLeft();
                    break;
                case 'R':
                    rover.turnRight();
                    break;
                case 'F':
                    allSucceeded &= move(1);
                    break;
                case 'B':
                    allSucceeded &= move(-1);
                    break;
                default:
                    logger.warn("Unknown command: {}", cmd);
            }
        }

        logger.info(rover.getState());
        return allSucceeded;
    }

    private boolean move(int delta) {
        if (Math.abs(delta) > MAX_DELTA) {
            throw new IllegalArgumentException(String.format("Delta: %d exceeds max: %d", delta, MAX_DELTA));
        }

        int[] from = rover.getPosition();
        int newX = from[0], newY = from[1];
        switch (rover.getDirection()) {
            case North:
                newY += delta;
                break;
            case South:
                newY -= delta;
                break;
            case East:
                newX += delta;
                break;
            case West:
                newX -= delta;
                break;
        }

        Optional<int[]> dest = surfaceGrid.resolveDestination(newX, newY);
        if (dest.isPresent()) {
            rover.setPosition(dest.get()[0], dest.get()[1]);
            surfaceGrid.move(from, dest.get());
            publisher.publish(new RoverMovedEvent(rover.getName(), from, dest.get()));
            return true;
        } else {
            logger.warn("Invalid movement: [{}, {}] outside grid bounds or cell occupied.", newX, newY);
            publisher.publish(new RoverBlockedEvent(rover.getName(), from, new int[]{newX, newY}));
            return false;
        }
    }
}
