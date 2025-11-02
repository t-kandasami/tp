package arpa.home.nustudy.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import arpa.home.nustudy.exceptions.NUStudyException;

class HourValidatorTest {

    @Test
    void parseHours_validWholeNumber() throws NUStudyException {
        assertEquals(1.0, HourValidator.parseHours("1"));
        assertEquals(24.0, HourValidator.parseHours("24"));
    }

    @Test
    void parseHours_validHalfNumber() throws NUStudyException {
        assertEquals(1.5, HourValidator.parseHours("1.5"));
        assertEquals(12.5, HourValidator.parseHours("12.5"));
    }

    @Test
    void parseHours_invalidBelowMin() {
        NUStudyException exception = assertThrows(NUStudyException.class, () -> {
            HourValidator.parseHours("0");
        });
        assertEquals("Hours must be between 0.5 and 24, in 0.5 increments.", exception.getMessage());
    }

    @Test
    void parseHours_invalidAboveMax() {
        NUStudyException exception = assertThrows(NUStudyException.class, () -> {
            HourValidator.parseHours("24.5");
        });
        assertEquals("Hours must be between 0.5 and 24, in 0.5 increments.", exception.getMessage());
    }

    @Test
    void parseHours_invalidNonIncrement() {
        NUStudyException exception = assertThrows(NUStudyException.class, () -> {
            HourValidator.parseHours("2.3");
        });
        assertEquals("Hours must be between 0.5 and 24, in 0.5 increments.", exception.getMessage());
    }

    @Test
    void parseHours_invalidNonNumeric() {
        NUStudyException exception = assertThrows(NUStudyException.class, () -> {
            HourValidator.parseHours("abc");
        });
        assertEquals("Hours must be a double", exception.getMessage());
    }
}
