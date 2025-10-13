package arpa.home.nustudy.utils;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import arpa.home.nustudy.exceptions.NUStudyCommandException;

class ParserTest {
    @Test
    void parseCommand() {
        final String input = "Hello";
        assertThrows(NUStudyCommandException.class, () -> Parser.parseCommand(input));
    }
}
