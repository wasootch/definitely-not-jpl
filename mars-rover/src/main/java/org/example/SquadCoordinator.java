package org.example;

import java.util.ArrayList;
import java.util.List;

public class SquadCoordinator {
    private final SurfaceGrid surfaceGrid;
    private final List<Rover> rovers = new ArrayList<>();

    public SquadCoordinator() {
        this(new SurfaceGrid());
    }

    public SquadCoordinator(SurfaceGrid surfaceGrid) {
        this.surfaceGrid = surfaceGrid;
    }

    public void deployRover(Rover rover) {
        if (!isPositionEmpty(rover.getPosition())) {
            throw new RuntimeException("Unable to deploy rover. Position occupied.");
        }

        this.surfaceGrid.register(rover.getPosition());
        rovers.add(rover);
    }

    public boolean commandRover(String name, String commands) {
        Rover rover = rovers.stream()
                .filter(r -> r.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Rover not found: " + name));

        MovementExecutor executor = new MovementExecutor(rover, surfaceGrid);
        return executor.executeCommands(commands);
    }

    public String getRoverState(String name) {
        return rovers.stream()
                .filter(r -> r.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Rover not found: " + name))
                .getState();
    }

    private boolean isPositionEmpty(int[] position) {
        return !surfaceGrid.isOccupied(position[0], position[1]);
    }
}
