package org.example;

import java.util.HashSet;
import java.util.Set;

public class SurfaceGrid {
    private final int maxX;
    private final int maxY;
    private final int minX;
    private final int minY;

    private final Set<String> occupied = new HashSet<>();

    public SurfaceGrid() {
        this(0, 0, 5, 5);
    }

    public SurfaceGrid(int minX, int minY, int maxX, int maxY) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    public void register(int[] position) {
        occupied.add(key(position[0], position[1]));
    }

    public void move(int[] from, int[] dest) {
        occupied.remove(key(from[0], from[1]));
        occupied.add(key(dest[0], dest[1]));
    }

    public boolean validDestination(int x, int y) {
        return inBounds(x, y) && !isOccupied(x, y);
    }

    public boolean isOccupied(int x, int y) {
        return occupied.contains(key(x, y));
    }

    private String key(int x, int y) {
        return x + "," + y;
    }

    public boolean inBounds(int x, int y) {
        return (x >= minX && x <= maxX) && (y >= minY && y <= maxY);
    }
}
