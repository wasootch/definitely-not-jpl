package org.example;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SquadCoordinator {
    private static final int MAX_HISTORY = 10;
    
    private final SurfaceGrid surfaceGrid;
    private final RoverEventPublisher publisher = new RoverEventPublisher();
    private final List<Rover> rovers = new ArrayList<>();
    private final Map<String, Deque<CommandRecord>> history = new HashMap<>();

    public SquadCoordinator() {
        this(new SurfaceGrid());
    }

    public SquadCoordinator(SurfaceGrid surfaceGrid) {
        this.surfaceGrid = surfaceGrid;
    }

    public void subscribe(RoverEventListener listener) {
        publisher.subscribe(listener);
    }

    public void deployRover(Rover rover) {
        if (!isPositionEmpty(rover.getPosition())) {
            throw new RuntimeException("Unable to deploy rover. Position occupied.");
        }

        this.surfaceGrid.register(rover.getPosition());
        rovers.add(rover);
        history.put(rover.getName(), new ArrayDeque<>());
        publisher.publish(new RoverDeployedEvent(rover.getName(), rover.getPosition()));
    }

    public boolean commandRover(String name, String commands) {
        Rover rover = rovers.stream()
                .filter(r -> r.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Rover not found: " + name));

        MovementExecutor executor = new MovementExecutor(rover, surfaceGrid, publisher);
        boolean result = executor.executeCommands(commands);

        Deque<CommandRecord> roverHistory = history.get(name);
        if (roverHistory.size() == MAX_HISTORY) {
            roverHistory.pollFirst();
        }
        roverHistory.addLast(new CommandRecord(name, commands, result));

        return result;
    }

    public List<CommandRecord> getHistory(String name) {
        if (!history.containsKey(name)) {
            throw new IllegalArgumentException("Rover not found: " + name);
        }
        return Collections.unmodifiableList(new ArrayList<>(history.get(name)));
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
