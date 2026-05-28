package org.example;

public class CommandValidator {
    private static final String VALID_CHARS = "FBLR";

    public static void validateCommand(String commands) {
        for (char c : commands.toCharArray()) {
            if (VALID_CHARS.indexOf(c) == -1) {
                throw new IllegalArgumentException(String.format("Invalid command: %s in %s", c, commands));
            }
        }
    }
}
