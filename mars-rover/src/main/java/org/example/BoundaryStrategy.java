package org.example;

import java.util.Optional;

public interface BoundaryStrategy {
    Optional<int[]> resolve(int x, int y, int minX, int minY, int maxX, int maxY);
}
