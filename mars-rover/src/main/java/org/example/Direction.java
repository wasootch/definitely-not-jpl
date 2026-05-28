package org.example;

public enum Direction {
    North, East, South, West; // Clockwise order

    private static final Direction[] VALUES = values();

    public Direction turnRight() {
        return VALUES[(this.ordinal() + 1) % VALUES.length];
    }

    public Direction turnLeft() {
        // Adding VALUES.length prevents negative modulo results in Java
        return VALUES[(this.ordinal() + VALUES.length - 1) % VALUES.length];
    }
}