package arpa.home.nustudy.utils;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import arpa.home.nustudy.exceptions.WrongDateFormatException;

class DateParserTest {

    @Test
    void parseDate_validYyyyMmDdFormat_success() throws WrongDateFormatException {
        LocalDate result = DateParser.parseDate("2025-10-26");
        assertEquals(LocalDate.of(2025, 10, 26), result,
                "Date should be parsed correctly from yyyy-MM-dd format");
    }

    @Test
    void parseDate_validSlashFormat_success() throws WrongDateFormatException {
        LocalDate result = DateParser.parseDate("26/10/2025");
        assertEquals(LocalDate.of(2025, 10, 26), result,
                "Date should be parsed correctly from d/M/yyyy format");
    }

    @Test
    void parseDate_validDashFormat_success() throws WrongDateFormatException {
        LocalDate result = DateParser.parseDate("26-10-2025");
        assertEquals(LocalDate.of(2025, 10, 26), result,
                "Date should be parsed correctly from d-M-yyyy format");
    }

    @Test
    void parseDate_singleDigitDayMonth_success() throws WrongDateFormatException {
        LocalDate result = DateParser.parseDate("1/2/2025");
        assertEquals(LocalDate.of(2025, 2, 1), result,
                "Date should handle single digit day and month");
    }

    @Test
    void parseDate_emptyString_throwsException() {
        assertThrows(WrongDateFormatException.class, () -> {
            DateParser.parseDate("");
        }, "Date parser should throw WrongDateFormatException for empty string");
    }

    @Test
    void parseDate_withLeadingTrailingSpaces_success() throws WrongDateFormatException {
        LocalDate result = DateParser.parseDate("  2025-10-26  ");
        assertEquals(LocalDate.of(2025, 10, 26), result,
                "Date parser should trim whitespace");
    }

    @Test
    void parseDate_invalidFormat_throwsException() {
        assertThrows(WrongDateFormatException.class, () -> {
            DateParser.parseDate("26 Oct 2025");
        }, "Should throw WrongDateFormatException for unrecognized format");
    }

    @Test
    void parseDate_invalidDate_throwsException() {
        assertThrows(WrongDateFormatException.class, () -> {
            DateParser.parseDate("32/13/2025");
        }, "Should throw WrongDateFormatException for invalid date values");
    }

    @Test
    void formatDate_validDate_returnsCorrectFormat() {
        LocalDate date = LocalDate.of(2025, 10, 26);
        String result = DateParser.formatDate(date);
        assertEquals("26 Oct 2025", result,
                "Date should be formatted as 'dd MMM yyyy'");
    }
}
