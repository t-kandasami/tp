package arpa.home.nustudy.utils;

import arpa.home.nustudy.exceptions.NUStudyException;

public class HourValidator {
    private static final int MAX_INT = 24;
    private static final int MIN_INT = 1;

    private static boolean isValidHours(final double hours) {
        return hours >= MIN_INT && hours <= MAX_INT && (hours * 2) % 1 == 0;
    }

    public static double parseHours(final String input) throws NUStudyException {
        double hours;
        try {
            hours = Double.parseDouble(input);
        } catch (NumberFormatException e) {
            throw new NUStudyException("Hours must be a double");
        }
        if (!isValidHours(hours)) {
            throw new NUStudyException("Hours must be between 1 and 24, in 0.5 increments.");
        }
        return hours;
    }
}
