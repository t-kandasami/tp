package arpa.home.nustudy.utils;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import arpa.home.nustudy.exceptions.WrongDateFormatException;

/**
 * Utility class for parsing and formatting dates.
 */
public class DateParser {

    private static final String[] DATE_PATTERNS = {
        "yyyy-MM-dd", "d/M/yyyy", "d-M-yyyy"
    };

    /**
     * Returns a LocalDate object parsed from a date string.
     * @param date The date string to parse
     * @return A LocalDate object representing the parsed date
     * @throws WrongDateFormatException If the date format is not recognized
     */
    public static LocalDate parseDate(String date) throws WrongDateFormatException {
        if (date == null || date.isEmpty()) {
            throw new WrongDateFormatException();
        }

        for (String pattern : DATE_PATTERNS) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

            try {
                return LocalDate.parse(date.trim(), formatter);
            } catch (DateTimeParseException ignored) {
                // Intentionally ignore and try the next pattern
            }
        }
        throw new WrongDateFormatException();

    }

    /**
     * Returns a human-readable representation of LocalDateTime.
     *
     * @param date The LocalDate to format
     *
     * @return A formatted date string in "dd MMM yyyy" format
     */
    public static String formatDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        return date.format(formatter);
    }
}
