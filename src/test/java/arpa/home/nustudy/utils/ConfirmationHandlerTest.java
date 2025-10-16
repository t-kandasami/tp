package arpa.home.nustudy.utils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ConfirmationHandlerTest {
    @Test
    void firstLevelConfirmation() {
        assertTrue(checkFirstLevelInput("y"));
        assertTrue(checkFirstLevelInput("Y"));
        assertFalse(checkFirstLevelInput("n"));
        assertFalse(checkFirstLevelInput("N"));
    }

    public static boolean checkFirstLevelInput(String input) {
        if (input == null) {
            return false;
        }

        input = input.trim().toLowerCase();

        if ("y".equals(input)) {
            return true;
        }

        if ("n".equals(input)) {
            return false;
        }

        return false;
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

    public static boolean checkSecondLevelInput(final String input, final String safeword) {
        if (input == null || safeword == null) {
            return false;
        }

        return input.equals(safeword.toUpperCase());
    }
}

