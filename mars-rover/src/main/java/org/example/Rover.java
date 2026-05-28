package org.example;

import java.util.Arrays;

public class Rover {
    private final String name;

    private int x;
    private int y;
    private Direction direction;

    public Rover(String name) {
        this(name, new int[2], Direction.North);
    }

    public Rover(String name, int[] position, Direction direction) {
        this.name = name;
        this.direction = direction;
        this.x = position[0];
        this.y = position[1];
    }

    public String getName() {
        return name;
    }

    public Direction getDirection() {
        return direction;
    }

    public void turnLeft() {
        direction = direction.turnLeft();
    }

    public void turnRight() {
        direction = direction.turnRight();
    }

    public int[] getPosition() {
        return new int[]{x, y};
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String getState() {
        return String.format("Rover: %s \nPosition: %s\nDirection: %s\n\n", name, Arrays.toString(getPosition()), direction);
    }

}