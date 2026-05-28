package org.example;

import java.util.Optional;

public class BlockingStrategy implements BoundaryStrategy {
    @Override
    public Optional<int[]> resolve(int x, int y, int minX, int minY, int maxX, int maxY) {
        return Optional.empty();
    }
}
