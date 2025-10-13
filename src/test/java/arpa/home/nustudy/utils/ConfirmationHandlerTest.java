package arpa.home.nustudy.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConfirmationHandlerTest {

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

    public static boolean checkSecondLevelInput(final String input, final String safeword) {
        if (input == null || safeword == null) {
            return false;
        }
        return input.equals(safeword.toUpperCase());
    }

    @Test
    void firstLevelConfirmation() {
        assertTrue(ConfirmationHandlerTest.checkFirstLevelInput("y"));
        assertTrue(ConfirmationHandlerTest.checkFirstLevelInput("Y"));
        assertFalse(ConfirmationHandlerTest.checkFirstLevelInput("n"));
        assertFalse(ConfirmationHandlerTest.checkFirstLevelInput("N"));
    }

    @Test
    void secondLevelConfirmation() {
        assertTrue(ConfirmationHandlerTest.checkSecondLevelInput("RESET", "RESET"));
        assertTrue(ConfirmationHandlerTest.checkSecondLevelInput("RESET ALL", "RESET ALL"));
        assertFalse(ConfirmationHandlerTest.checkSecondLevelInput("reset", "RESET ALL"));
        assertFalse(ConfirmationHandlerTest.checkSecondLevelInput("reset all", "RESET ALL"));
        assertFalse(ConfirmationHandlerTest.checkSecondLevelInput("null", "RESET"));
        assertFalse(ConfirmationHandlerTest.checkSecondLevelInput("null", "RESET ALL"));

    }
}
