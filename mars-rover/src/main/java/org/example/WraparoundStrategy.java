package org.example;

import java.util.Optional;

public class WraparoundStrategy implements BoundaryStrategy {
    @Override
    public Optional<int[]> resolve(int x, int y, int minX, int minY, int maxX, int maxY) {
        int width  = maxX - minX + 1;
        int height = maxY - minY + 1;
        int wrappedX = ((x - minX) % width  + width)  % width  + minX;
        int wrappedY = ((y - minY) % height + height) % height + minY;
        return Optional.of(new int[]{wrappedX, wrappedY});
    }
}
