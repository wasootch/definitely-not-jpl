package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BoundaryStrategyTest {

    @Test
    void blockingStrategyAlwaysReturnsEmpty() {
        BoundaryStrategy strategy = new BlockingStrategy();
        assertTrue(strategy.resolve(6, 0, 0, 0, 5, 5).isEmpty());
        assertTrue(strategy.resolve(-1, 0, 0, 0, 5, 5).isEmpty());
        assertTrue(strategy.resolve(0, 6, 0, 0, 5, 5).isEmpty());
    }

    @Test
    void wraparoundWrapsXPastMax() {
        BoundaryStrategy strategy = new WraparoundStrategy();
        assertArrayEquals(new int[]{0, 0}, strategy.resolve(6, 0, 0, 0, 5, 5).orElseThrow());
    }

    @Test
    void wraparoundWrapsXBeforeMin() {
        BoundaryStrategy strategy = new WraparoundStrategy();
        assertArrayEquals(new int[]{5, 0}, strategy.resolve(-1, 0, 0, 0, 5, 5).orElseThrow());
    }

    @Test
    void wraparoundWrapsYPastMax() {
        BoundaryStrategy strategy = new WraparoundStrategy();
        assertArrayEquals(new int[]{0, 0}, strategy.resolve(0, 6, 0, 0, 5, 5).orElseThrow());
    }

    @Test
    void wraparoundWrapsYBeforeMin() {
        BoundaryStrategy strategy = new WraparoundStrategy();
        assertArrayEquals(new int[]{0, 5}, strategy.resolve(0, -1, 0, 0, 5, 5).orElseThrow());
    }

    @Test
    void wraparoundRespectsNonZeroMinBounds() {
        BoundaryStrategy strategy = new WraparoundStrategy();
        // grid 1-3 (width 3): x=4 should wrap to 1
        assertArrayEquals(new int[]{1, 2}, strategy.resolve(4, 2, 1, 1, 3, 3).orElseThrow());
    }
}
