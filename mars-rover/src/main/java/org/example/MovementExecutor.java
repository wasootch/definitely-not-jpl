package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class MovementExecutor {
    private static final Logger logger = LoggerFactory.getLogger(MovementExecutor.class);
    private static final int MAX_DELTA = 1;

    private final Rover rover;
    private final SurfaceGrid surfaceGrid;

    public MovementExecutor(Rover rover, SurfaceGrid surfaceGrid) {
        this.rover = rover;
        this.surfaceGrid = surfaceGrid;
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

        int[] position = rover.getPosition();
        int newX = position[0], newY = position[1];
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
            surfaceGrid.move(position, dest.get());
            return true;
        } else {
            logger.warn("Invalid movement: [{}, {}] outside grid bounds or cell occupied.", newX, newY);
            return false;
        }
    }
}
