package seedu.nustudy.utils;

import org.junit.jupiter.api.Test;
import seedu.nustudy.exceptions.NUStudyCommandException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ParserTest {

    @Test
    void parseCommand() {
        String input = "Hello";
        assertThrows(NUStudyCommandException.class, () -> Parser.parseCommand(input));
    }
}
