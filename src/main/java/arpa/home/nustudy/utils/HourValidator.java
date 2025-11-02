package arpa.home.nustudy.utils;

import arpa.home.nustudy.exceptions.NUStudyCommandException;

public class HourValidator {
    private static final int MAX = 24;
    private static final double MIN = 0.5;

    private static boolean isValidHours(final double hours) {
        return hours >= MIN && hours <= MAX && (hours * 2) % 1 == 0;
    }

    public static double parseHours(final String input) throws NUStudyCommandException {
        double hours;
        try {
            hours = Double.parseDouble(input);
        } catch (NumberFormatException e) {
            throw new NUStudyCommandException("Hours must be a double");
        }
        if (!isValidHours(hours)) {
            throw new NUStudyCommandException("Hours must be between 0.5 and 24, in 0.5 increments.");
        }
        return hours;
    }
}
