package arpa.home.nustudy.utils;

import arpa.home.nustudy.ui.UserInterface;

public class ConfirmationHandler {
    /**
     * Handles user first confirmation prompts by persistently asking for a single 'y' or 'n' input. Handles lower (y/n)
     * or upper case (Y/N) inputs and trims whitespace.
     *
     * @param message The confirmation prompt to display for the user.
     *
     * @return true if 'y' is parsed, else false if 'n' is parsed.
     */
    public static boolean firstLevelConfirmation(final String message) {
        System.out.println(message + " (y/n)");

        while (true) {
            final String input = UserInterface.readInput().trim().toLowerCase();

            if (input.isEmpty()) {
                System.out.println("Please type 'y' or 'n': ");
                continue;
            }

            switch (input) {
            case "y":
                return true;
            case "n":
                return false;
            default:
                System.out.println("Please only type 'y' or 'n': ");
            }
        }
    }

    /**
     * Handles user second confirmation prompts by asking for a strict matching input with required safeword.
     *
     * @param safeword The safeword in all uppercase required for second confirmation.
     * @param action   The objective of double confirmation.
     *
     * @return true if {@code safeword} is parsed, else false.
     */
    public static boolean secondLevelConfirmation(final String safeword, final String action) {
        System.out.println("Please type \"" + safeword.toUpperCase() + "\" to double confirm " + action);

        final String input = UserInterface.readInput();

        if (input.equals(safeword.toUpperCase())) {
            System.out.println("Confirmation successful");
            return true;
        } else {
            System.out.println("Incorrect input, reset cancelled");
            return false;
        }
    }
}
