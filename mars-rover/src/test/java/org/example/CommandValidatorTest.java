package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CommandValidatorTest {

    @Test
    void validCommandsDoNotThrow() {
        assertDoesNotThrow(() -> CommandValidator.validateCommand("FBLR"));
        assertDoesNotThrow(() -> CommandValidator.validateCommand("F"));
        assertDoesNotThrow(() -> CommandValidator.validateCommand("FFRFF"));
    }

    @Test
    void invalidCharacterThrows() {
        assertThrows(IllegalArgumentException.class, () -> CommandValidator.validateCommand("FXF"));
    }

    @Test
    void lowercaseIsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> CommandValidator.validateCommand("fblr"));
    }

    @Test
    void emptyCommandDoesNotThrow() {
        assertDoesNotThrow(() -> CommandValidator.validateCommand(""));
    }

    @Test
    void exceptionMessageContainsInvalidChar() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> CommandValidator.validateCommand("FQB")
        );
        assertTrue(ex.getMessage().contains("Q"));
    }
}
