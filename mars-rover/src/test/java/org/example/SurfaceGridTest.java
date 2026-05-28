package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SurfaceGridTest {

    @Test
    void defaultConstructorCreatesFiveByFiveGrid() {
        SurfaceGrid grid = new SurfaceGrid();
        assertTrue(grid.inBounds(0, 0));
        assertTrue(grid.inBounds(5, 5));
        assertFalse(grid.inBounds(6, 0));
        assertFalse(grid.inBounds(0, 6));
    }

    @Test
    void inBoundsReturnsFalseOutsideCustomBounds() {
        SurfaceGrid grid = new SurfaceGrid(0, 0, 3, 3);
        assertTrue(grid.inBounds(3, 3));
        assertFalse(grid.inBounds(4, 0));
        assertFalse(grid.inBounds(-1, 0));
    }

    @Test
    void unregisteredPositionIsNotOccupied() {
        SurfaceGrid grid = new SurfaceGrid();
        assertFalse(grid.isOccupied(2, 2));
    }

    @Test
    void registeredPositionIsOccupied() {
        SurfaceGrid grid = new SurfaceGrid();
        grid.register(new int[]{2, 2});
        assertTrue(grid.isOccupied(2, 2));
    }

    @Test
    void moveFreesOldPositionAndOccupiesNew() {
        SurfaceGrid grid = new SurfaceGrid();
        int[] pos = new int[]{1, 1};
        grid.register(pos);
        grid.move(pos, new int[]{1, 2});
        assertFalse(grid.isOccupied(1, 1));
        assertTrue(grid.isOccupied(1, 2));
    }

    @Test
    void resolveDestinationReturnsPositionForEmptyInBoundsCell() {
        SurfaceGrid grid = new SurfaceGrid();
        assertTrue(grid.resolveDestination(3, 3).isPresent());
    }

    @Test
    void resolveDestinationEmptyWhenOutOfBoundsWithBlockingStrategy() {
        SurfaceGrid grid = new SurfaceGrid();
        assertTrue(grid.resolveDestination(6, 0).isEmpty());
    }

    @Test
    void resolveDestinationEmptyForOccupiedCell() {
        SurfaceGrid grid = new SurfaceGrid();
        grid.register(new int[]{2, 2});
        assertTrue(grid.resolveDestination(2, 2).isEmpty());
    }

    @Test
    void resolveDestinationWrapsXPastMax() {
        SurfaceGrid grid = new SurfaceGrid(0, 0, 5, 5, new WraparoundStrategy());
        int[] resolved = grid.resolveDestination(6, 0).orElseThrow();
        assertArrayEquals(new int[]{0, 0}, resolved);
    }

    @Test
    void resolveDestinationWrapsXBeforeMin() {
        SurfaceGrid grid = new SurfaceGrid(0, 0, 5, 5, new WraparoundStrategy());
        int[] resolved = grid.resolveDestination(-1, 0).orElseThrow();
        assertArrayEquals(new int[]{5, 0}, resolved);
    }

    @Test
    void resolveDestinationWrapsYPastMax() {
        SurfaceGrid grid = new SurfaceGrid(0, 0, 5, 5, new WraparoundStrategy());
        int[] resolved = grid.resolveDestination(0, 6).orElseThrow();
        assertArrayEquals(new int[]{0, 0}, resolved);
    }

    @Test
    void resolveDestinationWrapsYBeforeMin() {
        SurfaceGrid grid = new SurfaceGrid(0, 0, 5, 5, new WraparoundStrategy());
        int[] resolved = grid.resolveDestination(0, -1).orElseThrow();
        assertArrayEquals(new int[]{0, 5}, resolved);
    }

    @Test
    void resolveDestinationEmptyWhenWrappedCellIsOccupied() {
        SurfaceGrid grid = new SurfaceGrid(0, 0, 5, 5, new WraparoundStrategy());
        grid.register(new int[]{0, 0});
        assertTrue(grid.resolveDestination(6, 0).isEmpty());
    }
}
