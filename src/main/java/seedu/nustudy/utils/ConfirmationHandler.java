package seedu.nustudy.utils;

import seedu.nustudy.ui.UserInterface;

public class ConfirmationHandler {

    /**
     * Handles user confirmation prompts by persistently asking for a single 'y' or 'n' input.
     * Handles lower (y/n) or upper case (Y/N) inputs and trims whitespace.
     *
     * @param message The confirmation prompt to display for the user.
     * @return true if 'y' is parsed, else false if 'n' is parsed.
     */
    public static boolean getConfirmation(String message) {
        System.out.println(message + " (y/n)");

        while (true) {
            String input = UserInterface.readInput().trim().toLowerCase();
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
}
