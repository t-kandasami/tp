package arpa.home.nustudy.utils;

import arpa.home.nustudy.exceptions.NUStudyCommandException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ParserTest {

    @Test
    void parseCommand() {
        final String input = "Hello";
        assertThrows(NUStudyCommandException.class, () -> Parser.parseCommand(input));
    }
}
