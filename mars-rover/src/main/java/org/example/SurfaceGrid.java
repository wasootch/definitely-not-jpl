package org.example;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class SurfaceGrid {
    private final int minX;
    private final int minY;
    private final int maxX;
    private final int maxY;
    private final BoundaryStrategy boundaryStrategy;

    private final Set<String> occupied = new HashSet<>();

    public SurfaceGrid() {
        this(0, 0, 5, 5);
    }

    public SurfaceGrid(int minX, int minY, int maxX, int maxY) {
        this(minX, minY, maxX, maxY, new BlockingStrategy());
    }

    public SurfaceGrid(int minX, int minY, int maxX, int maxY, BoundaryStrategy boundaryStrategy) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
        this.boundaryStrategy = boundaryStrategy;
    }

    public void register(int[] position) {
        occupied.add(key(position[0], position[1]));
    }

    public void move(int[] from, int[] dest) {
        occupied.remove(key(from[0], from[1]));
        occupied.add(key(dest[0], dest[1]));
    }

    public Optional<int[]> resolveDestination(int x, int y) {
        int[] candidate;
        if (inBounds(x, y)) {
            candidate = new int[]{x, y};
        } else {
            Optional<int[]> resolved = boundaryStrategy.resolve(x, y, minX, minY, maxX, maxY);
            if (resolved.isEmpty()) return Optional.empty();
            candidate = resolved.get();
        }
        return isOccupied(candidate[0], candidate[1]) ? Optional.empty() : Optional.of(candidate);
    }

    public boolean isOccupied(int x, int y) {
        return occupied.contains(key(x, y));
    }

    public boolean inBounds(int x, int y) {
        return (x >= minX && x <= maxX) && (y >= minY && y <= maxY);
    }

    private String key(int x, int y) {
        return x + "," + y;
    }
}
