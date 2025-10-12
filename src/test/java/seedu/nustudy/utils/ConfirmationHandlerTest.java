package seedu.nustudy.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ConfirmationHandlerTest {

    public static boolean checkFirstLevelInput(String input) {
        if (input == null) {
            return false;
        }

        input = input.trim().toLowerCase();
        if (input.equals("y")) {
            return true;
        }
        if (input.equals("n")) {
            return false;
        }

        return false;
    }

    @Test
    void firstLevelConfirmation() {
        assertTrue(checkFirstLevelInput("y"));
        assertTrue(checkFirstLevelInput("Y"));
        assertFalse(checkFirstLevelInput("n"));
        assertFalse(checkFirstLevelInput("N"));
    }

    public static boolean checkSecondLevelInput(String input, String safeword) {
        if (input == null || safeword == null) {
            return false;
        }
        return input.equals(safeword.toUpperCase());
    }

    @Test
    void secondLevelConfirmation() {
        assertTrue(checkSecondLevelInput("RESET", "RESET"));
        assertTrue(checkSecondLevelInput("RESET ALL", "RESET ALL"));
        assertFalse(checkSecondLevelInput("reset", "RESET ALL"));
        assertFalse(checkSecondLevelInput("reset all", "RESET ALL"));
        assertFalse(checkSecondLevelInput("null", "RESET"));
        assertFalse(checkSecondLevelInput("null", "RESET ALL"));

    }
}
