package arpa.home.nustudy.utils;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import arpa.home.nustudy.command.Command;
import arpa.home.nustudy.command.EditCourseNameCommand;
import arpa.home.nustudy.command.EditSessionCommand;
import arpa.home.nustudy.exceptions.NUStudyCommandException;

class CommandParserTest {
    @Test
    void parseCommand_editCommandWithTwoArgument_returnsEditCourseNameCommand()
            throws NUStudyCommandException {
        final Command command = CommandParser.parseCommand("edit CS2113 CS2103");
        assertTrue(command instanceof EditCourseNameCommand,
                "Should return EditCourseNameCommand for valid edit command");
    }

    @Test
    void parseCommand_editCommandWithThreeArguments_returnsEditSessionCommand()
            throws NUStudyCommandException {
        Command command = CommandParser.parseCommand("edit CS2113 1 22/10/2025");
        assertTrue(command instanceof EditSessionCommand,
                "Should return EditSessionCommand for valid edit command");
    }

    @Test
    void parseCommand_editCommandWithEmptyArgument_throwsException()
            throws NUStudyCommandException {
        assertThrows(NUStudyCommandException.class, () -> CommandParser.parseCommand("edit"),
                "Should throw exception when empty argument");
    }

    @Test
    void parseCommand_editCommandWithOneArgument_throwsException()
            throws NUStudyCommandException {
        assertThrows(NUStudyCommandException.class, () -> CommandParser.parseCommand("edit cs2113"),
                "Should throw exception when only one argument");
    }

    @Test
    void parseCommand_editCommandWithFourArgument_throwsException()
            throws NUStudyCommandException {
        assertThrows(NUStudyCommandException.class, () -> CommandParser.parseCommand("edit cs2113 1 22/10/2025 1"),
                "Should throw exception more than three arguments");
    }
}
